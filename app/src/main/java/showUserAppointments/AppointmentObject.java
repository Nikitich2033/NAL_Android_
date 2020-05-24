package showUserAppointments;

import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.text.format.Time;

import java.util.Date;


public class AppointmentObject {

    private int SalonLogo;
    public  String UserId;
    public String SalonId;
    public String ServiceId;
    public String UserFirst;
    public String UserMiddle;
    public String UserLast;
    public String ServiceName;
    public String serviceDate;
    public String serviceStartTime;
    public String serviceEndTime;

    public AppointmentObject(int salonLogo,String UserId, String SalonID, String ServiceId, String userFirst, String userMiddle, String userLast, String serviceName, String serviceDate, String serviceStartTime, String serviceEndTime) {
        SalonLogo = salonLogo;
        this.UserId = UserId;
        SalonId = SalonID;
        this.ServiceId = ServiceId;
        UserFirst = userFirst;
        UserMiddle = userMiddle;
        UserLast = userLast;
        ServiceName = serviceName;
        this.serviceDate = serviceDate;
        this.serviceStartTime = serviceStartTime;
        this.serviceEndTime = serviceEndTime;
    }

    public String getUserId(){return UserId;}

    public String getSalonId(){return SalonId;}

    public String getServiceDate() {
        return serviceDate;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public String getUserFirst() {
        return UserFirst;
    }

    public String getUserLast() {
        return UserLast;
    }

    public String getUserMiddle() {
        return UserMiddle;
    }

    public String getServiceEndTime() {
        return serviceEndTime;
    }

    public String getServiceStartTime() {
        return serviceStartTime;
    }

    public int getSalonLogo() {
        return SalonLogo;
    }
}
