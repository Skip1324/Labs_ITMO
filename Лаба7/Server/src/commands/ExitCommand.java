package commands;

import DB.User;
import Manager.CollectionManager;
import Manager.Command;
import Manager.CommandResult;
import Manager.IOManager;
import Util.ReceiveServer;
import data.City;

import java.io.IOException;

public class ExitCommand extends Command {
    private final CollectionManager col;
    private final IOManager ioManager;
    private ReceiveServer receiveServer;
    /**
     * Command 'save'. Saves the collection to str file.
     */
    public ExitCommand(CollectionManager col, IOManager ioManager, ReceiveServer receiveServer) {
        super("save", "сохраняет коллекцию в файл.");
        this.col = col;
        this.ioManager = ioManager;
        this.receiveServer = receiveServer;
    }

    /**
     * Executes the command.
     *
     * @return Command exit status.
     * @throws IOException When something with file went wrong.
     */

    @Override
    public CommandResult execute(String val, City city, String end, User user) throws IOException {
        if (!val.isEmpty() && !end.equals("")) {
            return new CommandResult("exit","Некорректный ввод, у этой команды нет аргумента!",false);
        }

        return new CommandResult("exit","До встречи!",true);
    }

}

