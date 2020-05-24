package showUserAppointments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nal.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AppointmentsListAdapter extends RecyclerView.Adapter<AppointmentsListAdapter.AppointmentsViewHolder> {

    private ArrayList<AppointmentObject> AppointmentsList;

    public static class AppointmentsViewHolder extends RecyclerView.ViewHolder{
        public ImageView SalonLogo;
        public TextView FirstName;
        public TextView LastName;
        public TextView ServiceName;
        public TextView Date;
        public TextView StartTime;
        public TextView EndTime;


        public AppointmentsViewHolder(@NonNull View itemView) {
            super(itemView);
            SalonLogo= itemView.findViewById(R.id.SalonImage);
            FirstName = itemView.findViewById(R.id.AppointmentFirstName);
            LastName = itemView.findViewById(R.id.AppointmentLastName);
            ServiceName = itemView.findViewById(R.id.AppointmentServiceName);
            Date = itemView.findViewById(R.id.AppointmentDate);
            StartTime = itemView.findViewById(R.id.AppointmentStartTime);
            EndTime = itemView.findViewById(R.id.AppointmentEndTime);


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
    public void onBindViewHolder(@NonNull AppointmentsViewHolder holder, int position) {
        AppointmentObject currentAppointment = AppointmentsList.get(position);

        holder.SalonLogo.setImageResource(R.drawable.ic_subject);
        holder.FirstName.setText(currentAppointment.getUserFirst());
        holder.LastName.setText(currentAppointment.getUserLast());
        holder.ServiceName.setText(currentAppointment.getServiceName());
        holder.Date.setText(currentAppointment.getServiceDate());
        holder.StartTime.setText(currentAppointment.getServiceStartTime());
        holder.EndTime.setText(currentAppointment.getServiceEndTime());


    }

    @Override
    public int getItemCount() {

        return AppointmentsList.size();
    }
}
