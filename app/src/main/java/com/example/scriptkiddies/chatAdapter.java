package com.example.scriptkiddies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class chatAdapter extends RecyclerView.Adapter {

    private ArrayList<ChatsModal>chatsModalArrayList;
    private Context context;

    public chatAdapter(ArrayList<ChatsModal> chatsModalArrayList, Context context) {
        this.chatsModalArrayList = chatsModalArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_msg_rv_item,parent,false);
                return new UserViewHolder(view);


            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_response,parent,false);
                return new BotViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatsModal chatsModal = chatsModalArrayList.get(position);
        switch (chatsModal.getSender()){
            case "user":
                ((UserViewHolder)holder).usertextview.setText(chatsModal.getMessage());
                break;
            case "bot":
                ((BotViewHolder)holder).botchatView.setText(chatsModal.getMessage());
                break;
        }
    }

    @Override
    public int getItemViewType(int position){
        switch (chatsModalArrayList.get(position).getSender()){
            case "user":
                return 0;
            case "bot":
                return 1;
            default:
                return -1;
        }
    }

    @Override
    public int getItemCount() {
        return chatsModalArrayList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{

        TextView usertextview;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            usertextview = itemView.findViewById(R.id.user);

        }
    }

    public static class BotViewHolder extends RecyclerView.ViewHolder{
      TextView botchatView;

        public BotViewHolder(@NonNull View itemView) {
            super(itemView);
            botchatView = itemView.findViewById(R.id.bot);
        }
    }
}
