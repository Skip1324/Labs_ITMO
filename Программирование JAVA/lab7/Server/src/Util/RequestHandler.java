package Util;

import DB.User;
import Manager.*;
import Server.Server;
import data.City;
import sun.misc.Signal;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class RequestHandler {
    private final Queue<Request> requestQueue;
    private ExecutorService processingThreadPool;
    private ExecutorService responseThreadPool;
    private CommandManager commands;
    private Boolean statusFile;

    public RequestHandler(CommandManager commands, Boolean statusFile) {
        requestQueue = new ConcurrentLinkedQueue<>();
        processingThreadPool = Executors.newCachedThreadPool();
        responseThreadPool = ForkJoinPool.commonPool();
        this.commands = commands;
        this.statusFile = statusFile;
    }

    public void start() {
        Thread requestThread = new Thread(this::readRequests);
        requestThread.start();
    }

    private void readRequests() {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(1050);
            while (true) {
                byte[] buffer = new byte[10000];
                DatagramPacket packetRecive = new DatagramPacket(buffer, buffer.length);
                socket.receive(packetRecive);
                synchronized (requestQueue) {
                    requestQueue.offer(new Request(packetRecive));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }

    public void processRequests() {
        while (true) {
            Request request;
            synchronized (requestQueue) {
                request = requestQueue.poll();
            }
            if (request != null) {
                processingThreadPool.submit(() -> processRequest(request));
            }
        }
    }

    private void processRequest(Request request) {
        try {
            byte[] data = request.getPacket().getData();
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            ObjectInputStream ois = new ObjectInputStream(bis);
            Object obj = ois.readObject();
            SenderClient s = (SenderClient) obj;
            String name = s.getName();
            String arg = s.getArg();
            City city = s.getCity();
            User user = s.getUser();
            String end = s.getEnd();
            if (name.equals("")) {
                return;
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
            if (!commands.getMap().containsKey(name) || user == null) {
                if (!statusFile) {
                    String message = ("Неизвестная команда, напишите help, чтобы вывести список доступных команд!");
                    CommandResult commandResult = new CommandResult("Ответ от сервера", message, false);
                    request.setSerializator(new Serializator<>(commandResult));
                }
            }else {
            CommandResult commandResult = commands.getMap().get(name).execute(arg, city, end, user);
            request.setSerializator(new Serializator<>(commandResult));
            }
            responseThreadPool.submit(() -> sendResponse(request));
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendResponse(Request request) {
        try {
            byte[] responseBuffer = request.getSerializator().serialize();
            InetAddress address = request.getPacket().getAddress();
            int port = request.getPacket().getPort();
            DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length, address, port);
            DatagramSocket socket = new DatagramSocket();
            socket.send(responsePacket);
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

class Request {
    private DatagramPacket datagramPacket;
    private Serializator<CommandResult> serializator;

    public Request(DatagramPacket packet) {
        this.datagramPacket = packet;

    }

    public DatagramPacket getPacket(){
        return datagramPacket;
    }
    public void setSerializator(Serializator<CommandResult> serializator){
        this.serializator = serializator;
    }
    public Serializator<CommandResult> getSerializator(){
        return serializator;
    }

}