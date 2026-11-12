package hexlet.code.controller;

import hexlet.code.dto.BasePage;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import hexlet.code.dto.UrlPage;
import hexlet.code.dto.UrlsPage;
import hexlet.code.dto.BasePage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;

import java.sql.SQLException;
import java.util.Map;

import java.net.URI;
import java.net.URL;

public class UrlsController {
    public static void root(Context ctx) {
        var page = new BasePage();
        String flash = ctx.consumeSessionAttribute("flash");
        page.setFlash(flash);
        ctx.render("index.jte", Map.of("page", page));
    }


    public static void index(Context ctx) throws SQLException {
        var urls = UrlRepository.getEntities();
        var page = new UrlsPage(urls);
        String flash = ctx.consumeSessionAttribute("flash");
        page.setFlash(flash);
        ctx.render("urls/index.jte", Map.of("page", page));
    }
    
    public static void create(Context ctx) {
        var name = ctx.formParam("url");

        try {
            URI uri = URI.create(name);
            URL url = uri.toURL();

            var urlSave = new Url(url.getProtocol() + "://" + url.getHost());
            UrlRepository.save(urlSave);
            ctx.sessionAttribute("flash", "Страница успешно добавлена");
            ctx.redirect("/urls");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            var page = new BasePage();
            page.setFlash("Некорректный URL");
            ctx.render("index.jte", Map.of("page", page));
        }
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
