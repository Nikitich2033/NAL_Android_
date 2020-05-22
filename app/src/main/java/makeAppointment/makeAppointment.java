package makeAppointment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.nal.R;

import home.home;

public class makeAppointment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_appointment);
        ActionBar actionBar= getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
    }

    public void onClickGoHome(View view) {
        Intent intent =new Intent(this, home.class);
        startActivity(intent);
    }

    public void onClickFindSalon(View view) {
    }

    public void onClickAllSalons(View view) {
        Intent intent=new Intent(this,allSalons.class);
        startActivity(intent);
    }

    public void onClickFindTreatment(View view) {
        Intent intent=new Intent(this,makeAppointmentByMainTags.class);
        startActivity(intent);
    }
}