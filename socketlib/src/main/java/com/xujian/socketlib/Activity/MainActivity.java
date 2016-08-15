package com.xujian.socketlib.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xujian.socketlib.Protocol.Protocol;
import com.xujian.socketlib.Protocol.WillProtocol;
import com.xujian.socketlib.R;
import com.xujian.socketlib.Interface.SocketManager;
import com.xujian.socketlib.SocketManagerlmpl;
import com.xujian.socketlib.CallBack.TcpSocketCallback;
import com.xujian.socketlib.CallBack.TcpSocketConnectorCallback;
import com.xujian.socketlib.Utils.DebugLogs;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editText, editText2;
    private Button Start, Stop,Reconnection;
    private TextView str;

    //    private String host = "192.168.200.240";//"47.90.1.87";
//    private int port = 8080;//6600;
    private String host = "47.90.1.87";
    private int port = 6600;
    private SocketManager socketManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        str = (TextView) findViewById(R.id.str);
        Start = (Button) findViewById(R.id.Start);
        Stop = (Button) findViewById(R.id.stop);
        Reconnection = (Button) findViewById(R.id.Reconnection);
        Start.setOnClickListener(this);
        Stop.setOnClickListener(this);
        Reconnection.setOnClickListener(this);

        socketManager = new SocketManagerlmpl();
        socketManager.startSocket(host, port, connectorCallback, tcpSocketCallback);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Start://发送数据包
                String jsonString = "{\"barid\":2007,\"password\":\"\",\"token\":\"3EF2AEC00FF73B05F28372CBFAE3B95E\",\"userid\":51481}";
                byte[] bytes = WillProtocol.sendMessage(10001, jsonString);
                socketManager.sendMessage(bytes);
                break;
            case R.id.stop://停止socket连接
                socketManager.stopSocket();
                break;
            case R.id.Reconnection://重新连接
                socketManager.Reconnection();
                break;
        }
    }

    private TcpSocketConnectorCallback connectorCallback = new TcpSocketConnectorCallback() {
        @Override
        public void retryOverlimit(int connectTime) {
            DebugLogs.d("------retryOverlimit--------->" + connectTime);
        }

        @Override
        public void connectFaild() {
            DebugLogs.d("------connectFaild--------->");
        }

        @Override
        public void connectSuc() {
            DebugLogs.d("------connectSuc--------->");
            String jsonString = "{\"barid\":2007,\"password\":\"\",\"token\":\"3EF2AEC00FF73B05F28372CBFAE3B95E\",\"userid\":51481}";
            byte[] bytes = WillProtocol.sendMessage(10001, jsonString);
            socketManager.sendMessage(bytes);
        }
    };

    private TcpSocketCallback tcpSocketCallback = new TcpSocketCallback() {

        @Override
        public void onReceiveParcel(Protocol protocol, byte[] pack) {
            if (pack == null) {
                return;
            }
            byte[] datas = protocol.getData(pack);//获得服务器返回的数据
            int type = protocol.getType(pack);//获得服务器状态码
            final String data = new String(datas).trim();
            DebugLogs.d("-------收到的包" + type);
            DebugLogs.d("-------收到的内容" + data);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    str.setText(String.format("数据结果:%s", data));
                }
            });
        }

        @Override
        public void onLostConnect() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    str.setText(String.format("服务器状态:onLostConnect"));
                }
            });
        }

        @Override
        public void onReadTaskFinish() {
            DebugLogs.d("------onReadTaskFinish--------->");
        }
    };
}
