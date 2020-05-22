package makeAppointment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.nal.R;

import java.util.ArrayList;

import home.home;
import mySQLInteractions.sqlInteractions;

public class allSalons extends AppCompatActivity {
    private RecyclerView RVallSalons;
    private ProgressBar progressBar4;
    private ArrayList<String> SalonIds=new ArrayList<>();
    private ArrayList<salonObject> salonObjects=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_salons);
        ActionBar actionBar= getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        RVallSalons=findViewById(R.id.RVallSalons);
        progressBar4=findViewById(R.id.progressBar4);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        new getIDsAsyncAndObjects().execute();
    }
    class getIDsAsyncAndObjects extends AsyncTask<Void, Void,Void > {

        @Override
        protected Void doInBackground(Void... voids) {
            SalonIds=sqlInteractions.getSalonIds();
            salonObjects=sqlInteractions.getObjectsSalon(SalonIds);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar4.setVisibility(View.INVISIBLE);
            rvAdapterFilteredSalons RVAdapterFilteredSalons=new rvAdapterFilteredSalons(salonObjects);
            RVallSalons.setLayoutManager(new LinearLayoutManager(allSalons.this));
            RVallSalons.setAdapter(RVAdapterFilteredSalons);
        }
    }

    public void onClickGoHome(View view) {
        Intent intent=new Intent(this, home.class);
        startActivity(intent);
    }
}
