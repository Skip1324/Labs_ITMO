import DB.User;
import Exeception.IncorrectDataException;
import Manager.*;
import data.City;
import sun.misc.Signal;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.sql.SQLException;


/**
 * Operates command input.
 */
public class InputManager {
    private final IOManager ioManager;
    private final Boolean statusInput;
    User user = null;

    public InputManager(IOManager ioManager) throws SocketException {
        this.ioManager = ioManager;
        statusInput = true;
    }


    /**
     * Mode for catching commands from user input.
     *
     * @throws IOException            When something with file went wrong.
     * @throws IncorrectDataException if
     */

    public void execute() throws IOException, IncorrectDataException, EOFException, ClassNotFoundException {
        String name, line, end, arg;
        String[] command;
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
                        IOManager.writeln("\nДо свидания!");
                        System.exit(0);
                    });
            line = ioManager.readLine();
            if (line.trim().length() == 0) {
                continue;
            }
            command = (line.trim() + " ").split(" ", 3);
            name = command[0].trim();
            arg = command[1].trim();
            if (user != null && name.equals("login") && arg.equals("")) {
                System.out.println("Вы уже авторизованны!");
                continue;
            }
            if (name.equals("save")) {
                ioManager.writeln("У вас недостаточно прав на эту команду!");
                continue;
            }
            if (name.equals("exit")) {
                ioManager.writeln("До новых встреч!");
                System.exit(0);
                continue;
            }
            if (command.length < 3) {
                end = "";
            } else {
                end = command[2].trim();
            }
            SenderClient s = new SenderClient(name, arg);
            switch (name) {
                case "insert":
                case "update":
                    if (user != null) {
                        if (arg.matches("^[0-9]+$") && end.equals("")) {
                            City city = new CommunicationManager(ioManager).askCity();
                            s.setCity(city);
                        }
                    } else {
                        System.out.println("Войдите по команде: \"login\"");
                        continue;
                    }
                    break;
                case "remove_greater":
                case "remove_lower":
                    if (user != null) {
                        if (arg.equals("")) {
                            City city = new CommunicationManager(ioManager).askCity();
                            s.setCity(city);
                        }
                    } else {
                        System.out.println("Войдите по команде: \"login\"");
                        continue;
                    }
                    break;
                case "login":
                    Login login = new Login(ioManager);
                    try {
                        user = login.login();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    continue;
                default:
                    if (user == null) {
                        System.out.println("Войдите по команде: \"login\"");
                        continue;
                    }
            }
            s.setUser(user);
            s.setEnd(end);
            Serializator<SenderClient> ser = new Serializator<>(s);
            DatagramChannel channel = DatagramChannel.open();
            InetSocketAddress serverAddress = new InetSocketAddress("localhost", 1050);

            ByteBuffer sendBuffer = ByteBuffer.wrap(ser.serialize());
            channel.send(sendBuffer, serverAddress);


            ByteBuffer receiveBuffer = ByteBuffer.allocate(100000);
            channel.receive(receiveBuffer);
            receiveBuffer.flip();

            byte[] responseData = new byte[receiveBuffer.remaining()];
            receiveBuffer.get(responseData);
            ByteArrayInputStream bis = new ByteArrayInputStream(responseData);
            ObjectInputStream ois = new ObjectInputStream(bis);
            Object obj = ois.readObject();
            channel.close();
            if (obj instanceof SenderClient || obj == null) {
                continue;
            }
            CommandResult commandResult = (CommandResult) obj;
            if (commandResult.getData() == null) {
                continue;
            }
            ioManager.writeln(commandResult.getData());
            String res = (String) commandResult.getData();
            if (res.contains("До встречи!")) {
                System.exit(0);
            }
        }
    }

}








