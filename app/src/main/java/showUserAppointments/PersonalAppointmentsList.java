package showUserAppointments;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.nal.R;

import java.util.Date;
import java.util.ArrayList;

import home.home;
import mySQLInteractions.sqlInteractions;

//jjjj
public class PersonalAppointmentsList extends AppCompatActivity {

    private RecyclerView appointmentsRecyclerView;
    private RecyclerView.Adapter Adapter;
    private RecyclerView.LayoutManager LayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_appointments_list);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null ){

            actionBar.hide();
        }

        ArrayList<AppointmentObject> appointmentList = new ArrayList<>();
       String UserID = "nikitalyakhovoy@gmail.com";


       appointmentList = sqlInteractions.getUserFutureAppointments(UserID);

        appointmentsRecyclerView = findViewById(R.id.AppointmentsRecycler);
        appointmentsRecyclerView.setHasFixedSize(true);
        LayoutManager = new LinearLayoutManager(this);
        Adapter = new AppointmentsListAdapter(appointmentList);

        appointmentsRecyclerView.setLayoutManager(LayoutManager);
        appointmentsRecyclerView.setAdapter(Adapter);

    }

    public void onClickGoHome(View view) {
        Intent intent = new Intent(getApplicationContext(), home.class);
        startActivity(intent);

    }
}
