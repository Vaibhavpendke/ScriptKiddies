package com.example.scriptkiddies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView chats;
    private EditText user;
    private FloatingActionButton sendmsg;
    private final String BOT_KEY = "bot";
    private final String USER_KEY = "user";
    private ArrayList<ChatsModal>chatsModalArrayList;
    private chatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chats =findViewById(R.id.idRVChats);
        user = findViewById(R.id.idEdtMessage);
        sendmsg = findViewById(R.id.idFABSend);
        chatsModalArrayList = new ArrayList<>();
        chatAdapter = new chatAdapter(chatsModalArrayList,this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        chats.setLayoutManager(manager);
        chats.setAdapter(chatAdapter);

        sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v ) {
                if (user.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter your feelings", Toast.LENGTH_SHORT).show();
                    return;
                }
                getResponse(user.getText().toString());
                user.setText("");
            }
        });
    }

    private void getResponse(String message){
        chatsModalArrayList.add(new ChatsModal(message,USER_KEY));
        chatAdapter.notifyDataSetChanged();
        String url = "http://api.brainshop.ai/get?bid=174004&key=0r7LYAWPGDzhqDwq&uid=[uid]&msg=[msg]";
        String BASE_URL = "http://api.brainshop.ai/";
        Retrofit retrofit = new  Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ReyrofitAPI reyrofitAPI = retrofit.create(ReyrofitAPI.class);
        Call<MsgModal> call = reyrofitAPI.getMessage(url);
        call.equals(new Callback<MsgModal>() {
            @Override
            public void onResponse(Call<MsgModal> call, Response<MsgModal> response) {
                if(response.isSuccessful()){
                    MsgModal modal = response.body();
                    chatsModalArrayList.add(new ChatsModal(modal.getCnt(),BOT_KEY));
                    chatAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<MsgModal> call, Throwable t) {
                chatsModalArrayList.add(new ChatsModal("please again send your question",BOT_KEY));
                chatAdapter.notifyDataSetChanged();
            }
        });



    }
}