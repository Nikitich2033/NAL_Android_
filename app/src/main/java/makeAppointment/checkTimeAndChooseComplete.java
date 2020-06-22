package makeAppointment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.nal.R;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;

import Constants.PreferenceUtils;
import home.home;
import mySQLInteractions.sqlInteractions;

public class checkTimeAndChooseComplete extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private TextView BlockedTimeTV;
    private String SalonId;
    private String ServiceId;
    private String MasterId;
    private String checkDate;
    private ArrayList<Time> blockedTime;
    private ProgressBar progressBar7;
    private String chosenSatrtTime;
    private String chosenEndTime;
    private int hour;
    private int minute;
    private int treatmentDuration;
    private String treatmentName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_time_and_choose_complete);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        DisplayMetrics dm= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        getWindow().setLayout((int)(width*.9),(int)(height*.6));
        BlockedTimeTV=findViewById(R.id.BlockedTimeTV);
        progressBar7=findViewById(R.id.progressBar7);
        Intent intent=getIntent();
        SalonId=intent.getStringExtra("SalonId");
        ServiceId=intent.getStringExtra("ServiceId");
        MasterId=intent.getStringExtra("MasterId");
        checkDate=intent.getStringExtra("checkDate");
        treatmentDuration=intent.getIntExtra("treatmentDuration",0);
        treatmentName=intent.getStringExtra("treatmentName");
        this.hour=Calendar.getInstance().get(Calendar.HOUR);
        this.minute=Calendar.getInstance().get(Calendar.MINUTE);
        new getBlockedTimessAsync().execute();

    }

    public void onClickChooseTime(View view) {
        showTimePickerDialog();
    }
    private void showTimePickerDialog(){
        TimePickerDialog timePickerDialog=new TimePickerDialog(
                this,
                this,
                this.hour,
                this.minute,
                true);
        timePickerDialog.show();
       // progressBar7.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        this.hour=hourOfDay;
        this.minute=minute;
        chosenSatrtTime=this.hour+":"+this.minute+":"+"00";
        Time time=new Time(this.hour,this.minute,0);
        int temp=time.getMinutes()+treatmentDuration;
        time.setMinutes(temp);
        chosenEndTime=time.toString();
        progressBar7.setVisibility(View.VISIBLE);
        new makeAppointment().execute();

    }
    class makeAppointment extends AsyncTask<Void, Void,Void > {

        @Override
        protected Void doInBackground(Void... voids) {
           sqlInteractions.addAppointment(SalonId,"nikitalyakhovoy@gmail.com",ServiceId,checkDate,chosenSatrtTime,chosenEndTime,MasterId);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar7.setVisibility(View.INVISIBLE);
            Intent intent=new Intent(checkTimeAndChooseComplete.this, home.class);
            startActivity(intent);

        }
    }
    class getBlockedTimessAsync extends AsyncTask<Void, Void,Void > {

        @Override
        protected Void doInBackground(Void... voids) {
            blockedTime= sqlInteractions.getBlockedTimes(SalonId,ServiceId,checkDate,MasterId);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            StringBuilder stringBlock=new StringBuilder();
            for(int i=0;i<blockedTime.size();i=i+2){
                stringBlock.append(blockedTime.get(i).getHours()+":"+blockedTime.get(i).getMinutes()+"-"+blockedTime.get(i+1).getHours()+":"+blockedTime.get(i+1).getMinutes()+"\n");
            }
            BlockedTimeTV.setText(String.valueOf(stringBlock));
            progressBar7.setVisibility(View.INVISIBLE);
            BlockedTimeTV.setVisibility(View.VISIBLE);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("DESTROYED");
        if (PreferenceUtils.getRememberFromPrefs(this).equals("false")) {
            PreferenceUtils.clearPrefs(this);
        }
    }

}
