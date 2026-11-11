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
        var urls = UrlRepository.getEntities();
        var page = new UrlsPage(urls);
        String flash = ctx.consumeSessionAttribute("flash");
        page.setFlash(flash);
        ctx.render("urls/index.jte", Map.of("page", page));
    }

    public static void create(Context ctx) throws SQLException {
        var name = ctx.formParam("url");

        var url = new Url(name);
        UrlRepository.save(url);
        ctx.sessionAttribute("flash", "Страница успешно добавлена");
        ctx.redirect("/urls");
    }

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();

        var url = UrlRepository.find(id)
                .orElseThrow(
                        () -> new NotFoundResponse(
                                "Entity with id = " + id + " not found"));
        var page = new UrlPage(url);
        String flash = ctx.consumeSessionAttribute("flash");
        page.setFlash(flash);
        ctx.render("urls/show.jte", Map.of("page", page));
    }


}
