package com.example.android.chitchat;

import android.graphics.ColorSpace;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.chitchat.Adapter.CustomListAdapter;
import com.example.android.chitchat.Adapter.CustomListAdapter2;
import com.example.android.chitchat.Model.PostModel;
import com.example.android.chitchat.Model.PostModel2;
import com.example.android.chitchat.Model.messages;
import com.example.android.chitchat.Model.user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class chat extends AppCompatActivity {
    private ListView listView;
    private FirebaseAuth fa;
    private DatabaseReference drf;
    private ListView main_chat_pain;

    ArrayList<messages> arrayList2 = new ArrayList<messages>();

    ImageView send;
    EditText chat;
    String uid;
    CustomListAdapter2 customListAdapter;
    HashMap<String, String> hm = new HashMap<>();
    user u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        u = (user) getIntent().getSerializableExtra("h");
        PostModel2 postmodel3 = new PostModel2("hello");
        Toast.makeText(getApplicationContext(), u.getUname(), Toast.LENGTH_SHORT).show();

        chat = (EditText) findViewById(R.id.chat_text);
        send = (ImageView) findViewById(R.id.chat_send);
        main_chat_pain = (ListView) findViewById(R.id.listview_chatpage);
        fa = FirebaseAuth.getInstance();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Toast.makeText(getApplicationContext(), uid, Toast.LENGTH_SHORT).show();

        drf = FirebaseDatabase.getInstance().getReference().child("chat");
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = chat.getText().toString();
                chat.setHint("Type a Message");
                sendmess(s);
            }
        });

        customListAdapter = new CustomListAdapter2(chat.this, arrayList2, uid);
        main_chat_pain.setAdapter(customListAdapter);
        drf.child(uid).child(u.getKey()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String a = dataSnapshot.getValue().toString();
                String mas[] = a.split(",");
                if (mas.length > 0) {
                    for (int i = 0; i < mas.length; i++) {
                        int f = mas[i].indexOf("=");
                        if (i != 3) {
                            mas[i] = mas[i].substring(f + 1);
                        } else {
                            mas[i] = mas[i].substring(f + 1, (mas[i].length()) - 1);
                        }
                        //postmodel1=new PostModel2(mas[1]);
                    }
                    messages m = new messages(mas[1], mas[3], mas[0], mas[2]);
                    // m.setMessage(mas[1]);
                    arrayList2.add(m);
                }
                customListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        customListAdapter = new CustomListAdapter2(chat.this, arrayList2, uid);
        // main_chat_pain.setAdapter(customListAdapter);
    }

    private void sendmess(String s) {


        hm.put("time", "");
        hm.put("type", "text");
        hm.put("massage", s);
        hm.put("sender", uid);
        // drf.child("XDqMJVOl7XWvyBTi5cMvUNV1nSc2");
        drf.child(uid).child(u.getKey()).push().setValue(hm).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    drf.child(u.getKey()).child(uid).push().setValue(hm).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getApplicationContext(), "massage send", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }


}
