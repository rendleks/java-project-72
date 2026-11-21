package hexlet.code;

import static org.assertj.core.api.Assertions.assertThat;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import io.javalin.testtools.TestConfig;

import java.net.http.HttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.stream.Collectors;


import hexlet.code.model.Url;
import hexlet.code.repository.BaseRepository;
import hexlet.code.repository.UrlRepository;
import org.junit.jupiter.api.*;

public class AppTest {

    private static final TestConfig FOLLOW_REDIRECTS =
            new TestConfig(
                    true,
                    true,
                    HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL).build());


    @Test
    void testInit() {
        assertThat(true).isEqualTo(true);
    }

    private static Javalin app;

    @BeforeEach
    public final void setUp() throws  IOException, SQLException {
        app = App.getApp();
        runScript("truncate.sql");
        runScript("seed-test-db.sql");
    }

    private static void runScript(String fileName) throws IOException, SQLException {
        var inputStream = AppTest.class.getClassLoader().getResourceAsStream(fileName);
        String sql;
        try (var reader =
                new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            sql = reader.lines().collect(Collectors.joining("\n"));
        }
        try (var connection = BaseRepository.dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    @Test
    public void testMainPage() {
        JavalinTest.test(
                app,
                (server, client) -> {
                    var response = client.get("/");
                    assertThat(response.code()).isEqualTo(200);
                    assertThat(response.body().string()).contains("Анализатор страниц");
                }
        );
    }

    @Test
    public void testUrlsPage() {
        JavalinTest.test(
                app,
                (server, client) -> {
                    var response = client.get("/urls");
                    assertThat(response.code()).isEqualTo(200);
                }
        );
    }

    @Test
    public void testCreateUrl() {
        JavalinTest.test(
                app,
                FOLLOW_REDIRECTS,
                (server, client) -> {
                    var requestBody = "url=https://www.example.com";
                    var response = client.post("/urls", requestBody);
                    assertThat(response.code()).isEqualTo(200);
                    assertThat(response.body().string()).contains("https://www.example.com");
                });
    }

    @Test
    public void testUrlPage() {
        JavalinTest.test(
                app,
                (server, client) -> {
                    var response = client.get("/urls/1");
                    assertThat(response.code()).isEqualTo(200);
                    assertThat(response.body().string()).contains("https://ya.ru");
                }
        );
    }

    @Test
    public void testUrlNotFound() {
        JavalinTest.test(
                app,
                (server, client) -> {
                    var response = client.get("/urls/9999999");
                    assertThat(response.code()).isEqualTo(404);
                }
        );
    }

}
