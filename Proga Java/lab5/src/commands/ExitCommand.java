package commands;

import Exceptions.IncorrectDataException;
import Managers.IOManager;
import Managers.InputManager;
/**
 * Command 'exit' close program.
 */
public class ExitCommand extends Command{
    private final InputManager inp;
    private final IOManager io;
    public ExitCommand(InputManager inp, IOManager io) {
        super("exit", "завершает программу.");
        this.inp = inp;
        this.io = io;
    }
    /**
     * Executes the command.
     * @return Command exit status.
     * @throws IncorrectDataException If something goes wrong with data in collection.
     */
    @Override
    public boolean execute(String val) throws IncorrectDataException {
        if (!val.isEmpty()) {
            io.printerr("Некорректный ввод, у этой команды нет аргумента!");
            return false;
        }
        io.writeln("До встречи!");
        inp.turnOffInput();
        return true;
    }
}
