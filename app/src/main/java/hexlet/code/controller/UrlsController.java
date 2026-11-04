package hexlet.code.controller;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import hexlet.code.dto.UrlPage;
import hexlet.code.dto.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;

import java.sql.SQLException;
import java.util.Map;

public class UrlsController {
    public static void index(Context ctx) throws SQLException {
        String flash = ctx.consumeSessionAttribute("flash");
        var urls = UrlRepository.getEntities();
        var page = new UrlsPage(urls);
        ctx.render("urls/index.jte", Map.of("page", page));
    }

    public static void create(Context ctx) throws SQLException {
        var name = ctx.formParam("url");

        var url = new Url(name);
        UrlRepository.save(url);
        ctx.sessionAttribute("flash", "Url has been added");
        ctx.redirect("/urls");
    }


}
