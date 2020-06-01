package com.example.nal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import home.home;
import makeAppointment.allFilteredSalonsByName;
import makeAppointment.makeAppointmentByMainTags;
import makeAppointment.rvAdapterFilteredSalons;
import mySQLInteractions.sqlInteractions;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=new Intent(this, home.class);
        startActivity(intent);
    }
}
