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
        ctx.status(200);
        ctx.render("urls/index.jte", Map.of("page", page));
    }
    
    public static void create(Context ctx) {
        var name = ctx.formParam("url");

        try {
            URI uri = URI.create(name);
            URL url = uri.toURL();
            var urlName = url.getProtocol() + "://" + url.getHost();

            if (!UrlRepository.find(urlName).isEmpty()) {
                var page = new BasePage();
                page.setFlash("Страница уже существует");
                ctx.render("index.jte", Map.of("page", page));
                return;
            }

            var urlSave = new Url(urlName);
            UrlRepository.save(urlSave);

            var page = new UrlPage(urlSave);
            page.setFlash("Страница успешно добавлена");
            ctx.render("urls/show.jte", Map.of("page", page));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            var page = new BasePage();
            page.setFlash("Некорректный URL");
            ctx.status(422);
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
