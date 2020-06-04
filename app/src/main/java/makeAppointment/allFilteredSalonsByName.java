package makeAppointment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.nal.R;

import java.util.ArrayList;
import java.util.HashMap;

import home.home;
import mySQLInteractions.sqlInteractions;

public class allFilteredSalonsByName extends AppCompatActivity {
    private String ServiceName;
    private ArrayList<salonObject> salonObjects=new ArrayList<>();
    private ProgressBar progressBar1;
    private RecyclerView RVfoundSalonsByName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_filtered_salons_by_name);
        ActionBar actionBar= getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        progressBar1=findViewById(R.id.progressBar1);
        RVfoundSalonsByName=findViewById(R.id.RVfoundSalonsByTag);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new getIDsAsyncAndObjects().execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void onClickHome(View view) {
        Intent intent1=new Intent(this, home.class);
        startActivity(intent1);
    }

    class getIDsAsyncAndObjects extends AsyncTask<Void, Void,Void > {

        @Override
        protected Void doInBackground(Void... voids) {
            Intent intent=getIntent();
            ServiceName=intent.getStringExtra("ServiceName");
            StringBuilder allIDsString=sqlInteractions.getSalonIds(ServiceName);
            String[] SalonIds=allIDsString.toString().split("#");
            HashMap<String,String> hm=sqlInteractions.getSalonIds2(ServiceName);
            salonObjects=sqlInteractions.getObjectsSalon2(SalonIds,hm);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar1.setVisibility(View.INVISIBLE);
            rvAdapterFilteredSalonsWithService RVAdapterFilteredSalonsWithService=new rvAdapterFilteredSalonsWithService(salonObjects);
            RVfoundSalonsByName.setLayoutManager(new LinearLayoutManager(allFilteredSalonsByName.this));
            RVfoundSalonsByName.setAdapter(RVAdapterFilteredSalonsWithService);
        }
    }

}
