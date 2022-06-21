package com.example.smarthealthmonitoring;
import android.app.Activity;
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

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class MyAdapter extends ArrayAdapter<String> {
    public final String id[];
    public final String name[];
    public final String phoneno[];
    public final String timing[];
    public final String specialist[];
    public final Context context;

    public MyAdapter(Context context, String id[], String name[], String phoneno[], String timing[], String specialist[]) {
        super(context, R.layout.doctorlist, id);
        this.context = context;
        this.id = id;
        this.name = name;
        this.phoneno = phoneno;
        this.timing = timing;
        this.specialist = specialist;
    }

    @Override
    public View getView(final int position, View convertview, ViewGroup parent) {
        if (convertview == null) {
            convertview = LayoutInflater.from(getContext()).inflate(R.layout.doctorlist, parent, false);
        }
        final TextView list_txt1=(TextView)convertview.findViewById(R.id.Name);
        final TextView list_txt2=(TextView)convertview.findViewById(R.id.mobile);
//        list_txt2.setPaintFlags(list_txt2.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        final TextView list_txt3=(TextView)convertview.findViewById(R.id.timing);
        final TextView list_txt4=(TextView)convertview.findViewById(R.id.specialist);
        list_txt1.setText("Name :- "+name[position]);
        list_txt2.setText(phoneno[position]);
        list_txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+list_txt2.getText()));
                context.startActivity(intent);
            }
        });
        list_txt3.setText("Timing :- "+timing[position]);
        list_txt4.setText("Speciality :- "+specialist[position]);
        return convertview;

    }
}
