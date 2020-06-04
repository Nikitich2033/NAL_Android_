package makeAppointment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import makeAppointment.SalonOptions;
import com.example.nal.R;

import java.util.ArrayList;
import java.util.Calendar;

import mySQLInteractions.sqlInteractions;

public class checkMasters extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private static String ServiceId;
    private static String SalonId;
    private ArrayList<Master> allMasters;
    private ListView LVMasters;
    private static String MasterId;
    private ProgressBar progressBar6;
    private static int day;
    private static int month;
    private static int year;
    private static int treatmentDuration;
    private static String treatmentName;
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
        Intent intent=getIntent();
        ServiceId=intent.getStringExtra("ServiceId");
        SalonId=intent.getStringExtra("SalonId");
        treatmentDuration=intent.getIntExtra("treatmentDuration",0);
        treatmentName=intent.getStringExtra("treatmentName");
        LVMasters=findViewById(R.id.LVMasters);
        progressBar6=findViewById(R.id.progressBar6);
        new getMastersAsync().execute();
        this.year= Calendar.getInstance().get(Calendar.YEAR);
        this.month=Calendar.getInstance().get(Calendar.MONTH);
        this.day=Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

    }
    class listviewmasterAdapter extends ArrayAdapter<Master> {
        Context context;
        listviewmasterAdapter(Context context,ArrayList<Master> allMasters){
            super(context, R.layout.listviewlayoutmaster,R.id.TVMasterName,allMasters);
            this.context=context;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final Master item=getItem(position);
            if(convertView==null){
                convertView=LayoutInflater.from(getContext()).inflate(R.layout.listviewlayoutmaster,parent,false);
            }
            TextView TVMasterName=convertView.findViewById(R.id.TVMasterName);
            Button checkMasterButton=convertView.findViewById(R.id.checkMasterButton);
            TVMasterName.setText(item.getMasterName());
            checkMasterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MasterId=item.getMasterId();
                    showDatePickerDialog();
                }
            });
            return convertView;
        }
    }
    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog=new DatePickerDialog(
                this,
                this,
                this.year,
                this.month,
                this.day
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        this.day=dayOfMonth;
        this.month=month+1;
        this.year=year;
        Intent intent=new Intent(this,checkTimeAndChooseComplete.class);
        intent.putExtra("SalonId",SalonId);
        intent.putExtra("ServiceId",ServiceId);
        intent.putExtra("MasterId",MasterId);
        intent.putExtra("checkDate",String.valueOf(this.year+"-"+this.month+"-"+this.day));
        intent.putExtra("treatmentDuration",treatmentDuration);
        intent.putExtra("treatmentName",treatmentName);
        startActivity(intent);
    }

    class getMastersAsync extends AsyncTask<Void, Void,Void > {

        @Override
        protected Void doInBackground(Void... voids) {
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
