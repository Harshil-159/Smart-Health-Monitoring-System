package com.example.smarthealthmonitoring;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

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

public class MyHealth extends AppCompatActivity {
    RequestQueue queue;
    JsonArray jsonArray;
    String id,name,email,phone,age,weight,bpm,temp;
    TextView myName,myEmail,myPhone,myAge,myWeight,myBpm,myTemp;
    public String UrlFetchData=IpAddress.getIp()+"smart_health_monitoring/MyHealth.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_health);
        queue = Volley.newRequestQueue(this);
        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        id=sharedPreferences.getString("id","");
        myName=findViewById(R.id.myName);
        myEmail=findViewById(R.id.myEmail);
        myPhone=findViewById(R.id.myPhone);
        myAge=findViewById(R.id.myAge);
        myWeight=findViewById(R.id.myWeight);
        myBpm=findViewById(R.id.myBpm);
        myTemp=findViewById(R.id.myTemp);
        System.out.println(")))))))((((((("+id);
        StringRequest makeRequest = new StringRequest(Request.Method.POST, UrlFetchData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String res) {
                        System.out.println(res);
                        Gson gson = new Gson();
                        if (res.contains("id") && res.contains("Name") && res.contains("age") ) {

                            jsonArray = gson.fromJson(res, JsonArray.class);
                            for (int i = 0; i < jsonArray.size(); i++) {
                                JsonObject jobj = jsonArray.get(i).getAsJsonObject();
                                name=jobj.get("Name").getAsString();
                                email=jobj.get("Email").getAsString();
                                phone=jobj.get("Phone_No").getAsString();
                                age=jobj.get("age").getAsString();
                                weight=jobj.get("weight").getAsString();
                                temp=jobj.get("temperature").getAsString();
                                bpm=jobj.get("bpm").getAsString();

                            }
                            myName.setText(name);
                            myEmail.setText(email);
                            myPhone.setText(phone);
                            myAge.setText(age);
                            myWeight.setText(weight);
                            myTemp.setText(temp);
                            myBpm.setText(bpm);
                            System.out.println(name+"{{}}{}{}{}"+email+"{}{}{}{}"+phone+"  {}"+age+"{}{"+weight+"{}{}{}"+temp+"{}{}{"+bpm);
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
                            params.put("id",id);
                            return params;
                        }
                    };
        queue.add(makeRequest);
                }
        }