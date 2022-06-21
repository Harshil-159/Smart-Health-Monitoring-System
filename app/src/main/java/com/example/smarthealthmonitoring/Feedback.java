package com.example.smarthealthmonitoring;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
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

public class Feedback extends AppCompatActivity {
    RatingBar ratingbar;
    Button button;
    public String URL_REGIST=IpAddress.getIp()+"smart_health_monitoring/feedback.php";
    EditText editText;
    String name,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        name=sharedPreferences.getString("Name","");
        id=sharedPreferences.getString("id","");

        addListenerOnButtonClick();
    }
    public void addListenerOnButtonClick(){
        ratingbar=(RatingBar)findViewById(R.id.ratingBar);
        button=(Button)findViewById(R.id.button);
        editText=findViewById(R.id.editTextTextMultiLine);
        //Performing action on Button Click
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                //Getting the rating and displaying it on the toast
                final String comment=editText.getText().toString().trim();
                final String rating=String.valueOf(ratingbar.getRating());
                if(rating.isEmpty()){
                    Toast.makeText(Feedback.this, "Please Rate between 1 to 5", Toast.LENGTH_SHORT).show();
                }
                else if(comment.isEmpty()){
                    editText.setError("Enter description");
                    editText.setFocusable(true);
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

                                            Toast.makeText(Feedback.this, responseDescription, Toast.LENGTH_SHORT).show();


                                        } else {

                                            Toast.makeText(Feedback.this, responseDescription, Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(Feedback.this, "Feedback fail" + e.toString(), Toast.LENGTH_SHORT).show();
                                        // reg.setVisibility(View.GONE);
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            Toast.makeText(Feedback.this, "Feedback failed" + error.toString(), Toast.LENGTH_SHORT).show();
                            // reg.setVisibility(View.GONE);
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("name", name);
                            params.put("id",id);
                            params.put("rating",rating);
                            params.put("comment",comment);

                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);


                }
            }

        });
    }
}