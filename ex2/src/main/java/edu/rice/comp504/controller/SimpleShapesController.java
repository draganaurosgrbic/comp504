package edu.rice.comp504.controller;

import com.google.gson.Gson;
import edu.rice.comp504.adapter.DispatchAdapter;
import org.eclipse.jetty.http.HttpStatus;

import java.awt.*;

import static spark.Spark.*;

/**
 * The SimpleShapesController is responsible for interfacing between the view and the model.  The model will determine
 * how shape objects are created.  The view is the browser.  The browser has a canvas that renders the shapes.
 * The controller interacts with the view by receiving REST get requests for various shapes.
 */
public class SimpleShapesController {

    /**
     *  Entry point into the program.
     * @param args  The arguments
     */
    public static void main(String[] args) {
        staticFiles.location("/public");
        port(getHerokuAssignedPort());

        Gson gson = new Gson();
        DispatchAdapter adapter = new DispatchAdapter();

        post("/canvas/dims", (request, response) -> {
            Point point = new Point();
            String[] array = request.body().split("&");
            for (String item: array) {
                if (item.startsWith("height=")) {
                    point.y = (int) Double.parseDouble(item.substring(7));
                } else if (item.startsWith("width=")) {
                    point.x = (int) Double.parseDouble(item.substring(6));
                }
            }
            DispatchAdapter.setCanvasDims(point);
            return gson.toJson(DispatchAdapter.getCanvasDims());
        });

        get("/shapes", (request, response) -> gson.toJson(adapter.getShapes()));

        get("/shape/circle", (request, response) -> {
            adapter.addShape("circle");
            return gson.toJson(adapter.updateShapes());
        });

        get("/shape/triangle", (request, response) -> {
            adapter.addShape("triangle");
            return gson.toJson(adapter.updateShapes());
        });

        get("/shape/square", (request, response) -> {
            adapter.addShape("square");
            return gson.toJson(adapter.updateShapes());
        });

        get("/shape/rectangle", (request, response) -> {
            adapter.addShape("rectangle");
            return gson.toJson(adapter.updateShapes());
        });

        get("/shape/complex", (request, response) -> {
            adapter.addShape("complex");
            return gson.toJson(adapter.updateShapes());
        });

        get("/shapes/clear", (request, response) -> {
            adapter.removeShapes();
            return gson.toJson(null);
        });

        get("/canvas", (request, response) -> {
            adapter.removeShapes();
            response.redirect("/", HttpStatus.FOUND_302);
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
