package home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nal.R;

import makeAppointment.makeAppointment;
import showUserAppointments.SeeMyAppointments;


public class home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ActionBar actionBar= getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
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