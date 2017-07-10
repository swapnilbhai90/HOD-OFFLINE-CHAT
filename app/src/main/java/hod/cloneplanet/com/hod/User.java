package hod.cloneplanet.com.hod;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by swapnilbhaisare on 08/07/17.
 */

public class User extends RealmObject {

    @PrimaryKey
    private int chat_id;


    private String user_type;
    private String chat_text;
    private String chat_time ;

    public int getChat_id() {
        return chat_id;
    }

    public void setChat_id(int chat_id) {
        this.chat_id = chat_id;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getChat_text() {
        return chat_text;
    }

    public void setChat_text(String chat_text) {
        this.chat_text = chat_text;
    }

    public String getChat_time() {
        return chat_time;
    }

    public void setChat_time(String chat_time) {
        this.chat_time = chat_time;
    }
}
