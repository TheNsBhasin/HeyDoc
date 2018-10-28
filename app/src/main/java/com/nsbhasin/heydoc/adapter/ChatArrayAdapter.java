package com.nsbhasin.heydoc.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.nsbhasin.heydoc.R;
import com.nsbhasin.heydoc.activity.MainActivity;
import com.nsbhasin.heydoc.async.Chat;
import com.nsbhasin.heydoc.async.Welcome;
import com.nsbhasin.heydoc.database.entity.ChatMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChatArrayAdapter extends ArrayAdapter<ChatMessage> {
    private static final String TAG = ChatArrayAdapter.class.getSimpleName();

    private Context mContext;
    private List<ChatMessage> mData;
    public ListView mChatList;

    private ArrayList<String> mDataset;

    private Button mMale;
    private Button mFemale;

    private Button mNoCorrect;
    private Button mYesGo;

    private Button mYes;
    private Button mNo;
    private Button mDoNotKnow;

    private Button mYesMore;
    private Button mNoGo;

    private Button mStartAgain;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    public ChatArrayAdapter(@NonNull Context context, List<ChatMessage> data, ListView chatList) {
        super(context, R.layout.sent_message, data);
        this.mContext = context;
        this.mData = data;
        this.mChatList = chatList;
    }

    @Override
    public void add(@Nullable ChatMessage object) {
        super.add(object);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int viewType = getItem(position).isMine();

        if (viewType == 0) {

            if (getItem(position - 1).isMine() != 1) {
                getItem(position - 1).setIsMine(1);
            }
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.sent_message, parent, false);
            TextView textView = (TextView) convertView.findViewById(R.id.sent_msg);
            textView.setText(getItem(position).getMessage());
            TextView time = (TextView) convertView.findViewById(R.id.time);
            String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
            time.setText(mydate);
            textView.startAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in));
        }

        if (viewType == 1) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.recieved_message, parent, false);
            TextView textView = (TextView) convertView.findViewById(R.id.recieved_msg);
            textView.setText(getItem(position).getMessage());
            TextView time = (TextView) convertView.findViewById(R.id.time);
            String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
            time.setText(mydate);
            textView.startAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in));
        }

        if (viewType == 2) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.card4, parent, false);
            TextView textView2 = convertView.findViewById(R.id.textView2);
            textView2.setText(getItem(position).getMessage());
            mMale = convertView.findViewById(R.id.male1);
            mMale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    getItem(getCount() - 1).setIsMine(1);
                    JSONObject jsn = new JSONObject();
                    try {
                        jsn.put("message", "male");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    MainActivity cont = (MainActivity) mContext;
                    new Chat(cont).execute(cont.Authorization, jsn.toString());
                    ChatMessage chatMessage = new ChatMessage(0, "male");
                    add(chatMessage);
                    add(new ChatMessage(1, "typing..."));
                    mChatList.setSelection(getCount() - 1);
                }
            });
            mFemale = convertView.findViewById(R.id.female);
            mFemale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    getItem(getCount() - 1).setIsMine(1);
                    ChatMessage chatMessage = new ChatMessage(0, "female");
                    add(chatMessage);
                    JSONObject jsn = new JSONObject();
                    try {
                        jsn.put("message", "female");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    MainActivity context = (MainActivity) mContext;
                    new Chat(context).execute(context.Authorization, jsn.toString());
                    add(new ChatMessage(1, "typing..."));
                    mChatList.setSelection(getCount() - 1);
                }
            });
        }

        if (viewType == 3) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.card1, parent, false);
            TextView textView2 = convertView.findViewById(R.id.textView);
            textView2.setText(getItem(position).getMessage());
            mYesGo = convertView.findViewById(R.id.cardbtn1);
            mYesGo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    getItem(getCount() - 1).setIsMine(1);
                    JSONObject jsn = new JSONObject();
                    try {
                        jsn.put("message", "yes,go on");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    MainActivity context = (MainActivity) mContext;
                    new Chat(context).execute(context.Authorization, jsn.toString());
                    ChatMessage chatMessage = new ChatMessage(0, "Yes,go on");
                    add(chatMessage);
                    add(new ChatMessage(1, "typing..."));

                    mChatList.setSelection(getCount() - 1);
                }
            });
            mNoCorrect = convertView.findViewById(R.id.cardbtn2);
            mNoCorrect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    getItem(getCount() - 1).setIsMine(1);
                    ChatMessage chatMessage = new ChatMessage(0, "No,add more");
                    add(chatMessage);
                    JSONObject jsn = new JSONObject();
                    try {
                        jsn.put("message", "No,add more");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    MainActivity context = (MainActivity) mContext;
                    new Chat(context).execute(context.Authorization, jsn.toString());
                    add(new ChatMessage(1, "typing..."));
                    mChatList.setSelection(getCount() - 1);
                }
            });
        }

        if (viewType == 4) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.card2, parent, false);
            TextView textView2 = convertView.findViewById(R.id.textView);
            textView2.setText(getItem(position).getMessage());
            mNoGo = convertView.findViewById(R.id.cardbtn2);
            mNoGo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    getItem(getCount() - 1).setIsMine(1);
                    JSONObject jsn = new JSONObject();
                    try {
                        jsn.put("message", "No,let's go on");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    MainActivity context = (MainActivity) mContext;
                    new Chat(context).execute(context.Authorization, jsn.toString());
                    ChatMessage chatMessage = new ChatMessage(0, "No,let's go on");
                    add(chatMessage);
                    add(new ChatMessage(1, "typing..."));
                    mChatList.setSelection(getCount() - 1);
                }
            });
            mYesMore = convertView.findViewById(R.id.cardbtn1);
            mYesMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    getItem(getCount() - 1).setIsMine(1);
                    ChatMessage chatMessage = new ChatMessage(0, "Yes,there's more");
                    add(chatMessage);
                    JSONObject jsn = new JSONObject();
                    try {
                        jsn.put("message", "Yes,there's more");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    MainActivity context = (MainActivity) mContext;
                    new Chat(context).execute(context.Authorization, jsn.toString());
                    add(new ChatMessage(1, "typing..."));
                    mChatList.setSelection(getCount() - 1);
                }
            });
        }

        if (viewType == 5) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.yes_and_no, parent, false);
            TextView textView2 = (TextView) convertView.findViewById(R.id.textView2);
            textView2.setText(getItem(position).getMessage());
            mYes = (Button) convertView.findViewById(R.id.cardbtn1);
            mYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    getItem(getCount() - 1).setIsMine(1);
                    JSONObject jsn = new JSONObject();
                    try {
                        jsn.put("message", "yes");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    MainActivity context = (MainActivity) mContext;
                    new Chat(context).execute(context.Authorization, jsn.toString());
                    ChatMessage chatMessage = new ChatMessage(0, "Yes");
                    add(chatMessage);
                    add(new ChatMessage(1, "typing..."));
                    mChatList.setSelection(getCount() - 1);
                }
            });
            mNo = convertView.findViewById(R.id.cardbtn2);
            mNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    getItem(getCount() - 1).setIsMine(1);
                    JSONObject jsn = new JSONObject();
                    try {
                        jsn.put("message", "no");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    MainActivity context = (MainActivity) mContext;
                    new Chat(context).execute(context.Authorization, jsn.toString());
                    ChatMessage chatMessage = new ChatMessage(0, "No");
                    add(chatMessage);
                    add(new ChatMessage(1, "typing..."));
                    mChatList.setSelection(getCount() - 1);
                }
            });


            mDoNotKnow = convertView.findViewById(R.id.cardbtn3);
            mDoNotKnow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    getItem(getCount() - 1).setIsMine(1);
                    JSONObject jsn = new JSONObject();

                    try {
                        jsn.put("message", "don't know");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    MainActivity context = (MainActivity) mContext;
                    new Chat(context).execute(context.Authorization, jsn.toString());
                    ChatMessage chatMessage = new ChatMessage(0, "I don't know");
                    add(chatMessage);
                    add(new ChatMessage(1, "typing..."));
                    mChatList.setSelection(getCount() - 1);
                }
            });

        }

        if (viewType == 6) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.results, parent, false);
            mDataset = new ArrayList<>();
            String[] str = getItem(position).getMessage().split("\\r?\\n");
            for (int i = 1; i < str.length; i++) {
                mDataset.add(str[i]);
            }

            mRecyclerView = convertView.findViewById(R.id.recycler_view);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new MessageAdapter(mDataset);
            mRecyclerView.setAdapter(mAdapter);
            mStartAgain = convertView.findViewById(R.id.start_again);
            mStartAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {

                    MainActivity context = (MainActivity) mContext;
                    mData.removeAll(mData);
                    notifyDataSetChanged();
                    new Welcome(mContext).execute();
                }
            });
        }
        return convertView;
    }
}
