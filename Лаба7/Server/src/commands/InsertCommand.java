package commands;
import DB.DBConnector;
import DB.User;
import Exeception.IncorrectDataException;
import Manager.*;
import data.City;
import java.io.*;
import java.sql.SQLException;

/**
 * Command 'insert'. Insert a new element to collection.
 */
public class InsertCommand extends Command {
    private final CollectionManager collectionManager;
    private final IOManager io;
    private final CommunicationManager communicationManager;
    private String message;
    DBConnector db = new DBConnector();

    public InsertCommand(CollectionManager collectionManager, IOManager io, CommunicationManager com, CommunicationManager communicationManager) {
        super("insert {element}", "add new element in collection.");
        this.collectionManager = collectionManager;
        this.io = io;
        this.communicationManager = communicationManager;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     * @throws IncorrectDataException When data for element is incorrect.
     */
    @Override
    public CommandResult execute(String val, City city, String end, User user) throws IOException {
        if (val.isEmpty()) {
            message = ("Введите ключ для нового элемента в аргумент команды!");
            return new CommandResult("insert", message, false);
        }
        if (city != null) {
            try {
                if(collectionManager.getCollection().containsKey(Integer.parseInt(val)) && db.checkId(Integer.parseInt(val)) != user.getId()){
                    message = "Элемент с этим ключом уже занят!";
                    return new CommandResult("insert", message, false);
                }
                if(collectionManager.getCollection().containsKey(Integer.parseInt(val)) && db.checkId(Integer.parseInt(val)) == user.getId()){
                    db.deleteElementDBbyKey(Integer.parseInt(val),"=",user);
                }
                db.insertDB(city,Integer.parseInt(val),user);
                city.setId(db.setIdDb());
                city.setUserId(user.getId());
                collectionManager.insertElement(Integer.parseInt(val),city);
                message = ("Элемент успешно добавлен.");
                return new CommandResult("insert", message, true);
            } catch (NumberFormatException e) {
                message = "Аргумент команды должен быть числом!";
                return new CommandResult("insert", message, false);
            }
            catch (SQLException e) {
                message = "Не получилось добавить элемент в базу данных!";
                return new CommandResult("insert", message, false);
            }
        }
        if (val.matches("^[0-9]+$")) {
            City c = communicationManager.askCity();
            System.out.println(c.toString());
            try {
                if(collectionManager.getCollection().containsKey(Integer.parseInt(val)) && db.checkId(Integer.parseInt(val)) != user.getId()){
                    message = "Элемент с этим ключом уже занят!";
                    return new CommandResult("insert", message, false);
                }
                if(collectionManager.getCollection().containsKey(Integer.parseInt(val)) && db.checkId(Integer.parseInt(val)) == user.getId()){
                    db.deleteElementDBbyKey(Integer.parseInt(val),"=",user);
                }
                db.insertDB(c,Integer.parseInt(val),user);
                c.setId(db.setIdDb());
                c.setUserId(user.getId());
                collectionManager.insertElement(Integer.parseInt(val),c);
                message = ("Элемент успешно добавлен.");
                return new CommandResult("insert", message, true);
            } catch (SQLException e) {
                message = "Не получилось добавить элемент в базу данных!";
                return new CommandResult("insert", message, false);
            }
        }
        else {
            message = "У этой команды 1 аргумент, который является числом!";
            return new CommandResult("insert", message, false);
        }

    }
}
