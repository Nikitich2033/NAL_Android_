package makeAppointment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nal.R;

import java.util.ArrayList;

import mySQLInteractions.sqlInteractions;

public class rvAdapterTreatments extends RecyclerView.Adapter<rvAdapterTreatments.treatmentsObjectHolder> {
    private ArrayList<Treatment> treatmentsObjects=new ArrayList<>();

    public rvAdapterTreatments(ArrayList<Treatment> treatmentsObjects) {
        this.treatmentsObjects = treatmentsObjects;
    }

    @NonNull
    @Override
    public treatmentsObjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.treatment_detail,parent,false);
        return new treatmentsObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull treatmentsObjectHolder holder, int position) {
        final Treatment treatmentObject=treatmentsObjects.get(position);
        holder.treatmentNameTV.setText(treatmentObject.getServiceName());
        holder.treatmentDurationTV.setText(String.valueOf(treatmentObject.getDurationMin()));
        holder.treatmentPriceTV.setText(String.valueOf(treatmentObject.getPrice()));
        holder.chooseTreatment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),checkMasters.class);
                intent.putExtra("ServiceId",treatmentObject.getServiceId());
                intent.putExtra("treatmentDuration",treatmentObject.getDurationMin());
                intent.putExtra("treatmentName",treatmentObject.getServiceName());
                v.getContext().startActivity(intent);
            }
        });
        if (treatmentObject.getImageBitmaps()==null){
            holder.expandableImages.setVisibility(View.GONE);
        }else {
            for(int i=0;i<treatmentObject.getImageBitmaps().size();i=i+1){
                holder.ImageViews[i].setImageBitmap(treatmentObject.getImageBitmaps().get(i));

            }
            for(int a=treatmentObject.getImageBitmaps().size();a<5;a=a+1){
                holder.ImageViews[a].setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return treatmentsObjects.size();
    }

    class treatmentsObjectHolder extends RecyclerView.ViewHolder{
        private TextView treatmentNameTV;
        private TextView treatmentDurationTV;
        private TextView treatmentPriceTV;
        private Button chooseTreatment;
        private ScrollView expandableImages;
        private ImageView[] ImageViews=new ImageView[5];
        public treatmentsObjectHolder(@NonNull View itemView) {
            super(itemView);
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
