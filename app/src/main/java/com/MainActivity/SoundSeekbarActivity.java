package com.MainActivity;

import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.willprojeck.okhttp.okhttp_text.R;
import com.xj.utils.mediaPlayer.AudioUtils.Audio;
import com.xj.utils.mediaPlayer.AudioUtils.MediaUtils;
import com.xj.utils.mediaPlayer.SeekBarListener;
import com.xj.utils.utils.DebugLogs;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



/**
 * Created by xujian on 16/2/20.
 * 调节音量控制器
 */
public class SoundSeekbarActivity extends BinderActivity implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, AdapterView.OnItemClickListener, Runnable, View.OnClickListener {
    protected static final int SEARCH_MUSIC_SUCCESS = 0;// 搜索成功标记
    private ImageView btn_music_play, btn_music_prev, btn_music_next, btn_music_stop;
    private SeekBar seekBar;
    private ProgressBar psBar;
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private TextView volumeView, music_title, music_next;
    private ListView music_list;
    private Button btn;
    private ProgressDialog pd;

    // 定义当前播放器的状态
    private static final int IDLE = 0;
    private static final int PAUSE = 1;
    private static final int START = 2;
    private static final int CURR_TIME_VALUE = 1;

    private int currIndex = 0;// 表示当前播放的音乐索引
    private int currState = IDLE; // 当前播放器的状态
    private boolean flag = true;//控制进度条线程标记
    // 定义线程池（同时只能有一个线程运行）
    private ExecutorService es = Executors.newSingleThreadExecutor();
    private boolean iffirst = false;
    private Timer mTimer;
    private TimerTask mTimerTask;
    private boolean isChanging=false;//互斥变量，防止定时器与SeekBar拖动时进度冲突
    private List<Audio> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp3);
        initView();         //界面初始化
        SoundController();  //控制音量调节
        musicController();  //音乐控制器
    }

    private void initView() {
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        btn = (Button) findViewById(R.id.btn);
        volumeView = (TextView) findViewById(R.id.volumeView);
        music_title = (TextView) findViewById(R.id.music_title);
        music_next = (TextView) findViewById(R.id.music_next);
        music_list = (ListView) findViewById(R.id.music_list);
        btn_music_play = (ImageView) findViewById(R.id.btn_music_play);
        btn_music_prev = (ImageView) findViewById(R.id.btn_music_prev);
        btn_music_next = (ImageView) findViewById(R.id.btn_music_next);
        btn_music_stop = (ImageView) findViewById(R.id.btn_music_stop);
        psBar = (ProgressBar) findViewById(R.id.psBar);
        music_list.setOnItemClickListener(SoundSeekbarActivity.this);
        btn.setOnClickListener(this);
        btn_music_play.setOnClickListener(this);
        btn_music_prev.setOnClickListener(this);
        btn_music_next.setOnClickListener(this);
        btn_music_stop.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        currIndex = position;
        start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                SearchMusic();
                break;
            case R.id.btn_music_play://播放
                play(btn_music_play);
                break;
            case R.id.btn_music_prev://上一首
                previous(btn_music_prev);
                break;
            case R.id.btn_music_next://下一首
                next(btn_music_next);
                break;
            case R.id.btn_music_stop://停止
                start();
                break;
        }
    }


    class MusicListAdapter extends BaseAdapter {

        public int getCount() {
            return list.size();
        }

        public Object getItem(int position) {
            return list.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, null);
            }
            TextView tv_music_name = (TextView) convertView.findViewById(android.R.id.text1);
            tv_music_name.setText(list.get(position).getDisplayName());
            return convertView;
        }

    }

    private void play() {
        switch (currState) {
            case IDLE:
                start();
                break;
            case PAUSE:
                mediaPlayer.pause();
                btn_music_play.setImageResource(R.drawable.btn_room_background_music_play);
                currState = START;
                break;
            case START:
                mediaPlayer.start();
                btn_music_play.setImageResource(R.drawable.btn_room_background_music_suspended);
                currState = PAUSE;
        }
    }

    //上一首
    private void previous() {
        if ((currIndex - 1) >= 0) {
            currIndex--;
            start();
        } else {
            Toast.makeText(this, "当前已经是第一首歌曲了", Toast.LENGTH_SHORT).show();
        }
    }

    //下一自首
    private void next() {
        if (currIndex + 1 < list.size()) {
            currIndex++;
            start();
        } else {
            Toast.makeText(this, "当前已经是最后一首歌曲了", Toast.LENGTH_SHORT).show();
        }
    }

    //开始播放
    private void start() {
        if (list.size() > 0 && currIndex < list.size()) {
            String SongPath = list.get(currIndex).getPath();
            mediaPlayer.reset();
            try {
                mediaPlayer.setDataSource(SongPath);
                mediaPlayer.prepare();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });
                initSeekBar();
//                es.execute(this);
                music_title.setText(list.get(currIndex).getDisplayName().split(".mp3")[0]);
                if (currIndex + 1 < list.size()) {
                    music_next.setText(list.get(currIndex + 1).getDisplayName().split(".mp3")[0]);
                } else {
                    music_next.setText("没有下一首歌曲了");
                }
                btn_music_play.setImageResource(R.drawable.btn_room_background_music_suspended);
                currState = PAUSE;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "播放列表为空", Toast.LENGTH_SHORT).show();
        }
    }

    private void musicController() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(SoundSeekbarActivity.this);
        mediaPlayer.setOnErrorListener(SoundSeekbarActivity.this);
    }

    private void musicStop() {
        if (mediaPlayer != null) {
            flag= false;
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    //播放按钮
    public void play(View v) {
        play();
    }

    //上一首按钮
    public void previous(View v) {
        previous();
    }

    //下一首按钮
    public void next(View v) {
        next();
    }

//    //监听器，当当前歌曲播放完时触发，播放下一首
    public void onCompletion(MediaPlayer mp) {
        if (list.size() > 0) {
            next();
        } else {
            Toast.makeText(this, "播放列表为空", Toast.LENGTH_SHORT).show();
        }
    }

    //当播放异常时触发
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mp.reset();
        return false;
    }

    //初始化SeekBar
    private void initSeekBar() {
        psBar.setMax(mediaPlayer.getDuration());
        psBar.setProgress(0);
        //----------定时器记录播放进度---------//
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                psBar.setProgress(mediaPlayer.getCurrentPosition());
            }
        };
//        DebugLogs.e("--获取文件播放时长-->"+toTime(mediaPlayer.getDuration()));
    }

    //将音乐文件时长显示出来
    private String toTime(int time) {
        int minute = time / 1000 / 60;
        int s = time / 1000 % 60;
        String mm, ss;
        if (minute < 10) mm = "0" + minute;
        else mm = minute + "";
        if (s < 10) ss = "0" + s;
        else ss = "" + s;
        return mm + ":" + ss;
    }

    @Override
    public void run() {
        flag = true;
        while (flag) {
            if (mediaPlayer.getCurrentPosition() < psBar.getMax()) {
                psBar.setProgress(mediaPlayer.getCurrentPosition());
//                DebugLogs.e("播放时长"+toTime(mediaPlayer.getCurrentPosition())) ;
            } else {
                flag = false;
            }
        }
    }

    /**
     * 音乐搜索，
     */
    private void SearchMusic() {
        pd = ProgressDialog.show(SoundSeekbarActivity.this, "", "正在搜索音乐文件...", true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                DebugLogs.d("扫描到的歌曲---》" + MediaUtils.getAudioList(SoundSeekbarActivity.this));
                list = MediaUtils.getAudioList(SoundSeekbarActivity.this);
                if (list == null) {
                    return;
                }
                handler.sendEmptyMessage(SEARCH_MUSIC_SUCCESS);
            }
        }).start();
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SEARCH_MUSIC_SUCCESS://搜索音乐结束
                    if (pd != null) {
                        pd.dismiss();
                    }
                    music_list.setAdapter(new MusicListAdapter());
                    break;
                case CURR_TIME_VALUE:
                    break;
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        musicStop();
    }


    /**
     * 控制系统的音量
     */
    private void SoundController() {
        //获取音量服务
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        //获取系统音量最大值
        int MaxSound = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        //音量控制Bar的最大值设置为系统音量最大值
        seekBar.setMax(MaxSound);
        //获取当前音量
        int currentSount = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        float num = ((float) currentSount / MaxSound) * 100;
        DecimalFormat df = new DecimalFormat("0");//格式化小数
        String s = df.format(num) + "%";//返回的是String类型
        volumeView.setText(s);
        //音量控制Bar的当前值设置为系统音量当前值
        seekBar.setProgress(currentSount);
        seekBar.setOnSeekBarChangeListener(new SeekBarListener(audioManager, volumeView));
    }

}
