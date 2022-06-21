package com.example.smarthealthmonitoring;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button submit;
    EditText etName, etEmail, etMobile, etPassword, address,age;
    TextView textView;
    int flag_Sql=0,flag_Firebase=0;
    public String URL_REGIST=IpAddress.getIp()+"smart_health_monitoring/register.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        System.out.println(MyFirebaseInstanceIdService.getToken(this));
        mAuth = FirebaseAuth.getInstance();
        etName = findViewById(R.id.editTextTextPersonName2);
        etEmail = findViewById(R.id.editTextTextEmailAddress);
        etMobile = findViewById(R.id.editTextPhone);
        etPassword = findViewById(R.id.editTextTextPassword3);
        address = findViewById(R.id.address);
        age=findViewById(R.id.age);
        textView= findViewById(R.id.textView4);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this,Login.class));
            }
        });
        submit = findViewById(R.id.button6);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });


    }

    public void registerUser() {
        final String Name = etName.getText().toString().trim();
        final String Email = etEmail.getText().toString().trim();
        final String Mobile = etMobile.getText().toString();
        final String Password = etPassword.getText().toString().trim();
        final String adr = address.getText().toString().trim();
        final String ag=age.getText().toString().trim();
        final String token=MyFirebaseInstanceIdService.getToken(this);
        System.out.println("--------"+token);

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
        } else if (adr.isEmpty()) {
            address.setError("Address is Empty");
        } else if (ag.isEmpty()) {
            age.setError("Age is Empty");
        } else if (!(Mobile.length() == 10)) {
            etMobile.setError("Mobile Number Not Valid");

        }
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

                                Toast.makeText(Register.this, responseDescription, Toast.LENGTH_SHORT).show();

                               Intent intent=new Intent(Register.this,Login.class);
                               startActivity(intent);
                               finish();
                            } else {

                                Toast.makeText(Register.this, responseDescription, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Register.this, "Register fail" + e.toString(), Toast.LENGTH_SHORT).show();
                            // reg.setVisibility(View.GONE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(Register.this, "Register failed" + error.toString(), Toast.LENGTH_SHORT).show();
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
                params.put("address", adr);
                params.put("age",ag);
                params.put("token",token);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);




    }


}