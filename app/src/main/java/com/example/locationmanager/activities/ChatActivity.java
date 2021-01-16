package com.example.locationmanager.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.example.locationmanager.R;
import com.example.locationmanager.adapters.ChatAdapter;
import com.example.locationmanager.models.Chat;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private static final String TAG = "ChatActivity";
    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private Toolbar toolbar;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        userName = getIntent().getStringExtra("name");
        init();
    }

    private void init(){
        toolbar = findViewById(R.id.toolbar_chat);
        toolbar.setTitle(userName);

        recyclerView = findViewById(R.id.rv_chat);
        setUpRecyclerView();
    }

    private void setUpRecyclerView(){
        chatAdapter = new ChatAdapter(getChats());
        recyclerView.setAdapter(chatAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
}