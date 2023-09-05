import Exeception.IncorrectDataException;
import Manager.*;
import data.City;
import sun.misc.Signal;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;


/**
 * Operates command input.
 */
public class InputManager {
    private final IOManager ioManager;
    private Boolean statusInput;


    public InputManager(IOManager ioManager) {
        this.ioManager = ioManager;
        statusInput = true;
    }


    /**
     * Mode for catching commands from user input.
     *
     * @throws IOException            When something with file went wrong.
     * @throws IncorrectDataException if
     */

    public void execute() throws IOException, IncorrectDataException, EOFException {
        try {
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
                if(name.equals("save")){
                    ioManager.writeln("У вас недостаточно прав на эту команду!");
                    continue;
                }
                if(name.equals("exit")){
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
                        if (arg.matches("^[0-9]+$") && end.equals("")) {
                            City city = new CommunicationManager(ioManager).askCity();
                            s.setCity(city);}
                        break;
                    case "remove_greater":
                    case "remove_lower":
                        if (arg.equals("")){
                            City city = new CommunicationManager(ioManager).askCity();
                            s.setCity(city);
                        break;}
                }
                s.setEnd(end);
                Serializator<SenderClient> ser = new Serializator<SenderClient>(s);
                DatagramSocket socket = new DatagramSocket();
                socket.setSoTimeout(30000);
                InetAddress address = InetAddress.getByName("localhost");
                int port = 1050;
                byte[] buffer = ser.serialize();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
                socket.send(packet);
                byte[] responseBuffer = new byte[100000];
                DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);
                socket.receive(responsePacket);
                byte[] data = responsePacket.getData();
                ByteArrayInputStream bis = new ByteArrayInputStream(data);
                ObjectInputStream ois = new ObjectInputStream(bis);
                Object obj = ois.readObject();
                CommandResult commandResult = (CommandResult) obj;
                if (commandResult.getData() == null) {
                    continue;
                }
                ioManager.writeln(commandResult.getData());
                if (commandResult.getData().equals("До встречи!")) {
                    System.exit(0);
                }
                socket.close();
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SocketTimeoutException e) {
            IOManager.writeln("Ошибка: сервер не отвечает");
            execute();
        }
    }
}




