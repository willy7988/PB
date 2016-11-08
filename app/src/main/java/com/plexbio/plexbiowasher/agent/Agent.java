package com.plexbio.plexbiowasher.agent;

import android.app.*;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.*;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.*;
import android.support.v7.app.ActionBar;
import android.text.InputFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.plexbio.plexbiowasher.InputFilterMinMax;
import com.plexbio.plexbiowasher.R;
import com.plexbio.plexbiowasher.initial.HomeActivity;
import com.plexbio.plexbiowasher.menu.MenuItemAdapter;
import com.plexbio.plexbiowasher.servicemode.ServiceModeFragment;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Created by Willy on 2016/11/2.
 */

public class Agent {
    private static final String TAG_LOG = "TAG";
    private static final String LANGUAGE = "LANGUAGE";
    private static final String SHAREDPREFERENCES = "com.plexbio";

    private ActionBar mActionBar;
    private Boolean closeDialogStatus = false;


    public Agent() {

    }

    public void addDBLogs(Context context, String name, String status) {
        PlexBioDataBaseHelper mHelper = new PlexBioDataBaseHelper(context);
        SQLiteDatabase mDB = mHelper.getWritableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        ContentValues values = new ContentValues();
        values.put("FUNCTION", name);
        values.put("STATUS", status);
        values.put("_DATETIME", dateFormat.format(date));
        mDB.insertOrThrow("LOGS", null, values);
    }

    public String getDBLogs(Context context) {
        PlexBioDataBaseHelper mHelper = new PlexBioDataBaseHelper(context);
        SQLiteDatabase mDB = mHelper.getWritableDatabase();
        Cursor cursor = mDB.query("LOGS", new String[]{"FUNCTION ", "STATUS", "_DATETIME"}, null, null, null, null, null);
        cursor.moveToFirst();
        String logs = "";

        while (!cursor.isAfterLast()) {
            logs += cursor.getString(2) + "--" + '\t' + cursor.getString(0) + '\t' + "   STATUS =  " + cursor.getString(1) + '\t' + '\n';
            cursor.moveToNext();
        }
        cursor.close();
        mDB.close();

        return logs;
    }


    public void addLogs(Context context, String tag, String message) {
        DebugLog.LOGE(tag, message);
        setLogs(context, tag, message);
    }

    public void setLogs(Context context, String tag, String message) {

        SharedPreferences sp = context.getSharedPreferences(SHAREDPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        String logs = sp.getString(TAG_LOG, "");
        logs = logs + tag + "\t" + message + "\n";
        DebugLog.LOGE("setLogs", logs);
        editor.putString(TAG_LOG, logs);
        editor.commit();


    }

    public String getLogs(Context context, Handler callback) {
        SharedPreferences sp = context.getSharedPreferences(SHAREDPREFERENCES, Context.MODE_PRIVATE);
        return sp.getString(TAG_LOG, "");
    }

    public void createDialog(Context context, String title, String message, final Handler callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater li = LayoutInflater.from(context);
        View view = li.inflate(R.layout.basic_dialog, null);

        if (view != null) {
            TextView title1 = (TextView) view.findViewById(R.id.dialog_Title);
            TextView description = (TextView) view.findViewById(R.id.dialog_description);
            title1.setText(title);
            description.setText(message);
        }
        builder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Message msg = new Message();
                        msg.what = 1;
                        callback.sendMessage(msg);

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void createSetTimeDialog(Context context, String title, final Handler callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater li = LayoutInflater.from(context);
        View view = li.inflate(R.layout.set_time_dialog, null);

        EditText etHours = (EditText) view.findViewById(R.id.set_rinse_time_hours);
        EditText etMins = (EditText) view.findViewById(R.id.set_rinse_time_mins);
        EditText etSeconds = (EditText) view.findViewById(R.id.set_rinse_time_seconds);

        etHours.setFilters(new InputFilter[]{new InputFilterMinMax("0", "23",context)});
        etMins.setFilters(new InputFilter[]{new InputFilterMinMax("0", "59",context)});
        etSeconds.setFilters(new InputFilter[]{new InputFilterMinMax("0", "59",context)});
        Log.e("TAG", "1" + etHours);
        Log.e("TAG", "2" +etMins);

        if (view != null) {
            TextView title1 = (TextView) view.findViewById(R.id.set_time_dialog_Title);
            title1.setText(title);
        }
        builder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Message msg = new Message();
                        msg.what = 1;
                        callback.sendMessage(msg);
                        //儲存rinse time

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void createProgressDialog(final Context context, final String title, final String message, final int index, final int time) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater li = LayoutInflater.from(context);
        View view = li.inflate(R.layout.progress_dialog, null);
        final Handler handler = new Handler();


        TextView title1 = (TextView) view.findViewById(R.id.progress_dialog_Title);
        final TextView description = (TextView) view.findViewById(R.id.progress_dialog_description);
        final ProgressBar pb = (ProgressBar) view.findViewById(R.id.progressbar);
        final TextView progressView = (TextView) view.findViewById(R.id.progress_percentage_text);
        final TextView timeView = (TextView) view.findViewById(R.id.time_view);


        title1.setText(title);
        description.setText(message);
        pb.setMax(time);

        //set up dialog view but do nothing
        builder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();
        dialog.show();

        //get the positive and negative button from the dialog
        final Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
        final Button negativeButton = dialog.getButton(Dialog.BUTTON_NEGATIVE);

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if the process has been finished, then press ok button
                //the dialog will dismiss
                if (closeDialogStatus) {
                    dialog.dismiss();
                } else {

                    // if the process yet started, after pressing ok button
                    // the buttons will disappear, then do the process
                    positiveButton.setVisibility(View.INVISIBLE);
                    negativeButton.setVisibility(View.INVISIBLE);
                    addDBLogs(context, title, message);

                    switch (index) {
                        //do rinse
                        case MenuItemAdapter.FLUID_PATH_RINSE:

                            addDBLogs(context, title, message);
                            break;
                        //do prime
                        case MenuItemAdapter.FLUID_PATH_PRIME:

                            addDBLogs(context, title, message);
                            break;
                        //do rinse and prime
                        case MenuItemAdapter.FLUID_PATH_RINSE_PRIME:

                            addDBLogs(context, title, message);
                            break;
                    }

                    handler.post(new Runnable() {
                        float seconds = 0;
                        DecimalFormat df2 = new DecimalFormat("##0.0");

                        @Override
                        public void run() {
                            seconds++;
                            int hours = (int) Math.floor(seconds / 3600);
                            int minutes = (int) Math.floor(seconds % 3600 / 60);
                            int secs = (int) Math.floor(seconds % 60);
                            Log.e("TAG", "hours = " + hours + "minutes =" + minutes + "secs =" + secs + "HHAH" + seconds * 100 / time);
                            pb.incrementProgressBy(1);
                            progressView.setText(df2.format(seconds * 100 / time) + "%");
                            String currentTime = String.format("%d:%02d:%02d", hours, minutes, secs);
                            timeView.setText(currentTime);

                            if (seconds == time) {
                                positiveButton.setVisibility(View.VISIBLE);
                                closeDialogStatus = true;
                                return;
                            }
                            handler.postDelayed(this, 1000);
                        }
                    });


                }
            }
        });

    }


    public void createClockDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater li = LayoutInflater.from(context);
        View view = li.inflate(R.layout.clock_dialog, null);
        builder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).setNegativeButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void createInfoDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater li = LayoutInflater.from(context);
        View view = li.inflate(R.layout.info_dialog, null);
        builder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).setNegativeButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void changeLanguage(Context context, String language) {
        Configuration config = new Configuration();
        setupLanguage(context, language);
        switch (language) {
            case "en":
                config.locale = Locale.ENGLISH;
                break;
            case "cn":
                config.locale = Locale.CHINESE;
                break;
            default:
                config.locale = Locale.ENGLISH;
                break;
        }
        context.getResources().updateConfiguration(config, null);
        ((Activity) context).sendBroadcast(new Intent("Language.changed"));
//        ((Activity)context).finish();
    }

    public void setupHeader(Context context, String title) {
        View viewActionBar = LayoutInflater.from(context).inflate(R.layout.action_bar_title, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_title);
        textviewTitle.setText(title);
        ((AppCompatActivity) context).getSupportActionBar().setCustomView(viewActionBar, params);
    }

    public void setupLanguage(Context context, String Language) {
        SharedPreferences sp = context.getSharedPreferences(SHAREDPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString(LANGUAGE, Language);
        editor.commit();
    }

    public String getCurrentLanguage(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SHAREDPREFERENCES, Context.MODE_PRIVATE);
        return sp.getString(LANGUAGE, "");
    }

    public void setDisplayBackButton(final Context context, final String title) {

        final Button mBackButton;
        final FragmentManager fm = ((FragmentActivity) context).getSupportFragmentManager();

        mBackButton = (Button) ((FragmentActivity) context).findViewById(R.id.back_btn);
        mBackButton.setVisibility(View.VISIBLE);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupHeader(context, title);
                fm.popBackStackImmediate();
                mBackButton.setVisibility(View.INVISIBLE);
            }
        });

    }

    public void setHideBackButton(Context context) {
        final Button mBackButton;
        mBackButton = (Button) ((FragmentActivity) context).findViewById(R.id.back_btn);
        mBackButton.setVisibility(View.INVISIBLE);
    }


}
