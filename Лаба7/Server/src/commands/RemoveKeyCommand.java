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
 * command 'remove_key' remove element of collection, when it equals key of input.
 */

public class RemoveKeyCommand extends Command {
    private final CollectionManager col;
    private final IOManager io;
    private String message;
    private DBConnector dbConnector = new DBConnector();

    public RemoveKeyCommand(CollectionManager col, IOManager io) {
        super("remove_key", "удаляет элемент из коллекции по его ключу.");
        this.col = col;
        this.io = io;
    }

    /**
     * Executes the command.
     *
     * @return Command exit status.
     * @throws IncorrectDataException if something goes wrong with data in collection.
     */

    @Override
    public CommandResult execute(String val, City city, String end, User user) throws IncorrectDataException {
        if (val.isEmpty() && !end.equals("")) {
            message = "Введите ключ по которому хотите удалить в аргумент команды!";
            return new CommandResult("remove_key", message,false);
        }
        try {
            dbConnector.deleteElementDBbyKey(Integer.parseInt(val),"=",user);
            message = col.removeKeyElement(col.getCollection(), Integer.parseInt(val),user);
        } catch (NumberFormatException e) {
            message = "Значение должно быть числом!";
        } catch (SQLException e) {
            message = "Не получилось добавить элемент в базу данных!";
            return new CommandResult("insert", message, false);
        }
        return new CommandResult("remove_key", message,true);

    }



}
