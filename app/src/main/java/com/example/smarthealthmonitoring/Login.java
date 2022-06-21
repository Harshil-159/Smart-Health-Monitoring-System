package com.example.smarthealthmonitoring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    EditText Email,Password;
    TextView t1,t2;
    Button btn;
    FirebaseAuth fAuth;
    String name,age,address,email,password,phoneno,u_id,fcm;
    private String URL_Login=IpAddress.getIp()+"smart_health_monitoring/login.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        System.out.println(MyFirebaseInstanceIdService.getToken(this));
        Email=findViewById(R.id.editTextTextPersonName2);
        Password=findViewById(R.id.editTextTextPassword);
        fAuth = FirebaseAuth.getInstance();

        t1=findViewById(R.id.LoginDoctor);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),doctorLogin.class);
                startActivity(intent);
            }
        });

        t2=findViewById(R.id.textView6);
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Register.class);
                startActivity(intent);
            }
        });

        btn=findViewById(R.id.button7);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginData();
            }
        });
    }
    public void loginData() {
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
                                    age = object.getString("age").trim();
                                    address = object.getString("address").trim();
                                    phoneno = object.getString("Phone_No").trim();
                                    u_id = object.getString("id").trim();
                                    password=object.getString("Password").trim();
                                    fcm=object.getString("user_fcm_token").trim();
                                    SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("Email", email);
                                    editor.putString("Mob_No", phoneno);
                                    editor.putString("Name", name);
                                    editor.putString("id", u_id);
                                    editor.putString("age", age);
                                    editor.putString("address", address);

                                    editor.apply();
//                                    SaveSharedPref saveSharedPref=new SaveSharedPref(name,age,address,phoneno,email,u_id);
//                                    saveSharedPref.setName(object.getString("Name"));
//                                    saveSharedPref.setEmail(email);
//                                    saveSharedPref.setAddress(address);
//                                    saveSharedPref.setPhoneno(phoneno);
//                                    saveSharedPref.setAge(age);
//                                    saveSharedPref.setId(u_id);

//                                    Toast.makeText(MainActivity.this, "login success  ", Toast.LENGTH_SHORT).show();

                                }
                                System.out.println(name);


                                Intent intent =new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(Login.this, responseDescription, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Login.this, "login fail" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(Login.this, "login failed" + error.toString(), Toast.LENGTH_SHORT).show();
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

