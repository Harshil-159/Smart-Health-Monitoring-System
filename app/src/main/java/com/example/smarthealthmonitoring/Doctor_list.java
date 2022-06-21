package com.example.smarthealthmonitoring;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
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
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class Doctor_list extends AppCompatActivity {
    public String UrlFetchData=IpAddress.getIp()+"smart_health_monitoring/getList.php";
    String[] id;
    String[] name;
    String[] phoneno;
    String[] timing;
    String[] specialist;
    String toastMsg;
    ListView listView ;
    RequestQueue queue;
    TextView phn;
    Context context;
    JsonArray array;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
        listView=(ListView)findViewById(R.id.lst);
        context=this;

        phn=findViewById(R.id.mobile);
//        phn.setPaintFlags(phn.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        queue = Volley.newRequestQueue(this);
        StringRequest makeRequest = new StringRequest(Request.Method.POST, UrlFetchData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String res) {
                        System.out.println(res);
                        Gson gson = new Gson();
                        if (res.contains("id") && res.contains("Doctor_Name") && res.contains("Mobile_No") ) {

                            array = gson.fromJson(res, JsonArray.class);
                            id = new String[array.size()];
                            name = new String[array.size()];
                            phoneno = new String [array.size()];
                            timing = new String [array.size()];
                            specialist = new String [array.size()];
                            for (int i = 0; i < array.size(); i++) {
                                System.out.println("in loop");
                                JsonObject jobj = array.get(i).getAsJsonObject();

                                id[i] = jobj.get("id").getAsString();
                                name[i] = jobj.get("Doctor_Name").getAsString();
                                phoneno[i]=jobj.get("Mobile_No").getAsString();
                                timing[i]=jobj.get("Timing").getAsString();
                                specialist[i]=jobj.get("Dr_Speciality").getAsString();

                            }
                            MyAdapter myAdapter = new MyAdapter(context, id, name, phoneno,timing,specialist);
                            listView.setAdapter(myAdapter);

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


}