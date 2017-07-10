package hod.cloneplanet.com.hod;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.RealmResults;

import static hod.cloneplanet.com.hod.R.id.receiver_message;


/**
 * Created by swapnilbhaisare on 01/07/17.
 */

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int TYPE_USER = 0;
    private static final int TYPE_RECEIVER = 1;
    Context context;
    RealmResults<User> chat_array ;
    public ChatAdapter(Context applicationContext, RealmResults<User> chat_array) {
        context=applicationContext;
        this.chat_array=chat_array;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_RECEIVER) {

            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.layout_receiver,
                    parent, false);

            return new ViewHolderReciever(v);

        } else  if (viewType == TYPE_USER) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_sender,
                    parent, false);

            return new ViewHolderSender(v);

        }

        throw new RuntimeException("there is no type that matches the type " + viewType
                + " + make sure your using types correctly");

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        if(holder instanceof ViewHolderReciever)
        {

            ((ViewHolderReciever) holder).txt_receiver_message.setText(chat_array.get(position).getChat_text());
        }else if(holder instanceof ViewHolderSender)
        {
            ((ViewHolderSender) holder).txt_sender_message.setText(chat_array.get(position).getChat_text());

        }
    }

    @Override
    public int getItemViewType(int position) {
        if(chat_array.get(position).getUser_type().equals("sender"))
        {
            return TYPE_USER;
        } else{
            return   TYPE_RECEIVER;
        }

    }

    @Override
    public int getItemCount() {
        return chat_array.size();
    }

    private class ViewHolderReciever extends RecyclerView.ViewHolder {
        private final TextView txt_receiver_message;
        public ViewHolderReciever(View v) {
            super(v);
            txt_receiver_message=(TextView)v.findViewById(R.id.receiver_message);
        }
    }

    private class ViewHolderSender extends RecyclerView.ViewHolder {


        private final TextView txt_sender_message;

        public ViewHolderSender(View v) {
            super(v);
           txt_sender_message=(TextView)v.findViewById(R.id.sender_message);

        }
    }
}
