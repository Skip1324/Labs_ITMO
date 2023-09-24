package commands;
import DB.DBConnector;
import DB.User;
import Exeception.IncorrectDataException;
import Manager.*;
import data.City;
import java.io.IOException;
import java.sql.SQLException;

public class RemoveGreaterCommand extends Command {
    private final CollectionManager col;
    private final IOManager io;
    private final CommunicationManager com;
    private String message;
    private DBConnector dbConnector = new DBConnector();
    /**
     * Command 'remove_greater'. Removes elements greater than user entered.
     */
    public RemoveGreaterCommand(CollectionManager col, IOManager io, CommunicationManager com) throws IncorrectDataException {
        super("remove_greater", "удаляет из коллекции все элементы, превышающие заданный.");
        this.col = col;
        this.io = io;
        this.com = com;

    }
    /**
     * Executes the command.
     * @return Command exit status.
     * @throws IOException When something with file went wrong.
     */
    @Override
    public CommandResult execute(String val, City c,String end, User user) throws IOException {
        if(!val.isEmpty() && !end.equals("")){
            message = "Некорректный ввод, у этой команды нет аргумента!";
            return new CommandResult("remove_greater",message,false);
        }
        if(c != null) {
            try {
                dbConnector.deleteElementDBbyComparator(c.getName(),c.getArea(),c.getPopulation(),">",user);
            } catch (SQLException e) {
                message = "Не получилось добавить элемент в базу данных!";
                return new CommandResult("insert", message, false);
            }
            message = col.removeGreaterElement(col.getCollection(), c, user);
            return new CommandResult("remove_greater", message, true);
        }
        if (val.equals("") && c == null) {
        City city =  com.askCity();
            try {
                dbConnector.deleteElementDBbyComparator(city.getName(),city.getArea(),city.getPopulation(),">",user);
            } catch (SQLException e) {
                message = "Не получилось добавить элемент в базу данных!";
                return new CommandResult("insert", message, false);
            }
            message = col.removeGreaterElement(col.getCollection(), city,user);
            return new CommandResult("remove_greater", message, true);
        } else {
            message = "У этой команды нет аргумента!";
            return new CommandResult("remove_greater", message, false);
        }

    }
}

