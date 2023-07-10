package commands;
import Exceptions.IncorrectDataException;
import Managers.CollectionManager;
import Managers.IOManager;
import java.io.IOException;
/**
 * Command 'remove_lower'. Removes elements lower than user entered.
 */
public class RemoveLowerCommand extends Command{
    private final CollectionManager col;
    private final IOManager io;
    public RemoveLowerCommand(CollectionManager col, IOManager io) throws IncorrectDataException {
        super("remove_lower", "удаляет из коллекции все элементы, меньшие, чем заданный.");
        this.col = col;
        this.io = io;
    }
    /**
     * Executes the command.
     * @return Command exit status.
     * @throws IOException When something with file went wrong.
     */
    @Override
    public boolean execute(String val) throws IOException {
        if(!val.isEmpty()){
            io.printerr("Некорректный ввод, у этой команды нет аргумента!");
            return false;
        }
//        CommunicationManager com = new CommunicationManager(new Scanner(System.in));
//        City city = new City(com.setName(),com.setCoordinates(),com.setDate(),com.setArea(),com.setPopulation(),com.setMetersAboutSeaLevel(),com.setAgglo(),com.setClimate(),com.setStandardOfLiving(),com.setGovernor());
        col.removeLowerElement(col.getCollection());
        return true;

    }
}
