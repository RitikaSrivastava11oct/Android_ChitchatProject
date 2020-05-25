package com.example.android.chitchat;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import static com.example.android.chitchat.R.id.button_join_signup;

public class signUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference dref;
    private FirebaseStorage firestor;
    private StorageReference fs;
    StorageReference sf;
    private Button button_signup;
    public EditText edit_email,edit_pwd,edit_uname,edit_fname,edit_lname;
    private ImageView plus_image_signup;
    private String imageUrl="https://firebasestorage.googleapis.com/v0/b/chitchat-1e375.appspot.com/o/profile%2FdefaultImage.png?alt=media&token=f1633e58-0ba5-4c0d-84a6-febefd04f879";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        button_signup = (Button) findViewById(R.id.button_join_signup);
        edit_email = (EditText) findViewById(R.id.edit_email_signup);
        edit_pwd = (EditText) findViewById(R.id.edit_password_signup);
        edit_fname = (EditText) findViewById(R.id.edit_firstname_signup);
        edit_lname = (EditText) findViewById(R.id.edit_lastname_signup);
        edit_uname = (EditText) findViewById(R.id.edit_username_signup);
        plus_image_signup = (ImageView) findViewById(R.id.imageView_plus_signup);

        mAuth = FirebaseAuth.getInstance();
        fs = FirebaseStorage.getInstance().getReference();

        plus_image_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, 20);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //  super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 20 && resultCode == RESULT_OK) {
                // if (data != null) {
                Uri selectedImage = data.getData();
              //  Log.d("hi", "onActivityResult: " + selectedImage);
                sf = fs.child("profile").child("pro.jpg");
                sf.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "not  done", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                sf.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                {
                    @Override
                    public void onSuccess(Uri downloadUrl)
                    {
                        //do something with downloadurl
                        Glide.with(getApplicationContext()).load(downloadUrl).into(circleImageView);
                    }
                });
            } else {
                Log.d("hi", "onActivityResult: image not selected");


            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("at img", "onActivityResult:not ok");
        }


        button_signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String email,pwd;
                final String fname,lname,uname;
                fname=edit_fname.getText().toString();
                lname=edit_lname.getText().toString();
                uname=edit_uname.getText().toString();
                email=edit_email.getText().toString();
                pwd=edit_pwd.getText().toString();
                adduser(email,pwd,fname,lname,uname);
            }

        });
    }

    private  void adduser(String email,String password,final  String fname,final  String lname,final  String uname){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                          if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"log failed", Toast.LENGTH_SHORT).show();

                          }
                           else{
                              Toast.makeText(getApplicationContext(),"log success",Toast.LENGTH_SHORT).show();
                              String s=FirebaseAuth.getInstance().getCurrentUser().getUid();

                              dref= FirebaseDatabase.getInstance().getReference().child("users").child(s);
                              HashMap hm=new HashMap();
                              hm.put("fname",fname);
                              hm.put("lname",lname);
                              hm.put("uname",uname);
                              hm.put("status","hi there");
                              hm.put("image",imageUrl);
                              dref.setValue(hm);
                          }

                    }
                });
    }
}
