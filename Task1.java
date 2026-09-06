import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

interface Storage {
    void save(String data);
    String retrieve(int id);
}

class Database implements Storage {
    private final String url;
    private final String user;
    private final String password;

    Database(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public void save(String data) {
        final String query = "INSERT INTO storage (`data`) VALUES (?);";
        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement stmnt = con.prepareStatement(query)) {
            stmnt.setString(1, data);
            stmnt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Возникла ошибка при сохранении данных в БД", e);
        }
    }

    @Override
    public String retrieve(int id) {
        final String query = "SELECT `data` FROM storage WHERE id=?";
        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement stmnt = con.prepareStatement(query)) {
            stmnt.setInt(1, id);
            try (ResultSet rs = stmnt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("data");
                }
                throw new RuntimeException("Не найдено записи с id=%d".formatted(id));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Возникла ошибка при чтении записи с id=%d из БД".formatted(id), e);
        }
    }
}

public class Task1 {
    public static void main(String[] args) {
        Storage db = new Database(
            System.getenv("DB_URL"),
            System.getenv("DB_USERNAME"),
            System.getenv("DB_PASSWORD")
        );
        db.save("apple");
        db.save("banana");
        System.out.printf("Первый фрукт - %s\n", db.retrieve(1));
        System.out.printf("Второй фрукт - %s\n", db.retrieve(2));
    }
}
