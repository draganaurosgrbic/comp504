package edu.rice.comp504.controller;

import com.google.gson.Gson;
import edu.rice.comp504.adapter.DispatchAdapter;
import org.eclipse.jetty.http.HttpStatus;

import java.awt.*;

import static spark.Spark.*;
public class SimpleShapesController {

    /**
     * Entry point into the program.
     */
    public static void main(String[] args) {
        staticFiles.location("/public");
        port(getHerokuAssignedPort());

        Gson gson = new Gson();
        DispatchAdapter adapter = new DispatchAdapter();

        post("/canvas/dims", (request, response) -> {
            int x = (int) Double.parseDouble(request.queryParams("width"));
            int y = (int) Double.parseDouble(request.queryParams("height"));
            DispatchAdapter.setCanvasDims(new Point(x, y));
            return gson.toJson(DispatchAdapter.getCanvasDims());
        });

        get("/shapes", (request, response) -> gson.toJson(adapter.getShapes()));

        get("/shape/:shape", (request, response) -> {
            String name = request.params("shape");
            int matrix = 1;     // can it be zero??
            if (request.queryParams("matrix") != null) {
                matrix = Integer.parseInt(request.queryParams("matrix"));
            }
            boolean frame = false;
            if (request.queryParams("frame") != null) {
                frame = request.queryParams("frame").equals("true");
            }
            boolean randomColors = false;
            if (request.queryParams("random_colors") != null) {
                randomColors = request.queryParams("random_colors").equals("true");
            }
            adapter.addShape(name, matrix, frame, randomColors);
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
