package commands;

import Exceptions.IncorrectDataException;
import Managers.CommandManager;
import Managers.IOManager;

import java.util.Map;

public class HelpCommand extends Command {
    private final CommandManager com;
    private final IOManager io;

    /**
     * Command 'help'. Print info about all programs.
     */
    public HelpCommand(CommandManager com, IOManager io) {
        super("help", "вывести справку по доступным командам.");
        this.com = com;
        this.io = io;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     * @throws IncorrectDataException if something goes wrong with data in collection.
     */
    @Override
    public boolean execute(String val) throws IncorrectDataException {
        if (!val.isEmpty()) {
            io.printerr("Некорректный ввод, у этой команды нет аргумента!");
            return false;
        }
        Map<String, Command> commandsMap = com.getMap();
        for (Map.Entry<String, Command> entry : commandsMap.entrySet()) {
            io.writeln((entry.getValue().getName() + " :" + entry.getValue().getDescription()));
        }
        return true;
    }
}

