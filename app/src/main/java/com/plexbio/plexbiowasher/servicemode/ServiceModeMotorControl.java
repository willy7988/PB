package com.plexbio.plexbiowasher.servicemode;


import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.plexbio.plexbiowasher.R;
import com.plexbio.plexbiowasher.agent.Agent;
import com.plexbio.plexbiowasher.initial.HomeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceModeMotorControl extends Fragment implements View.OnClickListener {

    private RadioButton radio_x_btn;
    private RadioButton radio_y_btn;
    private RadioButton radio_pipet_btn;
    private RadioButton radio_magnetic_btn;
    private RadioButton radio_heater_lid_btn;
    private List<RadioButton> radioButtons = new ArrayList<RadioButton>();

    private Button jog_plus_btn;
    private Button jog_minus_btn;
    private Button home_btn;
    private Button dat_btn;
    private Button abs_btn;
    private Button stop_btn;


    private final static int X_AXIS = 0;
    private final static int Y_AXIS = 100;
    private final static int PIPET_AXIS = 200;
    private final static int MAGNETIC_AXIS = 300;
    private final static int HEATER_LID_AXIS = 400;

    private static int mPointer=99;

    public ServiceModeMotorControl() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        new Agent().setDisplayBackButton(getActivity(), getString(R.string.side_menu_service_mode));
        View view = inflater.inflate(R.layout.fragment_service_mode_motor_control, container, false);

        jog_plus_btn = (Button) view.findViewById(R.id.axis_control_jot_plus_btn);
        jog_minus_btn = (Button) view.findViewById(R.id.axis_control_jog_minus_btn);
        home_btn = (Button) view.findViewById(R.id.axis_control_home_btn);
        dat_btn = (Button) view.findViewById(R.id.axis_control_dat_btn);
        abs_btn = (Button) view.findViewById(R.id.axis_control_abs_btn);
        stop_btn = (Button) view.findViewById(R.id.axis_control_stop_btn);

        jog_plus_btn.setOnClickListener(this);
        jog_minus_btn.setOnClickListener(this);
        home_btn.setOnClickListener(this);
        dat_btn.setOnClickListener(this);
        abs_btn.setOnClickListener(this);
        stop_btn.setOnClickListener(this);

        radio_x_btn = (RadioButton) view.findViewById(R.id.x_axis_radio_btn);
        radioButtons.add(radio_x_btn);
        radioButtons.add((RadioButton) view.findViewById(R.id.y_axis_radio_btn));
        radioButtons.add((RadioButton) view.findViewById(R.id.pipet_axis_radio_btn));
        radioButtons.add((RadioButton) view.findViewById(R.id.magnetic_axis_radio_btn));
        radioButtons.add((RadioButton) view.findViewById(R.id.heater_lid_axis_radio_btn));

        if(mPointer<=4)radioButtons.get(mPointer).setChecked(true);

        for (final RadioButton button : radioButtons) {
            button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        processRadioButtonClick(buttonView);

                    }
                }
            });
        }
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.axis_control_jot_plus_btn:
                jogPlusCommand(mPointer*100);
                break;
            case R.id.axis_control_jog_minus_btn:
                jogMinusCommand(mPointer*100);
                break;
            case R.id.axis_control_home_btn:
                HomeCommand(mPointer*100);
                break;
            case R.id.axis_control_abs_btn:
                ABSCommand(mPointer*100);
                break;
            case R.id.axis_control_dat_btn:
                DATCommand(mPointer*100);
                break;
            case R.id.axis_control_stop_btn:
                STOPCommand(mPointer*100);
                break;
            default:

                break;
        }
    }

    private void jogPlusCommand(int index){
        switch (index){
            case X_AXIS:
                Log.e("TAG", "JOG+  X ");
                break;
            case Y_AXIS:
                Log.e("TAG", "JOG+  Y ");
                break;
            case PIPET_AXIS:
                Log.e("TAG", "JOG+  P ");
                break;
            case MAGNETIC_AXIS:
                Log.e("TAG", "JOG+  M ");
                break;
            case HEATER_LID_AXIS:
                Log.e("TAG", "JOG+  H");
                break;
            default:
                Toast.makeText(getContext(),"You have to select at least one!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void jogMinusCommand(int index){
        switch (index){
            case X_AXIS:
                Log.e("TAG", "JOG-  X ");
                break;
            case Y_AXIS:
                Log.e("TAG", "JOG-  Y ");
                break;
            case PIPET_AXIS:
                Log.e("TAG", "JOG-  P ");
                break;
            case MAGNETIC_AXIS:
                Log.e("TAG", "JOG-  M ");
                break;
            case HEATER_LID_AXIS:
                Log.e("TAG", "JOG- H");
                break;
            default:
                Toast.makeText(getContext(),"You have to select at least one!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void HomeCommand(int index){
        switch (index){
            case X_AXIS:
                Log.e("TAG", "Home  X ");
                break;
            case Y_AXIS:
                Log.e("TAG", "Home  Y ");
                break;
            case PIPET_AXIS:
                Log.e("TAG", "Home  P ");
                break;
            case MAGNETIC_AXIS:
                Log.e("TAG", "Home  M ");
                break;
            case HEATER_LID_AXIS:
                Log.e("TAG", "Home  H");
                break;
            default:
                Toast.makeText(getContext(),"You have to select at least one!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void ABSCommand(int index){
        switch (index){
            case X_AXIS:
                Log.e("TAG", "ABS  X ");
                break;
            case Y_AXIS:
                Log.e("TAG", "ABS  Y ");
                break;
            case PIPET_AXIS:
                Log.e("TAG", "ABS  P ");
                break;
            case MAGNETIC_AXIS:
                Log.e("TAG", "ABS  M ");
                break;
            case HEATER_LID_AXIS:
                Log.e("TAG", "ABS  H");
                break;
            default:
                Toast.makeText(getContext(),"You have to select at least one!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void DATCommand(int index){
        switch (index){
            case X_AXIS:
                Log.e("TAG", "DAT  X ");
                break;
            case Y_AXIS:
                Log.e("TAG", "DAT  Y ");
                break;
            case PIPET_AXIS:
                Log.e("TAG", "DAT  P ");
                break;
            case MAGNETIC_AXIS:
                Log.e("TAG", "DAT  M ");
                break;
            case HEATER_LID_AXIS:
                Log.e("TAG", "DAT  H");
                break;
            default:
                Toast.makeText(getContext(),"You have to select at least one!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void STOPCommand(int index){
        switch (index){
            case X_AXIS:
                Log.e("TAG", "STOP  X ");
                break;
            case Y_AXIS:
                Log.e("TAG", "STOP  Y ");
                break;
            case PIPET_AXIS:
                Log.e("TAG", "STOP  P ");
                break;
            case MAGNETIC_AXIS:
                Log.e("TAG", "STOP  M ");
                break;
            case HEATER_LID_AXIS:
                Log.e("TAG", "STOP  H");
                break;
            default:
                Toast.makeText(getContext(),"You have to select at least one!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    private void processRadioButtonClick(CompoundButton buttonView) {
        int index = 0;
        for (RadioButton button : radioButtons) {
            if (button != buttonView) {
                button.setChecked(false);
                index++;
            }else{
               mPointer=index;
                Log.e("TAG", Integer.toString(mPointer));
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        new Agent().setHideBackButton(getActivity());

    }


}
