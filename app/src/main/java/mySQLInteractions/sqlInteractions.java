package mySQLInteractions;
import android.nfc.Tag;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
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

import makeAppointment.Treatment;
import makeAppointment.salonObject;

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
}

