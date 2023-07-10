package Managers;
import Exceptions.IncorrectDataException;
import data.City;
import data.StandardOfLiving;
import java.time.LocalDate;
import java.util.*;

/**
 * Class operates the collection itself.
 */
public class CollectionManager {
    private final TreeMap<Integer, City> cityTreeMap;
    private final LocalDate date;
    private final TreeSet<Long> usedID;
    private String s;
    private int colSize;


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
     * @param element A City to add.
     * @param key A number of City to add.
     */
    public void insertElement(Integer key, City element) {
        try {
            if (Objects.equals(element.getID(), null)) {
                if (usedID.isEmpty()) {
                    element.setId(1L);
                    usedID.add(1L);
                } else {
                    element.setId(usedID.last() + 1L);
                    usedID.add(usedID.last() + 1L);
                }
            } else if (usedID.contains(element.getID())) {
                element.setId(usedID.last() + 1L);
                usedID.add(usedID.last() + 1L);
            } else {
                usedID.add(element.getID());
            }
        } catch (IncorrectDataException e) {
            e.printStackTrace();
        }
        cityTreeMap.put(key, element);
    }

    /**
     * Removes a City to collection on user input key.
     * @param val A key to remove.
     */
    public void removeKeyElement(TreeMap<Integer, City> cityTreeMap, int val) {
        if (cityTreeMap.size() == 0) {
            IOManager.writeln("В коллекции нет элементов.");
        } else {
            int v = cityTreeMap.size();
            cityTreeMap.remove(val);
            colSize = cityTreeMap.size();
            if (v - colSize == 1) {
                s = "Элемент удален.";
            } else {
                s = "Элемента с этим ключом нет.";
            }
            IOManager.writeln(s);
            s = null;
        }
    }
    /**
     * Update id of element in collection.
     * @param val A id to update.
     */
    public void updId(TreeMap<Integer, City> cityTreeMap, Long val) throws IncorrectDataException {
        List<Integer> valus = new ArrayList<>();
        List<City> cit = new ArrayList<>();
        if (cityTreeMap.size() == 0) {
            IOManager.writeln("В коллекции нет элементов.");
        } else {
            for (Map.Entry<Integer, City> ct : cityTreeMap.entrySet()) {
                Integer key = ct.getKey();
                City city = ct.getValue();
                valus.add(key);
                cit.add(city);
            }
            CommunicationManager com = new CommunicationManager(new Scanner(System.in));
            City element = new City(com.setName(), com.setCoordinates(), com.setDate(), com.setArea(), com.setPopulation(), com.setMetersAboutSeaLevel(), com.setAgglo(), com.setClimate(), com.setStandardOfLiving(), com.setGovernor());
            for (City list : cit) {
                for (Integer list1 : valus) {
                    if (list.getID().equals(val)) {
                        element.setId(val);
                        cityTreeMap.replace(list1, list, element);
                        s = "Элемент обновлен.";
                    }
                }
            }
        }
        if (s == null) {
            s = "Элемента с таким id нет.";
        }
        IOManager.writeln(s);
        s = null;
    }

    /**
     * Clear all element in collection.
     */

    public void clearCollection() {
        usedID.clear();
        cityTreeMap.clear();

    }

    /**
     * Show all element in collection.
     */

    public void show() {
        for (Map.Entry<Integer, City> ct : cityTreeMap.entrySet()) {
            City city = ct.getValue();
            IOManager.writeln(city);
        }
    }

    /**
     * @return Initialization time.
     */
    public LocalDate getTime() {
        return date;
    }

    /**
     * @return city Collection.
     */
    public TreeMap<Integer, City> getCollection() {
        return cityTreeMap;
    }

    /**
     * @return Size of Collection.
     */
    public int getSize() {
        return cityTreeMap.size();
    }


    /**
     * Deletes all elements that greater
     * @throws IncorrectDataException if data of input city not correct.
     */
    public void removeGreaterElement(TreeMap<Integer, City> cityTreeMap) throws IncorrectDataException {
        List<Integer> valus = new ArrayList<>();
        List<City> cit = new ArrayList<>();
        if (cityTreeMap.size() == 0) {
            IOManager.writeln("В коллекции нет элементов.");
        } else {
            for (Map.Entry<Integer, City> ct : cityTreeMap.entrySet()) {
                Integer key = ct.getKey();
                City city = ct.getValue();
                valus.add(key);
                cit.add(city);
            }
            CommunicationManager com = new CommunicationManager(new Scanner(System.in));
            City element = new City(com.setName(), com.setCoordinates(), com.setDate(), com.setArea(), com.setPopulation(), com.setMetersAboutSeaLevel(), com.setAgglo(), com.setClimate(), com.setStandardOfLiving(), com.setGovernor());
            int v = cityTreeMap.size();
            for (Integer list1 : valus) {
                for (City list : cit) {
                    if (list.compareTo(element) > 0) {
                        usedID.remove(list.getID());
                        if (!(usedID.contains(list1.longValue()))) {
                            cityTreeMap.remove(list1);
                            colSize = cityTreeMap.size();
                            if (v - colSize == 1) {
                                s = "Элемент удален.";
                            }
                            if (v - colSize != 1 && v != colSize) {
                                s = "Элементы удалены.";
                            }
                        }
                    }
                }
            }
            if (s == null) {
                s = "Элементов большего этого в коллекции нет.";
            }
        }
        if (s != null) {
            IOManager.writeln(s);
            s = null;
        }
    }

    /**
     * Deletes all elements that lower
     * @throws IncorrectDataException if data of input city not correct.
     */
    public void removeLowerElement(TreeMap<Integer, City> cityTreeMap) throws IncorrectDataException {
        List<Integer> valus = new ArrayList<>();
        List<City> cit = new ArrayList<>();
        if (cityTreeMap.size() == 0) {
            IOManager.writeln("В коллекции нет элементов.");
        } else {
            for (Map.Entry<Integer, City> ct : cityTreeMap.entrySet()) {
                Integer key = ct.getKey();
                City city = ct.getValue();
                valus.add(key);
                cit.add(city);
            }
            CommunicationManager com = new CommunicationManager(new Scanner(System.in));
            City element = new City(com.setName(), com.setCoordinates(), com.setDate(), com.setArea(), com.setPopulation(), com.setMetersAboutSeaLevel(), com.setAgglo(), com.setClimate(), com.setStandardOfLiving(), com.setGovernor());
            int v = cityTreeMap.size();
            for (Integer list1 : valus) {
                for (City list : cit) {
                    if (list.compareTo(element) < 0) {
                        usedID.remove(list.getID());
                        if (!(usedID.contains(list1.longValue()))) {
                            cityTreeMap.remove(list1);
                            colSize = cityTreeMap.size();
                            if (v - colSize == 1) {
                                s = "Элемент удален.";
                            }
                            if (v - colSize != 1 && v != colSize) {
                                s = "Элементы удалены.";
                            }
                        }
                    }
                }
            }
            if (s == null) {
                s = "Элементов меньше этого в коллекции нет.";
            }
        }
        if (s != null) {
            IOManager.writeln(s);
            s = null;
        }

    }

    /**
     * Deletes all elements that keys lower which user input.
     * @param val Key val on which to delete lower elements.
     */

    public void removeLowerKey(TreeMap<Integer, City> cityTreeMap, int val) {
        List<Integer> valus = new ArrayList<>();
        List<City> cit = new ArrayList<>();
        if (cityTreeMap.size() == 0) {
            IOManager.writeln("В коллекции нет элементов.");
        } else {
            for (Map.Entry<Integer, City> ct : cityTreeMap.entrySet()) {
                Integer key = ct.getKey();
                City city = ct.getValue();
                valus.add(key);
                cit.add(city);
            }
            int v = cityTreeMap.size();
            for (Integer list : valus) {
                if (list < val) {
                    cityTreeMap.remove(list);
                    for (City list1 : cit) {
                        usedID.remove(list1.getID());
                        colSize = cityTreeMap.size();
                    }
                    if (v - colSize == 1) {
                        s = "Меньший элемент удален.";
                    }
                    if (v != colSize && v - colSize != 1) {
                        s = "Меньшие элементы удалены.";
                    }
                } else {
                    colSize = cityTreeMap.size();
                    if (v == colSize) {
                        s = "Элементов меньше этого нет.";
                    }
                }
            }
            if (s != null) {
                IOManager.writeln(s);
                s = null;
            }
        }
    }

    /**
     * Print all elements that MetersAboveSeaLevel equals user input.
     * @param db filter on which print to equal user input sea level.
     */
    public void filterByMetersAboveSeaLevel(TreeMap<Integer, City> cityTreeMap, Double db) {
        List<City> cit = new ArrayList<>();
        if (cityTreeMap.size() == 0) {
            IOManager.writeln("В коллекции нет элементов.");
        } else {
            for (Map.Entry<Integer, City> ct : cityTreeMap.entrySet()) {
                City city = ct.getValue();
                cit.add(city);
            }
            for (City ct : cit) {
                if (ct.getMetersAboveSeaLevel() == db) {
                    s = ct.toString();
                }
            }
            if (s == null) {
                s = "В коллекции нет элементов с таким уровнем моря.";
            }
            IOManager.writeln(s);
            s = null;
        }
    }

    /**
     * Print all elements that name contains str.
     * @param str substring which match with name of element of all collection.
     */
    public void containsName(TreeMap<Integer, City> cityTreeMap, String str) {
        if (cityTreeMap.size() == 0) {
            IOManager.writeln("В коллекции нет элементов.");
        } else {
            for (Map.Entry<Integer, City> city1 : cityTreeMap.entrySet()) {
                City city = city1.getValue();
                if (city.getName().contains(str)) {
                    s = city.toString();
                } else {
                    s = "В коллекции нет элементов с подходящей подстрокой.";
                }
            }
            if (s != null) {
                IOManager.writeln(s);
                s = null;
            }
        }
    }

    /**
     * Print field StandardOfLiving of element of all collection.
     */
    public void printStandardOfLiving(TreeMap<Integer, City> cityTreeMap) {
        List<StandardOfLiving> allStandardOfLiving = new ArrayList<>();
        for (Map.Entry<Integer, City> city1 : cityTreeMap.entrySet()) {
            City city = city1.getValue();
            allStandardOfLiving.add(city.getStandardOfLiving());
        }
        Collections.sort(allStandardOfLiving);
        Collections.reverse(allStandardOfLiving);
        if (allStandardOfLiving.size() == 0) {
            IOManager.writeln("В коллекции нет элементов.");
        } else {
            IOManager.writeln(allStandardOfLiving);
        }
    }
}