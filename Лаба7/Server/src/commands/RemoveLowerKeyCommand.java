package commands;
import DB.DBConnector;
import DB.User;
import Exeception.IncorrectDataException;
import Manager.CollectionManager;
import Manager.Command;
import Manager.CommandResult;
import Manager.IOManager;
import data.City;

import java.io.IOException;
import java.sql.SQLException;

/**
 * command 'remove_lower_key' Removes elements lower than key, which user entered.
 */

public class RemoveLowerKeyCommand extends Command {
    private final CollectionManager col;
    private final IOManager io;
    private String message;
    private DBConnector dbConnector = new DBConnector();
    public RemoveLowerKeyCommand(CollectionManager col, IOManager io) {
        super("remove_lower_key", "удаляет из коллекции все элементы, ключ которых меньше, чем заданный.");
        this.col = col;
        this. io = io;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     * @throws IncorrectDataException if something goes wrong with data in collection.
     */

    @Override
    public CommandResult execute(String val, City city, String end, User user) throws IOException {
        if (val.isEmpty() && !end.equals("")) {
            message = "Введите ключ по которому хотите удалить в аргумент команды!";
            return new CommandResult("remove_lower_key",message,false);
        }
        try{
            dbConnector.deleteElementDBbyKey(Integer.parseInt(val),"<",user);
            message = col.removeLowerKey(col.getCollection(),Integer.parseInt(val));
        }catch (NumberFormatException e){
            message = "Значение должно быть числом!";
        } catch (SQLException e) {
            message = "Не получилось добавить элемент в базу данных!";
            return new CommandResult("insert", message, false);
        }
        return new CommandResult("remove_lower_key", message, true);
    }


}
