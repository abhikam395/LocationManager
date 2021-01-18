package com.example.locationmanager.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.locationmanager.R;
import com.example.locationmanager.adapters.ChatAdapter;
import com.example.locationmanager.models.AuthUser;
import com.example.locationmanager.models.Chat;
import com.example.locationmanager.models.ChatUser;
import com.example.locationmanager.utils.GlobalApplication;
import com.example.locationmanager.utils.SharePreferenceManager;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ChatActivity";
    private SharePreferenceManager sharePreferenceManager;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference messageDatabaseReference;
    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private Toolbar toolbar;
    private ChatUser fromUser;
    private ChatUser toUser;
    private ImageButton sendButton;
    private EditText edtMessage;
    private String id;
    private List<Chat> chatList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        toUser = (ChatUser) getIntent().getSerializableExtra("toUser");
        init();
        setListener();
    }

    private void init(){
        sharePreferenceManager = new SharePreferenceManager(this);
        chatList = new ArrayList<>();

        toolbar = findViewById(R.id.toolbar_chat);
        toolbar.setTitle(toUser.name);

        recyclerView = findViewById(R.id.rv_chat);
        sendButton = findViewById(R.id.imgBtn_send_chat);
        edtMessage = findViewById(R.id.edt_message_chat);
        setDataToFromUser();
        useFirebase();
        setUpRecyclerView();
        readDataFromDatabase();
    }

    private void setListener(){
        sendButton.setOnClickListener(this);
    }

    private void setUpRecyclerView(){
        chatAdapter = new ChatAdapter(chatList, this);
        recyclerView.setAdapter(chatAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        GlobalApplication.activityResumed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        GlobalApplication.activityPaused();
    }

    private void readDataFromDatabase(){
        Query query = messageDatabaseReference.child(id);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d(TAG, "onChildAdded: " + previousChildName);
                chatList.add(snapshot.getValue(Chat.class));
                recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                chatList.add(snapshot.getValue(Chat.class));
                chatAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "onChildRemoved: ");
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d(TAG, "onChildMoved: ");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled: ");
            }
        });
    }

    private void setDataToFromUser(){
        AuthUser user = sharePreferenceManager.getUser();
        fromUser = new ChatUser(user.id, user.getName());
    }

    private void useFirebase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        messageDatabaseReference = firebaseDatabase.getReference("messages");
        id = fromUser.id < toUser.id ? fromUser.id + "_" + toUser.id : toUser.id + "_" + fromUser.id;
    }

    private void pushMessage(){
        String message = edtMessage.getText().toString();

        Chat chat = new Chat();
        chat.setMessage(message).setFrom(fromUser).setDate(getCurrentDate())
                        .setTime(String.valueOf(Calendar.getInstance()
                        .getTime()));
        messageDatabaseReference.child(id).push().setValue(chat);
        edtMessage.setText("");
    }

    private String getCurrentDate(){
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgBtn_send_chat:{
                Log.d(TAG, "onClick: ");
                pushMessage();
                break;
            }
        }
    }
}