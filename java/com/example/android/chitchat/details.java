package com.example.android.chitchat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class details extends AppCompatActivity {

    private DatabaseReference dref;
    TextView un,fn,ln,st;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        un=(TextView)findViewById(R.id.uname_details);
        fn=(TextView)findViewById(R.id.fname_details);
        ln=(TextView)findViewById(R.id.lname_details);
        st=(TextView)findViewById(R.id.status_details);
        String s= FirebaseAuth.getInstance().getCurrentUser().getUid();
        dref= FirebaseDatabase.getInstance().getReference().child("users").child(s);
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String fname=dataSnapshot.child("fname").getValue().toString();
                String lname=dataSnapshot.child("lname").getValue().toString();
                String uname=dataSnapshot.child("uname").getValue().toString();
                String status=dataSnapshot.child("status").getValue().toString();
                un.setText(uname);
                fn.setText(fname);
                ln.setText(lname);
                st.setText(status);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent in=new Intent(details.this,ChitChat.class);
        startActivity(in);
        finish();
    }
}
