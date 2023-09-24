package Manager;

import DB.User;
import data.City;
import data.StandardOfLiving;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class operates the collection itself.
 */
public class CollectionManager {
    private final TreeMap<Integer, City> cityTreeMap;
    private final LocalDate date;
    private final TreeSet<Long> usedID;
    private int removedCount = 0;


    public CollectionManager() {
        cityTreeMap = new TreeMap<>();
        date = LocalDate.now();
        usedID = new TreeSet<>();
    }

     public CollectionManager(TreeMap<Integer, City> city) {
        this.cityTreeMap = city;
        date = LocalDate.now();
        usedID = new TreeSet<>();
    }

    /**
     * Adds a new city to collection and set his id.
     * And add id in usedId on automatic generate id.
     *
     * @param element A City to add.
     * @param key     A number of City to add.
     */
    synchronized public void insertElement(Integer key, City element) {
        cityTreeMap.put(key, element);
    }

    /**
     * Removes a City to collection on user input key.
     *
     * @param val A key to remove.
     */
    synchronized public String removeKeyElement(TreeMap<Integer, City> cityTreeMap, int val, User user) {
        if (cityTreeMap.get(val).getUserId() == user.getId()) {
            cityTreeMap.remove(val);
            return "Элемент удален!";
        } else {
            return "Этот элемент вам не принадлежит! ";
        }
    }

    /**
     * Update id of element in collection.
     *
     * @param val A id to update.
     */
    synchronized public String updId(TreeMap<Integer, City> cityTreeMap, Long val, City element) throws IOException {
        Optional<Map.Entry<Integer, City>> optionalEntry = cityTreeMap.entrySet().stream()
                .filter(entry -> entry.getValue().getID().equals(val))
                .findFirst();
        if (optionalEntry.isPresent()) {
            Map.Entry<Integer, City> entry = optionalEntry.get();
            Integer key = entry.getKey();
            cityTreeMap.put(key, element);
            return "Элемент обновлен.";
        } else {
            return "Элемента с таким id нет, поэтому его нельзя обновить.";
        }
    }

    /**
     * Clear all element in collection.
     */

    synchronized public void clearCollection(User user) {
        Iterator<Map.Entry<Integer, City>> iterator = cityTreeMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, City> entry = iterator.next();
            if (entry.getValue().getUserId() == user.getId()) {
                cityTreeMap.remove(entry.getKey());
            }
        }

    }

    /**
     * Show all element in collection.
     */

    synchronized public String show() {
        String message = cityTreeMap.values().stream()
                .map(City::toString)
                .collect(Collectors.joining());
        if (message.length() > 3000) {
            message = message.substring(0, 3000) + "\n....\n";
        }
        return message;
    }

    /**
     * @return Initialization time.
     */
    synchronized public LocalDate getTime() {
        return date;
    }

    /**
     * @return city Collection.
     */
    synchronized public TreeMap<Integer, City> getCollection() {
        return cityTreeMap;
    }

    /**
     * @return Size of Collection.
     */
    synchronized public int getSize() {
        return cityTreeMap.size();
    }


    /**
     * Deletes all elements that greater
     */
    synchronized public String removeGreaterElement(TreeMap<Integer, City> cityTreeMap, City element, User user) {
        int removedCount = 0;
        try {
            List<Integer> keysToRemove = cityTreeMap.entrySet()
                    .stream()
                    .filter(entry -> entry.getValue().compareTo(element) > 0)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            for (Integer key : keysToRemove) {
                City city = cityTreeMap.get(key);
                if (city != null && city.getUserId() == user.getId()) {
                    cityTreeMap.remove(key);
                    removedCount++;
                }
            }

            if (removedCount == 0) {
                return "Элементов больше этого в коллекции нет.";
            } else if (removedCount == 1) {
                return "Элемент удален.";
            } else {
                return "Элемент удалены.";
            }
        } catch (NullPointerException e) {
            return "Invalid input, this command does not have an argument!";
        }
    }

    /**
     * Deletes all elements that lower
     */
    synchronized public String removeLowerElement(TreeMap<Integer, City> cityTreeMap, City element, User user) {
        int removedCount = 0;
        try {
            List<Integer> keysToRemove = cityTreeMap.entrySet()
                    .stream()
                    .filter(entry -> entry.getValue().compareTo(element) < 0)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            for (Integer key : keysToRemove) {
                City city = cityTreeMap.get(key);
                if (city != null && city.getUserId() == user.getId()) {
                    cityTreeMap.remove(key);
                    removedCount++;
                }
            }

            if (removedCount == 0) {
                return "Элементов меньше этого в коллекции нет.";
            } else if (removedCount == 1) {
                return "Элемент удален.";
            } else {
                return "Элемент удалены.";
            }
        } catch (NullPointerException e) {
            return "Invalid input, this command does not have an argument!";
        }
    }

    /**
     * Deletes all elements that keys lower which user input.
     *
     * @param val Key val on which to delete lower elements.
     */

    synchronized public String removeLowerKey(TreeMap<Integer, City> cityTreeMap, int val) {
        List<Integer> keysToRemove = cityTreeMap.entrySet()
                .stream()
                .filter(entry -> entry.getKey() < val)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        removedCount = 0;
        for (Integer key : keysToRemove) {
            usedID.remove(cityTreeMap.get(key).getID());
            cityTreeMap.remove(key);
            removedCount++;
        }
        if (removedCount == 0) {
            return "Элементов с ключом меньше этого в коллекции нет.";
        } else if (removedCount == 1) {
            return "Элемент удален.";
        } else {
            return "Элементы удалены.";
        }
    }

    /**
     * Print all elements that MetersAboveSeaLevel equals user input.
     *
     * @param db filter on which print to equal user input sea level.
     */
    synchronized public String filterByMetersAboveSeaLevel(TreeMap<Integer, City> cityTreeMap, Double db) {
        List<City> cities = cityTreeMap.entrySet().stream()
                .map(entry -> entry.getValue())
                .filter(city -> Double.compare(city.getMetersAboveSeaLevel(), db) == 0)
                .collect(Collectors.toList());
        if (!cities.isEmpty()) {
            String result = cities.stream().map(City::toString).collect(Collectors.joining(", "));
            return result;
        } else {
            return "В коллекции нет элементов с таким уровнем моря.";
        }
    }

    /**
     * Print all elements that name contains str.
     *
     * @param str substring which match with name of element of all collection.
     */
    synchronized public String containsName(TreeMap<Integer, City> cityTreeMap, String str) {
        List<City> cities = cityTreeMap.entrySet().stream()
                .map(entry -> entry.getValue())
                .filter(city -> (city.getName().contains(str)))
                .collect(Collectors.toList());
        if (!cities.isEmpty()) {
            String result = cities.stream().map(City::toString).collect(Collectors.joining(", "));
            return result;
        } else {
            return "В коллекции нет элементов с такой подстрокой.";
        }
    }

    /**
     * Print field StandardOfLiving of element of all collection.
     */
    synchronized public String printStandardOfLiving(TreeMap<Integer, City> cityTreeMap) {
        List<StandardOfLiving> allStandardOfLiving = cityTreeMap.entrySet().stream()
                .map(entry -> entry.getValue().getStandardOfLiving())
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        if (allStandardOfLiving.isEmpty()) {
            return "В коллекции нет элементов.";
        } else {
            return allStandardOfLiving.toString();
        }
    }
}