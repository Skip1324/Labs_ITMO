package Server;
import DB.DBConnector;
import Exeception.IncorrectDataException;
import Manager.*;
import Util.ReceiveServer;
import commands.*;
import org.apache.log4j.Logger;
import java.io.*;
import java.net.UnknownHostException;
import java.sql.SQLException;


public class Server {
    public static Logger logger = Logger.getLogger(Server.class);
    public static void main(String[] args) throws SQLException {
        try {
            CommandManager commands = new CommandManager();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            OutputStreamWriter out = new OutputStreamWriter(System.out);
            IOManager io = new IOManager(bufferedReader, out);
            CollectionManager col = new CollectionManager();
            DBConnector db = new DBConnector(col);
            db.parse();
            ReceiveServer rs = new ReceiveServer(commands, io);
            CommunicationManager com = new CommunicationManager(io);
            CommunicationManager communicationManager = new CommunicationManager(rs,io);
            commands.addCommand("filter_contains_name", new FilterContainsNameCommand(col, io));
            commands.addCommand("info", new InfoCommand(col, io));
            commands.addCommand("clear", new ClearCommand(col, io));
            commands.addCommand("remove_key", new RemoveKeyCommand(col, io));
            commands.addCommand("update", new UpdateIdCommand(col, io, com));
            commands.addCommand("insert", new InsertCommand(col, io, com, communicationManager));
            commands.addCommand("show", new ShowCommand(col, io));
            commands.addCommand("remove_greater", new RemoveGreaterCommand(col, io, com));
            commands.addCommand("remove_lower", new RemoveLowerCommand(col, io, com));
            commands.addCommand("remove_lower_key", new RemoveLowerKeyCommand(col, io));
            commands.addCommand("filter_by_meters_above_sea_level", new FilterByMetersAboveSeaLevelCommand(col, io));
            commands.addCommand("print_field_descending_standard_of_living", new PrintFieldDescendingStandartOfLivingCommand(col, io));
            commands.addCommand("exit", new ExitCommand(col,io,rs));
            commands.addCommand("execute_script", new ExecuteScriptCommand(rs, io));
            commands.addCommand("help", new HelpCommand(commands, io));
            logger.info("Соединение открыто");
            while (true) {
                rs.execute();
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IncorrectDataException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}