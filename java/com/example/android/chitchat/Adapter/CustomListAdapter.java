package com.example.android.chitchat.Adapter;

/**
 * Created by hp on 16-07-2018.
 */


import android.app.Activity;
        import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.android.chitchat.Model.user;
        import com.example.android.chitchat.R;
import com.example.android.chitchat.serch_recy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Komal on 20-07-2017.
 */

public class CustomListAdapter extends ArrayAdapter{
    Context context;
    ArrayList arrayList;
    String uid;
    //user u;
    //View rowView;
    public CustomListAdapter(Context context, ArrayList arrayList){
        super(context,android.R.layout.simple_list_item_1,arrayList);
        this.context=context;
        this.arrayList=arrayList;
    }
    public class ViewHolder{
        TextView textview_name;
       // TextView textview_last_msg;
        LinearLayout linearLayout;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView=convertView;
        ViewHolder holder;
        if(rowView==null)
        {
            LayoutInflater layoutInflater=((Activity)context).getLayoutInflater();
            rowView=layoutInflater.inflate(R.layout.list_item,parent,false);
            holder=new ViewHolder();
            holder.textview_name=(TextView)rowView.findViewById(R.id.textview_name);
           // holder.textview_last_msg=(TextView)rowView.findViewById(R.id.textview_last_msg);
            holder.linearLayout=(LinearLayout)rowView.findViewById(R.id.user_laout);
            //final View finalRowView = rowView;

            //rowView.setOnClickListener();
        }
        else{
            holder=(ViewHolder)rowView.getTag();
        }
         //PostModel postModel=(PostModel)arrayList.get(position);
        //messages postModel=(messages) arrayList.get(position);
        user u=(user) arrayList.get(position);
        holder.textview_name.setText(u.getFname().toString());
      //  holder.textview_last_msg.setText(u.getStatus().toString());
        final user us=u;
       final DatabaseReference d= FirebaseDatabase.getInstance().getReference().child("frinds");
        //.child(u.getKey());
         uid=FirebaseAuth.getInstance().getCurrentUser().getUid();

                holder.linearLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                d.child(uid).child(us.getKey()).setValue("friend").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getContext(), "done", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                Intent i=new Intent(getContext(),serch_recy.class) ;
                i.putExtra("h", us);
                context.startActivity(i);
            }
        });


        return rowView;
    }
}
