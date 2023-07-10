package Managers;
import Exceptions.IncorrectDataException;
import sun.misc.Signal;
import java.io.EOFException;
import java.io.IOException;
import java.util.Objects;

/**
 * Operates command input.
 */
public class InputManager {
    private final CommandManager commands;
    private final IOManager ioManager;
    private Boolean statusInput;
    private Boolean statusFile;

    public InputManager(CommandManager commands, IOManager ioManager) {
        this.commands = commands;
        this.ioManager = ioManager;
        statusInput = true;
        statusFile = false;
    }
    /**
     * Mode for catching commands from user input.
     * @throws IOException When something with file went wrong.
     * @throws IncorrectDataException if
     */

    public void execute() throws  IOException, IncorrectDataException, EOFException {
        try {
            String name, line;
            String[] command;
            boolean resultExecute;
            while (statusInput) {
                Signal.handle(new Signal("INT"),
                        signal -> {
                            IOManager.writeln("\nДо свидания =(");
                            System.exit(0);
                        });
                Signal.handle(new Signal("TERM"),
                        signal -> {
                            IOManager.writeln("\nДо свидания =(");
                            System.exit(0);
                        });
                Signal.handle(new Signal("ABRT"),
                        signal -> {
                            IOManager.writeln("\nДо свидания =(");
                            System.exit(0);
                        });
                line = ioManager.readLine();
                if (Objects.equals(line, null)) {
                    break;
                }
                command = (line.trim() + " ").split(" ", 2);
                name = command[0];
                if (name.equals("")) {
                    continue;
                }
                if (!commands.getMap().containsKey(name)) {
                    if (!statusFile) {
                        ioManager.printerr("Неизвестная команда, напишите help, что бы вывести список доступных команд!");
                    } else {
                        ioManager.printerr("Неизвестная команда в файле!");
                        break;
                    }
                    continue;
                }
                if (!(command[1].trim()).equals("")) {
                    String value = command[1].trim();
                    resultExecute = commands.getMap().get(name).execute(value);
                    continue;
                }
                resultExecute = commands.getMap().get(name).execute("");
                if (!(!statusFile || resultExecute)) {
                    break;
                }

            }
        }catch (EOFException e){
            IOManager.printerr("Пока");
        }

    }
    /**
     * Turn on file scanner.
     */
    public void turnOnFile(){
        statusFile = true;
    }
    /**
     * Turn off input.
     */
    public void turnOffInput(){
        statusInput = false;
    }
    /**
     * Turn off file scanner.
     */
    public void turnOffFile(){
        statusFile = false;
    }

}

