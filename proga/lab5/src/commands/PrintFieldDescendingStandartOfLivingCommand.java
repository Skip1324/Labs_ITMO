package commands;
import Exceptions.IncorrectDataException;
import Managers.CollectionManager;
import Managers.IOManager;
/**
 * Command 'print_field_descending_standard_of_living'. Print all fields standard of living in descending order.
 */
public class PrintFieldDescendingStandartOfLivingCommand extends Command{
    private final CollectionManager col;
    private final IOManager io;
    public PrintFieldDescendingStandartOfLivingCommand(CollectionManager col, IOManager io) {
        super("print_field_descending_standard_of_living", "выводит значения поля standardOfLiving всех элементов в порядке убывания.");
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
        col.printStandardOfLiving(col.getCollection());
        return true;
    }
}

