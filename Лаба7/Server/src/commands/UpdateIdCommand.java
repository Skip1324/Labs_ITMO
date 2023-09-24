package commands;


import DB.DBConnector;
import DB.User;
import Exeception.IncorrectDataException;
import Manager.*;
import data.City;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Command 'update'. Updates the information about selected city by id.
 */
public class UpdateIdCommand extends Command {
    private final CollectionManager col;
    private final IOManager io;
    private final CommunicationManager com;
    private String message;
    private DBConnector db = new DBConnector();
    public UpdateIdCommand(CollectionManager col, IOManager io, CommunicationManager com) {
        super("update", " обновляет значение элемента коллекции, id которого равен заданному.");
        this.col = col;
        this.io = io;
        this.com = com;
    }
    /**
     * Executes the command.
     *
     * @return Command exit status.
     * @throws IncorrectDataException When data for element is incorrect.
     */
    @Override
    public CommandResult execute(String val, City c, String end, User user) throws IOException {
        if(val.isEmpty() && !end.equals("")){
        message = "Введите id элемента,который хотите обновить в аргумент команды!";
        return new CommandResult("update", message,false);
        }
        if(c != null) {
            try {
                int key = db.checkKey(Long.parseLong(val));
                if(col.getCollection().containsKey(key) && db.checkId(key) != user.getId()){
                    System.out.println(col.getCollection().containsKey(key) + " " +  db.checkId(key) + " " + user.getId());
                    message = "Элемент с этим ключом принадлежит не вам!";
                    return new CommandResult("update", message, false);
                }
                if(col.getCollection().containsKey(key)&& db.checkId(key) == user.getId()) {
                    message = col.updId(col.getCollection(), Long.parseLong(val), c);
                    db.deleteElementDBbyKey(key, "=",user);
                    db.insertDB(c, key,user);
                    c.setId(db.setIdDb());
                    col.insertElement(key, c);
                }
                else {
                    message = "В коллекции нет элемента с таким id!";
                }
            } catch (NumberFormatException e) {
                message = "Значение должно быть числом!";
            } catch (SQLException e) {
                message = "Не получилось добавить элемент в базу данных!";
                return new CommandResult("insert", message, false);
            }
            return new CommandResult("update", message, true);
        }
        if (val.matches("^[0-9]+$")){
            City city = com.askCity();
                try {
                    int key = db.checkKey(Long.parseLong(val));
                    if(col.getCollection().containsKey(key) && db.checkId(key) != user.getId()){
                        message = "Элемент с этим ключом принадлежит не вам!";
                        return new CommandResult("update", message, false);
                    }
                    if(col.getCollection().containsKey(key) && db.checkId(key) == user.getId()) {
                        message = col.updId(col.getCollection(), Long.parseLong(val), city);
                        db.deleteElementDBbyKey(key, "=",user);
                        db.insertDB(city, key,user);
                        city.setId(db.setIdDb());
                        col.insertElement(key, city);
                    }
                    else {
                        message = "В коллекции нет элемента с таким id!";
                    }
                } catch (SQLException e) {
                    message = "Не получилось добавить элемент в базу данных!";
                    return new CommandResult("insert", message, false);
                }

        } else {
            message = "У этой команды 1 аргумент, который является числом!";
            return new CommandResult("insert", message, false);
        }

        return new CommandResult("insert", message, true);
    }

}

