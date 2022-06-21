package com.example.smarthealthmonitoring;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class doctorDashboard extends AppCompatActivity {
    public static String URL_PATIENT=IpAddress.getIp()+"smart_health_monitoring/getPatientData.php";
    String[] Age;
    String[] Name;
    String[] Bpm;
    String[] Weight;
    String[] Temp;
    ListView listView ;
    RequestQueue queue;
    Context context;
    JsonArray array;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dashboard);
        TextView textview=new TextView(doctorDashboard.this);
        textview.setText("Patient Data");
        textview.setTypeface(textview.getTypeface(), Typeface.BOLD_ITALIC);

        textview.setTextColor(Color.WHITE);

        textview.setGravity(Gravity.CENTER);

        textview.setTextSize(30);

        getSupportActionBar().setDisplayOptions(getSupportActionBar().DISPLAY_SHOW_CUSTOM);

        getSupportActionBar().setCustomView(textview);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#527a7a")));

        context=this;
        listView=findViewById(R.id.lstPatient);
        queue = Volley.newRequestQueue(this);
        StringRequest makeRequest = new StringRequest(Request.Method.POST, URL_PATIENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String res) {
                        System.out.println(res);
                        Gson gson = new Gson();
                        if (res.contains("id") && res.contains("Name") && res.contains("age") ) {

                            array = gson.fromJson(res, JsonArray.class);
                            Age = new String[array.size()];
                            Name = new String[array.size()];
                            Bpm = new String [array.size()];
                            Weight = new String [array.size()];
                            Temp = new String [array.size()];
                            for (int i = 0; i < array.size(); i++) {
                                System.out.println("in loop");
                                JsonObject jobj = array.get(i).getAsJsonObject();
                                Name[i] = jobj.get("Name").getAsString();
                                Age[i]=jobj.get("age").getAsString();
                                Bpm[i]=jobj.get("bpm").getAsString();
                                Weight[i]=jobj.get("weight").getAsString();
                                Temp[i]=jobj.get("temperature").getAsString();

                            }
                            PatientAdapter patientAdapter=new PatientAdapter(context,Name,Age,Bpm,Weight,Temp);
                            listView.setAdapter(patientAdapter);

                        }
                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Once the request is performed, failed code over here is executed
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                return params;
            }
        };
        queue.add(makeRequest);

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

                Intent intent=new Intent(doctorDashboard.this,DoctorEditProfile.class);
                startActivity(intent);
                break;
            case R.id.Logout:
//                SaveSharedPreference.clear(getApplicationContext());
                Intent intent1=new Intent(doctorDashboard.this,doctorLogin.class);
                startActivity(intent1);
                finish();
//                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}