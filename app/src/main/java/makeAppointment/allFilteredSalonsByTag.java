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

public class allFilteredSalonsByTag extends AppCompatActivity {
    private int tag;
    private ArrayList<String> SalonIds=new ArrayList<>();
    private ArrayList<salonObject> salonObjects=new ArrayList<>();
    private ProgressBar progressBar2;
    private RecyclerView RVfoundSalonsByTag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_filtered_salons_by_tag);
        ActionBar actionBar= getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        progressBar2=findViewById(R.id.progressBar1);
        RVfoundSalonsByTag=findViewById(R.id.RVfoundSalonsByTag);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new getIDsAsyncAndObjects().execute();
    }


    public void onClickHome(View view) {
        Intent intent1=new Intent(this, home.class);
        startActivity(intent1);
    }

    class getIDsAsyncAndObjects extends AsyncTask<Void, Void,Void > {

        @Override
        protected Void doInBackground(Void... voids) {
            Intent intent=getIntent();
            tag=intent.getIntExtra("tag",0);
            StringBuilder allIDsString=sqlInteractions.getSalonIds(tag);
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
            progressBar2.setVisibility(View.INVISIBLE);
            rvAdapterFilteredSalons RVAdapterFilteredSalons=new rvAdapterFilteredSalons(salonObjects);
            RVfoundSalonsByTag.setLayoutManager(new LinearLayoutManager(allFilteredSalonsByTag.this));
            RVfoundSalonsByTag.setAdapter(RVAdapterFilteredSalons);
        }
    }

}