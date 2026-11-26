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


import hexlet.code.repository.BaseRepository;
import org.junit.jupiter.api.*;

class AppTest {

    private static final TestConfig FOLLOW_REDIRECTS =
            new TestConfig(
                    true,
                    true,
                    HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL).build());

    private static Javalin app;

    @BeforeEach
    final void setUp() throws  IOException, SQLException {
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
    void testMainPage() {
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
    void testUrlsPage() {
        JavalinTest.test(
                app,
                (server, client) -> {
                    var response = client.get("/urls");
                    assertThat(response.code()).isEqualTo(200);
                }
        );
    }

    @Test
    void testCreateUrl() {
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
    void testUrlExists() {
        JavalinTest.test(
                app,
                (server, client) -> {
                    var requestBody = "url=https://ya.ru";
                    var response = client.post("/urls", requestBody);
                    assertThat(response.code()).isEqualTo(200);
                    assertThat(response.body().string()).contains("Страница уже существует");
                }
        );
    }

    @Test
    void  testInvalidUrl() {
        JavalinTest.test(
                app,
                (server, client) -> {
                    var requestBody = "url=ya.ru";
                    var response = client.post("/urls", requestBody);
                    assertThat(response.code()).isEqualTo(422);
                    assertThat(response.body().string()).contains("Некорректный URL");
                }
        );
    }


    @Test
    void testUrlPage() {
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
    void testUrlNotFound() {
        JavalinTest.test(
                app,
                (server, client) -> {
                    var response = client.get("/urls/9999999");
                    assertThat(response.code()).isEqualTo(404);
                }
        );
    }


    @Test
    void testGetPort() {
        var testApp = new App();
        assertThat(testApp.getPort()).isEqualTo(7070);
    }

}
