package commands;
import DB.DBConnector;
import DB.User;
import Exeception.IncorrectDataException;
import Manager.CollectionManager;
import Manager.Command;
import Manager.CommandResult;
import Manager.IOManager;
import data.City;

import java.sql.SQLException;

/**
 * Command 'clear'. Clears the collection.
 */
public class ClearCommand extends Command {
    private final CollectionManager col;
    private final IOManager io;
    private  String message;
    private DBConnector db = new DBConnector();
    public ClearCommand(CollectionManager col, IOManager io) {
        super("clear", "очищает коллекцию.");
        this.col = col;
        this.io = io;
    }
    /**
     * Executes the command.
     * @return Command exit status.
     * @throws IncorrectDataException If something goes wrong with data in collection.
     */
    @Override
    public CommandResult execute(String val, City city, String end, User user) throws IncorrectDataException {
        if(!val.isEmpty() && !end.equals("")) {
            return new CommandResult("clear","Некорректный ввод, у этой команды нет аргумента!",false);
        }
        try {
            db.clearDB(user);
            message = "Коллекция очищена!";
            System.out.println("jopa");
        } catch (SQLException e) {
             message = "Не получилось добавить элемент в базу данных!";
            return new CommandResult("insert", message, false);
        }
        System.out.println("lichiko");
        col.clearCollection(user);
        System.out.println("siski");
        return new CommandResult("clear",message,true);
    }

}
