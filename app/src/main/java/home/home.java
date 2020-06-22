package home;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nal.BackService;
import com.example.nal.R;

import Constants.PreferenceUtils;
import makeAppointment.makeAppointment;
import showUserAppointments.SeeMyAppointments;

public class home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        ActionBar actionBar= getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }

        TextView myAwesomeTextView = findViewById(R.id.welcomeTextView);
        myAwesomeTextView.setText("Добрый день, " + PreferenceUtils.getWelcomeNameFromPrefs(this));

        startService(new Intent(this, BackService.class));

    }




    public void onClickAccountSettings(View view) {
    }

    public void onClickSeeMyAppointments(View view) {
        Intent intent = new Intent(getApplicationContext(), SeeMyAppointments.class);
        startActivity(intent);
    }

    public void onClickMakeAppointment(View view) {
        Intent intent=new Intent(this, makeAppointment.class);
        startActivity(intent);
    }


}
