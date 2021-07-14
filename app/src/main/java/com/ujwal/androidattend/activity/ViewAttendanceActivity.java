package com.ujwal.androidattend.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ujwal.androidattend.R;
import com.ujwal.androidattend.adapter.ViewAttendanceAdapter;
import com.ujwal.androidattend.helper.LocationHelper;

public class ViewAttendanceActivity extends AppCompatActivity {

    RecyclerView attendanceRv;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    ViewAttendanceAdapter viewAttendanceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);

        firebaseAuth = FirebaseAuth.getInstance();
        attendanceRv = (RecyclerView) findViewById(R.id.attendance_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        attendanceRv.setLayoutManager(linearLayoutManager);
//        attendanceRv.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = FirebaseDatabase.getInstance().getReference().child("attendance");
        FirebaseRecyclerOptions<LocationHelper> options =
                new FirebaseRecyclerOptions.Builder<LocationHelper>()
                        .setQuery(databaseReference.child(firebaseAuth.getUid()), LocationHelper.class)
                        .build();

        viewAttendanceAdapter = new ViewAttendanceAdapter(options);
        attendanceRv.setAdapter(viewAttendanceAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        viewAttendanceAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewAttendanceAdapter.stopListening();
    }
}