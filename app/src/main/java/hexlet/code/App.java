package hexlet.code;

import gg.jte.resolve.DirectoryCodeResolver;
import hexlet.code.dto.BasePage;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.staticfiles.Location;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Map;
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

import hexlet.code.repository.BaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class App {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    private static final Path TEMLATES_PATH = Path.of("src", "main", "resources", "templates");
    private static final Path STATIC_PATH = Path.of("src", "main", "resources", "static");
    private static final Path JTE_CLASSES_PATH = Path.of("jte-classes");

    private static String getMode() {
        return System.getenv().getOrDefault("APP_ENV", "production");
    }

    private static boolean isDevelopment() {
        return getMode().equals("development") && Files.isDirectory(TEMLATES_PATH);
    }

    private static boolean isProduction() {
        return  getMode().equals("production");
    }

    public static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.valueOf(port);
    }

    public static void main(String[] args) throws IOException, SQLException {
        var app = getApp();
        app.start(getPort());
    }

    public static TemplateEngine createTemplateEngine() {
        if (isDevelopment()) {
            var codeResolver = new DirectoryCodeResolver(TEMLATES_PATH);
            return TemplateEngine.create(codeResolver, JTE_CLASSES_PATH, ContentType.Html);
        }
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

    private static String getDataBaseUrl() {
        return System.getenv().getOrDefault("JDBC_DATABASE_URL", "jdbc:h2:mem:project");
    }

    public static Javalin getApp() throws  IOException, SQLException {

        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(getDataBaseUrl());

        var dataSource = new HikariDataSource(hikariConfig);

        var sql = readResourceFile("schema.sql");

        LOG.info(sql);
        try (var connection = dataSource.getConnection();
                var statement = connection.createStatement()) {
            statement.execute(sql);
        }
        BaseRepository.dataSource = dataSource;

        var app =
                Javalin.create(
                        config -> {
                            if (!isProduction()) {
                                config.bundledPlugins.enableDevLogging();
                            }

                            config.fileRenderer(new JavalinJte(createTemplateEngine()));

                            if (isDevelopment()) {
                                config.staticFiles.add(STATIC_PATH.toString(), Location.EXTERNAL);
                            } else {
                                config.staticFiles.add("/static", Location.CLASSPATH);
                            }

                            config.routes.before(
                                    ctx -> {
                                        ctx.contentType("text/html; charset=utf-8");
                                    });

                            config.routes.get("/", ctx -> {

                                var page = new BasePage();
                                page.setFlash("Hi, I'm progger on java!");
                                ctx.render("page.jte", Map.of("page", page));
//                               ctx.result("Hi, I am Progger!");
                            });
                        }
                );
        return app;
    }
}
