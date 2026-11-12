package app.persistence;

import app.entities.CupcakeTop;
import app.entities.Order;
import app.exceptions.DatabaseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapperTest {
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=test";
    private static final String DB = "cupcake";

    private static ConnectionPool connectionPool;
    private static OrderMapper orderMapper;

    @BeforeAll
    static void setUpClass(){
        connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);
        orderMapper = new OrderMapper(connectionPool);

        try (Connection connection = connectionPool.getConnection();
             Statement stmt = connection.createStatement()) {

            stmt.execute("CREATE SCHEMA IF NOT EXISTS test");

            // Drop tables
            stmt.execute("DROP TABLE IF EXISTS test.orderline_history");
            stmt.execute("DROP TABLE IF EXISTS test.order_history");
            stmt.execute("DROP TABLE IF EXISTS test.orderlines");
            stmt.execute("DROP TABLE IF EXISTS test.orders");
            stmt.execute("DROP TABLE IF EXISTS test.cupcake_tops");
            stmt.execute("DROP TABLE IF EXISTS test.cupcake_bottoms");
            stmt.execute("DROP TABLE IF EXISTS test.admins");
            stmt.execute("DROP TABLE IF EXISTS test.customers");

            // Drop sequences
            stmt.execute("DROP SEQUENCE IF EXISTS test.customers_customer_id_seq");
            stmt.execute("DROP SEQUENCE IF EXISTS test.admins_admin_id_seq");
            stmt.execute("DROP SEQUENCE IF EXISTS test.orders_order_id_seq");
            stmt.execute("DROP SEQUENCE IF EXISTS test.orderlines_orderline_id_seq");
            stmt.execute("DROP SEQUENCE IF EXISTS test.order_history_id_seq");
            stmt.execute("DROP SEQUENCE IF EXISTS test.orderline_history_id_seq");

            // Create tables
            stmt.execute("""
                CREATE TABLE test.customers AS
                SELECT * FROM public.customers
                WITH NO DATA
                """);
            stmt.execute("""
                CREATE TABLE test.admins AS
                SELECT * FROM public.admins
                WITH NO DATA
                """);
            stmt.execute("""
                CREATE TABLE test.cupcake_tops AS
                SELECT * FROM public.cupcake_tops
                WITH NO DATA
                """);
            stmt.execute("""
                CREATE TABLE test.cupcake_bottoms AS
                SELECT * FROM public.cupcake_bottoms
                WITH NO DATA
                """);
            stmt.execute("""
                CREATE TABLE test.orders AS
                SELECT * FROM public.orders
                WITH NO DATA
                """);
            stmt.execute("""
                CREATE TABLE test.orderlines AS
                SELECT * FROM public.orderlines
                WITH NO DATA
                """);
            stmt.execute("""
                CREATE TABLE test.order_history AS
                SELECT * FROM public.order_history
                WITH NO DATA
                """);
            stmt.execute("""
                CREATE TABLE test.orderline_history AS
                SELECT * FROM public.orderline_history
                WITH NO DATA
                """);


            // Create sequences and set defaults
            stmt.execute("CREATE SEQUENCE test.customers_customer_id_seq");
            stmt.execute("""
                ALTER TABLE test.customers
                ALTER COLUMN customer_id
                SET DEFAULT nextval('test.customers_customer_id_seq')
                """);


            stmt.execute("CREATE SEQUENCE test.admins_admin_id_seq");
            stmt.execute("""
                ALTER TABLE test.admins
                ALTER COLUMN admin_id
                SET DEFAULT nextval('test.admins_admin_id_seq')
                """);


            stmt.execute("CREATE SEQUENCE test.orders_order_id_seq");
            stmt.execute("""
                ALTER TABLE test.orders
                ALTER COLUMN order_id
                SET DEFAULT nextval('test.orders_order_id_seq')
                """);

            // orders.order_date har DEFAULT CURRENT_DATE i public
            stmt.execute("""
                ALTER TABLE test.orders
                ALTER COLUMN order_date
                SET DEFAULT CURRENT_DATE
                """);

            stmt.execute("CREATE SEQUENCE test.orderlines_orderline_id_seq");
            stmt.execute("""
                ALTER TABLE test.orderlines
                ALTER COLUMN orderline_id
                SET DEFAULT nextval('test.orderlines_orderline_id_seq')
                """);


            stmt.execute("CREATE SEQUENCE test.order_history_id_seq");
            stmt.execute("""
                ALTER TABLE test.order_history
                ALTER COLUMN id
                SET DEFAULT nextval('test.order_history_id_seq')
                """);


            stmt.execute("CREATE SEQUENCE test.orderline_history_id_seq");
            stmt.execute("""
                ALTER TABLE test.orderline_history
                ALTER COLUMN id
                SET DEFAULT nextval('test.orderline_history_id_seq')
                """);


        }  catch (
    SQLException e) {
        fail("Database setup failed: " + e.getMessage());
    }
    }

    @BeforeEach
    void setUp() {

        try (Connection connection = connectionPool.getConnection();
             Statement stmt = connection.createStatement()) {
            // Truncate (nulstiller også sequences, da de er OWNED BY kolonnerne)
            stmt.execute("""
                    TRUNCATE TABLE
                    test.orderline_history,
                    test.order_history,
                    test.orderlines,
                    test.orders,
                    test.cupcake_tops,
                    test.cupcake_bottoms,
                    test.admins,
                    test.customers
                RESTART IDENTITY CASCADE
              """);

            // Insert test data
            stmt.execute("""
                INSERT INTO test.customers (email, name, password, balance) VALUES
                  ('john@gmail.com', 'John', 'password123', 500),
                  ('anna@gmail.com', 'Anna', 'anna123', 350),
                  ('peter@gmail.com', 'Peter', 'peter123', 200),
                  ('sara@gmail.com', 'Sara', 'sara123', 750),
                  ('mike@gmail.com', 'Mike', 'mike123', 100),
                  ('ledut@ek.dk', 'Lukas', '1234', 1000),
                  ('olga@ek.dk', 'Olga', '1234', 1000),
                  ('alex@gmail.com', 'Alexander', '1234', 1000),
                  ('magnus@gmail.com', 'Magnus', '1234', 1000)
                """);

            stmt.execute("""
                INSERT INTO test.admins (email, name, password) VALUES
                  ('admin@cupcake.dk', 'Admin User', 'admin123'),
                  ('manager@cupcake.dk', 'Manager User', 'manager123')
                """);

            stmt.execute("""
                INSERT INTO test.cupcake_tops (flavor, price) VALUES
                  ('Chokolade', 10.00),
                  ('Blåbær', 8.00),
                  ('Hindbær', 9.00),
                  ('Jordbær', 7.00),
                  ('Rom/Rosin', 11.00),
                  ('Applesin', 12.00),
                  ('Citron', 13.00),
                  ('Skimmelost', 9.00)
                """);

            stmt.execute("""
                INSERT INTO test.cupcake_bottoms (flavor, price) VALUES
                  ('Chokolade', 15.00),
                  ('Vanilje', 18.00),
                  ('Muskatnød', 12.00),
                  ('Pistache', 5.00),
                  ('Mandel', 20.00)
                """);

            stmt.execute("""
                INSERT INTO test.orders (customer_id, status) VALUES
                  (1, 'CART'),
                  (2, 'PLACED'),
                  (3, 'APPROVED'),
                  (4, 'PLACED'),
                  (5, 'CART')
                """);

            stmt.execute("""
                INSERT INTO test.orderlines (order_id, cupcake_topping, cupcake_bottom, quantity) VALUES
                  (1, 'Chokolade', 'Chokolade', 2),
                  (1, 'Jordbær', 'Vanilje', 1),
                  (2, 'Hindbær', 'Mandel', 2),
                  (3, 'Blåbær', 'Chokolade', 1),
                  (4, 'Blåbær', 'Pistache', 3),
                  (4, 'Blåbær', 'Vanilje', 1),
                  (5, 'Rom/Rosin', 'Vanilje', 1),
                  (5, 'Citron', 'Pistache', 2),
                  (5, 'Skimmelost', 'Muskatnød', 1)
                """);

            stmt.execute("""
                INSERT INTO test.order_history (customer_id, status, order_date) VALUES
                  (1, 'COMPLETED', '2026-11-01'),
                  (2, 'COMPLETED', '2026-11-03'),
                  (3, 'COMPLETED', '2026-11-04'),
                  (1, 'COMPLETED', '2026-11-04'),
                  (4, 'DENIED', '2026-11-05'),
                  (5, 'COMPLETED', '2026-11-07')
                """);

            stmt.execute("""
                INSERT INTO test.orderline_history
                  (order_id, cupcake_topping, cupcake_bottom, cupcake_topping_price, cupcake_bottom_price, quantity) VALUES
                  (1, 'Chokolade', 'Mandel', 10.00, 20.00, 2),
                  (1, 'Blåbær', 'Vanilje', 8.00, 18.00, 1),
                  (2, 'Chokolade', 'Mandel', 10.00, 20.00, 1),
                  (3, 'Blåbær', 'Pistache', 8.00, 5.00, 2),
                  (4, 'Skimmelost', 'Pistache', 9.00, 5.00, 3),
                  (5, 'Chokolade', 'Vanilje', 10.00, 18.00, 2),
                  (6, 'Skimmelost', 'Mandel', 9.00, 20.00, 1)
                """);


        }catch (SQLException e){
            fail("Test data setup failed: " + e.getMessage());
        }
    }

    @Test
    void testConnection() throws SQLException {
        assertNotNull(connectionPool.getConnection());
    }

    @Test
    void allCakeTops() throws DatabaseException {
        List<CupcakeTop> allCupcakeTops = orderMapper.allCakeTops();

        assertEquals(8, allCupcakeTops.size());
    }

    @Test
    void getOrderById() throws DatabaseException {
        Order order = orderMapper.getOrderById(1);

        assertEquals("CART", order.getStatus());

    }
}