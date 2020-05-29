package mySQLInteractions;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.nfc.Tag;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.mysql.jdbc.log.Log;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import makeAppointment.Master;
import makeAppointment.Treatment;
import makeAppointment.salonObject;
import showUserAppointments.AppointmentObject;


public class sqlInteractions {
    public static void addUser(String Name, String secondName, String Surname, String eMail, String password, String Telephone){
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://172.93.133.103/alexandr_usersEnter?useUnicode=yes&characterEncoding=UTF-8", "alexandr_NikLeo", "(IronBallsBISM)");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String hash=getSHA256Hash(eMail+password);
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String sqlStatement = "Insert into alexandr_usersEnter.personalDetails" +
                    "(UserId,Name,secondName,Surname,Hash,Telephone)" +
                    " values ('"+eMail+"','"+Name+"','"+secondName+"','"+Surname+"','"
                    +hash+"','"+Telephone+"')";
            statement.executeUpdate(sqlStatement);
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static String getUserDetail(String userID,String userDetail){
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://172.93.133.103/alexandr_usersEnter?useUnicode=yes&characterEncoding=UTF-8", "alexandr_NikLeo", "(IronBallsBISM)");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sqlSatatement = "SELECT * FROM alexandr_usersEnter.personalDetails where UserId=?;";
        try (PreparedStatement statement = connection.prepareStatement((sqlSatatement))) {
            statement.setString(1, userID);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<String> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(resultSet.getString(userDetail));
            }
            connection.close();
            return result.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void deleteUser(String UserId){
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://172.93.133.103/alexandr_usersEnter?useUnicode=yes&characterEncoding=UTF-8", "alexandr_NikLeo", "(IronBallsBISM)");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String sqlStatement = "Delete from alexandr_usersEnter.personalDetails where UserId='"+UserId+"';";
            statement.executeUpdate(sqlStatement);
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void changeUserDetail(String UserId,String userDetail,String newValue){
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://172.93.133.103/alexandr_usersEnter?useUnicode=yes&characterEncoding=UTF-8", "alexandr_NikLeo", "(IronBallsBISM)");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(userDetail.equals("Hash")){
            String Hash=getSHA256Hash(UserId+newValue);
            Statement statement = null;
            try {
                statement = connection.createStatement();
                String sqlStatement = "update alexandr_usersEnter.personalDetails set " + userDetail + "='" + Hash + "' where UserId='" + UserId + "';";
                statement.executeUpdate(sqlStatement);
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            Statement statement = null;
            try {
                statement = connection.createStatement();
                String sqlStatement = "update alexandr_usersEnter.personalDetails set " + userDetail + "='" + newValue + "' where UserId='" + UserId + "';";
                statement.executeUpdate(sqlStatement);
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static String getSHA256Hash(String data) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance( "SHA-256" );
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String text = data;
        md.update( text.getBytes( StandardCharsets.UTF_8 ) );
        byte[] digest = md.digest();
        String hex = String.format( "%064x", new BigInteger( 1, digest ) );
        return hex;
    }

    private static String encodeImage(String imgPath){
        byte[] data;
        String result="";
        try {
            data = Files.readAllBytes(Paths.get(imgPath));
            result = Base64.getMimeEncoder().encodeToString(data);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static Bitmap decodeImage(String encodedImg){
        byte[] data= Base64.getMimeDecoder().decode(encodedImg);
        Bitmap salonBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

        return salonBitmap;
    }



    public static ArrayList<AppointmentObject> getUserFutureAppointments(String UserName){


        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://172.93.133.103/alexandr_salonsSpecial?useUnicode=yes&characterEncoding=UTF-8", "alexandr_NikLeo", "(IronBallsBISM)");
        } catch (ClassNotFoundException e) {
            System.out.println("First try");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("First try");
            e.printStackTrace();
        }

        String sqlStatement1 = "SELECT SalonId, ServiceId, date \n" +
                "FROM alexandr_usersLog.allApointments,alexandr_usersEnter.personalDetails\n" +
                "Where alexandr_usersLog.allApointments.UserId =" + "\"" + UserName + "\"" +
                "and cast(Current_timestamp as date) <= date \n" +
                "and alexandr_usersLog.allApointments.UserId = alexandr_usersEnter.personalDetails.UserId;";


        ResultSet resultSet;
        System.out.println("Before try");

        assert connection != null;
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement1)) {
           resultSet = statement.executeQuery();
            ArrayList<AppointmentObject> appointments = new ArrayList<>();

            Bitmap empty = null;
            while (resultSet.next()) {
                AppointmentObject temp = new AppointmentObject(empty,"","","","",
                        "","","","","","","","","");

                temp.SalonId = resultSet.getString("SalonId");
                temp.ServiceId = resultSet.getString("ServiceId");
                temp.serviceDate = resultSet.getString("date");


                appointments.add(temp);

            }


            for (AppointmentObject appointment: appointments
            ) {

                String sqlStatement2= "SELECT StartTime,EndTime,name,ServiceName,MasterName,salonLogo FROM alexandr_salonsSpecial."+appointment.SalonId+"Pictures,alexandr_allSalons.SalonDetails, alexandr_salonsSpecial.Inter"+appointment.SalonId+"Appointments,alexandr_salonsSpecial."+appointment.SalonId+"Offers, alexandr_salonsSpecial."+appointment.SalonId+"Masters" +
                        " where alexandr_salonsSpecial.Inter" +appointment.SalonId + "Appointments.ServiceId = "+ "\"" + appointment.ServiceId + "\""+
                        " And Date = " + "\"" + appointment.serviceDate + "\""  +
                        " and alexandr_salonsSpecial.Inter" + appointment.SalonId +"Appointments.ServiceId = alexandr_salonsSpecial."+appointment.SalonId+"Offers.ServiceId" +
                        " and alexandr_salonsSpecial.Inter" +appointment.SalonId+"Appointments.MasterId = alexandr_salonsSpecial."+appointment.SalonId+"Masters.MasterId"+
                        " and alexandr_allSalons.SalonDetails.SalonId =" + "\"" + appointment.SalonId + "\"" +
                        ";";

                    try (PreparedStatement statement1 = connection.prepareStatement(sqlStatement2)) {
                        ResultSet resultSet1 = statement1.executeQuery();
                        resultSet1.next();
                        appointment.SalonLogo = decodeImage(resultSet1.getString("salonLogo"));
                        appointment.serviceStartTime = resultSet1.getString("StartTime");
                        appointment.serviceEndTime = resultSet1.getString("EndTime");
                        appointment.ServiceName = resultSet1.getString("ServiceName");
                        appointment.SalonName = resultSet1.getString("name");
                        String TempMasterName =  resultSet1.getString("MasterName");
                        appointment.MasterFirst = TempMasterName.split(" ")[0];
                        appointment.MasterLast = TempMasterName.split(" ")[1];

                    }
                    catch (SQLException e) {
                        System.out.println("Inside try");
                        e.printStackTrace();
                    }

                }


            connection.close();
            return appointments;
        } catch (SQLException e) {
            System.out.println("Second try");
            e.printStackTrace();
        }
        return null;


    }

    public static ArrayList<AppointmentObject> getUserPastAppointments(String UserName){



        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://172.93.133.103/alexandr_salonsSpecial?useUnicode=yes&characterEncoding=UTF-8", "alexandr_NikLeo", "(IronBallsBISM)");
        } catch (ClassNotFoundException e) {
            System.out.println("First try");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("First try");
            e.printStackTrace();
        }

        String sqlStatement1 = "SELECT SalonId, ServiceId, date \n" +
                "FROM alexandr_usersLog.allApointments,alexandr_usersEnter.personalDetails\n" +
                "Where alexandr_usersLog.allApointments.UserId =" + "\"" + UserName + "\"" +
                "and cast(Current_timestamp as date) > date \n" +
                "and alexandr_usersLog.allApointments.UserId = alexandr_usersEnter.personalDetails.UserId;";


        ResultSet resultSet;
        System.out.println("Before try");

        assert connection != null;
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement1)) {
            resultSet = statement.executeQuery();
            ArrayList<AppointmentObject> appointments = new ArrayList<>();

            Bitmap empty = null;
            while (resultSet.next()) {
                AppointmentObject temp = new AppointmentObject(empty,"","","","",
                        "","","","","","","","","");

                temp.SalonId = resultSet.getString("SalonId");
                temp.ServiceId = resultSet.getString("ServiceId");
                temp.serviceDate = resultSet.getString("date");


                appointments.add(temp);

            }


            for (AppointmentObject appointment: appointments
            ) {

                String sqlStatement2= "SELECT StartTime,EndTime,name,ServiceName,MasterName,salonLogo FROM alexandr_salonsSpecial."+appointment.SalonId+"Pictures,alexandr_allSalons.SalonDetails, alexandr_salonsSpecial.Inter"+appointment.SalonId+"Appointments,alexandr_salonsSpecial."+appointment.SalonId+"Offers, alexandr_salonsSpecial."+appointment.SalonId+"Masters" +
                        " where alexandr_salonsSpecial.Inter" +appointment.SalonId + "Appointments.ServiceId = "+ "\"" + appointment.ServiceId + "\""+
                        " And Date = " + "\"" + appointment.serviceDate + "\""  +
                        " and alexandr_salonsSpecial.Inter" + appointment.SalonId +"Appointments.ServiceId = alexandr_salonsSpecial."+appointment.SalonId+"Offers.ServiceId" +
                        " and alexandr_salonsSpecial.Inter" +appointment.SalonId+"Appointments.MasterId = alexandr_salonsSpecial."+appointment.SalonId+"Masters.MasterId"+
                        " and alexandr_allSalons.SalonDetails.SalonId =" + "\"" + appointment.SalonId + "\"" +
                        ";";

                try (PreparedStatement statement1 = connection.prepareStatement(sqlStatement2)) {
                    ResultSet resultSet1 = statement1.executeQuery();
                    resultSet1.next();
                    appointment.SalonLogo = decodeImage(resultSet1.getString("salonLogo"));
                    appointment.serviceStartTime = resultSet1.getString("StartTime");
                    appointment.serviceEndTime = resultSet1.getString("EndTime");
                    appointment.ServiceName = resultSet1.getString("ServiceName");
                    appointment.SalonName = resultSet1.getString("name");
                    String TempMasterName =  resultSet1.getString("MasterName");
                    appointment.MasterFirst = TempMasterName.split(" ")[0];
                    appointment.MasterLast = TempMasterName.split(" ")[1];

                }
                catch (SQLException e) {
                    System.out.println("Inside try");
                    e.printStackTrace();
                }

            }


            connection.close();
            return appointments;
        } catch (SQLException e) {
            System.out.println("Second try");
            e.printStackTrace();
        }
        return null;


    }

    public static ArrayList<String> getTreatmentsNames(){
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://172.93.133.103/alexandr_salonsSpecial?useUnicode=yes&characterEncoding=UTF-8", "alexandr_NikLeo", "(IronBallsBISM)");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sqlSatatement = "SELECT * FROM alexandr_allSalons.alltreatmentsbyname;";
        try (PreparedStatement statement = connection.prepareStatement((sqlSatatement))) {
            ResultSet resultSet = statement.executeQuery();
            ArrayList<String> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(resultSet.getString("ServiceName"));
            }
            connection.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static StringBuilder getSalonIds(String ServiceName){
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://172.93.133.103/alexandr_allSalons?useUnicode=yes&characterEncoding=UTF-8", "alexandr_NikLeo", "(IronBallsBISM)");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sqlSatatement = "SELECT * FROM alexandr_allSalons.alltreatmentsbyname where ServiceName=?;";
        try (PreparedStatement statement = connection.prepareStatement((sqlSatatement))) {
            statement.setString(1, ServiceName);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<String> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(resultSet.getString("Salons"));
            }
            connection.close();
            StringBuilder sb=new StringBuilder();
            sb.append(result.get(0));
            return sb;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static StringBuilder getSalonIds(int tag){
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://172.93.133.103/alexandr_allSalons?useUnicode=yes&characterEncoding=UTF-8", "alexandr_NikLeo", "(IronBallsBISM)");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sqlSatatement = "SELECT * FROM alexandr_allSalons.alltreatmentsbytags where tag=?;";
        try (PreparedStatement statement = connection.prepareStatement((sqlSatatement))) {
            statement.setInt(1, tag);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<String> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(resultSet.getString("Salons"));
            }
            connection.close();
            StringBuilder sb=new StringBuilder();
            sb.append(result.get(0));
            return sb;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static ArrayList<String> getSalonIds(){
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://172.93.133.103/alexandr_allSalons?useUnicode=yes&characterEncoding=UTF-8", "alexandr_NikLeo", "(IronBallsBISM)");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sqlSatatement = "SELECT * FROM alexandr_allSalons.SalonDetails;";
        try (PreparedStatement statement = connection.prepareStatement((sqlSatatement))) {
            ResultSet resultSet = statement.executeQuery();
            ArrayList<String> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(resultSet.getString("SalonId"));
            }
            connection.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static ArrayList<salonObject> getObjectsSalon(ArrayList<String> SalonIds){
       Connection connection = null;
       try {
           Class.forName("com.mysql.jdbc.Driver");
           connection = DriverManager.getConnection("jdbc:mysql://172.93.133.103/alexandr_allSalons?useUnicode=yes&characterEncoding=UTF-8", "alexandr_NikLeo", "(IronBallsBISM)");
       } catch (ClassNotFoundException e) {
           e.printStackTrace();
       } catch (SQLException e) {
           e.printStackTrace();
       }
       ArrayList<salonObject> salons=new ArrayList<>();
       for(int i=0;i<SalonIds.size();i=i+1) {
           String SalonId=SalonIds.get(i);
           String name = "";
           String adressLine1 = "";
           String adressLine2 = "";
           String eMail = "";
           String tel1 = "";
           String tel2 = "";
           ArrayList<Time> openTimes = new ArrayList<>();

           String sqlSatatement = "SELECT * FROM alexandr_allSalons.SalonDetails where SalonId=?;";
           try (PreparedStatement statement = connection.prepareStatement((sqlSatatement))) {
               statement.setString(1, SalonId);
               ResultSet resultSet = statement.executeQuery();
               ArrayList<String> result1 = new ArrayList<>();
               ArrayList<String> result2 = new ArrayList<>();
               ArrayList<String> result3 = new ArrayList<>();
               ArrayList<String> result4 = new ArrayList<>();
               ArrayList<String> result5 = new ArrayList<>();
               ArrayList<String> result6 = new ArrayList<>();
               ArrayList<Time> result7 = new ArrayList<>();
               ArrayList<Time> result8 = new ArrayList<>();
               ArrayList<Time> result9 = new ArrayList<>();
               ArrayList<Time> result10 = new ArrayList<>();
               ArrayList<Time> result11= new ArrayList<>();
               ArrayList<Time> result12 = new ArrayList<>();
               ArrayList<Time> result13 = new ArrayList<>();
               ArrayList<Time> result14 = new ArrayList<>();
               ArrayList<Time> result15 = new ArrayList<>();
               ArrayList<Time> result16 = new ArrayList<>();
               ArrayList<Time> result17 = new ArrayList<>();
               ArrayList<Time> result18 = new ArrayList<>();
               ArrayList<Time> result19 = new ArrayList<>();
               ArrayList<Time> result20 = new ArrayList<>();
               while (resultSet.next()) {
                   result1.add(resultSet.getString("name"));
                   result2.add(resultSet.getString("addressLine1"));
                   result3.add(resultSet.getString("addressLine2"));
                   result4.add(resultSet.getString("eMail"));
                   result5.add(resultSet.getString("tel1"));
                   result6.add(resultSet.getString("tel2"));
                   result7.add(resultSet.getTime("mondayStart"));
                   result8.add(resultSet.getTime("mondayEnd"));
                   result9.add(resultSet.getTime("tuesdayStart"));
                   result10.add(resultSet.getTime("tuesdayEnd"));
                   result11.add(resultSet.getTime("wednesdayStart"));
                   result12.add(resultSet.getTime("wednesdayEnd"));
                   result13.add(resultSet.getTime("thursdayStart"));
                   result14.add(resultSet.getTime("thursdayEnd"));
                   result15.add(resultSet.getTime("fridayStart"));
                   result16.add(resultSet.getTime("fridayEnd"));
                   result17.add(resultSet.getTime("saturdayStart"));
                   result18.add(resultSet.getTime("saturdayEnd"));
                   result19.add(resultSet.getTime("sundayStart"));
                   result20.add(resultSet.getTime("sundayEnd"));
               }
               name = result1.get(0);
               adressLine1 = result2.get(0);
               adressLine2 = result3.get(0);
               eMail = result4.get(0);
               tel1 = result5.get(0);
               tel2 = result6.get(0);
               openTimes.add(result7.get(0));
               openTimes.add(result8.get(0));
               openTimes.add(result9.get(0));
               openTimes.add(result10.get(0));
               openTimes.add(result11.get(0));
               openTimes.add(result12.get(0));
               openTimes.add(result13.get(0));
               openTimes.add(result14.get(0));
               openTimes.add(result15.get(0));
               openTimes.add(result16.get(0));
               openTimes.add(result17.get(0));
               openTimes.add(result18.get(0));
               openTimes.add(result19.get(0));
               openTimes.add(result20.get(0));
           } catch (SQLException e) {
               e.printStackTrace();
           }
           salons.add(new salonObject(SalonId,name,adressLine1,adressLine2,eMail,tel1,tel2,openTimes));
       }
       try {
           connection.close();
       } catch (SQLException e) {
           e.printStackTrace();
       }

       return salons;
   }
    public static ArrayList<String> getTreatmentsIds(String SalonId){
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://172.93.133.103/alexandr_salonsSpecial?useUnicode=yes&characterEncoding=UTF-8", "alexandr_NikLeo", "(IronBallsBISM)");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sqlSatatement = "SELECT * FROM alexandr_salonsSpecial."+SalonId+"Offers;";
        try (PreparedStatement statement = connection.prepareStatement((sqlSatatement))) {
            //statement.setString(1, SalonId);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<String> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(resultSet.getString("ServiceId"));
            }
            connection.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static ArrayList<Treatment> getObjectsTreatments(ArrayList<String> TreatmentsIds,String SalonId){
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://172.93.133.103/alexandr_salonsSpecial?useUnicode=yes&characterEncoding=UTF-8", "alexandr_NikLeo", "(IronBallsBISM)");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList<Treatment> treatmentsObjects=new ArrayList<>();
        for(int i=0;i<TreatmentsIds.size();i=i+1) {
            String ServiceId=TreatmentsIds.get(i);
            String ServiceName="";
            int DurationMin=0;
            double Price=0.0;
            int Tag=0;
            String sqlSatatement = "SELECT * FROM alexandr_salonsSpecial." + SalonId + "Offers where ServiceId=?;";
            try (PreparedStatement statement = connection.prepareStatement((sqlSatatement))) {
                statement.setString(1, TreatmentsIds.get(i));
                ResultSet resultSet = statement.executeQuery();
                ArrayList<String> result1 = new ArrayList<>();
                ArrayList<Integer> result2 = new ArrayList<>();
                ArrayList<Double> result3 = new ArrayList<>();
                ArrayList<Integer> result4 = new ArrayList<>();
                while (resultSet.next()) {
                    result1.add(resultSet.getString("ServiceName"));
                    result2.add(resultSet.getInt("DurationMin"));
                    result3.add(resultSet.getDouble("Price"));
                    result4.add(resultSet.getInt("Tag"));
                }
                ServiceName=result1.get(0);
                DurationMin=result2.get(0);
                Price=result3.get(0);
                Tag=result4.get(0);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            treatmentsObjects.add(new Treatment(ServiceId,ServiceName,DurationMin,Price, Tag));
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return treatmentsObjects;
    }
    public static ArrayList<Master> getMasters(String SalonId,String ServiceId) {
        ArrayList<Master> masters = new ArrayList<>();
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://172.93.133.103/alexandr_salonsSpecial?useUnicode=yes&characterEncoding=UTF-8", "alexandr_NikLeo", "(IronBallsBISM)");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sqlSatatement = "SELECT * FROM alexandr_salonsSpecial." + SalonId + "Masters where ServiceId=?;";
        try (PreparedStatement statement = connection.prepareStatement((sqlSatatement))) {
            statement.setString(1, ServiceId);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<String> result = new ArrayList<>();
            ArrayList<String> result1 = new ArrayList<>();
            while (resultSet.next()) {
                result.add(resultSet.getString("MasterName"));
                result1.add(resultSet.getString("MasterId"));
            }
            connection.close();
            for (int i = 0; i < result.size(); i = i + 1) {
                Master master = new Master(result1.get(i), result.get(i));
                masters.add(master);
            }
            return masters;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static ArrayList<Time> getBlockedTimes(String SalonId,String ServiceId,String date,String MasterId) {
        ArrayList<Time> blockedTimes = new ArrayList<>();
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://172.93.133.103/alexandr_salonsSpecial?useUnicode=yes&characterEncoding=UTF-8", "alexandr_NikLeo", "(IronBallsBISM)");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sqlSatatement = "SELECT * FROM alexandr_salonsSpecial.Inter"+SalonId+"Appointments where ServiceId=? and Date=? and MasterId=? order by StartTime;";
        try (PreparedStatement statement = connection.prepareStatement((sqlSatatement))) {
            statement.setString(1, ServiceId);
            statement.setString(2, date);
            statement.setString(3, MasterId);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Time> result = new ArrayList<>();
            ArrayList<Time> result1 = new ArrayList<>();
            while (resultSet.next()) {
                result.add(resultSet.getTime("StartTime"));
                result1.add(resultSet.getTime("EndTime"));
            }
            connection.close();
            for (int i = 0; i < result.size(); i = i + 1) {
               blockedTimes.add(result.get(i));
               blockedTimes.add(result1.get(i));
            }
            return blockedTimes;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void addAppointment(String SalonId,String UserId,String ServiceId,String Date,
                                      String StartTime,String EndTime,String MasterId){
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://172.93.133.103/alexandr_salonsSpecial?useUnicode=yes&characterEncoding=UTF-8", "alexandr_NikLeo", "(IronBallsBISM)");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String sqlStatement = "insert into alexandr_salonsSpecial.Inter"+SalonId+"Appointments(UserId,ServiceId,Date,StartTime,EndTime,MasterId)" +
                    "values(\""+UserId+"\",\""+ServiceId+"\",\""+Date+"\",\""+StartTime+"\",\""+EndTime+"\",\""+MasterId+"\");";
            statement.executeUpdate(sqlStatement);
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        Connection connection2 = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection2 = DriverManager.getConnection("jdbc:mysql://172.93.133.103/alexandr_usersLog?useUnicode=yes&characterEncoding=UTF-8", "alexandr_NikLeo", "(IronBallsBISM)");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Statement statement1 = null;
        try {
            statement1 = connection2.createStatement();
            String sqlStatement1 = "insert into alexandr_usersLog.allApointments(UserId,SalonId,ServiceId,Date)" +
                    "values(\""+UserId+"\",\""+SalonId+"\",\""+ServiceId+"\",\""+Date+"\");";
            statement1.executeUpdate(sqlStatement1);
            connection2.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<salonObject> getMapObjectsSalon(ArrayList<String> SalonIds){
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://172.93.133.103/alexandr_allSalons?useUnicode=yes&characterEncoding=UTF-8", "alexandr_NikLeo", "(IronBallsBISM)");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList<salonObject> salons=new ArrayList<>();
        for(int i=0;i<SalonIds.size();i=i+1) {
            String SalonId=SalonIds.get(i);
            String name = "";
            String adressLine1 = "";
            String adressLine2 = "";
            String eMail = "";
            String tel1 = "";
            String tel2 = "";
            double Lat = 0;
            double Lan = 0;
            ArrayList<Time> openTimes = new ArrayList<>();

            String sqlSatatement = "SELECT * FROM alexandr_allSalons.SalonDetails where SalonId=?;";
            try (PreparedStatement statement = connection.prepareStatement((sqlSatatement))) {
                statement.setString(1, SalonId);
                ResultSet resultSet = statement.executeQuery();
                ArrayList<String> result1 = new ArrayList<>();
                ArrayList<String> result2 = new ArrayList<>();
                ArrayList<String> result3 = new ArrayList<>();
                ArrayList<String> result4 = new ArrayList<>();
                ArrayList<String> result5 = new ArrayList<>();
                ArrayList<String> result6 = new ArrayList<>();
                ArrayList<Time> result7 = new ArrayList<>();
                ArrayList<Time> result8 = new ArrayList<>();
                ArrayList<Time> result9 = new ArrayList<>();
                ArrayList<Time> result10 = new ArrayList<>();
                ArrayList<Time> result11= new ArrayList<>();
                ArrayList<Time> result12 = new ArrayList<>();
                ArrayList<Time> result13 = new ArrayList<>();
                ArrayList<Time> result14 = new ArrayList<>();
                ArrayList<Time> result15 = new ArrayList<>();
                ArrayList<Time> result16 = new ArrayList<>();
                ArrayList<Time> result17 = new ArrayList<>();
                ArrayList<Time> result18 = new ArrayList<>();
                ArrayList<Time> result19 = new ArrayList<>();
                ArrayList<Time> result20 = new ArrayList<>();
                ArrayList<Double> result21 = new ArrayList<>();
                ArrayList<Double> result22 = new ArrayList<>();
                while (resultSet.next()) {
                    result1.add(resultSet.getString("name"));
                    result2.add(resultSet.getString("addressLine1"));
                    result3.add(resultSet.getString("addressLine2"));
                    result4.add(resultSet.getString("eMail"));
                    result5.add(resultSet.getString("tel1"));
                    result6.add(resultSet.getString("tel2"));
                    result7.add(resultSet.getTime("mondayStart"));
                    result8.add(resultSet.getTime("mondayEnd"));
                    result9.add(resultSet.getTime("tuesdayStart"));
                    result10.add(resultSet.getTime("tuesdayEnd"));
                    result11.add(resultSet.getTime("wednesdayStart"));
                    result12.add(resultSet.getTime("wednesdayEnd"));
                    result13.add(resultSet.getTime("thursdayStart"));
                    result14.add(resultSet.getTime("thursdayEnd"));
                    result15.add(resultSet.getTime("fridayStart"));
                    result16.add(resultSet.getTime("fridayEnd"));
                    result17.add(resultSet.getTime("saturdayStart"));
                    result18.add(resultSet.getTime("saturdayEnd"));
                    result19.add(resultSet.getTime("sundayStart"));
                    result20.add(resultSet.getTime("sundayEnd"));
                    result21.add(resultSet.getDouble("Lat"));
                    result22.add(resultSet.getDouble("Lan"));
                }
                name = result1.get(0);
                adressLine1 = result2.get(0);
                adressLine2 = result3.get(0);
                eMail = result4.get(0);
                tel1 = result5.get(0);
                tel2 = result6.get(0);
                openTimes.add(result7.get(0));
                openTimes.add(result8.get(0));
                openTimes.add(result9.get(0));
                openTimes.add(result10.get(0));
                openTimes.add(result11.get(0));
                openTimes.add(result12.get(0));
                openTimes.add(result13.get(0));
                openTimes.add(result14.get(0));
                openTimes.add(result15.get(0));
                openTimes.add(result16.get(0));
                openTimes.add(result17.get(0));
                openTimes.add(result18.get(0));
                openTimes.add(result19.get(0));
                openTimes.add(result20.get(0));
                Lat=result21.get(0);
                Lan=result22.get(0);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            salons.add(new salonObject(SalonId,name,adressLine1,adressLine2,eMail,tel1,tel2,Lat,Lan,openTimes));
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return salons;
    }

}


