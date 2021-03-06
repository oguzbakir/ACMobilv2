package com.acmhacettepe.developers.acmobil;

/**
 * Created by Oguz on 8/6/2017.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import android.app.Activity;
import android.content.Context;
import android.content.SyncStatusObserver;
import android.graphics.Color;
import android.media.Image;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.w3c.dom.Text;


public class ChatAdapter extends BaseAdapter {

    private DatabaseReference root;
    private DatabaseReference userRoot;
    public static int lastId = 1000000;

    private static LayoutInflater inflater = null;
    ArrayList<ChatMessage> chatMessageList;

    public ChatAdapter(Activity activity, ArrayList<ChatMessage> list) {
        chatMessageList = list;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        userRoot = FirebaseDatabase.getInstance().getReference().child("RegisteredUsers").child(ChatMessage.userId1);
        userRoot.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Chats.nickname =  (dataSnapshot).getValue().toString();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Chats.nickname =  (dataSnapshot).getValue().toString();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        root = FirebaseDatabase.getInstance().getReference().child("Giybet").child("Messages");
        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                DataSnapshot i = dataSnapshot.getChildren().iterator().next();
                String body = ((HashMap)(i).getValue()).get("body").toString();
                String senderName = ((HashMap)(i).getValue()).get("senderName").toString();
                String[] msgidParts = (((HashMap)(i).getValue()).get("msgid").toString().split("-"));
                String msgid = msgidParts[0];
                String userId = ((HashMap)(i).getValue()).get("userId").toString();
                lastId = Integer.valueOf(msgidParts[0]);
                ChatMessage msg1 = new ChatMessage(senderName,body,msgid,userId);
                msg1.Date = ((HashMap)(i).getValue()).get("Date").toString();
                msg1.Time = ((HashMap)(i).getValue()).get("Time").toString();
                chatMessageList.add(msg1);
                notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public int getCount() {
        return chatMessageList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMessage message = (ChatMessage) chatMessageList.get(position);
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.chatbubble, null);


        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference users = database.child("Giybet").child("Messages");
        FirebaseAuth auth = FirebaseAuth.getInstance();
        final String userID = auth.getCurrentUser().getUid();




        TextView msg = (TextView) vi.findViewById(R.id.message_text);
        msg.setText(message.body);
        LinearLayout layout = (LinearLayout) vi
                .findViewById(R.id.bubble_layout);
        LinearLayout parent_layout = (LinearLayout) vi
                .findViewById(R.id.bubble_layout_parent);
        final TextView username =  (TextView) vi.findViewById(R.id.username);
        username.setText(message.senderName);
        ImageView pp = (ImageView) vi.findViewById(R.id.imageView3);
        TextView clock = (TextView) vi.findViewById(R.id.saat);
        clock.setText(message.Time);

        Picasso.with(Chats.context).load("https://robohash.org/" + message.senderName + "?set=set2&bgset=bg2&size=96x96").transform(new CircleTransform()).into(pp);



        // if message is mine then align to right
        if (message.IsMe()) {
            layout.setBackgroundResource(R.drawable.bubble1);
            parent_layout.setGravity(Gravity.LEFT);


        }
        // If not mine then align to left
        else {
            layout.setBackgroundResource(R.drawable.bubble1);
            parent_layout.setGravity(Gravity.LEFT);

        }
        msg.setTextColor(Color.BLACK);
        return vi;
    }

    public void add(ChatMessage object) {
        DatabaseReference messageRoot = root.child(object.msgid);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", object);
        messageRoot.updateChildren(map);
    }


}