package makeAppointment;

import android.app.Service;

public class Master {
    private String MasterId;
    private String MasterName;

    public Master(String masterId, String masterName) {
        MasterId = masterId;
        MasterName = masterName;
    }

    public String getMasterId() {
        return MasterId;
    }

    public String getMasterName() {
        return MasterName;
    }
}
