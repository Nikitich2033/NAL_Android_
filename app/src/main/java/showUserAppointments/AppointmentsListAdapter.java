package showUserAppointments;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nal.R;

import java.util.ArrayList;

public class AppointmentsListAdapter extends RecyclerView.Adapter<AppointmentsListAdapter.AppointmentsViewHolder> {

    private ArrayList<AppointmentObject> AppointmentsList;

    public static class AppointmentsViewHolder extends RecyclerView.ViewHolder{
        public ImageView arrowImg;
        public TextView SalonName;
        public TextView MasterFirst;
        public TextView MasterLast;
        public TextView ServiceName;
        public TextView Date;
        public TextView StartTime;
        public TextView EndTime;
        public RelativeLayout RLExpandAppointment;
        public CardView CVappointment;


        public AppointmentsViewHolder(@NonNull View itemView) {
            super(itemView);
            arrowImg = itemView.findViewById(R.id.arrowButton);
            SalonName = itemView.findViewById(R.id.AppointmentSalonName);
            MasterFirst = itemView.findViewById(R.id.MasterFirstName);
            MasterLast = itemView.findViewById(R.id.MasterLastName);
            ServiceName = itemView.findViewById(R.id.AppointmentServiceName);
            Date = itemView.findViewById(R.id.AppointmentDate);
            StartTime = itemView.findViewById(R.id.AppointmentStartTime);
            EndTime = itemView.findViewById(R.id.AppointmentEndTime);
            RLExpandAppointment = itemView.findViewById(R.id.ExpandAppoitnmentDetails);
            CVappointment = itemView.findViewById(R.id.CVappointment);
        }
    }

    public AppointmentsListAdapter(ArrayList<AppointmentObject> appointmentsList){
        AppointmentsList = appointmentsList;
    }

    @NonNull
    @Override
    public AppointmentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_item,parent,false);
        AppointmentsViewHolder APH = new AppointmentsViewHolder(v);
        return APH;
    }


    @Override
    public void onBindViewHolder(@NonNull final AppointmentsViewHolder holder, int position) {
        AppointmentObject currentAppointment = AppointmentsList.get(position);


        holder.MasterFirst.setText(currentAppointment.getMasterFirst());
        holder.MasterLast.setText(currentAppointment.getMasterLast());
        holder.ServiceName.setText(currentAppointment.getServiceName());
        holder.SalonName.setText(currentAppointment.getSalonName());
        holder.Date.setText(currentAppointment.getServiceDate());
        holder.StartTime.setText(currentAppointment.getServiceStartTime());
        holder.EndTime.setText(currentAppointment.getServiceEndTime());
        holder.arrowImg.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
        holder.arrowImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.RLExpandAppointment.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(holder.CVappointment , new AutoTransition());
                    holder.RLExpandAppointment.setVisibility(View.VISIBLE);
                    holder.arrowImg.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                } else {
                    TransitionManager.beginDelayedTransition(holder.CVappointment, new AutoTransition());
                    holder.RLExpandAppointment.setVisibility(View.GONE);
                    holder.arrowImg.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                }
            }
        });



    }

    @Override
    public int getItemCount() {

        return AppointmentsList.size();
    }
}
