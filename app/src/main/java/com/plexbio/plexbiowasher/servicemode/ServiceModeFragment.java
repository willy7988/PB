package com.plexbio.plexbiowasher.servicemode;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plexbio.plexbiowasher.R;



import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceModeFragment extends Fragment {

    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;

    private List<ServiceModeFragment.Cards> mCards;
    public ServiceModeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_service_mode,container,false);
        mRecyclerView=(RecyclerView)rootView.findViewById(R.id.menu_service_mode_recyclerView);

        init();

        return rootView;
    }

    public void init(){
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        initializeData();
        ServiceModeItemAdapter adapter = new ServiceModeItemAdapter(mCards, getActivity());

        mRecyclerView.setAdapter(adapter);
    }

    public static class Cards {
        String title;
        Cards(String title){
            this.title=title;
        }
    }

    private void initializeData(){
        mCards=new ArrayList<>();
        mCards.add(new ServiceModeFragment.Cards(getString(R.string.servicemode_motor_control)));
        mCards.add(new ServiceModeFragment.Cards(getString(R.string.servicemode_water_way)));
        mCards.add(new ServiceModeFragment.Cards(getString(R.string.servicemode_heater)));
        mCards.add(new ServiceModeFragment.Cards(getString(R.string.servicemode_parameter)));
        mCards.add(new ServiceModeFragment.Cards(getString(R.string.servicemode_step_by_step_tuning)));
    }

}
