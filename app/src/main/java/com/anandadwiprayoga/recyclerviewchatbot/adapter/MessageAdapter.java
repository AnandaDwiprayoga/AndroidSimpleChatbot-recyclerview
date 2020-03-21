package com.anandadwiprayoga.recyclerviewchatbot.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.anandadwiprayoga.recyclerviewchatbot.R;
import com.anandadwiprayoga.recyclerviewchatbot.model.MessageModel;

import java.util.List;

import static com.anandadwiprayoga.recyclerviewchatbot.model.MessageModel.TYPE_BOT;
import static com.anandadwiprayoga.recyclerviewchatbot.model.MessageModel.TYPE_SENDER;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    List<MessageModel> list;

    public MessageAdapter(List<MessageModel> list) {
        this.list = list;
    }

    public void addMassage(MessageModel msg){
        list.add(msg);
        notifyDataSetChanged();
    }

    public String getName(){
        if (list.size() > 2) {
            return list.get(2).getMessage();
        }
        return null;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == 0){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_sender, parent, false);
        }else if(viewType == 1){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_bot, parent, false);
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(list.get(position));

        //handle saat jenis message sama dengan mengurangi margin topnya
        if (position > 0) {
            if (list.get(position - 1).getType().equals(list.get(position).getType())) {
                if (holder.layuot.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) holder.layuot.getLayoutParams();
                    p.setMargins(0, 4, 16, 0);

                    holder.layuot.requestLayout();
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.size() > 0) {
            if (list.get(position).getType() == TYPE_SENDER) {
                return 0;
            } else if (list.get(position).getType() == TYPE_BOT) {
                return 1;
            }
        }
        return -1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMessage;
        private TextView tvTime;
        private ConstraintLayout layuot;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMessage    = itemView.findViewById(R.id.textMessage);
            tvTime       = itemView.findViewById(R.id.textTime);
            layuot       = itemView.findViewById(R.id.layoutMessage);
        }

        void bind(MessageModel msg){
            tvMessage.setText(msg.getMessage());
            tvTime.setText(msg.getTime());

        }
    }
}
