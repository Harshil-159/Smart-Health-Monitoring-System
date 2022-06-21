package com.example.smarthealthmonitoring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonArray;

public class MainActivity extends AppCompatActivity {
    Button btn1,btn2,btn3,btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigationView=findViewById(R.id.navibottom);
        navigationView.setOnNavigationItemSelectedListener(selectedListener);
        btn1=findViewById(R.id.listOfDoctors);
        btn2=findViewById(R.id.editProfile);
        btn3=findViewById(R.id.feedback);
        btn4=findViewById(R.id.myHealth);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentr=new Intent(getApplicationContext(),Doctor_list.class);
                startActivity(intentr);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,EditProfile.class);
                startActivity(intent);

            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Feedback.class);
                startActivity(intent);

            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,MyHealth.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.editProfile:
                Toast.makeText(this, "Edit profile", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,EditProfile.class);
                startActivity(intent);
                break;
            case R.id.Logout:
//                SaveSharedPreference.clear(getApplicationContext());
                Intent intent1=new Intent(MainActivity.this,Login.class);
                startActivity(intent1);
                finish();
//                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
    @Override
    protected  void  onResume(){
        super.onResume();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.listOfDoctors:
                            home home2=new home();
                            FragmentTransaction f1=getSupportFragmentManager().beginTransaction();
                            f1.replace(R.id.content,home2,"");
                            f1.commit();
                            Intent intentr=new Intent(getApplicationContext(),Doctor_list.class);
                            startActivity(intentr);

                            return true;
//                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
//                            startActivity(intent);


                        case R.id.feedBack:
                            Intent intenth=new Intent(getApplicationContext(),Feedback.class);
                            startActivity(intenth);

                            return  true;
                        case R.id.nav_profile:
                            Intent intentp=new Intent(getApplicationContext(),EditProfile.class);
                            startActivity(intentp);

                            return true;

                    }
                    return  false;
                }
            };
}