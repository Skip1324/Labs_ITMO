package Util;
import DB.User;
import Manager.*;
import java.io.*;
import java.net.*;
import java.sql.SQLException;

public class ReceiveServer {
    private final CommandManager commands;
    private final IOManager io;
    private Boolean statusInput = true;
    private Boolean statusFile = false;
    private String result = "";
    private volatile User user;
    Serializator<CommandResult> serializator;

    public ReceiveServer(CommandManager commands, IOManager io) throws SocketException {
        this.commands = commands;
        this.io = io;
    }

    public void execute() throws SQLException, IOException, ClassNotFoundException {
        while (statusInput){
            RequestHandler requestHandler = new RequestHandler(commands, statusFile);
            requestHandler.start();
            requestHandler.processRequests();
        }
    }
    synchronized public String execute_script(User user) throws IOException {
                String name, line;
                String[] command;
                CommandResult resultExecute;
                    while(statusFile) {
                        try {
                            line = io.readLine();
                            if (line == null) {
                                break;
                            }
                            command = (line.trim() + " ").split(" ", 2);
                            name = command[0].trim();
                            String value = command[1].trim();
                            if (name.equals("")) {
                                continue;
                            }
                            if (!commands.getMap().containsKey(name)) {
                                result = "Неизвестная команда в файле!";
                                break;
                            }
                            if (!value.equals("")) {
                                resultExecute = commands.getMap().get(name).execute(value, null, "", user);
//                                System.out.println(resultExecute.getData());
                                result += resultExecute.getData() + "\n";
                                continue;
                            }
                            resultExecute = commands.getMap().get(name).execute("", null, "", user);
                            if (result.length() < 3000) {
                                result += resultExecute.getData() + "\n";
                            }
                            if (!statusFile) {
                                break;
                            }
                            if (result.length() > 3000) {
                                result = result.substring(0, 3000) + "....\n";
                            }
                        } catch (IOException e) {
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
        return result;
    }
    /**
     * Turn on file scanner.
     */
    public void turnOnFile() {
        statusFile = true;
    }

    /**
     * Turn off input.
     */
    public void turnOffInput() {
        statusInput = false;
    }

    /**
     * Turn off file scanner.
     */
    public void turnOffFile() {
        statusFile = false;
    }

    public Boolean getStatusFile() {
        return statusFile;
    }
}




