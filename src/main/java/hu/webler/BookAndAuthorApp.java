package hu.webler;

import hu.webler.repository.DatabaseRepository;

import java.sql.Connection;
import java.sql.SQLException;

public class BookAndAuthorApp {
    public static void main(String[] args) {
        try {
            Connection connection = DatabaseRepository.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
