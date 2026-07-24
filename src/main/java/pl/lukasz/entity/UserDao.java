package pl.lukasz.entity;

import pl.lukasz.utils.DbUtil;
import pl.lukasz.utils.PasswordUtil;

import java.sql.*;
import java.util.Arrays;

public class UserDao {


    private static final String CREATE_USER_QUERY =
            "INSERT INTO users(username, email, password) VALUES (?, ?, ?);";
    private static final String READ_USER_QUERY =
            "SELECT * FROM users WHERE id = ?;";
    private static final String UPDATE_USER_QUERY =
            "UPDATE users set userName = ?, email = ? WHERE id = ?;";
    private static final String DELETE_USER_QUERY =
            "DELETE FROM users where id = ?;";
    private static final String FIND_ALL_USER_QUERY =
            "SELECT * FROM users;";
    private static final String CHANGE_PASSWORD_QUERY =
            "UPDATE users SET password = ? WHERE id = ?";



    //    create - zapisuje obiekt do tabeli jako nowy wiersz
    public User create(User user) {

        try (Connection conn = DbUtil.getConnection()) {

            int id = insert(conn, CREATE_USER_QUERY, user.getUserName(),
                        user.getEmail(), PasswordUtil.hashPassword(user.getPassword()));
            user.setId(id);
            return user;

            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
    }


////    read - wczytuje jeden wiersz z tabeli i zwraca obiekt, ktory ten wiersz reprezentuje
    public User read(int userId) {

        try (Connection conn = DbUtil.getConnection()) {

            String[][] temp = getData(READ_USER_QUERY, userId);

            if (temp.length == 0) {
                System.out.println("User with id " + userId + " not found.");
                return null;
            }

            User user = new User();
            user.setId(Integer.parseInt(temp[0][0]));
            user.setEmail(temp[0][1]);
            user.setUserName(temp[0][2]);
            user.setPassword(temp[0][3]);

            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

////    update - zapisuje obiekt do tabeli dokonujac modyfikacji istniejacego wczesniej wiersza tabeli
    public void update(User user) {

        try (Connection conn = DbUtil.getConnection()) {
            update(conn, UPDATE_USER_QUERY, user);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


////    delete - usuwa obiekt z tabeli czyli usuwa wiersz o id takim samym jak zapisany w obiekcie
    public void delete(int userId) {

        try (Connection conn = DbUtil.getConnection()) {
            remove(conn, DELETE_USER_QUERY, userId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

////    findAll - lista obiektow stworzonych z tabeli (wszystkich)
      public User[] findAll() {

          try (Connection conn = DbUtil.getConnection();
               PreparedStatement statement = conn.prepareStatement(FIND_ALL_USER_QUERY)){

              ResultSet resultSet = statement.executeQuery();

              User[] users = new User[0];

              while (resultSet.next()) {

                  User user = new User();
                  user.setId(resultSet.getInt("id"));
                  user.setUserName(resultSet.getString("username"));
                  user.setEmail(resultSet.getString("email"));
                  user.setPassword(resultSet.getString("password"));

                  users = addToArray(user, users);
              }
              return users;
          } catch (SQLException e) {
              e.printStackTrace();
          }

          return new User[0];
     }

              //          Będziemy również potrzebować mechanizmu, który pozwoli nam automatycznie powiększać tablicę.
    private User[] addToArray(User u, User[] users) {
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1); // Tworzymy kopię tablicy powiększoną o 1.
        tmpUsers[users.length] = u; // Dodajemy obiekt na ostatniej pozycji.
        return tmpUsers; // Zwracamy nową tablicę.
    }


    public void updatePassword(int userId, String newPassword) {

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement statement =
                     conn.prepareStatement(CHANGE_PASSWORD_QUERY)) {

            statement.setString(1, PasswordUtil.hashPassword(newPassword));
            statement.setInt(2, userId);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated == 0) {
                System.out.println("User with id " + userId + " not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    ////////////////////////

    public static int insert(Connection conn, String query, String... params) {
        try ( PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, params[0]);
            statement.setString(2, params[1]);
            statement.setString(3, params[2]);
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void remove(Connection conn, String query, int id) {
        try (PreparedStatement statement =
                     conn.prepareStatement(query);) {
            statement.setInt(1, id);

            int row = statement.executeUpdate();

            if (row == 0) {
                System.out.println("Couldnt find User id: " + id + ", to remove");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //    UPDATE
    public static void update(Connection conn, String query, String... params) {
        try ( PreparedStatement statement = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                statement.setString(i + 1, params[i]);
            }

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated == 0) {
                System.out.println("Could not find user to update with this query: " + query);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //    UPDATE - but takes user as parameter
    public static void update(Connection conn, String query, User user) {

        if (user == null) {
            return;
        }

        try (PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
//            statement.setString(3, PasswordUtil.hashPassword(user.getPassword()));
            statement.setInt(3, user.getId());

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated == 0) {
                System.out.println("Could not find user with id: " + user.getId());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //    //            4. Metoda count(String sqlQuery) - która zwraca ilość wierszy dla zadanego zapytania
    public static int count(String sqlQuery) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(sqlQuery);
             ResultSet resultSet = statement.executeQuery()) {

            int count = 0;

            while (resultSet.next()) {
                count++;
            }

            return count;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    //  5. Metoda zwracająca czy wiersz o zadanym id istnieje w tabeli:
    //      public static boolean exists(Connection conn, String tableName, int id)
    public static boolean exists(Connection conn, int id) {

        String sqlQuery = "SELECT * FROM users WHERE id = ?;";
        try (PreparedStatement statement = conn.prepareStatement(sqlQuery)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //    //            6. Metoda getData - która zwróci tablicę tablic w wynikami dla zadanego zapytania
    public static String[][] getData(String sqlQuery) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(sqlQuery)){

            ResultSet resultSet = statement.executeQuery();
            ResultSetMetaData rsmd = resultSet.getMetaData();

            int columnsNumber = rsmd.getColumnCount();
            int rowsNumber = count(sqlQuery);

            String[][] data = new String[rowsNumber][columnsNumber];

            int row = 0;

            while (resultSet.next()) {
                for (int col = 0; col < columnsNumber; col++) {
                    data[row][col] = resultSet.getString(col + 1);
                }
                row++;
            }
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new String[0][0];

    }


    //    //            6. Metoda getData - która zwróci tablicę tablic w wynikami dla zadanego zapytania
    public static String[][] getData(String sqlQuery, int id) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(sqlQuery)){

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            ResultSetMetaData rsmd = resultSet.getMetaData();

            int columnsNumber = rsmd.getColumnCount();

            if (!resultSet.next()) {
                return new String[0][0];
            }

            String[][] data = new String[1][columnsNumber];

            for (int col = 0; col < columnsNumber; col++) {
                data[0][col] = resultSet.getString(col + 1);
            }

            return data;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new String[0][0];

    }

}

