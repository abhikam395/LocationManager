package com.example.locationmanager.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locationmanager.R;
import com.example.locationmanager.models.AuthUser;
import com.example.locationmanager.models.Chat;
import com.example.locationmanager.utils.SharePreferenceManager;

import java.util.Calendar;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private static final String TAG = "ChatAdapter";
    private List<Chat> chatList;
    private SharePreferenceManager sharePreferenceManager;
    private AuthUser user;

    public ChatAdapter(List<Chat> chatList, Context context) {
        this.chatList = chatList;
        this.sharePreferenceManager = new SharePreferenceManager(context);
        this.user = sharePreferenceManager.getUser();
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder sender = new ViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.row_receiver_chat, parent, false));
        ViewHolder receiver = new ViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.row_sender_chat, parent, false));
        switch (viewType){
            case 0:
                return sender;
            case 1:
                return receiver;
        }
        return receiver;
    }

    @Override
    public int getItemViewType(int position) {
        Chat chat = chatList.get(position);
        if(chat.from.id == user.id) {
            Log.d(TAG, "getItemViewType: " + "self" + position);
            return 0;
        }
        else return 1;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        Chat chat = chatList.get(position);
        holder.lblName.setText(chat.from.name);
        holder.lblTime.setText(getTimeAgo(Long.parseLong(chat.time)));
        holder.lblMessage.setText(chat.message);
    }

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;


    public static String getTimeAgo(long time) {
        long now = Calendar.getInstance().getTimeInMillis();
        if (time > now || time <= 0) {
            return null;
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView lblName;
        private final TextView lblTime;
        private final TextView lblMessage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lblName = itemView.findViewById(R.id.lbl_username_chat);
            lblTime = itemView.findViewById(R.id.lbl_time_chat);
            lblMessage = itemView.findViewById(R.id.lbl_message_chat);
        }
    }
}
