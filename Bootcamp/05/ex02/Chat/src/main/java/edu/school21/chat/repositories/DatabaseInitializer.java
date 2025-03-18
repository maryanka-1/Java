package edu.school21.chat.repositories;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {
    private final DataSource dataSource;

    public DatabaseInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void initialize() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {


            InputStream schemaStream = getClass().getClassLoader().getResourceAsStream("./schema.sql");
            if (schemaStream != null) {
                BufferedReader schemaReader = new BufferedReader(new InputStreamReader(schemaStream));
                String line;
                StringBuilder schemaSql = new StringBuilder();
                while ((line = schemaReader.readLine()) != null) {
                    schemaSql.append(line).append("\n");
                }
                statement.execute(schemaSql.toString());
            }

            InputStream dataStream = getClass().getClassLoader().getResourceAsStream("./data.sql");
            if (dataStream != null) {
                BufferedReader dataReader = new BufferedReader(new InputStreamReader(dataStream));
                String line;
                StringBuilder dataSql = new StringBuilder();
                while ((line = dataReader.readLine()) != null) {
                    dataSql.append(line).append("\n");
                }
                statement.execute(dataSql.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}