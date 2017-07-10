package hod.cloneplanet.com.hod;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recycler_chat;
    private LinearLayoutManager mLayoutManager;
    private Realm realm;
    private EditText edt_chat;
    private ImageView img_attachment;
    private ImageView img_reccord;
    private ImageView img_send;
    private RealmResults<User> chat_array;
    private ChatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         initView();
        img_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData(edt_chat.getText().toString(),getDate(),"sender");
                addData(generateRandomWords(),getDate(),"receiver");
                chat_array=RealmController.with(MainActivity.this).getAllChat();
                edt_chat.clearAnimation();
                edt_chat.setText("");
                adapter.notifyDataSetChanged();
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                recycler_chat.smoothScrollToPosition(chat_array.size());

            }
        });


        edt_chat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length()>0)
                {
                showSend(true);
                }
                else
                {
                    showSend(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void initView() {

        recycler_chat=(RecyclerView)findViewById(R.id.recycler_chat);
        edt_chat=(EditText)findViewById(R.id.edt_chat);
        img_attachment=(ImageView)findViewById(R.id.img_attachment);
        img_send=(ImageView)findViewById(R.id.img_send);
        img_reccord=(ImageView)findViewById(R.id.img_reccord);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_chat.setLayoutManager(mLayoutManager);
        chat_array=RealmController.with(MainActivity.this).getAllChat();
        adapter=new ChatAdapter(getApplicationContext(),chat_array);
        recycler_chat.setAdapter(adapter);
        this.realm = RealmController.with(this).getRealm();
        RealmController.with(this).refresh();

    }

    private void showSend(boolean b) {
        if(b){
            img_attachment.setVisibility(View.GONE);
            img_reccord.setVisibility(View.GONE);
            img_send.setVisibility(View.VISIBLE);
        }else{
            img_attachment.setVisibility(View.VISIBLE);
            img_reccord.setVisibility(View.VISIBLE);
            img_send.setVisibility(View.GONE);
        }

    }


    public  void addData(String chat_text, String chat_time,String type ){
        Number currentIdNum = realm.where(User.class).maximumInt("chat_id");
        int nextId;
        if(currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue() + 1;
        }
        User user=new User();
        user.setChat_id(nextId);
        user.setChat_text(chat_text);
        user.setChat_time(chat_time);
        user.setUser_type(type);


        realm.beginTransaction();
        realm.copyToRealm(user);
        realm.commitTransaction();
    }

    private String getDate(){
        DateFormat dfTime = new SimpleDateFormat("h:mm a");
        String time = dfTime.format(Calendar.getInstance().getTime());
        return  time;
    }


    String generateRandomWords(){
        String words = "Hi:Bye:Hello:How can help you:Please wait your order is in progress...";
        String[] wordsAsArray = words.split(":");

        int index = new Random().nextInt(wordsAsArray.length);

      return wordsAsArray[index];
    }

}
