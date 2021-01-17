package com.example.locationmanager.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.example.locationmanager.R;
import com.example.locationmanager.adapters.ChatAdapter;
import com.example.locationmanager.models.AuthUser;
import com.example.locationmanager.models.Chat;
import com.example.locationmanager.models.ChatUser;
import com.example.locationmanager.utils.SharePreferenceManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private static final String TAG = "ChatActivity";
    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private Toolbar toolbar;
    private ChatUser fromUser;
    private ChatUser toUser;
    private SharePreferenceManager sharePreferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        toUser = (ChatUser) getIntent().getSerializableExtra("toUser");
        init();
        useFirebase();
    }

    private void init(){
        sharePreferenceManager = new SharePreferenceManager(this);

        toolbar = findViewById(R.id.toolbar_chat);
//        toolbar.setTitle(to.name);

        recyclerView = findViewById(R.id.rv_chat);
        setUpRecyclerView();
        setDataToFromUser();
    }

    private void setUpRecyclerView(){
        chatAdapter = new ChatAdapter(getChats());
        recyclerView.setAdapter(chatAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setDataToFromUser(){
        AuthUser user = sharePreferenceManager.getUser();
        fromUser = new ChatUser(user.id, user.name);
    }

    private List<Chat> getChats(){
        List<Chat> chats = new ArrayList<>();
        chats.add(returnChatInstance(1, " ", "10:00PM", "Hello", "11/01/2021"));
        chats.add(returnChatInstance(2, "kishan", "10:00PM", "Hello kishan", "11/01/2021"));
        chats.add(returnChatInstance(3, "rohan", "10:00PM", "Hello rohan", "11/01/2021"));
        chats.add(returnChatInstance(4, "jeff", "10:00PM", "Hello jeff", "11/01/2021"));
        chats.add(returnChatInstance(5, "soul", "10:00PM", "Hello soul", "11/01/2021"));
        chats.add(returnChatInstance(6, "elon", "10:00PM", "Hello elon", "11/01/2021"));
        return chats;
    }

    private Chat returnChatInstance(int id, String from, String time, String message, String date){
        Chat chat = new Chat();
        chat.setId(id).setFrom(from).setTime(time).setMessage(message).setDate(date);
        return chat;
    }

    private void useFirebase(){
        String id = fromUser.id + "_" + toUser.id;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("messages");
        Chat chat = new Chat();
        chat.setMessage("Hello").setFrom(fromUser.name);
        myRef.child(id).setValue(chat);
//        myRef.setValue("Hello, World!");
    }
}