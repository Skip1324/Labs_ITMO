package commands;

import Exceptions.IncorrectDataException;
import Managers.CollectionManager;
import Managers.IOManager;

/**
 * command 'Filter_contains_name' print element of collection if there is a substring in its name.
 */

public class FilterContainsNameCommand extends Command{
    private final CollectionManager col;
    private final IOManager io;
    public FilterContainsNameCommand(CollectionManager col,  IOManager io) {
        super("filter_contains_name", "выводит элементы, значение поля name которых содержит заданную подстроку.");
        this.col = col;
        this.io = io;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     * @throws IncorrectDataException If something goes wrong with data in collection.
     */

    @Override
    public boolean execute(String val) throws IncorrectDataException {
        if(val.isEmpty()){
            io.printerr("Введите подстроку, которую хотите найти в аргумент команды!");
            return false;
        }
        col.containsName(col.getCollection(),val);
        return true;
    }
}

