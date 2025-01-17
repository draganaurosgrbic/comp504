package edu.rice.comp504.controller;

import com.google.gson.Gson;
import edu.rice.comp504.model.MovingLine;

import static spark.Spark.*;


/**
 * Line draw controller is responsible for interfacing between the model and view for drawing a moving line.
 */
public class LineDrawController {

    /**
     * Program entry point.
     * @param args Program entry point arguments
     */
    public static void main(String[] args) {
        staticFileLocation("/public");
        port(getHerokuAssignedPort());
        Gson gson = new Gson();
        MovingLine line = new MovingLine(20);

        get("/line", (req, res) -> {
            return gson.toJson(line);
        });

        get("/reset", (req, res) -> {
            line.resetPos();
            return gson.toJson(line);
        });

        get("/update", (req, res) ->  {
            line.update();
            return gson.toJson(line);
        });

    }

    /**
     * Get the heroku assigned port number.
     * @return The port number
     */
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
