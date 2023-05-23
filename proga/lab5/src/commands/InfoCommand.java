package commands;


import Exceptions.IncorrectDataException;
import Managers.CollectionManager;
import Managers.IOManager;

/**
 * Command 'info'. Prints information about the collection.
 */
public class InfoCommand extends Command{
    private final CollectionManager col;
    private final IOManager io;
    public InfoCommand(CollectionManager col, IOManager io) {
        super("info", "выводит  информацию о коллекции.");
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
        if(!val.isEmpty()){
            io.printerr("Некорректный ввод, у этой команды нет аргумента!");
            return false;
        }
        io.writeln("Класс коллекции: " +col.getClass());
        io.writeln("Время инициализации: " +col.getTime());
        io.writeln("Размер коллекции: " +col.getSize());
        return true;

    }
}

