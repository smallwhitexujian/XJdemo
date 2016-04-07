package com.example.MainActivity.MarterialTransition;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Scene;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;

import com.willprojeck.okhttp.okhttp_text.R;

/**
 * Created by:      xujian
 * Version          ${version}
 * Date:            16/4/6
 * Description(描述):
 * Modification  History(历史修改):
 * Date              Author          Version
 * ---------------------------------------------------------
 * 16/4/6          xujian         ${version}
 * Why & What is modified(修改原因):
 */
public class TransitionActivity extends AppCompatActivity {
    private TransitionManager mTransitionManager;
    private Scene mScene1;
    private Scene mScene2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new TransitionFragment())
                    .commit();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ViewGroup container = (ViewGroup) findViewById(R.id.container);
            TransitionInflater transitionInflater = TransitionInflater.from(this);
            mTransitionManager = transitionInflater.inflateTransitionManager(R.transition.transition_manager, container);
            mScene1 = Scene.getSceneForLayout(container, R.layout.fragment_transition_scene_1, this);
            mScene2 = Scene.getSceneForLayout(container, R.layout.fragment_transition_scene_2, this);
        }
    }

    public void goToScene1(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mTransitionManager.transitionTo(mScene1);
        }
    }

    public void goToScene2(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mTransitionManager.transitionTo(mScene2);
        }
    }
}
