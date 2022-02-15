package com.example.alarmclockreminder;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.CaseMap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.alarmclockreminder.database.NotesDatabase;
import com.example.alarmclockreminder.entities.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.util.Calendar;
//comment
public class AddNoteActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{

    private FloatingActionButton setAlarmButton;
    private FloatingActionButton submitAlarmButton;
    private TextView mTextView;
    EditText titleEdit;
    EditText textEdit;
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
        setContentView(R.layout.activity_add_note);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //выбор времени по кнопке
        setAlarmButton = findViewById(R.id.button_time);
        mTextView = findViewById(R.id.time_set);
        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"time picker");
            }
        });

        //попытка сохранить напоминие без выбора время
        submitAlarmButton = findViewById(R.id.button_submit);
        submitAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.set_time_warning,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, hourOfDay);

        updateTimeText(c);

        //сохраниение по кнопке
        submitAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAlarm(c);
                Intent switchSettingsIntent = new Intent(AddNoteActivity.this, MainActivity.class);
                startActivity(switchSettingsIntent);
            }
        });
    }

    private void updateTimeText(Calendar c) {
        mTextView.setTextSize(30);
        mTextView.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime()));
    }

    private void startAlarm(Calendar c) {

        //set title for note
        final Note note = new Note();
        if(titleEdit.getText().toString().trim().isEmpty()){
            note.setTitle(getString(R.string.default_title));
        }else{
            note.setTitle(titleEdit.getText().toString());
        }
        note.setText(textEdit.getText().toString());
        note.setTime(DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime()));

        //setting alarm manager
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

        //insert note to database
        class SaveNoteTask extends AsyncTask<Void,Void,Void>{

            @Override
            protected Void doInBackground(Void... voids) {
                NotesDatabase.getDatabase(getApplicationContext()).noteDAO().insertNote(note);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        }
        new  SaveNoteTask().execute();
    }
}