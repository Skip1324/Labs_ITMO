package commands;
import Managers.CollectionManager;
import Managers.IOManager;
import Managers.ParsJson;

import java.io.File;
import java.io.IOException;

public class SaveCommand extends Command{
    private final CollectionManager col;
    private final ParsJson pars;
    private final File file;
    private final IOManager ioManager;
    /**
     * Command 'save'. Saves the collection to str file.
     */
    public SaveCommand(CollectionManager col, ParsJson pars, File file, IOManager ioManager) {
        super("save", "сохраняет коллекцию в файл.");
        this.col = col;
        this.pars = pars;
        this.file = file;
        this.ioManager = ioManager;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     * @throws IOException  When something with file went wrong.
     */

    @Override
    public boolean execute(String val) throws IOException {
        try {
            if (!val.isEmpty()) {
                ioManager.printerr("Некорректный ввод, у этой команды нет аргумента!");
                return false;
            }
            if (pars.serialize(col, file)) {
                ioManager.writeln("Коллекция сохранена!");
            } else {
                ioManager.writeln("Коллекция  не сохранилась!");
            }
            return true;
        } catch (IOException e) {
            ioManager.printerr("У пользователя не достаточно прав!");
            return false;
        }
    }
    }

