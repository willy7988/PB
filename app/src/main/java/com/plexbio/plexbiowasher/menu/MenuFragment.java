package com.plexbio.plexbiowasher.menu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plexbio.plexbiowasher.R;
import com.plexbio.plexbiowasher.initial.HomeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {
    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;

    private List<Cards> mCards;

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_menu,container,false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.menu_fragment_recyclerView);

        init();

        return rootView;


    }

    public void init(){
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        initializeData();
        MenuItemAdapter adapter = new MenuItemAdapter(mCards, getActivity());

        mRecyclerView.setAdapter(adapter);
       // mRecyclerView.smoothScrollToPosition(mCards.size()-1);
    }

    class Cards {
        String title;
        Cards(String title){
            this.title=title;
        }
    }
    private void initializeData(){
        mCards=new ArrayList<>();
        mCards.add(new Cards(getString(R.string.menu_maintenance)));
        mCards.add(new Cards(getString(R.string.menu_machine_reset)));
        mCards.add(new Cards(getString(R.string.menu_rinse_time)));
        mCards.add(new Cards(getString(R.string.menu_fluid_path)));
        mCards.add(new Cards(getString(R.string.menu_upgrade_firmware)));
        mCards.add(new Cards(getString(R.string.menu_upgrade_assay)));
        mCards.add(new Cards(getString(R.string.menu_date_time)));
        mCards.add(new Cards(getString(R.string.menu_language_select)));

    }

}
