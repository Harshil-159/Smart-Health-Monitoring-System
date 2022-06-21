package com.example.smarthealthmonitoring;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DoctorEditProfile extends AppCompatActivity {
    public static String URL_UPDATE = IpAddress.getIp()+"smart_health_monitoring/DoctorUpdateProfile.php";
    EditText U_name,U_email,U_mobile;
    Button btn;
    String updatedName,updatedEmail,updatedMob,id;
    String oldName,oldEmail,oldMob;
    SaveSharedPref saveSharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_edit_profile);
//        saveSharedPref=new SaveSharedPref();
        U_name=findViewById(R.id.updateDrName);
        U_email=findViewById(R.id.updateDrEmail);
        U_mobile=findViewById(R.id.updateDrMobile);
        btn=findViewById(R.id.UpdateDoctor);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateData();
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        oldName=sharedPreferences.getString("Dr_Name","");
        oldEmail=sharedPreferences.getString("Dr_Email","");
        oldMob=sharedPreferences.getString("Dr_MobNo","");
        id=sharedPreferences.getString("Dr_id","");

        U_name.setText(oldName);
        U_email.setText(oldEmail);
        U_mobile.setText(oldMob);
    }
    public void UpdateData(){
        updatedName = U_name.getText().toString().trim();
        updatedMob = U_mobile.getText().toString().trim();
        updatedEmail = U_email.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("JSON", response);
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("Dr_Email", updatedEmail);
                                editor.putString("Dr_MobNo", updatedMob);
                                editor.putString("Dr_Name", updatedName);

                                Toast.makeText(DoctorEditProfile.this, "User Updated Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(DoctorEditProfile.this, "Fail", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DoctorEditProfile.this, "network fail" + e.toString(), Toast.LENGTH_SHORT).show();
                            // reg.setVisibility(View.GONE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DoctorEditProfile.this, " failed" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("UpdatedName", updatedName);
                params.put("UpdatedMob", updatedMob);
                params.put("UpdatedEmail", updatedEmail);
                params.put("id", id);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}