package makeAppointment;

public class Treatment {
    private String ServiceId;
    private String ServiceName;
    private int DurationMin;
    private double Price;
    private int Tag;

    public Treatment(String serviceId, String serviceName, int durationMin, double price,int tag) {
        ServiceId = serviceId;
        ServiceName = serviceName;
        DurationMin = durationMin;
        Price = price;
        Tag=tag;
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
}
