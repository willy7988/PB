package com.plexbio.plexbiowasher.logstatus;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.plexbio.plexbiowasher.R;
import com.plexbio.plexbiowasher.agent.Agent;

/**
 * A simple {@link Fragment} subclass.
 */
public class LogDisplayFragment extends Fragment {

    private TextView tvLogs;
    private TextView dbLogs;
    private String logs1;

    private String logs;
    private Handler handler;

    public LogDisplayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_log_display, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        logs="";
        logs1="";

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Log.e("TAG Run Log post", String.valueOf(Thread.currentThread().getId()));
                super.handleMessage(msg);
                tvLogs.setText(logs);
                dbLogs.setText(logs1);
                Log.e("TAG" , "End");
            }

        };
        tvLogs = (TextView)getView().findViewById(R.id.logs);
        dbLogs = (TextView)getView().findViewById(R.id.dblogs);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("TAG" , "Start");
//        Handler handler2 =new Handler();
//        handler2.post(new Runnable() {
//            @Override
//            public void run() {
//                Log.e("TAG Run Log", String.valueOf(Thread.currentThread().getId()));
//                logs = new Agent().getLogs(getActivity(),handler);
//                logs1= new Agent().getDBLogs(getActivity());
//                handler.sendMessage(new Message());
//            }
//        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("TAG Run Log", String.valueOf(Thread.currentThread().getId()));
                logs = new Agent().getLogs(getActivity(),handler);
                logs1= new Agent().getDBLogs(getActivity());
                handler.sendMessage(new Message());
            }
        }).start();
    }

}
