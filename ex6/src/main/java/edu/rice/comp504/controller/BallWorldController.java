package edu.rice.comp504.controller;

import edu.rice.comp504.model.BallWorldStore;
import edu.rice.comp504.model.paintobj.APaintObject;
import com.google.gson.Gson;
import edu.rice.comp504.model.DispatcherAdapter;

import static spark.Spark.*;

/**
 * The shape world controllers communicates to the model make and update paint object requests from the view and
 * communicates to the view new paint objects and updates to the existing paint objects.
 */
public class BallWorldController {

    /**
     * Main entry point into the program.
     * @param args Program arguments normally passed to main on the command line
     */
    public static void main(String[] args) {
        staticFiles.location("/public");
        port(getHerokuAssignedPort());
        Gson gson = new Gson();
        BallWorldStore bwStore = new BallWorldStore();
        DispatcherAdapter dis = new DispatcherAdapter(bwStore);

        get("/load/:type", (request, response) -> {
            String type  = request.params(":type");
            APaintObject po = dis.loadPaintObj(type);
            return gson.toJson(po);
        });

        get("/update", (request, response) -> gson.toJson(dis.updateBallWorld()));

        post("/canvas/dims", (request, response) -> {
            BallWorldStore.setCanvasDims(request.queryParams("height"),request.queryParams("width"));
            return gson.toJson("canvas dimensions");
        });

        get("/clear", (request, response) -> {
            dis.removeListeners();
            return gson.toJson("removed all balls and inner walls in paintobj world");
        });

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
