package com.plexbio.plexbiowasher.servicemode;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plexbio.plexbiowasher.R;
import com.plexbio.plexbiowasher.agent.Agent;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceModeWaterWay extends Fragment {


    public ServiceModeWaterWay() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        new Agent().setDisplayBackButton(getActivity(),getString(R.string.side_menu_service_mode));
        return inflater.inflate(R.layout.fragment_service_mode_water_way, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        new Agent().setHideBackButton(getActivity());

    }
}
