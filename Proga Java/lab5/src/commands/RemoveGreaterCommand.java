package commands;
import Exceptions.IncorrectDataException;
import Managers.*;
import java.io.IOException;

public class RemoveGreaterCommand extends Command{
    private final CollectionManager col;
    private final IOManager io;
    /**
     * Command 'remove_greater'. Removes elements greater than user entered.
     */
    public RemoveGreaterCommand(CollectionManager col, IOManager io) throws IncorrectDataException {
        super("remove_greater", "удаляет из коллекции все элементы, превышающие заданный.");
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
        col.removeGreaterElement(col.getCollection());
        return true;

    }
}

