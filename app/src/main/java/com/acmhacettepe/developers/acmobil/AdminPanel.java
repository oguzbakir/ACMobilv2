package com.acmhacettepe.developers.acmobil;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AdminPanel extends AppCompatActivity {


    Button addUser;
    Button goToHomePage;
    Button addEvent;
    Button select_coor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);


        addUser = (Button) findViewById(R.id.uye_ekle_button);

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminPanel.this, AddUser.class));
            }
        });

        goToHomePage = (Button) findViewById(R.id.home_screen);

        goToHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addEvent = (Button) findViewById(R.id.addEventButton);

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminPanel.this, addEvent.class));
            }
        });

        select_coor = (Button) findViewById(R.id.koor_sec);

        select_coor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AdminPanel.this, SelectCoordinator.class));
            }

        });

    }
}
