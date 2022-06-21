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
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class doctorLogin extends AppCompatActivity {
    EditText Email,Password;
    TextView t1,t2,DrRegister;
    Button btn;
    FirebaseAuth fAuth;
    String name,age,address,email,password,phoneno,u_id;
    private String URL_Login=IpAddress.getIp()+"smart_health_monitoring/doctorLogin.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);
        Email=findViewById(R.id.drEmailLogin);
        Password=findViewById(R.id.drPasswordLogin);
        fAuth = FirebaseAuth.getInstance();

        t1=findViewById(R.id.LoginDoctor);
        t2=findViewById(R.id.textView6);

        btn=findViewById(R.id.drLgnBtn);
        DrRegister=findViewById(R.id.drRegister);
        DrRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(doctorLogin.this,doctorRegister.class);
                startActivity(intent);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginData();
            }
        });
    }
    public void loginData(){
        final String EmailS=Email.getText().toString().trim();
        final String PasswordS=Password.getText().toString().trim();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_Login,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("JSON",response);
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String responseDescription = jsonObject.getString("responseDescription");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");
                            if (success.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);// name="",email=",id=""
                                    name = object.getString("Name").trim();

                                    email = object.getString("Email").trim();
                                    phoneno=object.getString("Phone_No").trim();
                                    u_id=object.getString("dr_id").trim();
//                                    SaveSharedPref saveSharedPref=new SaveSharedPref(name,age,address,phoneno,email,u_id);
//                                    saveSharedPref.setName(object.getString("Name"));
//                                    saveSharedPref.setEmail(email);
//                                    saveSharedPref.setAddress(address);
//                                    saveSharedPref.setPhoneno(phoneno);
//                                    saveSharedPref.setAge(age);
//                                    saveSharedPref.setId(u_id);
                                    SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("Dr_Email", email);
                                    editor.putString("Dr_MobNo", phoneno);
                                    editor.putString("Dr_Name", name);
                                    editor.putString("Dr_id", u_id);

                                    editor.apply();
//
//                                    Toast.makeText(MainActivity.this, "login success  ", Toast.LENGTH_SHORT).show();

                                }
//                                System.out.println(name);


                                Intent intent =new Intent(getApplicationContext(),doctorDashboard.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(doctorLogin.this, responseDescription, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(doctorLogin.this, "login fail" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(doctorLogin.this, "login failed" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("email",EmailS);
                params.put("password",PasswordS);

                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
}