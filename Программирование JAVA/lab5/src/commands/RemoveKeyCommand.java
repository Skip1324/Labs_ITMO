package commands;

import Exceptions.IncorrectDataException;
import Managers.CollectionManager;
import Managers.IOManager;

/**
 * command 'remove_key' remove element of collection, when it equals key of input.
 */

public class RemoveKeyCommand extends Command {
    private final CollectionManager col;
    private final IOManager io;

    public RemoveKeyCommand(CollectionManager col, IOManager io) {
        super("remove_key", "удаляет элемент из коллекции по его ключу.");
        this.col = col;
        this.io = io;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     * @throws IncorrectDataException if something goes wrong with data in collection.
     */

    @Override
    public boolean execute(String val) throws IncorrectDataException {
        if (val.isEmpty()) {
            io.printerr("Введите ключ по которому хотите удалить в аргумент команды!");
            return false;
        }
        try {
            col.removeKeyElement(col.getCollection(), Integer.parseInt(val));
        } catch (NumberFormatException e) {
            IOManager.writeln("Значение должно быть числом!");
        }
        return true;

    }

}
