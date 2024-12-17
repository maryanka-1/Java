public class UserIdsGenerator {
    private int lastId = 1;
    private static UserIdsGenerator instance;

    private UserIdsGenerator() {}

    int generateId(){
        return lastId++;
    }
    public static UserIdsGenerator getInstance(){
        if(instance == null){
            instance = new UserIdsGenerator();
        }
        return instance;
    }

}
