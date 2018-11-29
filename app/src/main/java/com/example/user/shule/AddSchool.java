package com.example.user.shule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.shule.Models.School;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddSchool extends AppCompatActivity {
    EditText schoolname,desc;
    EditText lat,longit;
    Button submit;
    DatabaseReference rootRef,locationRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_school);

        schoolname = (EditText) findViewById(R.id.schoolname);
        desc = (EditText) findViewById(R.id.desc);
        lat = (EditText) findViewById(R.id.lat);
        longit = (EditText) findViewById(R.id.longit);

        submit = (Button) findViewById(R.id.btnSubmit);
        rootRef = FirebaseDatabase.getInstance().getReference();
        locationRef = rootRef.child("schools");



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = schoolname.getText().toString();
                String descr = desc.getText().toString();
                Double latitude =Double.parseDouble(lat.getText().toString());
                Double longitude = Double.parseDouble(longit.getText().toString());

                School school= new School();

                school.setName(name);
                school.setDesc(descr);
                school.setLat(latitude);
                school.setLongi(longitude);

//                locationRef.push().setValue(name);
                locationRef.setValue(school);


            }
        });

    }
}
