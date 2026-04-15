package Models;

public interface UserDao {
    void addUser(User user);
    User getUserById(int id);
    void updateUser(User user);
    void deleteUser(int user);
}