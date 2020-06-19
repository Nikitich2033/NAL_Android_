package com.example.nal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import home.home;
import makeAppointment.makeAppointmentByMainTags;
import mySQLInteractions.sqlInteractions;
import showUserAppointments.AppointmentsListAdapter;

import static mySQLInteractions.sqlInteractions.CheckLogIn;
import static mySQLInteractions.sqlInteractions.deleteAppointment;
import static mySQLInteractions.sqlInteractions.getFirstSecondName;
import static mySQLInteractions.sqlInteractions.getSHA256Hash;


public class MainActivity extends AppCompatActivity {

    EditText enteredEmail;
    EditText enteredPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    class AsyncCheckUserDetails extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            System.out.println("ASYNC CHECK 1");
        }

        @Override
        protected Boolean doInBackground(String... pStrings) {

            String userID,pass;
            userID  = pStrings[0];
            pass = pStrings[1];
            Boolean check =  CheckLogIn(userID,pass);

            return check;
        }

        @Override
        protected void onPostExecute(Boolean bool) {

        }

    }

    class AsyncGetUserName extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            System.out.println("ASYNC CHECK 1");
        }

        @Override
        protected String doInBackground(String... string) {


            String userID  = string[0];

            ArrayList<String> FirstSecondName =  getFirstSecondName(userID);
            String result = FirstSecondName.get(0)+" "+ FirstSecondName.get(1);
            return result;
        }

        @Override
        protected void onPostExecute(String test) {

        }

    }

    public void onClickSignInButton(View view) throws ExecutionException, InterruptedException {
        enteredEmail = findViewById(R.id.loginHint);
        enteredPass = findViewById(R.id.passHint);
        String pass = getSHA256Hash(enteredPass.getText().toString());
        String email = enteredEmail.getText().toString();

        String[] param = {email,pass};

        Boolean check = new AsyncCheckUserDetails().execute(param).get();

        if (check == true){
            Context context = getApplicationContext();
            CharSequence text = "Password Correct";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            String firstSecond = new AsyncGetUserName().execute(email).get();

            Intent intent=new Intent(this, home.class);
            intent.putExtra("WelcomeName",firstSecond);
            startActivity(intent);

        }
        else{
            Context context = getApplicationContext();
            CharSequence text = "Password Error";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        }

    }

    public void onClickSignUpButton(View view) {
    }
}
