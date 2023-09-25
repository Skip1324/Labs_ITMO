import Exeception.IncorrectDataException;
import Manager.IOManager;
import java.io.*;

public class h {
    public static void main(String[] args) {
        try {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        OutputStreamWriter out = new OutputStreamWriter(System.out);
        IOManager io = new IOManager(bufferedReader, out);
        InputManager inp = new InputManager(io);
        inp.execute();
        }
        catch (IncorrectDataException e) {
            IOManager.printerr("Неверные данные в Main!");
        } catch (IOException e) {
            IOManager.printerr("У пользователя недостаточно прав!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}