package com.sdot.yidai.newsfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sdot.yidai.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TipsFragment extends Fragment {


    public TipsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tips, container, false);
    }

}
