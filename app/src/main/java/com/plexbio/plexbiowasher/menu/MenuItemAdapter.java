package com.plexbio.plexbiowasher.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.plexbio.plexbiowasher.R;
import com.plexbio.plexbiowasher.agent.Agent;
import com.plexbio.plexbiowasher.initial.HomeActivity;

import java.util.List;

/**
 * Created by Willy on 2016/11/2.
 */

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.ViewHolder> implements View.OnClickListener {

    List<MenuFragment.Cards> cards;
    Context context;

    int mMaintenanceSelect = -1;
    int mFluidSelect = -2;
    int mDateTimeSelect = -3;
    int mLanguageSelect = -4;

    public final static int SETTING_DEFAULT = 0;
    public final static int RENEW_TANK = 100;
    public final static int CLEAN_MANIFOLD = 200;
    public final static int MACHINE_RESET = 300;
    public final static int FLUID_PATH_RINSE = 400;
    public final static int FLUID_PATH_PRIME = 500;
    public final static int FLUID_PATH_RINSE_PRIME = 600;
    public final static int UPGRADE_FIRMWARE = 700;
    public final static int UPGRADE_ASSAY = 800;


    MenuItemAdapter(List<MenuFragment.Cards> cards, Context c) {
        this.context = c;
        this.cards = cards;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView titleName;
        TextView itemNumber;

        LinearLayout maintenance_field;
        LinearLayout fluid_field;
        LinearLayout date_time_field;
        LinearLayout language_select_field;

        Button renew_tank_btn;
        Button clean_monifold_btn;

        Button rinse_btn;
        Button prime_btn;
        Button rinse_prime_btn;

        Button date_btn;
        Button time_btn;

        RadioButton english_btn;
        RadioButton chinese_btn;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cv);

            titleName = (TextView) itemView.findViewById(R.id.title_name);
            itemNumber = (TextView) itemView.findViewById(R.id.item_number);

            maintenance_field = (LinearLayout) itemView.findViewById(R.id.maintenance_selection);
            fluid_field = (LinearLayout) itemView.findViewById(R.id.fluid_path_selection);
            date_time_field = (LinearLayout) itemView.findViewById(R.id.date_time_selection);
            language_select_field = (LinearLayout) itemView.findViewById(R.id.language_selection);

            renew_tank_btn = (Button) itemView.findViewById(R.id.renew_tank_btn);
            clean_monifold_btn = (Button) itemView.findViewById(R.id.clean_manifold_btn);

            rinse_btn = (Button) itemView.findViewById(R.id.rinse_btn);
            prime_btn = (Button) itemView.findViewById(R.id.prime_btn);
            rinse_prime_btn = (Button) itemView.findViewById(R.id.rinse_prime_btn);
            date_btn = (Button) itemView.findViewById(R.id.date_btn);
            time_btn = (Button) itemView.findViewById(R.id.time_btn);

            english_btn = (RadioButton) itemView.findViewById(R.id.english_btn);
            chinese_btn = (RadioButton) itemView.findViewById(R.id.chinese_btn);
        }

    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {


        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.menu_card_view, viewGroup, false);
        ViewHolder pvh = new ViewHolder(v);
        return pvh;
    }

    //設定不同position的card的作動，將字串設定進入Card
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        Log.e("TAG", "This is it Bind View! item + " + i);
        viewHolder.titleName.setText(cards.get(i).title);
        viewHolder.itemNumber.setText(Integer.toString(i + 1));

        final boolean isExpandMaintenance = i == mMaintenanceSelect;
        final boolean isExpandFluid = i == mFluidSelect;
        final boolean isExpandDateTime = i == mDateTimeSelect;
        final boolean isExpandLanguageSelection = i == mLanguageSelect;

        viewHolder.maintenance_field.setVisibility(isExpandMaintenance ? View.VISIBLE : View.GONE);
        viewHolder.fluid_field.setVisibility(isExpandFluid ? View.VISIBLE : View.GONE);
        viewHolder.date_time_field.setVisibility(isExpandDateTime ? View.VISIBLE : View.GONE);
        viewHolder.language_select_field.setVisibility(isExpandLanguageSelection ? View.VISIBLE : View.GONE);

        viewHolder.english_btn.setChecked(new Agent().getCurrentLanguage(context) == "en" ? true : false);
        viewHolder.chinese_btn.setChecked(new Agent().getCurrentLanguage(context) == "cn" ? true : false);


        viewHolder.cardView.setActivated(isExpandDateTime || isExpandFluid || isExpandMaintenance);

        viewHolder.renew_tank_btn.setOnClickListener(this);
        viewHolder.clean_monifold_btn.setOnClickListener(this);
        viewHolder.rinse_btn.setOnClickListener(this);
        viewHolder.prime_btn.setOnClickListener(this);
        viewHolder.rinse_prime_btn.setOnClickListener(this);
        viewHolder.date_btn.setOnClickListener(this);
        viewHolder.time_btn.setOnClickListener(this);
        viewHolder.english_btn.setOnClickListener(this);
        viewHolder.chinese_btn.setOnClickListener(this);


        // 點擊card
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("TAG", "Click +" + Integer.toString(i));
                // TransitionManager.beginDelayedTransition(viewHolder.cardView,new Fade());
                switch (i) {
                    case 0:
                        mMaintenanceSelect = isExpandMaintenance ? -1 : i;
                        mFluidSelect = -2;
                        mDateTimeSelect = -3;
                        mLanguageSelect = -4;

                        notifyDataSetChanged();
                        break;
                    case 1:
                        mMaintenanceSelect = -1;
                        mFluidSelect = -2;
                        mDateTimeSelect = -3;
                        mLanguageSelect = -4;
                        notifyDataSetChanged();

                        onCreateCommandDialog(context.getString(R.string.menu_reset_machine_title), context.getString(R.string.menu_reset_machine_description), MACHINE_RESET);
                        break;
                    case 2:
                        mMaintenanceSelect = -1;
                        mFluidSelect = -2;
                        mDateTimeSelect = -3;
                        mLanguageSelect = -4;
                        notifyDataSetChanged();

                        onCreateCommandDialog(context.getString(R.string.menu_set_rinse_time));
                        break;
                    case 3:
                        mMaintenanceSelect = -1;
                        mFluidSelect = isExpandFluid ? -2 : i;
                        mDateTimeSelect = -3;
                        mLanguageSelect = -4;


                        notifyDataSetChanged();
                        break;
                    case 4:
                        mMaintenanceSelect = -1;
                        mFluidSelect = -2;
                        mDateTimeSelect = -3;
                        mLanguageSelect = -4;
                        notifyDataSetChanged();
                        //find upgrade firmware file, if true then do upgradefirmware
                        if (true) {
                            onCreateCommandDialog(context.getString(R.string.menu_upgrade_firmware_title), context.getString(R.string.menu_upgrade_firmware_description), UPGRADE_FIRMWARE);
                        } else {
                            // if doesn't find upgrade firmware file
                            onCreateCommandDialog(context.getString(R.string.menu_upgrade_firmware_fail_title), context.getString(R.string.menu_upgrade_firmware_fail_description), SETTING_DEFAULT);
                        }
                        break;
                    case 5:
                        mMaintenanceSelect = -1;
                        mFluidSelect = -2;
                        mDateTimeSelect = -3;
                        mLanguageSelect = -4;
                        notifyDataSetChanged();
                        if (true) {
                            onCreateCommandDialog(context.getString(R.string.menu_upgrade_assay_title), context.getString(R.string.menu_upgrade_assay_description), UPGRADE_ASSAY);
                        } else {
                            // if doesn't find upgrade assay file
                            onCreateCommandDialog(context.getString(R.string.menu_upgrade_assay_fail_title), context.getString(R.string.menu_upgrade_assay_fail_description), SETTING_DEFAULT);
                        }
                        break;
                    case 6:
                        mMaintenanceSelect = -1;
                        mFluidSelect = -2;
                        mDateTimeSelect = isExpandDateTime ? -3 : i;
                        mLanguageSelect = -4;
                        notifyDataSetChanged();
                        break;
                    case 7:
                        mMaintenanceSelect = -1;
                        mFluidSelect = -2;
                        mDateTimeSelect = -3;
                        mLanguageSelect = isExpandLanguageSelection ? -4 : i;
                        notifyDataSetChanged();
                        break;

                    default:
                        mMaintenanceSelect = -1;
                        mFluidSelect = -2;
                        mDateTimeSelect = -3;
                        mLanguageSelect = -4;
                        notifyDataSetChanged();

                        onLaunchDialog(i);
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.renew_tank_btn:
                onCreateCommandDialog(context.getString(R.string.menu_renew_tank_title), context.getString(R.string.menu_renew_tank_description), RENEW_TANK);
                break;
            case R.id.clean_manifold_btn:
                onCreateCommandDialog(context.getString(R.string.menu_clean_manifold_title), context.getString(R.string.menu_clean_manifold_description), CLEAN_MANIFOLD);
                break;
            case R.id.rinse_btn:

                // get the rinse time

                onCreateCommandDialog(context.getString(R.string.menu_rinse_title), context.getString(R.string.menu_rinse_description), FLUID_PATH_RINSE, 11);
                break;
            case R.id.prime_btn:
                onCreateCommandDialog(context.getString(R.string.menu_prime_title), context.getString(R.string.menu_prime_description), FLUID_PATH_PRIME, 10);
                break;
            case R.id.rinse_prime_btn:
                onCreateCommandDialog(context.getString(R.string.menu_rinse_prime_title), context.getString(R.string.menu_rinse_prime_description), FLUID_PATH_RINSE_PRIME, 30);
                break;
            case R.id.date_btn:
                context.startActivity(new Intent(Settings.ACTION_DATE_SETTINGS));
                break;
            case R.id.time_btn:
                context.startActivity(new Intent(Settings.ACTION_DATE_SETTINGS));
                break;
            case R.id.english_btn:


                // new Agent().setupLanguage(context,"en");
                new Agent().changeLanguage(context, "en");
                break;
            case R.id.chinese_btn:

                // new Agent().setupLanguage(context,"cn");
                new Agent().changeLanguage(context, "cn");
                break;


        }
    }

    void onLaunchDialog(int ID) {
//        MaintenanceDialog maintenanceDialog = new MaintenanceDialog();
//        maintenanceDialog.setID(ID);
//        maintenanceDialog.setCancelable(false);
//        maintenanceDialog.show(((FragmentActivity)context).getSupportFragmentManager(),"123");
    }


    void onCreateCommandDialog(final String title, final String description, final int index) {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        new Agent().addDBLogs(context, title, description);
                        switch (index) {
                            case RENEW_TANK:

                                break;
                            case CLEAN_MANIFOLD:

                                break;
                            case MACHINE_RESET:

                                break;
                            case FLUID_PATH_RINSE:

                                break;
                            case FLUID_PATH_PRIME:

                                break;
                            case FLUID_PATH_RINSE_PRIME:

                                break;
                            case UPGRADE_FIRMWARE:

                                break;
                            case UPGRADE_ASSAY:

                                break;
                            default:
                                break;
                        }
                        break;
                }
            }
        };

        new Agent().createDialog(context, title, description, handler);
    }

    void onCreateCommandDialog(final String title, final String description, final int index, int sec) {
        new Agent().addDBLogs(context, title, description);
        new Agent().createProgressDialog(context, title, description, index, sec);

    }

    void onCreateCommandDialog(final String title) {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        new Agent().addLogs(context, "SetTime", title);
                        //save the fluid time
                        break;
                }
            }
        };

        new Agent().createSetTimeDialog(context, title, handler);
    }
}
