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
    private static String baseUrl;

//    @BeforeAll
//    public static void beforeAll() throws IOException, SQLException {
//        app = App.getApp();
//        app.start(0);
//        int port = app.port();
//        baseUrl = "http://localhost:" + port;
//    }
//
//    @AfterAll
//    public static void afterAll() {
//        app.stop();
//    }

    @BeforeEach
    public final void setUp() throws  IOException, SQLException {
        app = App.getApp();
    }

    @BeforeEach
    void beforeEach() throws IOException, SQLException {
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

}
