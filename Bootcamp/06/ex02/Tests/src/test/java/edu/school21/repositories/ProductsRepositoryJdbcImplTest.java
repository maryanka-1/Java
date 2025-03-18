package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ProductsRepositoryJdbcImplTest {
    private EmbeddedDatabase dataSource;
    ProductsRepositoryJdbcImpl productsRepositoryJdbc;

    @BeforeEach
    public void init(){
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .setName("Day06")
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        this.productsRepositoryJdbc = new ProductsRepositoryJdbcImpl(dataSource);
    }

    @AfterEach
    public void destroy(){
        dataSource.shutdown();
    }

    final List<Product> EXPECTED_FIND_ALL_PRODUCTS = new ArrayList<>(Arrays.asList(
            new Product(1L, "bread", 50.10),
            new Product(2L, "milk", 100.00),
            new Product(3L, "beef", 350.60),
            new Product(4L, "tea", 123.45),
            new Product(5L, "sugar", 68.80),
            new Product(6L, "eggs", 140.00)
    ));
    final Product EXPECTED_FIND_BY_ID_PRODUCT = EXPECTED_FIND_ALL_PRODUCTS.get(1);
    final Product EXPECTED_UPDATED_PRODUCT = new Product(2L, "milk", 99.00);
    final Product EXPECTED_DELETED_PRODUCT = new Product(3L, "beef", 350.60);
    final Product EXPECTED_SAVE_PRODUCT = new Product(7L, "orange", 120.00);

    @Test
    public void findAllProducts(){
        List<Product> result = productsRepositoryJdbc.findAll();
        assertIterableEquals(EXPECTED_FIND_ALL_PRODUCTS, result);
    }

    @Test
    public void findByIdProduct(){
        Optional<Product> result = productsRepositoryJdbc.findById(EXPECTED_FIND_BY_ID_PRODUCT.getId());
        Assertions.assertEquals(result.get(), EXPECTED_FIND_BY_ID_PRODUCT);
    }

    @Test
    public void saveProduct(){
        productsRepositoryJdbc.save(EXPECTED_SAVE_PRODUCT);
        assertEquals(EXPECTED_SAVE_PRODUCT, productsRepositoryJdbc.findById(EXPECTED_SAVE_PRODUCT.getId()).get());
    }

    @Test
    void updateProduct(){
        productsRepositoryJdbc.update(EXPECTED_UPDATED_PRODUCT);
        Optional<Product> result = productsRepositoryJdbc.findById(2L);
        assertEquals(EXPECTED_UPDATED_PRODUCT, result.get());
    }
    @Test
    void deleteProduct(){
        productsRepositoryJdbc.delete(EXPECTED_DELETED_PRODUCT.getId());
        Optional<Product> result = productsRepositoryJdbc.findById(3L);
        Optional<Product> expected = Optional.empty();
        assertEquals(result, expected);

    }

}
