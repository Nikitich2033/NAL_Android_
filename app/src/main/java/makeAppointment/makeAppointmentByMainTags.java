package makeAppointment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nal.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import Constants.PreferenceUtils;
import home.home;
import mySQLInteractions.sqlInteractions;

public class makeAppointmentByMainTags extends AppCompatActivity {
    private ListView LVmaintags;
    private SearchView SVtreatments;
    private ListView LVallTreatments;
    private ArrayList<String> allTreatmentNames =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_appointment_by_main_tags);
        ActionBar actionBar= getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        LVallTreatments=findViewById(R.id.LVallTreatemnts);
        LVmaintags=findViewById(R.id.LVmaintags);
        SVtreatments=findViewById(R.id.SVtreatments);
        String[] allMaintags=this.getResources().getStringArray(R.array.treatments);
        try {
            allTreatmentNames=new Async().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        listviewtagAdapter listviewtagAdapter=new listviewtagAdapter(this,allMaintags);
        LVmaintags.setAdapter(listviewtagAdapter);
        LVmaintags.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==2) {
                    Intent intent=new Intent(getApplicationContext(),allFilteredSalonsByTag.class);
                    intent.putExtra("tag",3);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getApplicationContext(), makeAppointmentBySubTags.class);
                    intent.putExtra("subTagsId", position);
                    startActivity(intent);
                }
            }
        });
        final listviewsearchAdapter listviewsearchAdapter=new listviewsearchAdapter(this, allTreatmentNames);

        LVallTreatments.setAdapter(listviewsearchAdapter);
        SVtreatments.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                LVmaintags.setVisibility(View.INVISIBLE);
                LVallTreatments.setVisibility(View.VISIBLE);
                listviewsearchAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
    class Async extends AsyncTask<Void, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            return sqlInteractions.getTreatmentsNames();
        }
    }

    class listviewtagAdapter extends ArrayAdapter<String>{
        Context context;
        String[] allTreatments;
        listviewtagAdapter(Context context,String[] allTreatments){
            super(context,R.layout.listviewlayouttags,R.id.tag,allTreatments);
            this.context=context;
            this.allTreatments=allTreatments;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View listviewlayouttags=layoutInflater.inflate(R.layout.listviewlayouttags,parent,false);
            TextView tag=listviewlayouttags.findViewById(R.id.tag);
            tag.setText(allTreatments[position]);
            return super.getView(position, convertView, parent);
        }
    }

    class listviewsearchAdapter extends ArrayAdapter<String> implements Filterable {
        Context context;
        ArrayList<String> allTreatments;
        private ArrayList<String> allTreatmentsFiltered =new ArrayList<>();
        listviewsearchAdapter(Context context,ArrayList<String> allTreatments){
            super(context,R.layout.listviewlayoutsearch,R.id.treatmentName,allTreatments);
            this.context=context;
            this.allTreatments=allTreatments;
            this.allTreatmentsFiltered =allTreatments;
        }
        @Override
        public int getCount() {
            try {
                return allTreatmentsFiltered.size();
            }catch(NullPointerException e) {
                e.printStackTrace();
                return 0;
            }
        }
        @Override
        public String getItem(int position) {
            return allTreatmentsFiltered.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View listviewlayouttags=layoutInflater.inflate(R.layout.listviewlayoutsearch,parent,false);
            TextView treatmentName=listviewlayouttags.findViewById(R.id.treatmentName);
            treatmentName.setText(allTreatmentsFiltered.get(position));
            listviewlayouttags.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(makeAppointmentByMainTags.this, allFilteredSalonsByName.class);
                    intent.putExtra("ServiceName",allTreatmentsFiltered.get(position));
                    startActivity(intent);}
            });
            return listviewlayouttags;
        }
        @NonNull
        @Override
        public Filter getFilter() {
            Filter filter=new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if(constraint == null || constraint.length() == 0){
                        filterResults.count = allTreatments.size();
                        filterResults.values = allTreatments;
                    }else{
                        ArrayList<String> allTreatmentsresults = new ArrayList<>();
                        String searchStr = constraint.toString().toLowerCase();
                        for(String i: allTreatments){
                            if(i.toLowerCase().contains(searchStr) ){
                                allTreatmentsresults.add(i);
                                filterResults.count = allTreatmentsresults.size();
                                filterResults.values = allTreatmentsresults;
                            }
                        }
                    }
                    return filterResults;
                }
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    allTreatmentsFiltered = (ArrayList<String>)results.values;
                    notifyDataSetChanged();
                }
            };
            return filter;
        }
    }



    public void onClickGoHome(View view) {
        Intent intent=new Intent(this, home.class);
        startActivity(intent);
    }

    public void pressTitle(View view) {
        SVtreatments.clearFocus();
        LVmaintags.setVisibility(View.VISIBLE);
        LVallTreatments.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("DESTROYED");
        if (PreferenceUtils.getRememberFromPrefs(this).equals("false")) {
            PreferenceUtils.clearPrefs(this);
        }
    }
}
