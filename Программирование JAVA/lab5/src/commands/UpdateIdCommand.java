package commands;

import Exceptions.IncorrectDataException;
import Managers.CollectionManager;
import Managers.IOManager;
/**
 * Command 'update'. Updates the information about selected city by id.
 */
public class UpdateIdCommand extends Command{
    private final CollectionManager col;
    private final IOManager io;
    public UpdateIdCommand(CollectionManager col,IOManager io) {
        super("update", " обновляет значение элемента коллекции, id которого равен заданному.");
        this.col = col;
        this.io = io;
    }
    /**
     * Executes the command.
     * @throws IncorrectDataException When data for element is incorrect.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String val) throws IncorrectDataException{
        if(val.isEmpty()){
        io.printerr("Введите id элемента,который хотите обновить в аргумент команды!");
        return false;
        }
        try {
            col.updId(col.getCollection(), Long.parseLong(val));
        }catch(NumberFormatException e){
            IOManager.printerr("Значение должно быть числом!");
        }
        return true;

    }
}

