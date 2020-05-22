package showUserAppointments;

import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.text.format.Time;

import java.util.Date;


public class AppointmentObject {

    private int SalonLogo;
    private String UserFirst;
    private String UserMiddle;
    private String UserLast;
    private String ServiceName;
    private String serviceDate;
    private String serviceStartTime;
    private String serviceEndTime;

    public AppointmentObject(int salonLogo,String userFirst, String userMiddle, String userLast, String serviceName, String serviceDate, String serviceStartTime, String serviceEndTime) {
        SalonLogo = salonLogo;
        UserFirst = userFirst;
        UserMiddle = userMiddle;
        UserLast = userLast;
        ServiceName = serviceName;
        this.serviceDate = serviceDate;
        this.serviceStartTime = serviceStartTime;
        this.serviceEndTime = serviceEndTime;
    }

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
