package showUserAppointments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ProgressBar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.nal.R;

import java.util.ArrayList;

import home.home;
import mySQLInteractions.sqlInteractions;

public class PersonalAppointmentsList extends AppCompatActivity {

    private RecyclerView appointmentsRecyclerView;
    private RecyclerView.Adapter Adapter;
    private RecyclerView.LayoutManager LayoutManager;
    private ArrayList<AppointmentObject> appointmentList;
    private String UserID = "nikitalyakhovoy@gmail.com";
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
            Adapter = new AppointmentsListAdapter(appointmentList);
            appointmentsRecyclerView.setLayoutManager(LayoutManager);
            appointmentsRecyclerView.setAdapter(Adapter);

        }
    }



    public void onClickGoHome(View view) {
        Intent intent = new Intent(getApplicationContext(), home.class);
        startActivity(intent);

    }
}
