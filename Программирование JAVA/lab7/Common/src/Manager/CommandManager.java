package Manager;
import java.util.HashMap;
import java.util.Map;

/**
 * Operates the commands.
 */
public class CommandManager {
    private final Map<String, Command> commandsMap;
    public CommandManager() {
        commandsMap = new HashMap<>();
    }
    /**
     * Add command to command manager.
     * @param key Name of command.
     * @param com Object of command.
     */
    public void addCommand(String key, Command com) {
        commandsMap.put(key, com);
    }
    /**
     * @return Map of commands.
     */
    public Map<String, Command> getMap() {
        return commandsMap;
    }
}
