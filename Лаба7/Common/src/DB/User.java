package DB;

import java.io.Serializable;

public class User implements Serializable{
    private String login;
    private String password;
    private int id;
    public User(String login, String password, int id){
        this.login = login;
        this.password = password;
        this.id = id;
    }
    public String  getLogin(){
        return login;
    }
    public String  getPassword(){
        return password;
    }
    public int  getId(){
        return id;
    }
}
