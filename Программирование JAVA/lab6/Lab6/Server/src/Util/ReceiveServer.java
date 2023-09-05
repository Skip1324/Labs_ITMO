package Util;
import Manager.*;
import data.City;
import sun.misc.Signal;
import Server.Server;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Objects;

public class ReceiveServer {
    private SenderClient sender;
    private final CommandManager commands;
    private final IOManager io;
    private Boolean statusInput = true;
    private Boolean statusFile = false;
    private String result ="";
    public ReceiveServer(CommandManager commands, IOManager io){
        this.commands = commands;
        this.io = io;
    }


    public void execute() throws IOException {
        try {
            DatagramSocket socket = new DatagramSocket(1050);
            byte[] buffer = new byte[10000];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.setSoTimeout(1000);
            InputStreamReader ff = new InputStreamReader(System.in);
            try {
//            Timer timer = new Timer();
//                timer.schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        socket.close();
//                        timer.cancel();
//                    }
//                }, 1000);
//                timer.cancel();
                socket.receive(packet);
                byte[] data = packet.getData();
                ByteArrayInputStream bis = new ByteArrayInputStream(data);
                ObjectInputStream ois = new ObjectInputStream(bis);
                Object obj = ois.readObject();
                SenderClient s = (SenderClient) obj;
                sender = s;
                CommandResult resultExecute;
                String name = sender.getName();
                String arg = sender.getArg();
                City city = sender.getCity();
                if(arg != null && city != null){
                    Server.logger.info(name + " " + arg + " " +city);
                }
                if(arg != null ){
                    Server.logger.info(name + " " +arg);
                }
                else {Server.logger.info(name);}
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
                    if (Objects.equals(sender, null)) {
                        continue;
                    }
                    if (name.equals("")) {
                        continue;
                    }
                    if (!commands.getMap().containsKey(name)) {
                        if (!statusFile) {
                            String message = ("Неизвестная команда, напишите help, что бы вывести список доступных команд!");
                            resultExecute = new CommandResult("Ответ от сервера", message,false);
                            Serializator serializator = new Serializator<>(resultExecute);
                            byte[] responseBuffer = serializator.serialize();
                            InetAddress address = packet.getAddress();
                            int port = packet.getPort();
                            DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length, address, port);
                            socket.send(responsePacket);
                            socket.close();
                            break;
                        } else {
                            String message = ("Неизвестная команда в файле!");
                            resultExecute = new CommandResult("Ответ от сервера", message,false);
                            Serializator serializator = new Serializator<>(resultExecute);
                            byte[] responseBuffer = serializator.serialize();
                            InetAddress address = packet.getAddress();
                            int port = packet.getPort();
                            DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length, address, port);
                            socket.send(responsePacket);
                            socket.close();
                            break;
                        }
                    }
                    if (!(arg.equals(""))) {
                        resultExecute = commands.getMap().get(name).execute(arg,city, s.getEnd());
                        Serializator serializator = new Serializator<>(resultExecute);
                        byte[] responseBuffer = serializator.serialize();
                        InetAddress address = packet.getAddress();
                        int port = packet.getPort();
                        DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length, address, port);
                        socket.send(responsePacket);
                        socket.close();
                        break;
                    }
                    resultExecute = commands.getMap().get(name).execute("",city, s.getEnd());
                    Serializator serializator = new Serializator<>(resultExecute);
                    byte[] responseBuffer = serializator.serialize();
                    InetAddress address = packet.getAddress();
                    int port = packet.getPort();
                    DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length, address, port);
                    socket.send(responsePacket);
                    socket.close();
                    break;
                }
            } catch (SocketTimeoutException e) {
                int bufLen = System.in.available();
                if (bufLen > 0) {
                    byte[] bufer = new byte[bufLen];
                    System.in.read(bufer);
                    String input = new String(bufer);
                   if(input.contains("exit")){
                       commands.getMap().get("exit").execute("",null,"");
                       io.writeln("До новых встреч!");
                       System.exit(0);
                   }
                   if(input.contains("save")){
                       commands.getMap().get("save").execute("",null,"");
                   }
                }
            }
            finally {
                socket.close();
                execute();
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String execute_script() throws  IOException {
        try {
            String name, line;
            String[] command;
            CommandResult resultExecute;
            while (statusInput) {
                line = io.readLine();
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
                        result = ("Неизвестная команда, напишите help, что бы вывести список доступных команд!");
                    } else {
                        result = ("Неизвестная команда в файле!");
                        break;
                    }
                    continue;
                }
                if (!(command[1].trim()).equals("")) {
                    String value = command[1].trim();
                    resultExecute = commands.getMap().get(name).execute(value,null,"");
                    if(result.length() < 256){
                    result += (String) resultExecute.getData() + "\n";}
                    continue;
                }
                resultExecute = commands.getMap().get(name).execute("", null,"");
                if(result.length() < 256){
                    result += (String) resultExecute.getData() + "\n";}
                if (!(!statusFile || resultExecute.getResult())) {
                    break;
                }

            }
        }catch (EOFException e){
            IOManager.printerr("Пока");
        }
        return result + "....\n";
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
        public Boolean getStatusFile(){
            return statusFile;
        }
    }




