package fr.epita.quiz.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
public class TestDI {

    @Autowired
    @Qualifier("services.data.datasource")
    DataSource ds;

    @Test
    public void testDI() throws SQLException {
        //given
        System.out.println("testDI");

        //when
        Connection connection = ds.getConnection();

        //then
        System.out.println(connection.getSchema());
        Assertions.assertEquals("PUBLIC", connection.getSchema());

        connection.close();

    }

}
