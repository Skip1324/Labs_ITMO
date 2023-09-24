package Manager;
import DB.DBConnector;
import DB.SHA224;
import DB.User;
import Manager.Command;
import Manager.CommandResult;
import Manager.IOManager;
import data.City;

import java.io.IOException;
import java.sql.SQLException;

public class Login  {
    private IOManager io;
    private String message;
    DBConnector db = new DBConnector();
    private CommunicationManager communicationManager;

    public Login(IOManager io, CommunicationManager communicationManager) {
        this.io = io;
        this.communicationManager = communicationManager;
    }
    public Login(IOManager io) {
        this.io = io;
    }


    //    private String  Broadcast() throws IOException {
//        String broadcast = "Войдите по команде: \"login\" или зарегистрируйтесь по команде: \"reg\"";
//        while (true) {
//            io.writeln(broadcast);
//            String str = io.readLine();
//            if(str.equals("reg") || str.equals("login")){
//                return str;
//            }
//        }
//    }
    private String RegisterLogin() throws IOException {
        while (true) {
            io.writeln("Придумайте логин: ");
            String login = io.readLine();
            return login;
        }
    }

    private String RegisterPassword() throws IOException {
        while (true) {
            io.writeln("Придумайте пароль: ");
            String password = io.readLine();
            return new SHA224().encryptString(password);
        }
    }

    private String inputLogin() throws IOException, SQLException {
        while (true) {
            io.writeln("Напишите ваш логин: ");
            String login = io.readLine();
            return login;
        }
    }

    private String inputPassword() throws IOException {
        while (true) {
            io.writeln("Напишите ваш пароль: ");
            String password = io.readLine();
            return new SHA224().encryptString(password);
        }
    }

    public User login() throws IOException, SQLException {
        while (true) {
            io.writeln("Войдите по команде: \"login\" или зарегистрируйтесь по команде: \"reg\"");
            String str = io.readLine().trim();
            if (str.equals("reg")) {
                String login = RegisterLogin();
                String password = RegisterPassword();
                db.adduserDB(login, password);
                int userID = db.checkUserDB(login, password);
                User user = new User(login, password, userID);
                return user;
            }
            if (str.equals("login")) {
                String login = inputLogin();
                String password = inputPassword();
                int userID = db.checkUserDB(login, password);
                if (userID == 0) {
                    io.writeln("Неверный логин или пароль, или пользователя не существует!");
                    continue;
                }
                User user = new User(login, password, userID);
                return user;
            }
        }
    }

//    @Override
//    public CommandResult execute(String val, City city, String end) throws IOException, SQLException {
//        if (!val.isEmpty() && !end.equals("")) {
//            message = ("Что-то не так!");
//            return new CommandResult("login", message, false);
//        }
////        io.writeln("Войдите по команде: \"login\" или зарегистрируйтесь по команде: \"reg\"");
////        String str = io.readLine().trim();
//        if (val.equals("reg")) {
//            String login = communicationManager.regLogin();
//            String password = communicationManager.regPassword();
//            int userID = db.checkUserDB(login, password);
//            if (userID == 0) {
//                io.writeln("Неверный логин или пароль, или пользователя не существует!");
//            }
//            User user = new User(login,password,userID);
//            return new CommandResult(user);
//        }
//        if (val.equals("login")) {
//            String login = communicationManager.askLogin();
//            String password = communicationManager.askPassword();
//            int userID = db.checkUserDB(login, password);
//            User user = new User(login,password,userID);
//            return new CommandResult(user);
//        }
//        else {
//            return null;
//        }
//    }
}
