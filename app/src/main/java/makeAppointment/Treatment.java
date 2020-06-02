package makeAppointment;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.BitSet;

public class Treatment {
    private String ServiceId;
    private String ServiceName;
    private int DurationMin;
    private double Price;
    private int Tag;
    private ArrayList<Bitmap> imageBitmaps=new ArrayList<>();

    public Treatment(String serviceId, String serviceName, int durationMin, double price,int tag,ArrayList<Bitmap> imageStrings) {
        ServiceId = serviceId;
        ServiceName = serviceName;
        DurationMin = durationMin;
        Price = price;
        Tag=tag;
        this.imageBitmaps=imageStrings;
    }
    public int getTag(){
        return Tag;
    }
    public String getServiceId() {
        return ServiceId;
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
    public ArrayList<Bitmap> getImageBitmaps(){
        return imageBitmaps;
    }
}
