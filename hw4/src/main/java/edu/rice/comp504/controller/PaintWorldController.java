package edu.rice.comp504.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.rice.comp504.model.DispatchAdapter;
import edu.rice.comp504.model.PaintWorldStore;
import org.eclipse.jetty.http.HttpStatus;

import static spark.Spark.*;

public class PaintWorldController {

    /**
     * Entry point into the program.
     */
    public static void main(String[] args) {
        staticFiles.location("/public");
        port(getHerokuAssignedPort());
        Gson gson = new Gson();
        PaintWorldStore store = new PaintWorldStore();
        DispatchAdapter adapter = new DispatchAdapter(store);

        post("/canvas/dims", (request, response) -> {
            String errorMessage;
            try {
                PaintWorldStore.setCanvasDimensions(request.queryParams("width"), request.queryParams("height"));
                adapter.clearWorld();
                return gson.toJson("canvas dimensions successfully parsed and stored");
            } catch (Exception e) {
                errorMessage = "Canvas dimensions received from viewer are not parsable!";
            }

            response.redirect("/error.html?message=" + errorMessage.replace(" ", "%20"));
            return gson.toJson(null);
        });

        post("/load/:type", (request, response) -> {
            String errorMessage;
            try {
                JsonObject object = new JsonParser().parse(request.body()).getAsJsonObject();
                adapter.loadObject(
                    request.params(":type"),
                    object.get("updateStrategy").getAsString(),
                    object.get("strategySwitchable").getAsString(),
                    object.get("collisionStrategy").getAsString()
                );
                return gson.toJson("paint object loaded into paint world");
            } catch (Exception e) {
                errorMessage = "Failed to load paint object into paint world!";
            }

            response.redirect("/error.html?message=" + errorMessage.replace(" ", "%20"));
            return gson.toJson(null);
        });

        get("/remove/:id", (request, response) -> {
            String errorMessage;
            try {
                adapter.removeObjects(request.params(":id"));
                return gson.toJson("paint objects removed from paint world");
            } catch (Exception e) {
                errorMessage = "Failed to remove paint objects from paint world!";
            }

            response.redirect("/error.html?message=" + errorMessage.replace(" ", "%20"));
            return gson.toJson(null);
        });

        post("/switch", (request, response) -> {
            String errorMessage;
            try {
                JsonObject object = new JsonParser().parse(request.body()).getAsJsonObject();
                adapter.switchStrategies(
                    object.get("fromStrategy").getAsString(),
                    object.get("toStrategy").getAsString()
                );
                return gson.toJson("strategies switched for paint objects");
            } catch (Exception e) {
                errorMessage = "Failed to switch strategies for paint objects!";
            }

            response.redirect("/error.html?message=" + errorMessage.replace(" ", "%20"));
            return gson.toJson(null);
        });

        get("/update", (request, response) -> {
            String errorMessage;
            try {
                return gson.toJson(adapter.updateWorld());
            } catch (Exception e) {
                errorMessage = "Failed to update paint objects in paint world!";
            }

            response.redirect("/error.html?message=" + errorMessage.replace(" ", "%20"));
            return gson.toJson(null);
        });

        get("/clear", (request, response) -> {
            String errorMessage;
            try {
                adapter.clearWorld();
                return gson.toJson("removed all paint objects from paint world");
            } catch (Exception e) {
                errorMessage = "Failed to remove all paint objects from paint world!";
            }

            response.redirect("/error.html?message=" + errorMessage.replace(" ", "%20"));
            return gson.toJson(null);
        });

        get("/paintworld", (request, response) -> {
            String errorMessage;
            try {
                adapter.clearWorld();
                response.redirect("/", HttpStatus.FOUND_302);
                return gson.toJson(null);
            } catch (Exception e) {
                errorMessage = "Failed to clear the canvas!";
            }

            response.redirect("/error.html?message=" + errorMessage.replace(" ", "%20"));
            return gson.toJson(null);
        });

        get("/*", (request, response) -> {
            String errorMessage = "The url path you entered does not exist.";
            response.redirect("/error.html?message=" + errorMessage.replace(" ", "%20"));
            return gson.toJson(null);
        });

        post("/*", (request, response) -> {
            String errorMessage = "The url path you entered does not exist.";
            response.redirect("/error.html?message=" + errorMessage.replace(" ", "%20"));
            return gson.toJson(null);
        });

        put("/*", (request, response) -> {
            String errorMessage = "The url path you entered does not exist.";
            response.redirect("/error.html?message=" + errorMessage.replace(" ", "%20"));
            return gson.toJson(null);
        });

        delete("/*", (request, response) -> {
            String errorMessage = "The url path you entered does not exist.";
            response.redirect("/error.html?message=" + errorMessage.replace(" ", "%20"));
            return gson.toJson(null);
        });

    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }
}
