package makeAppointment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.nal.R;

import java.util.ArrayList;

import home.home;
import mySQLInteractions.sqlInteractions;

public class SalonOptions extends AppCompatActivity {
    public static String SalonId;
    private String SalonName;
    private ArrayList<String> TreatmentsIds=new ArrayList<>();
    private ArrayList<Treatment> treatmentsObjects=new ArrayList<>();
    private ProgressBar progressBar3;
    private LinearLayout LinearLayoutGroups;
    private LinearLayout[] allLLs;
    private TextView[] allMainTVs;
    private TextView[] allSubTVs;
    private RecyclerView[] allRecyclers;
    private TextView TVSalonName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon_options);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        TVSalonName=findViewById(R.id.TVSalonName);
        Intent intent=getIntent();
        SalonName=intent.getStringExtra("SalonName");
        TVSalonName.setText(SalonName);
        LinearLayoutGroups = findViewById(R.id.LinearLayoutGroups);
        allLLs = new LinearLayout[]{findViewById(R.id.LLHair), findViewById(R.id.LLFace), findViewById(R.id.LLmakeUP),
                findViewById(R.id.LLNails), findViewById(R.id.LLBody), findViewById(R.id.LLhairRemoval),
                findViewById(R.id.LLotherOther)};
        allMainTVs = new TextView[]{findViewById(R.id.hairTV), findViewById(R.id.faceTV),
                findViewById(R.id.makeUPTV), findViewById(R.id.nailsTV), findViewById(R.id.bodyTV),
                findViewById(R.id.hairRemovalTV), findViewById(R.id.otherOtherTV)};
        allSubTVs = new TextView[]{findViewById(R.id.femaleCutTV), findViewById(R.id.BlowDryTV),
                findViewById(R.id.HairColoringTV), findViewById(R.id.maleCutTV), findViewById(R.id.BalayageOmbreTV),
                findViewById(R.id.HairOtherTV), findViewById(R.id.eyelashextensionTV), findViewById(R.id.eyebrowColoringTV),
                findViewById(R.id.eyebrowThreadingTV), findViewById(R.id.eyebrowWaxingTV),
                findViewById(R.id.eyebrowLaminatingTV), findViewById(R.id.facialClassicTV), findViewById(R.id.faceOtherTV),
                findViewById(R.id.makeUPTV2),findViewById(R.id.pedicureTV), findViewById(R.id.manicureTV),
                findViewById(R.id.gelRemovalTV), findViewById(R.id.gelManicureTV),
                findViewById(R.id.gelPedicureTV), findViewById(R.id.nailExtensionTV), findViewById(R.id.nailOtherTV),
                findViewById(R.id.sprayTanningTV), findViewById(R.id.bodyScrabTV),
                findViewById(R.id.bodyWrapTV), findViewById(R.id.colonicTherapyTV),
                findViewById(R.id.cryolipolysisTV), findViewById(R.id.anticelluliteTV), findViewById(R.id.bodyOtherTV),
                findViewById(R.id.femaleWaxingTV), findViewById(R.id.hollywoodWaxingTV),
                findViewById(R.id.brazillianWaxingTV), findViewById(R.id.maleWaxingTV),
                findViewById(R.id.laserRemovalTV), findViewById(R.id.hairRemovalOtherTV),findViewById(R.id.otherOtherTV2)};
        allRecyclers = new RecyclerView[]{findViewById(R.id.femaleCutRV), findViewById(R.id.BlowDryRV),
                findViewById(R.id.HairColoringRV), findViewById(R.id.maleCutRV), findViewById(R.id.BalayageOmbreRV),
                findViewById(R.id.HairOtherRV), findViewById(R.id.eyelashextensionRV), findViewById(R.id.eyebrowColoringRV),
                findViewById(R.id.eyebrowThreadingRV), findViewById(R.id.eyebrowWaxingRV),
                findViewById(R.id.eyebrowLaminatingRV), findViewById(R.id.facialClassicRV), findViewById(R.id.faceOtherRV),
                findViewById(R.id.makeUPRV), findViewById(R.id.pedicureRV), findViewById(R.id.manicureRV),
                findViewById(R.id.gelRemovalRV), findViewById(R.id.gelManicureRV),
                findViewById(R.id.gelPedicureRV), findViewById(R.id.nailExtensionRV), findViewById(R.id.nailOtherRV),
                findViewById(R.id.sprayTanningRV), findViewById(R.id.bodyScrabRV),
                findViewById(R.id.bodyWrapRV), findViewById(R.id.colonicTherapyRV),
                findViewById(R.id.cryolipolysisRV), findViewById(R.id.anticelluliteRV), findViewById(R.id.bodyOtherRV),
                findViewById(R.id.femaleWaxingRV), findViewById(R.id.hollywoodWaxingRV),
                findViewById(R.id.brazillianWaxingRV), findViewById(R.id.maleWaxingRV),
                findViewById(R.id.laserRemovalRV), findViewById(R.id.hairRemovalOtherRV),
                findViewById(R.id.otherOtherRV)};
        progressBar3 = findViewById(R.id.progressBar1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new getIDsAsyncAndObjects().execute();

    }

    @Override
    public void onBackPressed() {
        if(false) {
            super.onBackPressed();
        }
    }

    public void onClickGoHome(View view) {
        Intent intent1=new Intent(this, home.class);
        startActivity(intent1);
    }

    class getIDsAsyncAndObjects extends AsyncTask<Void, Void,Void > {

        @Override
        protected Void doInBackground(Void... voids) {
            Intent intent=getIntent();
            SalonId=intent.getStringExtra("SalonId");
            TreatmentsIds=sqlInteractions.getTreatmentsIds(SalonId);
            treatmentsObjects=sqlInteractions.getObjectsTreatments(TreatmentsIds,SalonId);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ArrayList<Integer> foundTags=new ArrayList<>();
            for(int i=0;i<treatmentsObjects.size();i=i+1){
                if(!(foundTags.contains(treatmentsObjects.get(i).getTag()))) {
                    foundTags.add(treatmentsObjects.get(i).getTag());
                }
            }
            ArrayList<Treatment> a1=new ArrayList<>();
            ArrayList<Treatment> a2=new ArrayList<>();
            ArrayList<Treatment> a3=new ArrayList<>();
            ArrayList<Treatment> a4=new ArrayList<>();
            ArrayList<Treatment> a5=new ArrayList<>();
            ArrayList<Treatment> a6=new ArrayList<>();
            ArrayList<Treatment> a7=new ArrayList<>();
            ArrayList<Treatment> a8=new ArrayList<>();
            ArrayList<Treatment> a9=new ArrayList<>();
            ArrayList<Treatment> a10=new ArrayList<>();
            ArrayList<Treatment> a11=new ArrayList<>();
            ArrayList<Treatment> a12=new ArrayList<>();
            ArrayList<Treatment> a13=new ArrayList<>();
            ArrayList<Treatment> a14=new ArrayList<>();
            ArrayList<Treatment> a15=new ArrayList<>();
            ArrayList<Treatment> a16=new ArrayList<>();
            ArrayList<Treatment> a17=new ArrayList<>();
            ArrayList<Treatment> a18=new ArrayList<>();
            ArrayList<Treatment> a19=new ArrayList<>();
            ArrayList<Treatment> a20=new ArrayList<>();
            ArrayList<Treatment> a21=new ArrayList<>();
            ArrayList<Treatment> a22=new ArrayList<>();
            ArrayList<Treatment> a23=new ArrayList<>();
            ArrayList<Treatment> a24=new ArrayList<>();
            ArrayList<Treatment> a25=new ArrayList<>();
            ArrayList<Treatment> a26=new ArrayList<>();
            ArrayList<Treatment> a27=new ArrayList<>();
            ArrayList<Treatment> a28=new ArrayList<>();
            ArrayList<Treatment> a29=new ArrayList<>();
            ArrayList<Treatment> a30=new ArrayList<>();
            ArrayList<Treatment> a31=new ArrayList<>();
            ArrayList<Treatment> a32=new ArrayList<>();
            ArrayList<Treatment> a33=new ArrayList<>();
            ArrayList<Treatment> a34=new ArrayList<>();
            ArrayList<Treatment> a35=new ArrayList<>();
            for (int i=0;i<treatmentsObjects.size();i=i+1){
                if(treatmentsObjects.get(i).getTag()==1){
                    a1.add(treatmentsObjects.get(i));
                } else if(treatmentsObjects.get(i).getTag()==2){
                    a2.add(treatmentsObjects.get(i));
                } else if(treatmentsObjects.get(i).getTag()==3){
                    a3.add(treatmentsObjects.get(i));
                }else if(treatmentsObjects.get(i).getTag()==4){
                    a4.add(treatmentsObjects.get(i));
                }else if(treatmentsObjects.get(i).getTag()==5){
                    a5.add(treatmentsObjects.get(i));
                }else if(treatmentsObjects.get(i).getTag()==6){
                    a6.add(treatmentsObjects.get(i));
                }else if(treatmentsObjects.get(i).getTag()==7){
                    a7.add(treatmentsObjects.get(i));
                }else if(treatmentsObjects.get(i).getTag()==8){
                    a8.add(treatmentsObjects.get(i));
                }else if(treatmentsObjects.get(i).getTag()==9){
                    a9.add(treatmentsObjects.get(i));
                }else if(treatmentsObjects.get(i).getTag()==10){
                    a10.add(treatmentsObjects.get(i));
                }else if(treatmentsObjects.get(i).getTag()==11){
                    a11.add(treatmentsObjects.get(i));
                }else if(treatmentsObjects.get(i).getTag()==12){
                    a12.add(treatmentsObjects.get(i));
                }else if(treatmentsObjects.get(i).getTag()==13){
                    a13.add(treatmentsObjects.get(i));
                }else if(treatmentsObjects.get(i).getTag()==14){
                    a14.add(treatmentsObjects.get(i));
                }else if(treatmentsObjects.get(i).getTag()==15){
                    a15.add(treatmentsObjects.get(i));
                }else if(treatmentsObjects.get(i).getTag()==16){
                    a16.add(treatmentsObjects.get(i));
                }else if(treatmentsObjects.get(i).getTag()==17){
                    a17.add(treatmentsObjects.get(i));
                }else if(treatmentsObjects.get(i).getTag()==18){
                    a18.add(treatmentsObjects.get(i));
                }else if(treatmentsObjects.get(i).getTag()==19){
                    a19.add(treatmentsObjects.get(i));
                }else if(treatmentsObjects.get(i).getTag()==20){
                    a20.add(treatmentsObjects.get(i));
                }else if(treatmentsObjects.get(i).getTag()==21){
                    a21.add(treatmentsObjects.get(i));
                }else if(treatmentsObjects.get(i).getTag()==22){
                    a22.add(treatmentsObjects.get(i));
                }else if(treatmentsObjects.get(i).getTag()==23){
                    a23.add(treatmentsObjects.get(i));
                }else if(treatmentsObjects.get(i).getTag()==24){
                    a24.add(treatmentsObjects.get(i));
                }else if(treatmentsObjects.get(i).getTag()==25){
                    a25.add(treatmentsObjects.get(i));
                }else if(treatmentsObjects.get(i).getTag()==26){
                    a26.add(treatmentsObjects.get(i));
                }else if(treatmentsObjects.get(i).getTag()==27){
                    a27.add(treatmentsObjects.get(i));
                }else if(treatmentsObjects.get(i).getTag()==28){
                    a28.add(treatmentsObjects.get(i));
                }else if(treatmentsObjects.get(i).getTag()==29){
                    a29.add(treatmentsObjects.get(i));
                }else if(treatmentsObjects.get(i).getTag()==30){
                    a30.add(treatmentsObjects.get(i));
                }else if(treatmentsObjects.get(i).getTag()==31){
                    a31.add(treatmentsObjects.get(i));
                }else if(treatmentsObjects.get(i).getTag()==32){
                    a32.add(treatmentsObjects.get(i));
                }else if(treatmentsObjects.get(i).getTag()==33){
                    a33.add(treatmentsObjects.get(i));
                }else if(treatmentsObjects.get(i).getTag()==34){
                    a34.add(treatmentsObjects.get(i));
                }else if(treatmentsObjects.get(i).getTag()==35){
                    a35.add(treatmentsObjects.get(i));
                }
            }
            rvAdapterTreatments rvAdapterTreatments1=new rvAdapterTreatments(a1);
            allRecyclers[5].setLayoutManager(new LinearLayoutManager(SalonOptions.this));
            allRecyclers[5].setAdapter(rvAdapterTreatments1);
            rvAdapterTreatments rvAdapterTreatments2=new rvAdapterTreatments(a2);
            allRecyclers[12].setLayoutManager(new LinearLayoutManager(SalonOptions.this));
            allRecyclers[12].setAdapter(rvAdapterTreatments2);
            rvAdapterTreatments rvAdapterTreatments3=new rvAdapterTreatments(a3);
            allRecyclers[13].setLayoutManager(new LinearLayoutManager(SalonOptions.this));
            allRecyclers[13].setAdapter(rvAdapterTreatments3);
            rvAdapterTreatments rvAdapterTreatments4=new rvAdapterTreatments(a4);
            allRecyclers[20].setLayoutManager(new LinearLayoutManager(SalonOptions.this));
            allRecyclers[20].setAdapter(rvAdapterTreatments4);
            rvAdapterTreatments rvAdapterTreatments5=new rvAdapterTreatments(a5);
            allRecyclers[27].setLayoutManager(new LinearLayoutManager(SalonOptions.this));
            allRecyclers[27].setAdapter(rvAdapterTreatments5);
            rvAdapterTreatments rvAdapterTreatments6=new rvAdapterTreatments(a6);
            allRecyclers[33].setLayoutManager(new LinearLayoutManager(SalonOptions.this));
            allRecyclers[33].setAdapter(rvAdapterTreatments6);
            rvAdapterTreatments rvAdapterTreatments7=new rvAdapterTreatments(a7);
            allRecyclers[0].setLayoutManager(new LinearLayoutManager(SalonOptions.this));
            allRecyclers[0].setAdapter(rvAdapterTreatments7);
            rvAdapterTreatments rvAdapterTreatments8=new rvAdapterTreatments(a8);
            allRecyclers[1].setLayoutManager(new LinearLayoutManager(SalonOptions.this));
            allRecyclers[1].setAdapter(rvAdapterTreatments8);
            rvAdapterTreatments rvAdapterTreatments9=new rvAdapterTreatments(a9);
            allRecyclers[2].setLayoutManager(new LinearLayoutManager(SalonOptions.this));
            allRecyclers[2].setAdapter(rvAdapterTreatments9);
            rvAdapterTreatments rvAdapterTreatments10=new rvAdapterTreatments(a10);
            allRecyclers[3].setLayoutManager(new LinearLayoutManager(SalonOptions.this));
            allRecyclers[3].setAdapter(rvAdapterTreatments10);
            rvAdapterTreatments rvAdapterTreatments11=new rvAdapterTreatments(a11);
            allRecyclers[4].setLayoutManager(new LinearLayoutManager(SalonOptions.this));
            allRecyclers[4].setAdapter(rvAdapterTreatments11);
            rvAdapterTreatments rvAdapterTreatments12=new rvAdapterTreatments(a12);
            allRecyclers[6].setLayoutManager(new LinearLayoutManager(SalonOptions.this));
            allRecyclers[6].setAdapter(rvAdapterTreatments12);
            rvAdapterTreatments rvAdapterTreatments13=new rvAdapterTreatments(a13);
            allRecyclers[7].setLayoutManager(new LinearLayoutManager(SalonOptions.this));
            allRecyclers[7].setAdapter(rvAdapterTreatments13);
            rvAdapterTreatments rvAdapterTreatments14=new rvAdapterTreatments(a14);
            allRecyclers[8].setLayoutManager(new LinearLayoutManager(SalonOptions.this));
            allRecyclers[8].setAdapter(rvAdapterTreatments14);
            rvAdapterTreatments rvAdapterTreatments15=new rvAdapterTreatments(a15);
            allRecyclers[9].setLayoutManager(new LinearLayoutManager(SalonOptions.this));
            allRecyclers[9].setAdapter(rvAdapterTreatments15);
            rvAdapterTreatments rvAdapterTreatments16=new rvAdapterTreatments(a16);
            allRecyclers[10].setLayoutManager(new LinearLayoutManager(SalonOptions.this));
            allRecyclers[10].setAdapter(rvAdapterTreatments16);
            rvAdapterTreatments rvAdapterTreatments17=new rvAdapterTreatments(a17);
            allRecyclers[11].setLayoutManager(new LinearLayoutManager(SalonOptions.this));
            allRecyclers[11].setAdapter(rvAdapterTreatments17);
            rvAdapterTreatments rvAdapterTreatments18=new rvAdapterTreatments(a18);
            allRecyclers[14].setLayoutManager(new LinearLayoutManager(SalonOptions.this));
            allRecyclers[14].setAdapter(rvAdapterTreatments18);
            rvAdapterTreatments rvAdapterTreatments19=new rvAdapterTreatments(a19);
            allRecyclers[15].setLayoutManager(new LinearLayoutManager(SalonOptions.this));
            allRecyclers[15].setAdapter(rvAdapterTreatments19);
            rvAdapterTreatments rvAdapterTreatments20=new rvAdapterTreatments(a20);
            allRecyclers[16].setLayoutManager(new LinearLayoutManager(SalonOptions.this));
            allRecyclers[16].setAdapter(rvAdapterTreatments20);
            rvAdapterTreatments rvAdapterTreatments21=new rvAdapterTreatments(a21);
            allRecyclers[17].setLayoutManager(new LinearLayoutManager(SalonOptions.this));
            allRecyclers[17].setAdapter(rvAdapterTreatments21);
            rvAdapterTreatments rvAdapterTreatments22=new rvAdapterTreatments(a22);
            allRecyclers[18].setLayoutManager(new LinearLayoutManager(SalonOptions.this));
            allRecyclers[18].setAdapter(rvAdapterTreatments22);
            rvAdapterTreatments rvAdapterTreatments23=new rvAdapterTreatments(a23);
            allRecyclers[19].setLayoutManager(new LinearLayoutManager(SalonOptions.this));
            allRecyclers[19].setAdapter(rvAdapterTreatments23);
            rvAdapterTreatments rvAdapterTreatments24=new rvAdapterTreatments(a24);
            allRecyclers[21].setLayoutManager(new LinearLayoutManager(SalonOptions.this));
            allRecyclers[21].setAdapter(rvAdapterTreatments24);
            rvAdapterTreatments rvAdapterTreatments25=new rvAdapterTreatments(a25);
            allRecyclers[22].setLayoutManager(new LinearLayoutManager(SalonOptions.this));
            allRecyclers[22].setAdapter(rvAdapterTreatments25);
            rvAdapterTreatments rvAdapterTreatments26=new rvAdapterTreatments(a26);
            allRecyclers[23].setLayoutManager(new LinearLayoutManager(SalonOptions.this));
            allRecyclers[23].setAdapter(rvAdapterTreatments26);
            rvAdapterTreatments rvAdapterTreatments27=new rvAdapterTreatments(a27);
            allRecyclers[24].setLayoutManager(new LinearLayoutManager(SalonOptions.this));
            allRecyclers[24].setAdapter(rvAdapterTreatments27);
            rvAdapterTreatments rvAdapterTreatments28=new rvAdapterTreatments(a28);
            allRecyclers[25].setLayoutManager(new LinearLayoutManager(SalonOptions.this));
            allRecyclers[25].setAdapter(rvAdapterTreatments28);
            rvAdapterTreatments rvAdapterTreatments29=new rvAdapterTreatments(a29);
            allRecyclers[26].setLayoutManager(new LinearLayoutManager(SalonOptions.this));
            allRecyclers[26].setAdapter(rvAdapterTreatments29);
            rvAdapterTreatments rvAdapterTreatments30=new rvAdapterTreatments(a30);
            allRecyclers[28].setLayoutManager(new LinearLayoutManager(SalonOptions.this));
            allRecyclers[28].setAdapter(rvAdapterTreatments30);
            rvAdapterTreatments rvAdapterTreatments31=new rvAdapterTreatments(a31);
            allRecyclers[29].setLayoutManager(new LinearLayoutManager(SalonOptions.this));
            allRecyclers[29].setAdapter(rvAdapterTreatments31);
            rvAdapterTreatments rvAdapterTreatments32=new rvAdapterTreatments(a32);
            allRecyclers[30].setLayoutManager(new LinearLayoutManager(SalonOptions.this));
            allRecyclers[30].setAdapter(rvAdapterTreatments32);
            rvAdapterTreatments rvAdapterTreatments33=new rvAdapterTreatments(a33);
            allRecyclers[31].setLayoutManager(new LinearLayoutManager(SalonOptions.this));
            allRecyclers[31].setAdapter(rvAdapterTreatments33);
            rvAdapterTreatments rvAdapterTreatments34=new rvAdapterTreatments(a34);
            allRecyclers[32].setLayoutManager(new LinearLayoutManager(SalonOptions.this));
            allRecyclers[32].setAdapter(rvAdapterTreatments34);
            rvAdapterTreatments rvAdapterTreatments35=new rvAdapterTreatments(a35);
            allRecyclers[34].setLayoutManager(new LinearLayoutManager(SalonOptions.this));
            allRecyclers[34].setAdapter(rvAdapterTreatments35);
            allLLs[2].removeView(allSubTVs[13]);
            allLLs[6].removeView(allSubTVs[34]);
            int[] tagsList=null;
            int[] tagsList2=null;
            for(int i=0;i<7;i=i+1){
                if(i==0){
                    tagsList=new int[]{7, 8, 9, 10, 11, 1};
                    tagsList2=new int[]{0, 1, 2, 3, 4, 5};
                }
                else if(i==1){
                    tagsList=new int[]{12,13,14,15,16,17,2};
                    tagsList2=new int[]{6,7,6,9,10,11,12};
                }else if(i==2){
                    tagsList=new int[]{3};
                    tagsList2=new int[]{13};
                }else if(i==3){
                    tagsList=new int[]{18,19,20,21,22,23,4};
                    tagsList2=new int[]{14,15,16,17,18,19,20};
                }else if(i==4){
                    tagsList=new int[]{24,25,26,27,28,29,5};
                    tagsList2=new int[]{21,22,23,24,25,26,27};
                }else if(i==5){
                    tagsList=new int[]{30,31,32,33,34,6};
                    tagsList2=new int[]{28,29,30,31,32,33};
                }else if(i==6){
                    tagsList=new int[]{35};
                    tagsList2=new int[]{34};
                }
                boolean found=false;
                for (int l=0;l<tagsList.length;l=l+1){
                    if(foundTags.contains(tagsList[l])){
                        found=true;
                    }
                }
                if(found==false){
                    LinearLayoutGroups.removeView(allLLs[i]);
                    LinearLayoutGroups.removeView(allMainTVs[i]);
                }
                else {
                    for(int e=0;e<tagsList.length;e=e+1){
                        if(!(foundTags.contains(tagsList[e]))){
                            allLLs[i].removeView(allSubTVs[tagsList2[e]]);
                            allLLs[i].removeView(allRecyclers[tagsList2[e]]);
                        }
                    }
                }
            }

            progressBar3.setVisibility(View.GONE);
            LinearLayoutGroups.setVisibility(View.VISIBLE);
        }
    }

}
