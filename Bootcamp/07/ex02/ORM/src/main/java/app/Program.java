package app;

import manager.OrmManager;
import model.User;
import java.sql.SQLException;

public class Program {
    public static void main(String[] args) throws SQLException {

        OrmManager manager = new OrmManager();
        try {
            manager.tableInit();
            User user1 = new User(1L, "Vasya", "Pupkin", 22);
            User user2 = new User(2L, "Sveta", "Kukushkina", 19);
            manager.save(user1);
            manager.save(user2);
            user1.setAge(32);
            manager.update(user1);
            System.out.println(manager.findById(1L, User.class));
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        } finally {
            manager.close();
            System.exit(0);
        }

    }
}
