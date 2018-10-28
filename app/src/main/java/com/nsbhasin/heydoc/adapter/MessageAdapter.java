package com.nsbhasin.heydoc.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nsbhasin.heydoc.R;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private static final String TAG = MessageAdapter.class.getSimpleName();

    private ArrayList<String> mDataset;
    private Context mContext;

    public MessageAdapter(ArrayList<String> dataset) {
        this.mDataset = dataset;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_item, parent, false);
        mContext = view.getContext();
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        holder.mTitle.setText(mDataset.get(position));

        String google = "https://www.google.com.eg/search?q=";
        String query = mDataset.get(position);
        final String search = google.concat(query);

        holder.mMoreButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(search));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitle;
        private Button mMoreButton;

        public MessageViewHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title);
            mMoreButton = itemView.findViewById(R.id.btn_more);
        }
    }
}
