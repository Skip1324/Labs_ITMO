package commands;
import Exceptions.IncorrectDataException;
import Managers.CollectionManager;
import Managers.CommunicationManager;
import Managers.IOManager;
import data.City;
import java.util.Scanner;
/**
 * Command 'insert'. Insert a new element to collection.
 */
public class InsertCommand extends Command {
    private final CollectionManager collectionManager;
    private final IOManager io;

    public InsertCommand(CollectionManager collectionManager, IOManager io) {
        super("insert {element}", "add new element in collection.");
        this.collectionManager = collectionManager;
        this.io = io;
    }
    Scanner sc = new Scanner(System.in);
    CommunicationManager com = new CommunicationManager(sc);
    /**
     * Executes the command.
     * @throws IncorrectDataException When data for element is incorrect.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String val) throws IncorrectDataException {
         if(val.isEmpty()){
             IOManager.printerr("Введите ключ для нового элемента в аргумент команды!");
             return false;
    }
         try{
            collectionManager.insertElement(Integer.parseInt(val),(new City(com.setName(),com.setCoordinates(),com.setDate(),com.setArea(),com.setPopulation(),com.setMetersAboutSeaLevel(),com.setAgglo(),com.setClimate(),com.setStandardOfLiving(),com.setGovernor())));
            IOManager.writeln("Элемент успешно добавлен.");
        }catch(NumberFormatException e){
             IOManager.writeln("У элемента только один ключ и он может быть от 1 до 2147483647!");
        }
        return true;

    }
}


