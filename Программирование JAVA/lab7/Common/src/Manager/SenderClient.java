package Manager;
import DB.User;
import data.City;
import java.io.Serializable;
public class SenderClient implements Serializable {
    private  String arg;
    private String name;
    private City city;
    private String end;
    private User user;
    public SenderClient(String name, String arg){
        this.arg = arg;
        this.name = name;
    }

    public synchronized String getArg(){
        return arg;
    }
    public synchronized String getName(){
        return name;
    }
    public synchronized City getCity(){
        return city;
    }
    public synchronized String getEnd(){
        return end;
    }
    public synchronized void setCity(City city){
        this.city = city;
    }
    public synchronized void setEnd(String end){
        this.end = end;
    }
    public synchronized void setUser(User user) {
        this.user = user;
    }
    public synchronized User getUser(){
        return user;
    }

}
