package makeAppointment;

import android.content.Intent;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nal.R;
import com.zolad.zoominimageview.ZoomInImageView;

import java.sql.Time;
import java.util.ArrayList;

public class rvAdapterFilteredSalonsWithService extends RecyclerView.Adapter<rvAdapterFilteredSalonsWithService.salonObjectHolder> {
    private ArrayList<salonObject> salonObjects=new ArrayList<>();

    public rvAdapterFilteredSalonsWithService(ArrayList<salonObject> salonObjects){
        this.salonObjects=salonObjects;
    }

    @NonNull
    @Override
    public salonObjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.salonandservicedisplay,parent,false);
        return new salonObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final salonObjectHolder holder, int position) {
        final salonObject SalonObject=salonObjects.get(position);
        holder.TVname.setText(SalonObject.getName());
        holder.TVadressLine1.setText(SalonObject.getAdressLine1());
        holder.TVadressLine2.setText(SalonObject.getAdressLine2());
        holder.TVeMail.setText(SalonObject.geteMail());
        holder.TVtel1.setText(SalonObject.getTel1());
        holder.TVtel2.setText(SalonObject.getTel2());
        ArrayList<Time> openTimes=SalonObject.getOpenTimes();
        TextView[] daysTVs={holder.TVmondayTime,holder.TVtuesdayTime,holder.TVwednesdayTime,
                holder.TVthursdayTime,holder.TVfridayTime,holder.TVsaturdayTime,holder.TVsundayTime};
        holder.IVLogo.setImageBitmap(SalonObject.getLogoImage());
        for (int i=0;i<7;i=i+1){
            if(openTimes.get(i*2)!=null){
                String hourStart=String.valueOf(openTimes.get(i*2).getHours());
                String hourEnd=String.valueOf(openTimes.get(i*2+1).getHours());
                String minutesStart=String.valueOf(openTimes.get(i*2).getMinutes());
                String minutesEnd=String.valueOf(openTimes.get(i*2+1).getMinutes());
                if(hourStart.length()==1){
                    hourStart="0"+hourStart;
                }
                if(hourEnd.length()==1){
                    hourEnd="0"+hourEnd;
                }
                if(minutesStart.equals("0")){
                    minutesStart="00";
                }
                if(minutesEnd.equals("0")){
                    minutesEnd="00";
                }
                daysTVs[i].setText(hourStart+":"+minutesStart+"-"+hourEnd+":"+minutesEnd);
            }
            else {
                daysTVs[i].setText("Закрыт");
            }
        }
        holder.TVworkingHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.expandOpenTime.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(holder.CVsalon, new AutoTransition());
                    holder.expandOpenTime.setVisibility(View.VISIBLE);
                    holder.arrowButton.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                } else {
                    TransitionManager.beginDelayedTransition(holder.CVsalon, new AutoTransition());
                    holder.expandOpenTime.setVisibility(View.GONE);
                    holder.arrowButton.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                }
            }
        });
        holder.TVsalonDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.expandSalonDetails.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(holder.CVsalon, new AutoTransition());
                    holder.expandSalonDetails.setVisibility(View.VISIBLE);
                    holder.arrowButton2.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                } else {
                    TransitionManager.beginDelayedTransition(holder.CVsalon, new AutoTransition());
                    holder.expandSalonDetails.setVisibility(View.GONE);
                    holder.arrowButton2.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                }
            }
        });
        holder.chooseSalon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),SalonOptions.class);
                intent.putExtra("SalonId",SalonObject.getSalonId());
                intent.putExtra("SalonName",SalonObject.getName());
                v.getContext().startActivity(intent);
            }
        });
        holder.treatmentNameTV.setText(SalonObject.getServiceName());
        holder.treatmentDurationTV.setText(String.valueOf(SalonObject.getDurationMin()));
        holder.treatmentPriceTV.setText(String.valueOf(SalonObject.getPrice()));
        if (SalonObject.getImageBitmaps().get(0)==null&&SalonObject.getImageBitmaps().get(1)==null&&
                SalonObject.getImageBitmaps().get(2)==null&&SalonObject.getImageBitmaps().get(3)==null&&
                SalonObject.getImageBitmaps().get(4)==null){
            holder.expandableImages.setVisibility(View.GONE);
        }else {
            for(int i=0;i<5;i=i+1){
                if(SalonObject.getImageBitmaps().get(i)==null){
                    holder.ImageViews[i].setVisibility(View.INVISIBLE);
                }
                else {
                    holder.ImageViews[i].setImageBitmap(SalonObject.getImageBitmaps().get(i));
                }
            }
        }
        holder.chooseTreatment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),checkMasters.class);
                intent.putExtra("ServiceId",SalonObject.getServiceId());
                intent.putExtra("SalonId",SalonObject.getSalonId());
                intent.putExtra("treatmentDuration",SalonObject.getDurationMin());
                intent.putExtra("treatmentName",SalonObject.getServiceName());
                Log.i("lolo",SalonObject.getSalonId());
                Log.i("lolo",SalonObject.getServiceId());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return salonObjects.size();
    }

    class salonObjectHolder extends RecyclerView.ViewHolder{
        private TextView treatmentNameTV;
        private TextView treatmentDurationTV;
        private TextView treatmentPriceTV;
        private Button chooseTreatment;
        private ScrollView expandableImages;
        private ZoomInImageView[] ImageViews=new ZoomInImageView[5];
        private Button chooseSalon;
        private TextView TVworkingHours;
        private TextView TVsalonDetails;
        private ConstraintLayout expandOpenTime;
        private ConstraintLayout expandSalonDetails;
        private CardView CVsalon;
        private ImageView arrowButton;
        private ImageView arrowButton2;
        private TextView TVmonday;
        private TextView TVtuesday;
        private TextView TVwednesday;
        private TextView TVthursday;
        private TextView TVfriday;
        private TextView TVsaturday;
        private TextView TVsunday;
        private TextView TVname;
        private TextView TVadressLine1;
        private TextView TVadressLine2;
        private TextView TVeMail;
        private TextView TVtel1;
        private TextView TVtel2;
        private TextView TVmondayTime;
        private TextView TVtuesdayTime;
        private TextView TVwednesdayTime;
        private TextView TVthursdayTime;
        private TextView TVfridayTime;
        private TextView TVsaturdayTime;
        private TextView TVsundayTime;
        private ImageView IVLogo;
        public salonObjectHolder(@NonNull View itemView) {
            super(itemView);
            arrowButton=itemView.findViewById(R.id.arrowButton);
            CVsalon=itemView.findViewById(R.id.CVsalon);
            expandOpenTime=itemView.findViewById(R.id.expandOpenTime);
            chooseSalon=itemView.findViewById(R.id.chooseSalon);
            TVmonday=itemView.findViewById(R.id.TVmonday);
            TVtuesday=itemView.findViewById(R.id.TVtuesday);
            TVwednesday=itemView.findViewById(R.id.TVwednesday);
            TVthursday=itemView.findViewById(R.id.TVthursday);
            TVfriday=itemView.findViewById(R.id.TVfriday);
            TVsaturday=itemView.findViewById(R.id.TVsaturday);
            TVsunday=itemView.findViewById(R.id.TVsunday);
            TVworkingHours=itemView.findViewById(R.id.TVworkingHours);
            TVname=itemView.findViewById(R.id.TVname);
            TVadressLine1=itemView.findViewById(R.id.TVadressLine1);
            TVadressLine2=itemView.findViewById(R.id.TVadressLine2);
            TVeMail=itemView.findViewById(R.id.TVeMail);
            TVtel1=itemView.findViewById(R.id.TVtel1);
            TVtel2=itemView.findViewById(R.id.TVtel2);
            TVmondayTime=itemView.findViewById(R.id.TVmondayTime);
            TVtuesdayTime=itemView.findViewById(R.id.TVtuesdayTime);
            TVwednesdayTime=itemView.findViewById(R.id.TVwednesdayTime);
            TVthursdayTime=itemView.findViewById(R.id.TVthursdayTime);
            TVfridayTime=itemView.findViewById(R.id.TVfridayTime);
            TVsaturdayTime=itemView.findViewById(R.id.TVsaturdayTime);
            TVsundayTime=itemView.findViewById(R.id.TVsundayTime);
            IVLogo=itemView.findViewById(R.id.IVLogo);
            expandSalonDetails=itemView.findViewById(R.id.expandSalonDetails);
            TVsalonDetails=itemView.findViewById(R.id.TVsalonDetails);
            arrowButton2=itemView.findViewById(R.id.arrowButton2);
            treatmentNameTV=itemView.findViewById(R.id.treatmentNameTV);
            treatmentDurationTV=itemView.findViewById(R.id.treatmentDurationTV);
            treatmentPriceTV=itemView.findViewById(R.id.treatmentPriceTV);
            chooseTreatment=itemView.findViewById(R.id.chooseTreatment);
            ImageViews[0]=itemView.findViewById(R.id.IV1);
            ImageViews[1]=itemView.findViewById(R.id.IV2);
            ImageViews[2]=itemView.findViewById(R.id.IV3);
            ImageViews[3]=itemView.findViewById(R.id.IV4);
            ImageViews[4]=itemView.findViewById(R.id.IV5);
            expandableImages=itemView.findViewById(R.id.expandableImages);
        }
    }
}
