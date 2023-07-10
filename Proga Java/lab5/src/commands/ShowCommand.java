package commands;
import Managers.CollectionManager;
import Managers.IOManager;
import java.io.IOException;
/**
 * Command 'show'. Shows information about all elements of the collection.
 */
public class ShowCommand extends Command{
    private final CollectionManager col;
    private final IOManager io;
    public ShowCommand(CollectionManager col, IOManager io) {
        super("show", "выводит все элементы коллекции в строковом представлении.");
        this.col = col;
        this.io = io;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String val) throws IOException {
        if(!val.isEmpty()){
            io.printerr("Некорректный ввод, у этой команды нет аргумента!");
            return false;
        }
        if(col.getSize() == 0){
            io.writeln("Коллекция пустая.");
        }else {
            col.show();
        }
        return true;

    }
}

