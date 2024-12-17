public class Program {
    public static void main(String[] args) throws Exception {
        UsersList arrayList = new UsersArrayList();
        arrayList.addUser(new User("Anna", 1000));
        arrayList.addUser(new User("Anna", 1000));
        arrayList.addUser(new User("Sergey", 2000));
        arrayList.addUser(new User("David", 3000));
        arrayList.addUser(new User("John", 4000));
        arrayList.addUser(new User("Anastasia", 1000));
        arrayList.addUser(new User("Anfisa", 1000));
        arrayList.addUser(new User("Selly", 2000));
        arrayList.addUser(new User("Danny", 3000));
        arrayList.addUser(new User("George", 4000));
        arrayList.addUser(new User("Alisa", 1000));
        arrayList.addUser(new User("Alla", 1000));
        arrayList.addUser(new User("Semen", 2000));
        arrayList.addUser(new User("Dairy", 3000));
        arrayList.addUser(new User("Lisa", 4000));
        for (int i = 0; i < arrayList.getUserCount() + 1; i++) {
            try {
                System.out.println(arrayList.getUserIndex(i));
            } catch (UserNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println();
        try {
            System.out.println(arrayList.getUserId(1));
            System.out.println(arrayList.getUserId(2));
            System.out.println(arrayList.getUserId(3));
            System.out.println(arrayList.getUserId(4));
            System.out.println(arrayList.getUserId(5));
            System.out.println(arrayList.getUserId(6));
            System.out.println(arrayList.getUserId(28));
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }


    }
}
