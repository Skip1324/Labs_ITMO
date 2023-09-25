package DB;
import Exeception.IncorrectDataException;
import Manager.CollectionManager;
import data.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DBConnector {
    private CollectionManager col;
    public DBConnector(){
    }
    public DBConnector(CollectionManager col){
    this.col = col;
    }
    synchronized private static Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection("jdbc:postgresql://pg/studs", "s368254", "b7zKczx1xaR3Y668");
//            dbConnection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/db", "postgres", "8963");
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }
    synchronized public Long setIdDb() throws SQLException {
        long c = 0;
        Connection dbConnection = null;
        Statement statement = null;
        String id = "SELECT * FROM cities;";
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            ResultSet res = statement.executeQuery(id);
            while(res.next()){
                c = Long.parseLong(res.getString(1));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
        return c;
    }
    synchronized public  void deleteElementDBbyKey(int key,String sign, User user) throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        String delete = "Delete FROM Cities Where Cities.key "+sign+" "+key+" and fk_users_id = "+user.getId()+";";
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();

            statement.execute(delete);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }
    synchronized public void insertDB(City city, int key,User user) throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        String insert = "INSERT INTO Cities (key,name, x, y, creationDate, area, population, metersAboveSeaLevel, agglomeration, climate, standardOfLiving, governor_name,fk_users_id)\n" +
                "VALUES ("+key+",'"+city.getName()+"',"+city.getCoordinates().getX()+","+city.getCoordinates().getY()+",'"+city.getCreationDate()+"', "+city.getArea()+","+city.getPopulation()+", "+city.getMetersAboveSeaLevel()+", "+city.getAgglomeration()+", '"+city.getClimate()+"', '"+city.getStandardOfLiving()+"', '"+city.getGovernor().getName()+"',"+user.getId()+");";
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();

            statement.execute(insert);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    synchronized public void deleteElementDBbyComparator(String name, long area, int population, String sign, User user) throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        String delete = "DELETE FROM cities WHERE ((area " + sign + " " + area + ") OR (population " + sign + " " + population + " and area = " + area + ") OR (LENGTH(name) " + sign + " LENGTH('" + name + "') and area = " + area + " and population = " + population + ")) and (fk_users_id = " + user.getId() + ");";
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            statement.execute(delete);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    synchronized public void clearDB(User user) throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        String clear = "DELETE FROM Cities where fk_users_id = "+user.getId()+";";
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            statement.execute(clear);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }
    synchronized public void adduserDB(String login, String password) throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        String add = "INSERT INTO Users(login,password) VALUES ('"+login+"','"+password+"');";
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            statement.execute(add);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }
    synchronized public CollectionManager parse() throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        String s = "";
        String[] pars;
        String select = "SELECT * from cities;";
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            ResultSet res  = statement.executeQuery(select);
            while(res.next()){
                for(int i = 1; i <= res.getMetaData().getColumnCount();i++ ) {
                    s += res.getString(i) + "|";
                }
                pars = (s.trim() + " ").split("\\|",15);
                Long id = Long.parseLong(pars[0]);
                String name = pars[2];
                Coordinates coordinates = new Coordinates(Integer.parseInt(pars[3]), Float.parseFloat(pars[4]));
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
                Date date = dateFormat.parse(pars[5]);
                Long area = Long.valueOf(pars[6]);
                Integer population = Integer.valueOf(pars[7]);
                long seaLevel = Long.parseLong(pars[8]);
                Double agglomeration = Double.valueOf(pars[9]);
                Climate climate = Climate.valueOf(pars[10]);
                StandardOfLiving standardOfLiving = StandardOfLiving.valueOf(pars[11]);
                Human human = new Human();
                human.setName(pars[12]);
                int userId = Integer.parseInt(pars[13]);
                col.insertElement(Integer.valueOf(pars[1]), new City(id,name,coordinates,date,area,population,seaLevel,agglomeration,climate,standardOfLiving,human,userId));
                s = "";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (IncorrectDataException | ParseException e) {
            throw new RuntimeException(e);

        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
        return col;
    }
    synchronized public int checkUserDB(String login, String password) throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        int c = 0;
        String check = "SELECT users_id FROM users WHERE users.login = '"+login+"' and users.password ='"+ password+"'";
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            ResultSet res = statement.executeQuery(check);
            if(res.next()) {
                c = Integer.parseInt(res.getString(1));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
        return c;
    }
    synchronized public int checkKey(long id) throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        ResultSet val;
        int c = 0;
        String check = "SELECT key from cities where id = "+id+";";
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            val = statement.executeQuery(check);
            while (val.next()){
                c = val.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
        return c;
    }
    synchronized public int checkId(int key) throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        ResultSet val;
        int c = 0;
        String check = "SELECT fk_users_id from cities where key = "+key+";";
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            val = statement.executeQuery(check);
            while (val.next()){
                c = val.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
        return c;
    }
}
