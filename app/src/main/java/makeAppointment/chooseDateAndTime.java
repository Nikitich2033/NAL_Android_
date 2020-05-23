package makeAppointment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.nal.R;

import java.util.Calendar;

public class chooseDateAndTime extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    public static String ServiceId;
    private TextView chosenTimeTV;
    private TextView chosenDateTV;
    private static int day;
    private static int month;
    private static int year;
    private static int hour;
    private static int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_date_and_time);
        ActionBar actionBar= getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        DisplayMetrics dm= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        getWindow().setLayout((int)(width*.9),(int)(height*.2));

        chosenDateTV=findViewById(R.id.chosenDateTV);
        chosenTimeTV=findViewById(R.id.chosenTimeTV);
        Intent intent=getIntent();
        ServiceId=intent.getStringExtra("ServiceId");
        this.year=Calendar.getInstance().get(Calendar.YEAR);
        this.month=Calendar.getInstance().get(Calendar.MONTH);
        this.day=Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        this.hour=Calendar.getInstance().get(Calendar.HOUR);
        this.minute=Calendar.getInstance().get(Calendar.MINUTE);

        showDatePickerDialog();
    }
    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog=new DatePickerDialog(
                this,
                this,
                this.year,
                this.month,
                this.day
        );
        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                chosenDateTV.setText(chooseDateAndTime.day+"/"+chooseDateAndTime.month+"/"+chooseDateAndTime.year);
                chosenTimeTV.setText(chooseDateAndTime.hour+":"+chooseDateAndTime.minute);
            }
        });
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        this.day=dayOfMonth;
        this.month=month;
        this.year=year;
        TimePickerDialog timePickerDialog=new TimePickerDialog(
                chooseDateAndTime.this,
                chooseDateAndTime.this,
                this.hour,
                this.minute,
                true);
        timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                chosenDateTV.setText(chooseDateAndTime.day+"/"+chooseDateAndTime.month+"/"+chooseDateAndTime.year);
                chosenTimeTV.setText(chooseDateAndTime.hour+":"+chooseDateAndTime.minute);
            }
        });
        timePickerDialog.show();

    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        this.hour=hourOfDay;
        this.minute=minute;
        chosenDateTV.setText(this.day+"/"+this.month+"/"+this.year);
        chosenTimeTV.setText(this.hour+":"+this.minute);
    }

    public void onClickCancel(View view) {
        finish();
    }

    public void onClickFindMasters(View view) {
    }
}
