package com.plexbio.plexbiowasher.servicemode;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.plexbio.plexbiowasher.R;
import com.plexbio.plexbiowasher.agent.Agent;

import java.util.List;

/**
 * Created by Willy on 2016/11/3.
 */

public class ServiceModeItemAdapter extends RecyclerView.Adapter<ServiceModeItemAdapter.ViewHolder> {

    List<ServiceModeFragment.Cards> cards;
    Context context;



    ServiceModeItemAdapter(List<ServiceModeFragment.Cards> cards, Context c){
        this.context=c;
        this.cards=cards;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView titleName;
        TextView itemNumber;
        LinearLayout maintenance_field;
        LinearLayout fluid_field;
        LinearLayout date_time_field;
        LinearLayout language_select_field;

        ServiceModeMotorControl mServiceModeMotorControl;
        ServiceModeWaterWay mServiceModeWaterWay;
        ServiceModeHeater mServiceModeHeater;
       ServiceModeParameter mServiceModeParameter;
       ServiceModeStepTuning mServiceModeStepTuning;




        public ViewHolder(View itemView){
            super(itemView);

            maintenance_field = (LinearLayout) itemView.findViewById(R.id.maintenance_selection);
            fluid_field = (LinearLayout) itemView.findViewById(R.id.fluid_path_selection);
            date_time_field = (LinearLayout) itemView.findViewById(R.id.date_time_selection);
            language_select_field = (LinearLayout)itemView.findViewById(R.id.language_selection);

            mServiceModeMotorControl = new ServiceModeMotorControl();
            mServiceModeWaterWay = new ServiceModeWaterWay();
            mServiceModeHeater = new ServiceModeHeater();
            mServiceModeParameter = new ServiceModeParameter();
            mServiceModeStepTuning = new ServiceModeStepTuning();


            cardView = (CardView) itemView.findViewById(R.id.cv);
            titleName = (TextView) itemView.findViewById(R.id.title_name);
            itemNumber = (TextView) itemView.findViewById(R.id.item_number);
        }

    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.menu_card_view, viewGroup,false);
        ViewHolder pvh = new ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        viewHolder.titleName.setText(cards.get(position).title);
        viewHolder.itemNumber.setText(Integer.toString(position + 1));

        viewHolder.maintenance_field.setVisibility(View.GONE);
        viewHolder.fluid_field.setVisibility(View.GONE);
        viewHolder.date_time_field.setVisibility(View.GONE);
        viewHolder.language_select_field.setVisibility(View.GONE);

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = ((AppCompatActivity)context).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction().addToBackStack(null);
                switch (position){
                    case 0:

                        ft.replace(R.id.fragment_container,viewHolder.mServiceModeMotorControl).commit();
                        new Agent().setupHeader(context,context.getString(R.string.servicemode_motor_control));
                        break;
                    case 1:
                        ft.replace(R.id.fragment_container,viewHolder.mServiceModeWaterWay).commit();
                        new Agent().setupHeader(context,context.getString(R.string.servicemode_water_way));
                        break;
                    case 2:
                        ft.replace(R.id.fragment_container,viewHolder.mServiceModeHeater).commit();
                        new Agent().setupHeader(context,context.getString(R.string.servicemode_heater));
                        break;
                    case 3:
                        ft.replace(R.id.fragment_container,viewHolder.mServiceModeParameter).commit();
                        new Agent().setupHeader(context,context.getString(R.string.servicemode_parameter));
                        break;
                    case 4:
                        ft.replace(R.id.fragment_container,viewHolder.mServiceModeStepTuning).commit();
                        new Agent().setupHeader(context,context.getString(R.string.servicemode_step_by_step_tuning));
                        break;
                }
            }
        });

    }
}
