import java.util.ArrayList;

public class UsersArrayList implements UsersList {
    private int index = 0;
    private int capacity = 10;
    User[] usersArrayList = new User[capacity];

    private void increaseArray() {
        User[] newArray = new User[usersArrayList.length * 2];
        for (int i = 0; i < usersArrayList.length; i++) {
            newArray[i] = usersArrayList[i];
        }
        usersArrayList = newArray;
    }

    @Override
    public void addUser(User user) {
        if (index == usersArrayList.length) {
            increaseArray();
        }
        usersArrayList[index++] = user;
    }

    @Override
    public User getUserId(int id) throws UserNotFoundException {

        for (User s : usersArrayList) {
            if (s != null) {
                if (id == s.getId()) {
                    return s;
                }
            }
        }
        throw new UserNotFoundException("User with id " + id + " not found");
    }

    @Override
    public User getUserIndex(int ind) throws UserNotFoundException {
        if (ind >= 0 && ind < index) return usersArrayList[ind];
        throw new UserNotFoundException("Index out of bounds!");
    }

    @Override
    public int getUserCount() {
        return index;
    }
}
