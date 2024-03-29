package showUserAppointments;

import android.os.AsyncTask;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nal.R;


import java.util.ArrayList;

import static mySQLInteractions.sqlInteractions.deleteAppointment;

public class AppointmentsListAdapter extends RecyclerView.Adapter<AppointmentsListAdapter.AppointmentsViewHolder> {

    private ArrayList<AppointmentObject> AppointmentsList;

    public class AppointmentsViewHolder extends RecyclerView.ViewHolder{
        public ImageView arrowImg;
        public ImageView salonLogo;
        public Button cancelAppointment;
        public TextView SalonName;
        public String SalonID;
        public String UserID;
        public String serviceID;
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
            salonLogo = itemView.findViewById(R.id.salonLogoImg);
            cancelAppointment = itemView.findViewById(R.id.CancelAppointmentButton);
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

    class AsyncDeleteAppointment extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            System.out.println("ASYNC DELETE 1");
        }

        @Override
        protected Void doInBackground(String... pStrings) {

            String userID,salonID,date,serviceID,startTime,endTime;
            userID = pStrings[0];
            salonID = pStrings[1];
            date = pStrings[2];
            serviceID = pStrings[3];
            startTime = pStrings[4];
            endTime= pStrings[5];


            deleteAppointment(userID,salonID,date,serviceID,startTime,endTime);

            return null;

        }

        @Override
        protected void onPostExecute(Void a) {
            System.out.println("ASYNC DELETE 3");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final AppointmentsViewHolder holder, final int position) {
        final AppointmentObject currentAppointment = AppointmentsList.get(position);


        holder.MasterFirst.setText(currentAppointment.getMasterFirst());
        holder.MasterLast.setText(currentAppointment.getMasterLast());
        holder.ServiceName.setText(currentAppointment.getServiceName());
        holder.SalonName.setText(currentAppointment.getSalonName());
        holder.Date.setText(currentAppointment.getServiceDate());
        holder.StartTime.setText(currentAppointment.getServiceStartTime());
        holder.EndTime.setText(currentAppointment.getServiceEndTime());
        holder.salonLogo.setImageBitmap(currentAppointment.getSalonLogo());
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

        holder.SalonID = currentAppointment.getSalonId();
        holder.UserID = "nikitalyakhovoy@gmail.com";
        holder.serviceID = currentAppointment.getServiceId();

        holder.cancelAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.CVappointment.setVisibility(View.GONE);
                AppointmentsList.remove(position);

                PersonalAppointmentsList.appointmentsAdapter.notifyItemRemoved(position);
                String[] param = {holder.UserID,holder.SalonID,currentAppointment.serviceDate,holder.serviceID,currentAppointment.serviceStartTime,currentAppointment.serviceEndTime};
                new AsyncDeleteAppointment().execute(param);


            }
        });


    }

    @Override
    public int getItemCount() {

        return AppointmentsList.size();
    }
}
