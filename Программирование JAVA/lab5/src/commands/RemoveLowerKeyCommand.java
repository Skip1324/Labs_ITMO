package commands;
import Exceptions.IncorrectDataException;
import Managers.CollectionManager;
import Managers.IOManager;
import java.io.IOException;

/**
 * command 'remove_lower_key' Removes elements lower than key, which user entered.
 */

public class RemoveLowerKeyCommand extends Command{
    private final CollectionManager col;
    private final IOManager io;
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
    public boolean execute(String val) throws IOException {
        if (val.isEmpty()) {
            io.printerr("Введите ключ по которому хотите удалить в аргумент команды!");
            return false;
        }
        try{
            col.removeLowerKey(col.getCollection(),Integer.parseInt(val));
        }catch (NumberFormatException e){
            IOManager.printerr("Значение должно быть числом!");
        }
        return true;
    }
}
