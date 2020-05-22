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

import home.home;
import mySQLInteractions.sqlInteractions;

public class allFilteredSalonsByName extends AppCompatActivity {
    private String ServiceName;
    private ArrayList<String> SalonIds=new ArrayList<>();
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
        Log.i("koko","llll");
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
            StringBuilder word=new StringBuilder();
            for (int i=0; i<allIDsString.length();i=i+1){
                if(allIDsString.charAt(i)=='$'){
                    SalonIds.add(word.toString());
                    word.setLength(0);
                }
                else {
                    word.append(allIDsString.charAt(i));
                }
            }
            SalonIds.add(word.toString());
            salonObjects=sqlInteractions.getObjectsSalon(SalonIds);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar1.setVisibility(View.INVISIBLE);
            rvAdapterFilteredSalons RVAdapterFilteredSalons=new rvAdapterFilteredSalons(salonObjects);
            RVfoundSalonsByName.setLayoutManager(new LinearLayoutManager(allFilteredSalonsByName.this));
            RVfoundSalonsByName.setAdapter(RVAdapterFilteredSalons);
        }
    }

}
