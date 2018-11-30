package com.example.user.shule;

import android.app.ActionBar;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.user.shule.Models.School;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    Button submit;
    DatabaseReference locationRef;
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mAuth = FirebaseAuth.getInstance();

        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()==null)
                {
                    startActivity(new Intent(MapsActivity.this, LogInActivity.class));
                }
            }
        };
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Toast toast = Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_LONG);
                    toast.show();
                    return true;
                case R.id.navigation_map:
                    Toast map = Toast.makeText(getApplicationContext(), "Map", Toast.LENGTH_LONG);
                    map.show();
                    return true;
                case R.id.navigation_list:
                    Toast list = Toast.makeText(getApplicationContext(), "List", Toast.LENGTH_LONG);
                    list.show();
                    return true;
                case R.id.navigation_add:
                    Toast add = Toast.makeText(getApplicationContext(), "Add school", Toast.LENGTH_LONG);
                    add.show();
                    startActivity(new Intent(getApplicationContext(),AddSchool.class));
                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.log_out:
                mAuth.signOut();
                return true;
            case R.id.help:
//                showHelp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;



        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("schools");
//        Query queryRef = ref.limitToLast(100);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){

                for (DataSnapshot s : dataSnapshot.getChildren()){

                    System.out.println(s.getValue());

                    School school = s.getValue(School.class);
                    LatLng location=new LatLng(school.getLat(),school.getLongi());
                    mMap.addMarker(new MarkerOptions().position(location).title(school.getName())).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                }
            }}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Exception FB",databaseError.toException());

            }
        });
//        // Add a marker in Sydney and move the camera
//
//
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(trainLocation));
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(trainLocation,13f));
    }
}
