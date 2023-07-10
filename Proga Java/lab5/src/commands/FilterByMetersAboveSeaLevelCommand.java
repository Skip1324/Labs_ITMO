package commands;

import Exceptions.IncorrectDataException;
import Managers.CollectionManager;
import Managers.IOManager;

/**
 * command 'filter_by_meters_above_sea_level' print element of  the collection if it equals the input.
 */

public class FilterByMetersAboveSeaLevelCommand extends Command{
    private final CollectionManager col;
    private final IOManager io;
    public FilterByMetersAboveSeaLevelCommand(CollectionManager col,IOManager io) {
        super("filter_by_meters_above_sea_level", "выводит элементы, значение поля metersAboveSeaLevel которых равно заданному.");
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
            io.printerr("Введите расположение над уровнем моря аргументом команды!");
            return false;
        }
        try {
            col.filterByMetersAboveSeaLevel(col.getCollection(),Double.parseDouble(val));
        }catch(NumberFormatException e){
            IOManager.writeln("Передаваться должно только одно значение, которое является числом!");
        }

        return true;
    }
}
