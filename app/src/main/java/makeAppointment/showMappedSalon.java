package makeAppointment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ProgressBar;

import com.example.nal.R;

import java.util.ArrayList;

public class showMappedSalon extends AppCompatActivity {
    private salonObject mappedSalon=mappedSalons.chosenSalon;
    private RecyclerView RVchosenOnMapSalon;
    private ArrayList<salonObject> SalonObjects=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_mapped_salon);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        DisplayMetrics dm= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        getWindow().setLayout((int)(width*.9),(int)(height*.5));
        RVchosenOnMapSalon=findViewById(R.id.RVchosenOnMapSalon);
        SalonObjects.add(mappedSalon);
        rvAdapterFilteredSalons RVAdapterFilteredSalons=new rvAdapterFilteredSalons(SalonObjects);
        RVchosenOnMapSalon.setLayoutManager(new LinearLayoutManager(showMappedSalon.this));
        RVchosenOnMapSalon.setAdapter(RVAdapterFilteredSalons);
    }
}
