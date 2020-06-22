package showUserAppointments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.nal.R;

import java.util.ArrayList;

import Constants.PreferenceUtils;
import home.home;
import mySQLInteractions.sqlInteractions;

import Constants.PreferenceUtils;

import static Constants.PreferenceUtils.getEmailFromPrefs;

public class PersonalAppointmentsList extends AppCompatActivity {

    public RecyclerView appointmentsRecyclerView;
    public static RecyclerView.Adapter appointmentsAdapter;
    private RecyclerView.LayoutManager LayoutManager;
    private ArrayList<AppointmentObject> appointmentList;
    private String UserID ;
    private ProgressBar progressBar1;
    private int timeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_appointments_list);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null ){

            actionBar.hide();
        }

        progressBar1=findViewById(R.id.progressBar1);
        appointmentsRecyclerView = findViewById(R.id.AppointmentsRecycler);
        appointmentsRecyclerView.setHasFixedSize(true);
        LayoutManager = new LinearLayoutManager(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new getUserFutureApAsync().execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    class getUserFutureApAsync extends AsyncTask<Void, Void,Void > {

        @Override
        protected Void doInBackground(Void... voids) {
            Intent intent=getIntent();
            UserID = intent.getStringExtra("ID");
            timeValue = intent.getIntExtra("timeValue",2);
            if (timeValue == 0) {
                appointmentList = sqlInteractions.getUserFutureAppointments(UserID);
            }
            else if (timeValue == 1){
                appointmentList = sqlInteractions.getUserPastAppointments(UserID);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar1.setVisibility(View.INVISIBLE);
            appointmentsAdapter = new AppointmentsListAdapter(appointmentList);
            appointmentsRecyclerView.setLayoutManager(LayoutManager);
            appointmentsRecyclerView.setAdapter(appointmentsAdapter);

        }
    }



    public void onClickGoHome(View view) {
        Intent intent = new Intent(getApplicationContext(), home.class);
        startActivity(intent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("DESTROYED");
        if (PreferenceUtils.getRememberFromPrefs(this).equals("false")) {
            PreferenceUtils.saveWelcomeName(null, this);
            PreferenceUtils.saveEmailtoPrefs(null, this);
            PreferenceUtils.savePassToPrefs(null, this);
        }
    }

}
