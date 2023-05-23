import Exceptions.IncorrectDataException;
import Exceptions.QuantityOfArgsException;
import Managers.*;
import com.google.gson.JsonSyntaxException;
import commands.*;
import java.io.*;


public class Main {
    public static void main(String[] args) {
        try {
            if (args.length > 1) {
                throw new QuantityOfArgsException();
            }
            String filepath = args[0];
            CommandManager commands = new CommandManager();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            OutputStreamWriter out = new OutputStreamWriter(System.out);
            IOManager io = new IOManager(bufferedReader, out);
            String data = io.readFile(new File(filepath));
            ParsJson parsJson = new ParsJson();
            CollectionManager col = parsJson.deSerialize(data);
            InputManager inp = new InputManager(commands, io);
            commands.addCommand("filter_contains_name", new FilterContainsNameCommand(col, io));
            commands.addCommand("info", new InfoCommand(col, io));
            commands.addCommand("clear", new ClearCommand(col, io));
            commands.addCommand("remove_key", new RemoveKeyCommand(col, io));
            commands.addCommand("update", new UpdateIdCommand(col, io));
            commands.addCommand("insert", new InsertCommand(col, io));
            commands.addCommand("show", new ShowCommand(col, io));
            commands.addCommand("remove_greater", new RemoveGreaterCommand(col, io));
            commands.addCommand("remove_lower", new RemoveLowerCommand(col, io));
            commands.addCommand("remove_lower_key", new RemoveLowerKeyCommand(col, io));
            commands.addCommand("filter_by_meters_above_sea_level", new FilterByMetersAboveSeaLevelCommand(col, io));
            commands.addCommand("print_field_descending_standard_of_living", new PrintFieldDescendingStandartOfLivingCommand(col, io));
            commands.addCommand("save", new SaveCommand(col, parsJson, new File(filepath), io));
            commands.addCommand("exit", new ExitCommand(inp, io));
            commands.addCommand("execute_script", new ExecuteScriptCommand(inp, io));
            commands.addCommand("help", new HelpCommand(commands, io));
            inp.execute();
        }
        catch (FileNotFoundException e){
            IOManager.printerr("Файл не найден или у вас недостаточно прав!");
        }
        catch (JsonSyntaxException|NullPointerException|NumberFormatException e) {
            IOManager.printerr("Данные в файле некорректны!");
        }
        catch (IncorrectDataException e) {
            IOManager.printerr("Неверные данные!");

        } catch (IOException e) {
            IOManager.printerr("У пользователя недостаточно прав!");
        }
        catch (ArrayIndexOutOfBoundsException e){
            IOManager.printerr("Вы не ввели имя файла!");
        } catch (QuantityOfArgsException e) {
            IOManager.printerr("Передавать в аргумент можно только один файл");
        }
    }
}