package com.nsbhasin.heydoc.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.nsbhasin.heydoc.R;
import com.nsbhasin.heydoc.adapter.ChatArrayAdapter;
import com.nsbhasin.heydoc.async.Chat;
import com.nsbhasin.heydoc.async.Welcome;
import com.nsbhasin.heydoc.database.entity.ChatMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private ChatArrayAdapter mChatAdapter;

    private EditText mChatText;

    private ListView mChatList;

    private Button mSend;

    public String Authorization = "";
    private String Recieved = "";

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        mChatList = findViewById(R.id.chatlist);
        mChatText = findViewById(R.id.msgtyped);
        changeView();
        mChatAdapter = new ChatArrayAdapter(mContext, new ArrayList<ChatMessage>(), mChatList);
        mChatList.setAdapter(mChatAdapter);
        mChatAdapter.add(new ChatMessage(1, "typing..."));
        new Welcome(mContext).execute();
    }

    public void changeView() {
        mSend = findViewById(R.id.send_btn);
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String message = mChatText.getText().toString();
                ChatMessage chatMessage = new ChatMessage(0, message);

                mChatAdapter.add(chatMessage);

                JSONObject jsn = new JSONObject();
                try {
                    jsn.put("message", message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new Chat(mContext).execute(Authorization, jsn.toString());
                mChatText.setText("");
                mChatAdapter.add(new ChatMessage(1, "typing..."));
                mChatList.setSelection(mChatAdapter.getCount() - 1);
            }
        });
    }

    public void ServerWelcome(JSONObject res) {
        try {
            Recieved = (res.getString("message"));
            Authorization = res.getString("uuid");
            mChatAdapter.remove((ChatMessage) mChatAdapter.mChatList.getItemAtPosition(mChatAdapter.getCount() - 1));
            mChatAdapter.notifyDataSetChanged();

            ChatMessage chatMessage = new ChatMessage(1, Recieved);
            mChatAdapter.add(chatMessage);
            mChatList.setSelection(mChatAdapter.getCount() - 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void ServerChat(JSONObject res) {
        try {
            Recieved = (res.getString("message"));
            Log.d(TAG, Recieved);
            mChatAdapter.remove((ChatMessage) mChatAdapter.mChatList.getItemAtPosition(mChatAdapter.getCount() - 1));
            mChatAdapter.notifyDataSetChanged();

            ChatMessage chatMessage = new ChatMessage(1, Recieved);
            mChatAdapter.add(chatMessage);
            mChatList.setSelection(mChatAdapter.getCount() - 1);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
