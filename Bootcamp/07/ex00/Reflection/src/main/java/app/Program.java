package app;

import classes.User;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Program {
    private static String SEPARATOR = "---------------------";
    private static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        start();
        try {
            Class<?> clazz = Class.forName("classes." + scanner.nextLine());
            List<String[]> fieldsOfClass = getInfoFields(clazz);
            List<String[]> methodsOfClass = getInfoMethods(clazz);
            Object obj = create(clazz, fieldsOfClass);
            obj = change(obj, clazz);
            callMethod(obj, clazz);
        } catch (ClassNotFoundException e) {
            System.out.println("You entered an invalid class");
        } catch (NumberFormatException e) {
            System.out.println("You entered an invalid number");
        } catch (NoSuchFieldException e) {
            System.out.println("This field does not exist");
        } catch (NoSuchMethodException e) {
            System.out.println("You entered an invalid method");
        }

    }

    public static void start() {
        System.out.print("Classes:\n" +
                "  - User\n" +
                "  - Car\n" +
                SEPARATOR + "\n" +
                "Enter class name:\n" +
                "-> ");
    }

    public static List<String[]> getInfoFields(Class<?> clazz) throws ClassNotFoundException {
        System.out.println(SEPARATOR);
        Field[] fields = clazz.getDeclaredFields();
        List<String[]> fieldsOfClass = new ArrayList<>();
        System.out.println("fields:");
        for (int i = 0; i < fields.length; i++) {
            fieldsOfClass.add(new String[]{fields[i].getType().getSimpleName(), fields[i].getName()});
            System.out.println("       " + fieldsOfClass.get(i)[0] + " " + fieldsOfClass.get(i)[1]);
        }
        return fieldsOfClass;
    }

    public static List<String[]> getInfoMethods(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        List<String[]> methodsOfClass = new ArrayList<>();
        System.out.println("methods:");
        int i = 0;
        for (Method method : methods) {
            if (method.getDeclaringClass().equals(clazz) && !isMethodFromObject(method)) {
                methodsOfClass.add(new String[]{method.getReturnType().getSimpleName(),
                        method.getName(),
                        Arrays.toString(method.getParameterTypes()).replace("[", "").replace("]", "")});
                System.out.println("       " + methodsOfClass.get(i)[0] + " " +
                        methodsOfClass.get(i)[1] + "(" +
                        methodsOfClass.get(i)[2] + ")");
                i++;
            }
        }
        System.out.println(SEPARATOR);
        return methodsOfClass;
    }

    public static Object create(Class<?> clazz, List<String[]> fieldsOfClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        System.out.println("Let’s create an object.");
        Object userInstance = clazz.getDeclaredConstructor().newInstance();
        try {
            for (String[] fieldInfo : fieldsOfClass) {
                String fieldType = fieldInfo[0];
                String fieldName = fieldInfo[1];
                System.out.print(fieldName + ":\n-> ");
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                switch (fieldType) {
                    case "int":
                        int value = Integer.parseInt(scanner.nextLine());
                        field.set(userInstance, value);
                        break;
                    case "String":
                        String stringValue = scanner.nextLine();
                        field.set(userInstance, stringValue);
                        break;
                }
            }
            System.out.println("Object created: " + userInstance.toString());
            System.out.println(SEPARATOR);
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }

        return userInstance;
    }

    public static Object change(Object object, Class<?> clazz) throws NoSuchFieldException, IllegalAccessException {
        System.out.print("Enter name of the field for changing:\n-> ");
        if (scanner.hasNextLine()) {
            String fieldName = scanner.nextLine().trim();
            if (checkField(fieldName, clazz)) {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                String type = field.getType().getSimpleName();
                System.out.print("Enter " + type + " value:\n-> ");
                Object arg = getArg(type);
                field.set(object, arg);
                System.out.println("Object updated: " + object.toString());
                System.out.println(SEPARATOR);
            } else throw new NoSuchFieldException();
        }
        return object;
    }

    public static void callMethod(Object object, Class<?> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        System.out.print("Enter name of the method for call:\n" + "-> ");
        String callingMethod = scanner.nextLine().trim();
        String[] methodName = callingMethod.split("\\(");
        String[] enteredParam = methodName[1].replace(")", "").replace("(", "").split(",");
        Method[] methods = clazz.getDeclaredMethods();

        if (!callingMethod.endsWith(")")) {
            throw new NoSuchMethodException();
        }
        for (Method method : methods) {
            if (method.getName().equals(methodName[0])) {
                method.setAccessible(true);
                String[] param = Arrays.toString(method.getParameterTypes()).replace("[", "").replace("]", "").split(",");
                if (param.length != enteredParam.length || !Arrays.equals(param, enteredParam)) {
                    throw new NoSuchMethodException();
                }
                Object[] args = new Object[param.length];
                for (int i = 0; i < param.length; i++) {
                    System.out.print("Enter " + param[i].trim() + " value:\n-> ");
                    args[i] = getArg(param[i].trim());
                }
                Object result = method.invoke(object, args);
                System.out.println("Method returned:\n" + result.toString());
            }
        }


    }

    private static boolean isMethodFromObject(Method method) {
        try {
            Method objectMethod = Object.class.getMethod(method.getName(), method.getParameterTypes());
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    private static boolean checkField(String checkField, Class<?> clazz) throws NoSuchFieldException {
        boolean result = false;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals(checkField)) {
                result = true;
            }
        }
        return result;
    }

    private static boolean checkMethod(String checkMethod, Class<?> clazz) throws NoSuchMethodException {
        boolean result = false;
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            String methodName = method.getName() + Arrays.toString(method.getParameterTypes()).replace("[", "(").replace("]", ")");
            if (methodName.equals(checkMethod)) {
                result = true;
            }
        }
        return result;
    }

    public static Object getArg(String type) throws IllegalAccessException {
        Object result = null;
        try {
            switch (type) {
                case "int":
                    result = scanner.nextInt();
                    break;
                case "String":
                    result = scanner.nextLine();
                    break;
                default:
                    throw new IllegalAccessException("Unsupported field type: " + type);
            }
        } catch (InputMismatchException e) {
            throw new NumberFormatException();
        }
        return result;
    }
}
