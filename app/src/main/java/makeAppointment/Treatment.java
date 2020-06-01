package makeAppointment;

import java.util.ArrayList;

public class Treatment {
    private String ServiceId;
    private String ServiceName;
    private int DurationMin;
    private double Price;
    private int Tag;
    private ArrayList<String> imageStrings=new ArrayList<>();

    public Treatment(String serviceId, String serviceName, int durationMin, double price,int tag,ArrayList<String> imageStrings) {
        ServiceId = serviceId;
        ServiceName = serviceName;
        DurationMin = durationMin;
        Price = price;
        Tag=tag;
        this.imageStrings=imageStrings;
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
    public ArrayList<String> getImageStrings(){
        return imageStrings;
    }
}
