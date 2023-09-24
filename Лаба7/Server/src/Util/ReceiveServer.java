package Util;
import DB.User;
import Manager.*;
import data.City;
import sun.misc.Signal;
import Server.Server;
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
        DatagramSocket socket = new DatagramSocket(1050);
        byte[] buffer = new byte[10000];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.setSoTimeout(100000);
        InputStreamReader ff = new InputStreamReader(System.in);
        socket.receive(packet);
        byte[] data = packet.getData();
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object obj = ois.readObject();
        SenderClient s = (SenderClient) obj;
        String name = s.getName();
        String arg = s.getArg();
        City city = s.getCity();
        while (statusInput) {
            user = s.getUser();
            if (name.equals("")) {
                continue;
            }
            if (arg != null && city != null) {
                Server.logger.info(name + " " + arg + " " + city + " " + user.getId());
            }
            if (arg != null) {
                Server.logger.info(name + " " + arg + " " + user.getId());
            } else {
                Server.logger.info(name);
            }
            Signal.handle(new Signal("INT"), signal -> {
                IOManager.writeln("\nДо свидания =(");
                System.exit(0);
            });
            Signal.handle(new Signal("TERM"), signal -> {
                IOManager.writeln("\nДо свидания =(");
                System.exit(0);
            });
            Signal.handle(new Signal("ABRT"), signal -> {
                IOManager.writeln("\nДо свидания!");
                System.exit(0);
            });
            CommandResult commandResult = new CommandResult();
            if (!commands.getMap().containsKey(name) || user == null) {
                if (!statusFile) {
                    String message = ("Неизвестная команда, напишите help, чтобы вывести список доступных команд!");
                    commandResult = new CommandResult("Ответ от сервера", message, false);
                    serializator = new Serializator<>(commandResult);
                    byte[] responseBuffer = serializator.serialize();
                    InetAddress address = packet.getAddress();
                    int port = packet.getPort();
                    DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length, address, port);
                    socket.send(responsePacket);
                    socket.close();
                    break;

                } else {
                    String message = ("Неизвестная команда в файле!");
                    commandResult = new CommandResult("Ответ от сервера", message, false);
                    serializator = new Serializator<>(commandResult);
                    byte[] responseBuffer = serializator.serialize();
                    InetAddress address = packet.getAddress();
                    int port = packet.getPort();
                    DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length, address, port);
                    socket.send(responsePacket);
                    socket.close();
                    break;

                }
            }
            commandResult = commands.getMap().get(name).execute(arg, city, s.getEnd(), user);
            serializator = new Serializator<>(commandResult);
            byte[] responseBuffer = serializator.serialize();
            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length, address, port);
            socket.send(responsePacket);
            socket.close();
            break;
        }
    }
    synchronized public String execute_script() throws IOException {
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
                                result += resultExecute.getData() + "\n";
                                continue;
                            }
                            resultExecute = commands.getMap().get(name).execute("", null, "", user);
                            if (result.length() < 3000) {
                                result += resultExecute.getData() + "\n";
                            }
                            if (!statusFile || resultExecute.getResult()) {
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




