package com.example.MainActivity.MarterialTransition;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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
public class TransitionFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_transition_scene_1, container, false);
        return rootView;
    }
}
