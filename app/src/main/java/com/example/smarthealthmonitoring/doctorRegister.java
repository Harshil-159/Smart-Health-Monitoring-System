package com.example.smarthealthmonitoring;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class doctorRegister extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button submit;
    EditText etName, etEmail, etMobile, etPassword,etcnfrmPassword;
    TextView textView;

    public String URL_REGIST=IpAddress.getIp()+"smart_health_monitoring/doctorRegister.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_register);
        etName = findViewById(R.id.DrName);
        etEmail = findViewById(R.id.DrEmail);
        etMobile = findViewById(R.id.DrMobile);
        etPassword = findViewById(R.id.DrPassword);
        etcnfrmPassword=findViewById(R.id.DCPassword);
        mAuth=FirebaseAuth.getInstance();
        textView=findViewById(R.id.drLogin);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(doctorRegister.this,doctorLogin.class));
            }
        });
        submit = findViewById(R.id.buttonSignUp);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

    }
    public void registerUser(){
        final String Name = etName.getText().toString().trim();
        final String Email = etEmail.getText().toString().trim();
        final String Mobile = etMobile.getText().toString();
        final String Password = etPassword.getText().toString().trim();
        final String C_Password = etcnfrmPassword.getText().toString().trim();
        final String token=MyFirebaseInstanceIdService.getToken(this);


        if (Name.isEmpty()) {
            etName.setError("Field is Empty");

        } else if (Email.isEmpty()) {
            etEmail.setError("Email is Empty");
        } else if (!Email.matches(String.valueOf(Patterns.EMAIL_ADDRESS))) {
            etEmail.setError("Email is Not Valid");
        } else if (Mobile.isEmpty()) {
            etMobile.setError("Mobile is Empty");
        } else if (Password.isEmpty()) {
            etPassword.setError("Password is Empty");
        } else if (C_Password.isEmpty()) {
            etcnfrmPassword.setError("Confirm Password is Empty");
        } else if (!C_Password.equals(Password)){
            etcnfrmPassword.setError("Password doesn't match ");
        }
        else if (!(Mobile.length() == 10)) {
            etMobile.setError("Mobile Number Not Valid");
        }
        else{
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.d("JSON", response);
                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");//"1"
                                String responseDescription = jsonObject.getString("responseDescription");//"Registration succsfull"
                                // JSONArray jsonArray=jsonObject.getJSONArray("login");
                                //   String otp = jsonObject.getString("otp");

                                if (success.equals("1")) {

                                    Toast.makeText(doctorRegister.this, responseDescription, Toast.LENGTH_SHORT).show();

                                    Intent intent=new Intent(doctorRegister.this,doctorLogin.class);
                                    startActivity(intent);
                                    finish();
                                } else {

                                    Toast.makeText(doctorRegister.this, responseDescription, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(doctorRegister.this, "Register fail" + e.toString(), Toast.LENGTH_SHORT).show();
                                // reg.setVisibility(View.GONE);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(doctorRegister.this, "Register failed" + error.toString(), Toast.LENGTH_SHORT).show();
                    // reg.setVisibility(View.GONE);
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("name", Name);
                    params.put("mobile_number", Mobile);
                    params.put("email", Email);
                    params.put("password", Password);
                    params.put("token",token);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);


        }
    }
}