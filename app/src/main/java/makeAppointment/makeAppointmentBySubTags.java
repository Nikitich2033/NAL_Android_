package makeAppointment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nal.R;

import home.home;

import static android.app.PendingIntent.getActivity;
import static android.icu.text.DisplayContext.LENGTH_SHORT;

public class makeAppointmentBySubTags extends AppCompatActivity {
    private ListView LVsubtags;
    private int subTagsId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar= getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_appointment_by_sub_tags);
        LVsubtags=findViewById(R.id.LVsubtags);
        Intent intent=getIntent();
        subTagsId=intent.getIntExtra("subTagsId",0);
        int[] idslist = new int[7];
        String[] neededList=null;
        if(subTagsId==0) {
            neededList = this.getResources().getStringArray(R.array.hair);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, neededList);
            LVsubtags.setAdapter(adapter);
            idslist= new int[]{7, 8, 9, 10, 11, 1};
        }
        else if(subTagsId==1) {
            neededList = this.getResources().getStringArray(R.array.face);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, neededList);
            LVsubtags.setAdapter(adapter);
            idslist= new int[]{12,13,14,15,16,17,2};
        }
        else if(subTagsId==3) {
            neededList = this.getResources().getStringArray(R.array.nails);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, neededList);
            LVsubtags.setAdapter(adapter);
            idslist= new int[]{18,19,20,21,22,23,4};
        }
        else if(subTagsId==4) {
            neededList = this.getResources().getStringArray(R.array.body);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, neededList);
            LVsubtags.setAdapter(adapter);
            idslist= new int[]{24,25,26,27,28,29,5};
        }
        else if(subTagsId==5) {
            neededList = this.getResources().getStringArray(R.array.hairRemoval);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, neededList);
            LVsubtags.setAdapter(adapter);
            idslist= new int[]{30,31,32,33,34,6};
        }
        final int[] finalIdslist = idslist;
        final
        listviewtagAdapter listviewtagAdapter=new listviewtagAdapter(this,neededList);
        LVsubtags.setAdapter(listviewtagAdapter);
        LVsubtags.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),allFilteredSalonsByTag.class);
                intent.putExtra("tag",finalIdslist[position]);
                startActivity(intent);
            }
        });
    }

    public void onClickGoHome(View view) {
        Intent intent =new Intent(this, home.class);
        startActivity(intent);
    }
    class listviewtagAdapter extends ArrayAdapter<String>{
        Context context;
        String[] neededList;
        listviewtagAdapter(Context context,String[] neededList){
            super(context,R.layout.listviewlayouttags,R.id.tag,neededList);
            this.context=context;
            this.neededList=neededList;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View listviewlayouttags=layoutInflater.inflate(R.layout.listviewlayouttags,parent,false);
            TextView tag=listviewlayouttags.findViewById(R.id.tag);
            tag.setText(neededList[position]);
            return super.getView(position, convertView, parent);
        }
    }
}
