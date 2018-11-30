package com.example.user.shule;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
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
    ProgressDialog progressDoalog;

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
                locationRef.push().setValue(school);
//                ProgressDialog progressDoalog = new ProgressDialog(getApplicationContext());
//
//                progressDoalog.setMax(100);
//                progressDoalog.setMessage("loading....");
//                progressDoalog.setTitle("Adding School");
//                progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//                progressDoalog.show();
//
//                finish();


                progressDoalog = new ProgressDialog(AddSchool.this);
                progressDoalog.setMax(100);
                progressDoalog.setMessage("Saving school....");
                progressDoalog.setTitle("Adding School");
                progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDoalog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            while (progressDoalog.getProgress() <= progressDoalog
                                    .getMax()) {
                                Thread.sleep(200);
                                handle.sendMessage(handle.obtainMessage());
                                if (progressDoalog.getProgress() == progressDoalog
                                        .getMax()) {
                                    progressDoalog.dismiss();
                                    startActivity(new Intent(getApplicationContext(),MapsActivity.class));
                                    finish();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            Handler handle = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    progressDoalog.incrementProgressBy(2);
                }
            };

        });

    }
}
