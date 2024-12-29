package edu.rice.comp504.controller;

import com.google.gson.Gson;
import edu.rice.comp504.adapter.DispatchAdapter;
import edu.rice.comp504.model.CustomException;
import org.eclipse.jetty.http.HttpStatus;

import static spark.Spark.*;

public class BallWorldController {

    /**
     * Entry point into the program.
     */
    public static void main(String[] args) {
        staticFiles.location("/public");
        port(getHerokuAssignedPort());
        Gson gson = new Gson();
        DispatchAdapter adapter = new DispatchAdapter();

        post("/load", (request, response) -> {
            String errorMessage;
            try {
                return gson.toJson(adapter.loadBall(request.body(), "ball"));
            } catch (CustomException ce) {
                errorMessage = ce.getMessage();
            } catch (Exception e) {
                errorMessage = "Endpoint for loading ball failed!";
            }

            response.redirect("/error.html?message=" + errorMessage.replace(" ", "%20"));
            return gson.toJson(null);
        });

        get("/update", (request, response) -> {
            String errorMessage;
            try {
                return gson.toJson(adapter.updateBallWorld());
            } catch (CustomException ce) {
                errorMessage = ce.getMessage();
            } catch (Exception e) {
                errorMessage = "Endpoint for updating balls failed!";
            }

            response.redirect("/error.html?message=" + errorMessage.replace(" ", "%20"));
            return gson.toJson(null);
        });

        get("/clear", (request, response) -> {
            String errorMessage;
            try {
                adapter.removeAll();
                return gson.toJson(null);
            } catch (CustomException ce) {
                errorMessage = ce.getMessage();
            } catch (Exception e) {
                errorMessage = "Endpoint for clearing balls failed!";
            }

            response.redirect("/error.html?message=" + errorMessage.replace(" ", "%20"));
            return gson.toJson(null);
        });

        post("/canvas/dims", (request, response) -> {
            String errorMessage;
            try {
                adapter.setCanvasDims(request.body());
                return gson.toJson(null);
            } catch (CustomException ce) {
                errorMessage = ce.getMessage();
            } catch (Exception e) {
                errorMessage = "Endpoint for setting canvas dimensions failed!";
            }

            response.redirect("/error.html?message=" + errorMessage.replace(" ", "%20"));
            return gson.toJson(null);
        });

        get("/ballworld", (request, response) -> {
            String errorMessage;
            try {
                adapter.removeAll();
                response.redirect("/", HttpStatus.FOUND_302);
                return gson.toJson(null);
            } catch (CustomException ce) {
                errorMessage = ce.getMessage();
            } catch (Exception e) {
                errorMessage = "Endpoint for clearing balls failed!";
            }

            response.redirect("/error.html?message=" + errorMessage.replace(" ", "%20"));
            return gson.toJson(null);
        });

        get("/remove", (request, response) -> {
            String errorMessage;
            try {
                return gson.toJson(adapter.removeStrategy(request.queryParams("strategy")));
            } catch (CustomException ce) {
                errorMessage = ce.getMessage();
            } catch (Exception e) {
                errorMessage = "Endpoint for removing balls failed!";
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

        options("/*", (request, response) -> {
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
