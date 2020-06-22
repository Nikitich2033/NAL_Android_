package showUserAppointments;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.nal.R;

import Constants.PreferenceUtils;
import home.home;

public class SeeMyAppointments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null ){

            actionBar.hide();
        }

    }


    public void onClickGoHome(View view) {
        Intent intent = new Intent(getApplicationContext(), home.class);
        startActivity(intent);
    }

    public void onClickUpcoming_Appointments(View view) {
        Intent intent = new Intent(getApplicationContext(), PersonalAppointmentsList.class);
        intent.putExtra("timeValue",0);
        intent.putExtra("ID",PreferenceUtils.getEmailFromPrefs(this));
        startActivity(intent);
    }

    public void onClickPastAppointments(View view) {
        Intent intent = new Intent(getApplicationContext(), PersonalAppointmentsList.class);
        intent.putExtra("timeValue",1);
        intent.putExtra("ID",PreferenceUtils.getEmailFromPrefs(this));
        startActivity(intent);
    }

}

