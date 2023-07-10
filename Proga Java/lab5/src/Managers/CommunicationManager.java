package Managers;

import Exceptions.IncorrectDataException;
import data.Climate;
import data.Coordinates;
import data.Human;
import data.StandardOfLiving;

import java.time.Instant;
import java.util.Date;
import java.util.Scanner;

/**
 * Class which fill all fields of city element.
 */
public class CommunicationManager {
    public Scanner scanner;

    public CommunicationManager(Scanner sc) {
        this.scanner = sc;
    }

    /**
     * A method which check what symbols match with input.
     *
     * @param onlyDigits boolean flag for check.
     * @param str string which need to check.
     * If u need only digits expose param onlyDigits - true or if u need only letters expose param onlyDigits - false.
     */

    public static boolean containsOnlyDigitsOrLetters(String str, boolean onlyDigits) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        String regex = onlyDigits ? "^\\d+$" : "^[a-zA-Zа-яА-ЯёЁ]+$";
        return str.matches(regex);
    }

    /**
     * Set name for city on user input.
     */
    public String setName() {
        String name;
        while (true) {
            try {
                IOManager.writeln("Введите имя:");
                name = scanner.nextLine().trim();
                if (!containsOnlyDigitsOrLetters(name, false)) throw new IncorrectDataException();
                return name;
            } catch (IncorrectDataException e) {
                IOManager.printerr("Введенное имя некорректно, вводить можно только буквы!");
            }
        }
    }

    /**
     * Set X coordinate for city on user input.
     */
    public int setX() throws IncorrectDataException {
        int x;
        String line;
        while (true) {
            try {
                IOManager.writeln("Введите х:");
                line = scanner.nextLine().trim();
                if (!(line.matches("^\\d+$") || line.matches("^-\\d+$"))) throw new IncorrectDataException();
                x = Integer.parseInt(line);
                return x;
            } catch (IncorrectDataException e) {
                IOManager.printerr("Введенная координата х некорректна! Вводить можно только цифры!");
            } catch (NumberFormatException e) {
                IOManager.printerr("Значение может быть от 0 до 2147483647!");
            }
        }
    }

    /**
     * Set Y coordinate for city on user input.
     */

    public float setY() throws IncorrectDataException {
        float y;
        String line;
        while (true) {
            try {
                IOManager.writeln("Введите у:");
                line = scanner.nextLine().trim();
                if (!(line.matches("^\\d.+$") || line.matches("^-\\d.+$") || line.matches("^\\d+$") || line.matches("^-\\d+$")))
                    throw new IncorrectDataException();
                y = Float.parseFloat(line);
                return y;
            } catch (IncorrectDataException e) {
                IOManager.printerr("Введенная координата у некорректна!Вводить можно только цифры!");
            } catch (NumberFormatException e) {
                IOManager.printerr("Значение может быть от 0 до 3.4*10^38!");
            }
        }
    }

    /**
     * Set Date initialization for city on user input.
     */
    public Date setDate() {
        Date date = Date.from(Instant.now());
        IOManager.writeln(date);
        return date;
    }

    /**
     * Set Area for city on user input.
     */
    public long setArea() {
        long area;
        String line;
        while (true) {
            try {
                IOManager.writeln("Введите область:");
                line = scanner.nextLine().trim();
                if (!containsOnlyDigitsOrLetters(line, true)) throw new IncorrectDataException();
                area = Long.parseLong(line);
                if (area != 0) {
                    return area;
                } else {
                    IOManager.printerr("Значение области не может быть 0!");
                }
            } catch (IncorrectDataException e) {
                IOManager.printerr("Введенная область некорректна!Вводить можно только цифры!");
            } catch (NumberFormatException e) {
                IOManager.printerr("Значение может быть от 1 до  9223372036854775807!");
            }
        }
    }

    /**
     * Set Population for city on user input.
     */
    public int setPopulation() {
        int popul;
        String line;
        while (true) {
            try {
                IOManager.writeln("Введите количество населения:");
                line = scanner.nextLine().trim();
                if (!containsOnlyDigitsOrLetters(line, true)) throw new IncorrectDataException();
                popul = Integer.parseInt(line);
                if (popul != 0) {
                    return popul;
                } else {
                    IOManager.printerr("Значение населения не может быть 0!");
                }
            } catch (IncorrectDataException e) {
                IOManager.printerr("Введенное количество населения некорректно!Вводить можно только цифры!");
            } catch (NumberFormatException e) {
                IOManager.printerr("Значение может быть от 1 до 2147483647!");
            }
        }
    }

    /**
     * Set Meters about sea level for city on user input.
     */
    public long setMetersAboutSeaLevel() {
        long sealevel;
        String line;
        while (true) {
            try {
                IOManager.writeln("Введите значение над уровнем моря:");
                line = scanner.nextLine().trim();
                if (!(line.matches("^\\d+$") || line.matches("^-\\d+$"))) throw new IncorrectDataException();
                sealevel = Long.parseLong(line);
                return sealevel;
            } catch (IncorrectDataException e) {
                IOManager.printerr("Введенное значение над уровнем моря некорректно!Вводить можно только цифры!");
            } catch (NumberFormatException e) {
                IOManager.printerr("Значение может быть от от –9 223 372 036 854 775 808 до 9 223 372 036 854 775 807!");
            }
        }
    }

    /**
     * Set agglomeration for city on user input.
     */
    public Double setAgglo() {
        double aglo;
        String line;
        while (true) {
            try {
                IOManager.writeln("Введите количество агломераций:");
                line = scanner.nextLine().trim();
                if (!(line.matches("^\\d.+$") || line.matches("^-\\d.+$") || line.matches("^\\d+$") || line.matches("^-\\d+$")))
                    throw new IncorrectDataException();
                aglo = Double.parseDouble(line);
                return aglo;
            } catch (IncorrectDataException e) {
                IOManager.printerr("Введенное количество агломераций некорректно!Вводить можно только цифры!");
            } catch (NumberFormatException e) {
                IOManager.printerr("Значение может быть от 0 до ±1.7976931348623157*10^308!");
            }
        }
    }

    /**
     * Set climate for city on user input.
     */
    public Climate setClimate() {
        Climate climate;
        String line;
        while (true) {
            try {
                IOManager.writeln("Выберете климат из этого списка:\n " + Climate.sortClimate());
                line = scanner.nextLine().trim();
                if (!containsOnlyDigitsOrLetters(line, false)) throw new IncorrectDataException();
                climate = Climate.valueOf(line);
                return climate;
            } catch (IncorrectDataException | IllegalArgumentException e) {
                IOManager.printerr("Введенный климат некорректен!");
            }
        }
    }

    /**
     * Set standard of living for city on user input.
     */
    public StandardOfLiving setStandardOfLiving() {
        StandardOfLiving sol;
        String line;
        while (true) {
            try {
                IOManager.writeln("Выберете уровень жизни из этого списка:\n " + StandardOfLiving.sortStandard());
                line = scanner.nextLine().trim();
                if (!containsOnlyDigitsOrLetters(line, false)) throw new IncorrectDataException();
                sol = StandardOfLiving.valueOf(line);
                return sol;
            } catch (IncorrectDataException | IllegalArgumentException e) {
                IOManager.printerr("Введенный уровень жизни некорректен!");
            }
        }
    }

    /**
     * Set coordinates for city.
     */
    public Coordinates setCoordinates() throws IncorrectDataException {
        try {
            int x;
            float y;
            x = setX();
            y = setY();

            return new Coordinates(x, y);
        } catch (IncorrectDataException e) {
            IOManager.printerr("Что-то не то!");
            throw new IncorrectDataException();
        }
    }

    /**
     * Set Name of governor for city on user input.
     */
    public String setNameforHuman() {
        String humanName;
        while (true) {
            try {
                IOManager.writeln("Введите имя губернатора:");
                humanName = scanner.nextLine().trim();
                if (!containsOnlyDigitsOrLetters(humanName, false)) throw new IncorrectDataException();
                return humanName;
            } catch (IncorrectDataException e) {
                IOManager.printerr("Введенное имя губернатора некорректно!Вводить можно только буквы!");
            }
        }
    }

    /**
     * Set governor for.
     */
    public Human setGovernor() throws IncorrectDataException {
        try {
            String name = setNameforHuman();
            return new Human(name);
        } catch (Exception e) {
            throw new IncorrectDataException();
        }
    }
}


