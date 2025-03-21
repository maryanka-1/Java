package manager;

import annotation.OrmColumn;
import annotation.OrmColumnId;
import annotation.OrmEntity;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class OrmManager {
    private final HikariConfig config = new HikariConfig("/hikari.properties");
    private final HikariDataSource ds = new HikariDataSource(config);
    private final Map<String, Class<?>> classMap;

    public OrmManager() {
        classMap = getClassMap();
    }

    public void tableInit() {
        for (String className : classMap.keySet()) {
            Class<?> clazz = classMap.get(className);
            if (!clazz.isAnnotationPresent(OrmEntity.class)) {
                continue;
            }
            OrmEntity ormEntity = clazz.getAnnotation(OrmEntity.class);
            String tableName = ormEntity.table();
            StringBuilder query = new StringBuilder("DROP TABLE IF EXISTS ")
                    .append(tableName)
                    .append(";")
                    .append("CREATE TABLE IF NOT EXISTS ")
                    .append(tableName)
                    .append(" (");
            Field[] fields = clazz.getDeclaredFields();
            boolean first = true;
            for (Field field : fields) {
                if (field.isAnnotationPresent(OrmColumnId.class)) {
                    if (first) {
                        first = false;
                    } else query.append(",");
                    String fieldType = field.getType().getSimpleName();
                    if (fieldType.equals("long") || fieldType.equals("Long")) {
                        query.append("id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY");
                    } else {
                        throw new RuntimeException("Type must be long");
                    }
                } else if (field.isAnnotationPresent(OrmColumn.class)) {
                    if (first) {
                        first = false;
                    } else query.append(",");
                    OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
                    String fieldType = field.getType().getSimpleName();
                    String columnName = ormColumn.name();
                    int columnLength = ormColumn.length();
                    query.append(columnName).append(" ");
                    switch (fieldType) {
                        case "String" -> query.append("varchar(").append(columnLength).append(")");
                        case "Integer" -> query.append("int");
                        case "Long" -> query.append("bigint");
                        case "Double" -> query.append("double");
                        case "Boolean" -> query.append("boolean");
                    }
                }
            }
            query.append(");");
            System.out.println("SQL Query:");
            updateQuery(query.toString());
        }
    }

    public void save(Object entity) {
        Class<?> clazz = entity.getClass();
        if (!clazz.isAnnotationPresent(OrmEntity.class)) {
            return;
        }
        OrmEntity ormEntity = clazz.getAnnotation(OrmEntity.class);
        String tableName = ormEntity.table();
        List<String> columns = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                if (field.isAnnotationPresent(OrmColumnId.class)) {
                    columns.add("id");
                    values.add(field.get(entity));
                } else if (field.isAnnotationPresent(OrmColumn.class)) {
                    OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
                    columns.add(ormColumn.name());
                    values.add(field.get(entity));
                }
                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                System.err.println(e.getMessage());
                return;
            }
        }
        StringBuilder query = new StringBuilder("INSERT INTO ")
                .append(tableName)
                .append(" (");
        for (int i = 0; i < columns.size(); i++) {
            query.append(columns.get(i));
            if (i + 1 != columns.size()) {
                query.append(",");
            } else query.append(")");
        }
        query.append(" values (");
        for (int i = 0; i < values.size(); i++) {
            Object objValue = values.get(i);
            if (objValue.getClass().getSimpleName().equals("String")) {
                query.append("'").append(objValue).append("'");
            } else {
                query.append(objValue);
            }
            if (i + 1 != values.size()) {
                query.append(",");
            } else query.append(");");
        }
        System.out.println("SQL query for save:");
        updateQuery(query.toString());
    }

    public void update(Object entity) {
        Class<?> clazz = entity.getClass();
        if (!clazz.isAnnotationPresent(OrmEntity.class)) {
            return;
        }
        OrmEntity ormEntity = clazz.getAnnotation(OrmEntity.class);
        String tableName = ormEntity.table();
        long id = 0;
        List<String> columns = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                if (field.isAnnotationPresent(OrmColumnId.class)) {
                    id = (long) field.get(entity);
                } else if (field.isAnnotationPresent(OrmColumn.class)) {
                    OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
                    columns.add(ormColumn.name());
                    values.add(field.get(entity));
                }
                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                System.err.println(e.getMessage());
                return;
            }
        }
        if (id == 0) {
            return;
        }
        StringBuilder query = new StringBuilder("UPDATE ")
                .append(tableName)
                .append(" SET ");
        for (int i = 0; i < columns.size(); i++) {
            String column = columns.get(i);
            Object value = values.get(i);
            query.append(column).append("=");
            if (value.getClass().getSimpleName().equals("String")) {
                query.append("'").append(value).append("'");
            } else {
                query.append(value);
            }
            if (i + 1 != columns.size()) {
                query.append(",");
            }
        }
        query.append(" WHERE id=").append(id).append(";");
        System.out.println("SQL query for update:");
        updateQuery(query.toString());
    }

    public <T> T findById(Long id, Class<T> aClass) {
        if (!aClass.isAnnotationPresent(OrmEntity.class)) {
            return null;
        }
        String tableName = aClass.getAnnotation(OrmEntity.class).table();
        ResultSet result = executeQuery(
                "SELECT * FROM " +
                        tableName +
                        " where id=" +
                        id +
                        ";");
        try{
            if(!result.next()){
                return null;
            }
            T newInstance = aClass.getDeclaredConstructor().newInstance();
            for(Field field : aClass.getDeclaredFields()){
                field.setAccessible(true);
                if (field.isAnnotationPresent(OrmColumnId.class)) {
                    field.set(newInstance, result.getLong("id"));
                } else if (field.isAnnotationPresent(OrmColumn.class)) {
                    OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
                    field.set(newInstance, result.getObject(ormColumn.name()));
                }
                field.setAccessible(false);
            }
            System.out.println("Result SQL query findById:");
            return newInstance;
        } catch (SQLException|InstantiationException|IllegalAccessException e){
            System.err.println(e.getMessage());
            return null;
        } catch (InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private ResultSet executeQuery(String query) {
        try {
            printQuery(query);
            return ds.getConnection().createStatement().executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Class<?>> getClassMap() {
        Map<String, Class<?>> classes = new HashMap<>();
        try{
            ClassLoader classLoader = getClass().getClassLoader();
            String MODEL = "model";
            Enumeration<URL> resources = classLoader.getResources(MODEL);
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                File file = new File(resource.getFile());
                if (file.isDirectory()) {
                    for (File classFile : file.listFiles()) {
                        String fileName = classFile.getName();
                        if (fileName.endsWith(".class")) {
                            String className = fileName.substring(0, fileName.length() - 6);
                            String fullClassName = MODEL + "." + className;
                            Class<?> clazz = Class.forName(fullClassName);
                            classes.put(className, clazz);
                        }
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return classes;
    }

    private void updateQuery(String query) {
        try {
            printQuery(query);
            ds.getConnection().createStatement().executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void printQuery(String query) {
        System.out.println(query);
    }

    public void close(){
        ds.close();
    }

}
