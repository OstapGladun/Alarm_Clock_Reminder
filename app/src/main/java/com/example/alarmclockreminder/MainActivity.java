package com.example.alarmclockreminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity{

    FloatingActionButton addNoteButton;
    FloatingActionButton openSettingsButton;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        switch (sp.getString("theme", "ThemeOrange")){
            case "ThemeOrange":
                setTheme(R.style.ThemeOrange);
                break;
            case "ThemeRed":
                setTheme(R.style.ThemeRed);
                break;
            case "ThemePurple":
                setTheme(R.style.ThemePurple);
                break;
            case "ThemeTeal":
                setTheme(R.style.ThemeTeal);
                break;
            case "ThemeGreen":
                setTheme(R.style.ThemeGreen);
                break;
            case "ThemeYellow":
                setTheme(R.style.ThemeYellow);
                break;
        }
        setContentView(R.layout.activity_main);

        //переход в настройки по кнопке
        openSettingsButton = findViewById(R.id.button_settings);
        openSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchSettingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(switchSettingsIntent);
            }
        });

        //переход на екран создания напоминания по кнопке
        addNoteButton = findViewById(R.id.button_add);
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addNoteIntent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivity(addNoteIntent);
            }
        });


        /*Button buttonCancelAlarm = findViewById(R.id.button_cancel);
        buttonCancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });*/

    }

    /*private void cancelAlarm(){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);
        alarmManager.cancel(pendingIntent);
        mTextView.setText("AlarmCanceled");
    }*/

}