package makeAppointment;

import android.graphics.Bitmap;
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
    private double Lat;
    private double Lan;
    private ArrayList<Time> openTimes;
    private Bitmap LogoImage;
    private String ServiceId;
    private String ServiceName;
    private int DurationMin;
    private double Price;

    public String getServiceId() {
        return ServiceId;
    }

    public salonObject(String salonId, String name, String adressLine1, String adressLine2, String eMail, String tel1, String tel2, ArrayList<Time> openTimes, Bitmap logoImage, String serviceId, String serviceName, int durationMin, double price, ArrayList<Bitmap> imageBitmaps) {
        SalonId = salonId;
        this.name = name;
        this.adressLine1 = adressLine1;
        this.adressLine2 = adressLine2;
        this.eMail = eMail;
        this.tel1 = tel1;
        this.tel2 = tel2;
        this.openTimes = openTimes;
        LogoImage = logoImage;
        ServiceId = serviceId;
        ServiceName = serviceName;
        DurationMin = durationMin;
        Price = price;
        this.imageBitmaps = imageBitmaps;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public int getDurationMin() {
        return DurationMin;
    }

    public double getPrice() {
        return Price;
    }

    public ArrayList<Bitmap> getImageBitmaps() {
        return imageBitmaps;
    }

    private ArrayList<Bitmap> imageBitmaps=new ArrayList<>();

    public double getLat() {
        return Lat;
    }

    public double getLan() {
        return Lan;
    }

    public salonObject(String salonId, String name, String adressLine1, String adressLine2, String eMail, String tel1, String tel2, double lat, double lan, ArrayList<Time> openTimes,Bitmap LogoImage) {
        SalonId = salonId;
        this.name = name;
        this.adressLine1 = adressLine1;
        this.adressLine2 = adressLine2;
        this.eMail = eMail;
        this.tel1 = tel1;
        this.tel2 = tel2;
        Lat = lat;
        Lan = lan;
        this.openTimes = openTimes;
        this.LogoImage=LogoImage;
    }


    public salonObject(String SalonId,String name,String adressLine1,String adressLine2,String eMail,String tel1,String tel2,ArrayList<Time> openTimes,Bitmap LogoImage){
        this.SalonId=SalonId;
        this.name=name;
        this.adressLine1=adressLine1;
        this.adressLine2=adressLine2;
        this.eMail=eMail;
        this.tel1=tel1;
        this.tel2=tel2;
        this.openTimes=openTimes;
        this.LogoImage=LogoImage;
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
    public Bitmap getLogoImage(){
        return LogoImage;
    }
}
