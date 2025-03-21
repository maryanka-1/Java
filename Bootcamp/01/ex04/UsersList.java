public interface UsersList {
    void addUser(User user);

    User getUserId(int id) throws UserNotFoundException;

    User getUserIndex(int index) throws UserNotFoundException;

    int getUserCount();

}
