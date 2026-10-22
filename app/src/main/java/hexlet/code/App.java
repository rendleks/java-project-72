package hexlet.code;

import io.javalin.Javalin;
import io.javalin.http.Context;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.stream.Collectors;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import io.javalin.Javalin;
import io.javalin.http.NotFoundResponse;
import io.javalin.rendering.template.JavalinJte;
import io.javalin.validation.ValidationException;

@Slf4j
public class App {

    public static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.valueOf(port);
    }

    public static void main(String[] args) throws IOException, SQLException {
        var app = getApp();

        app.start(getPort());
    }

    public static TemplateEngine createTemplateEngine() {
        var classLoader = App.class.getClassLoader();
        var codeResolver = new ResourceCodeResolver("templates", classLoader);
        return TemplateEngine.create(codeResolver, ContentType.Html);
    }

    private static String readResourceFile(String fileName) throws IOException {
        var inputStream = App.class.getClassLoader().getResourceAsStream(fileName);
        try (BufferedReader reader =
                new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    public static Javalin getApp() throws  IOException, SQLException {

//        var hikariConfig = new HikariConfig();
//        hikariConfig.setJdbcUrl("jdbc:h2:mem:project;DB_CLOCE_DELAY=-1;");
//
//        var dataSource = new HikariDataSource(hikariConfig);
//        var sql = readResourceFile("schema.sql");

//        log.info(sql);
//        try (var connection = dataSource.getConnection();
//                var statement = connection.createStatement()) {
//            statement.execute(sql);
//        }
//        BaseRepository.dataSource = dataSource;

        var app =
                Javalin.create(
                        config -> {
                            config.bundledPlugins.enableDevLogging();
                            config.fileRenderer(new JavalinJte(createTemplateEngine()));

                            config.routes.before(
                                    ctx -> {
                                        ctx.contentType("text/html; charset=utf-8");
                                    });

                            config.routes.get("/", ctx -> {
                               ctx.result("Hi, I am Progger!");
                            });
                        }
                );
        return app;
    }
}
