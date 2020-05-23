package makeAppointment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import makeAppointment.SalonOptions;
import com.example.nal.R;

import java.util.ArrayList;

import mySQLInteractions.sqlInteractions;

public class checkMasters extends AppCompatActivity {
    private static String ServiceId;
    private ArrayList<String> allMasters;
    private ListView LVMasters;
    private ProgressBar progressBar6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_masters);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        DisplayMetrics dm= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        getWindow().setLayout((int)(width*.9),(int)(height*.6));
        LVMasters=findViewById(R.id.LVMasters);
        progressBar6=findViewById(R.id.progressBar6);
        new getMastersAsync().execute();

    }
    class listviewmasterAdapter extends ArrayAdapter<String> {
        Context context;
        ArrayList<String> allMasters;
        listviewmasterAdapter(Context context,ArrayList<String> allMasters){
            super(context,R.layout.listviewlayoutmaster,R.id.TVMasterName,allMasters);
            this.context=context;
            this.allMasters=allMasters;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View listviewlayoutmaster=layoutInflater.inflate(R.layout.listviewlayoutmaster,parent,false);
            TextView TVMasterName=listviewlayoutmaster.findViewById(R.id.TVMasterName);
            TVMasterName.setText(allMasters.get(position));
            return super.getView(position, convertView, parent);
        }
    }
    class getMastersAsync extends AsyncTask<Void, Void,Void > {

        @Override
        protected Void doInBackground(Void... voids) {
            Intent intent=getIntent();
            ServiceId=intent.getStringExtra("ServiceId");
            allMasters=sqlInteractions.getMasters(SalonOptions.SalonId,ServiceId);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            final listviewmasterAdapter listviewmasterAdapter=new listviewmasterAdapter(checkMasters.this,allMasters);
            LVMasters.setAdapter(listviewmasterAdapter);
            super.onPostExecute(aVoid);
            progressBar6.setVisibility(View.INVISIBLE);

        }
    }
}
