package edu.rice.comp504.controller;

import com.google.gson.Gson;
import edu.rice.comp504.adapter.DispatchAdapter;
import edu.rice.comp504.model.LineStore;

import static spark.Spark.*;


/**
 * Line draw controller is responsible for interfacing between the model and view for drawing a moving line.
 */
public class LineDrawController {

    /**
     * Main entry point into the program.
     * @param args Arguments that are normally passed to the executable on the command line
     */
    public static void main(String[] args) {
        staticFileLocation("/public");
        port(getHerokuAssignedPort());
        Gson gson = new Gson();
        LineStore ls = new LineStore();
        DispatchAdapter da = new DispatchAdapter(ls);

        get("/line/:kind", (req, res) ->
            gson.toJson(da.loadLine(req.params(":kind")))
        );

        get("/reset", (req, res) ->
            gson.toJson(da.resetLines())
        );

        get("/update", (req, res) ->
            gson.toJson(da.updateLines())
        );

        get("/switch", (req, res) ->
            gson.toJson( da.switchStrategy())
        );
    }

    /**
     * Get the Heroku assigned port number.
     * @return  Heroku assigned port number
     */
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
