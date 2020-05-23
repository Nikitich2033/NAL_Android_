package makeAppointment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nal.R;

public class checkMasters extends AppCompatActivity {
    private static String ServiceId;
    private String[] allMasters;
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
    }
    class listviewmasterAdapter extends ArrayAdapter<String> {
        Context context;
        String[] allMasters;
        listviewmasterAdapter(Context context,String[] allMasters){
            super(context,R.layout.listviewlayoutmaster,R.id.tag,allMasters);
            this.context=context;
            this.allMasters=allMasters;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View listviewlayoutmaster=layoutInflater.inflate(R.layout.listviewlayoutmaster,parent,false);
            TextView TVMasterName=listviewlayoutmaster.findViewById(R.id.TVMasterName);
            TVMasterName.setText(allMasters[position]);
            return super.getView(position, convertView, parent);
        }
    }
}
