package com.plexbio.plexbiowasher.servicemode;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.plexbio.plexbiowasher.R;
import com.plexbio.plexbiowasher.agent.Agent;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceModeHeater extends Fragment {


    public ServiceModeHeater() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        new Agent().setDisplayBackButton(getActivity(),getString(R.string.side_menu_service_mode));

        LayoutInflater li = LayoutInflater.from(getActivity());

        View view = li.inflate(R.layout.fragment_service_mode_heater, container, false);
        EditText et = (EditText)view.findViewById(R.id.edit_text_123);
        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    hideKeyboard(view);
                }
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        new Agent().setHideBackButton(getActivity());

    }

    public void hideKeyboard(View view){
        InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
}
