package commands;

import Exceptions.IncorrectDataException;
import Managers.CollectionManager;
import Managers.IOManager;
/**
 * Command 'clear'. Clears the collection.
 */
public class ClearCommand extends Command{
    private final CollectionManager col;
    private final IOManager io;
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
    public boolean execute(String val) throws IncorrectDataException {
        if(!val.isEmpty()) {
            io.printerr("Некорректный ввод, у этой команды нет аргумента!");
            return false;
        }
        col.clearCollection();
        io.writeln("Коллекция очищена!");
        return true;

    }
}
