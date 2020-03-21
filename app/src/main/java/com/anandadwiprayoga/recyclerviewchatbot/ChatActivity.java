package com.anandadwiprayoga.recyclerviewchatbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.opengl.ETC1;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.anandadwiprayoga.recyclerviewchatbot.adapter.MessageAdapter;
import com.anandadwiprayoga.recyclerviewchatbot.model.MessageModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.anandadwiprayoga.recyclerviewchatbot.model.MessageModel.TYPE_BOT;
import static com.anandadwiprayoga.recyclerviewchatbot.model.MessageModel.TYPE_SENDER;

public class ChatActivity extends AppCompatActivity {
    private EditText etInput;
    private ImageView imgSend;

    private RecyclerView recyclerView;
    private MessageAdapter adapter;
    private String name = "";

    private String userInput ="";

    private  boolean wesKenalan = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etInput = findViewById(R.id.inputMssg);
        imgSend = findViewById(R.id.imageView6);

        recyclerView = findViewById(R.id.recyclerview);

        adapter = new MessageAdapter(new ArrayList<MessageModel>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().trim().length() > 0){
                        imgSend.setVisibility(View.VISIBLE);
                    }else{
                        imgSend.setVisibility(View.GONE);
                    }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput = etInput.getText().toString();

                MessageModel msg = new MessageModel(etInput.getText().toString(),getCurrentTime(), TYPE_SENDER);
                adapter.addMassage(msg);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        balasanBot();
                    }
                },500);


                //clear input
                etInput.getText().clear();

            }
        });
    }

    private void balasanBot() {
        MessageModel msg = null;
        //jeda terlebih dahulu
        if (!wesKenalan) {
            if (adapter.getName() == null) {
                msg = new MessageModel(getResources().getString(R.string.opening), getCurrentTime(), TYPE_BOT);
                adapter.addMassage(msg);
            } else {
                name = adapter.getName();
                msg = new MessageModel(String.format(getResources().getString(R.string.service), name), getCurrentTime(), TYPE_BOT);
                adapter.addMassage(msg);
                wesKenalan = true;
            }
        }else{
            if(userInput.equals("1")){
                msg = new MessageModel(String.format(getResources().getString(R.string.sayang), name), getCurrentTime(), TYPE_BOT);
                adapter.addMassage(msg);
            }else if (userInput.equals("2")){
                msg = new MessageModel(String.format(getResources().getString(R.string.bobo), name), getCurrentTime(), TYPE_BOT);
                adapter.addMassage(msg);
            }else{
                msg = new MessageModel(getResources().getString(R.string.not_found), getCurrentTime(), TYPE_BOT);
                adapter.addMassage(msg);
                msg = new MessageModel(String.format(getResources().getString(R.string.service), name), getCurrentTime(), TYPE_BOT);
                adapter.addMassage(msg);
            }
        }
        //untuk scorll ketika ada data baru
        recyclerView.scrollToPosition(adapter.getItemCount() - 1);
    }

    private String getCurrentTime(){
        try {
            String time = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Calendar.getInstance().getTime()).toLowerCase();
            return time;
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    public void handleFinish(View view) {
        finish();
    }
}
