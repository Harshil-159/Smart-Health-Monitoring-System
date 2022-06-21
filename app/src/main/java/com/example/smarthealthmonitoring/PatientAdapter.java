package com.example.smarthealthmonitoring;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class PatientAdapter extends ArrayAdapter<String> {
    public final String name[];
    public final String age[];
    public final String bpm[];
    public final String weight[];
    public final String temp[];

    PatientAdapter(Context context, String name[], String age[], String bpm[],String weight[],String temp[]) {
        super(context, R.layout.pateint_list, name);
        this.name = name;
        this.age = age;
        this.bpm = bpm;
        this.weight=weight;
        this.temp=temp;
    }

    @Override
    public View getView(final int position, View convertview, ViewGroup parent) {
        if (convertview == null) {
            convertview = LayoutInflater.from(getContext()).inflate(R.layout.pateint_list, parent, false);
        }
        final TextView list_txt1=(TextView)convertview.findViewById(R.id.PatientValue);
        final TextView list_txt2=(TextView)convertview.findViewById(R.id.PatientAgeValue);
        final TextView list_txt3=(TextView)convertview.findViewById(R.id.PatientBpmValue);
        final TextView list_txt4=(TextView)convertview.findViewById(R.id.PatientWeightValue);
        final TextView list_txt5=(TextView)convertview.findViewById(R.id.PatientTempValue);
        list_txt1.setText(name[position]);
        list_txt2.setText(age[position]);
        list_txt3.setText(bpm[position]);
        list_txt4.setText(weight[position]);
        list_txt5.setText(temp[position]);
        return convertview;
    }
}
