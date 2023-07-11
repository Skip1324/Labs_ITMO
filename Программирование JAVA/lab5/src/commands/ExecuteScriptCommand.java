package commands;

import Exceptions.IncorrectDataException;
import Managers.IOManager;
import Managers.InputManager;

import java.io.*;
import java.util.Stack;
/**
 * Command 'execute_script'. Executes scripts from a file.
 */
public class ExecuteScriptCommand extends Command {
    private final InputManager inp;
    private final IOManager io;
    private final Stack<BufferedReader> previosReaders = new Stack<>();
    private final Stack<File> currentFiles = new Stack<>();

    public ExecuteScriptCommand(InputManager inp, IOManager io) {
        super("execute_script", "считывает и исполняет скрипт из указанного файла.");
        this.inp = inp;
        this.io = io;
    }
    /**
     * Executes the command.
     * @return Command exit status.
     * @throws IncorrectDataException if something goes wrong with data in collection.
     * @throws FileNotFoundException if file not found.
     */
    @Override
    public boolean execute(String val) throws IncorrectDataException, FileNotFoundException {
        if(val.isEmpty()){
            IOManager.printerr("Введите аргументом команды файл для скрипта!");
            return false;
        }
        File file = new File(val);
        try (BufferedReader newReader = new BufferedReader(new FileReader(file))) {
            if (file.exists() && !currentFiles.contains(file)) {
                io.writeln("Выполнение скрипта:" );
                io.writeln("------------------------------------------");
                currentFiles.push(file);
                previosReaders.push(io.getReader());
                io.setReader(newReader);
                inp.turnOnFile();
                inp.execute();
                currentFiles.pop();
                io.setReader(previosReaders.pop());
                inp.turnOffFile();
            } else if (!file.exists()) {
                io.printerr("Файла не существует!");
                return false;
            } else if(currentFiles.contains(file)) {
                io.printerr("Возникла рекурсия!");
                return false;
            }
        } catch (IncorrectDataException | NullPointerException e) {
            io.printerr("Неверные данные в файле!");
            return false;
        } catch (IOException e) {
            io.printerr("Файла не существует или у вас недостаточно прав!");
        }
        io.writeln("------------------------------------------");
        return true;
    }

}
