package makeAppointment;

import android.os.AsyncTask;

import java.sql.Time;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import mySQLInteractions.sqlInteractions;

public class salonObject {
    private String SalonId;
    private String name;
    private String adressLine1;
    private String adressLine2;
    private String eMail;
    private String tel1;
    private String tel2;
    private ArrayList<Time> openTimes;

    public salonObject(String SalonId,String name,String adressLine1,String adressLine2,String eMail,String tel1,String tel2,ArrayList<Time> openTimes){
        this.SalonId=SalonId;
        this.name=name;
        this.adressLine1=adressLine1;
        this.adressLine2=adressLine2;
        this.eMail=eMail;
        this.tel1=tel1;
        this.tel2=tel2;
        this.openTimes=openTimes;
    }

    public String getSalonId() {
        return SalonId;
    }

    public String getName() {
        return name;
    }

    public String getAdressLine1() {
        return adressLine1;
    }

    public String getAdressLine2() {
        return adressLine2;
    }

    public String geteMail() {
        return eMail;
    }

    public String getTel1() {
        return tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public ArrayList<Time> getOpenTimes() {
        return openTimes;
    }
}
