package controller;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import model.DispatchAdapter;
import model.Ghost;
import model.Pacman;

import static spark.Spark.*;

/**
 * The Pac-Man game controller creates the adapter(s) that communicate with the view.
 * The controller responds to requests from the view after contacting the adapter(s).
 */
public class GameController {

    /**
     * The main entry point into the program.
     * @param args  The program arguments normally specified on the cmd line
     */
    public static void main(String[] args) {
        staticFiles.location("/public");
        port(getHerokuAssignedPort());
        Gson gson = new Gson();
        DispatchAdapter dis = new DispatchAdapter();

        post("/startGame", (request, response) -> {
            dis.startGame();
            return gson.toJson("Game Started");
        });

        post("/exitGame", (request, response) -> {
            dis.endGame();
            return gson.toJson("Game Ended");
        });

        post("/pauseGame", (request, response) -> {
            dis.pauseGame();
            return gson.toJson("Game Paused");
        });

        post("/resumeGame", (request, response) -> {
            dis.startGame();
            return gson.toJson("Game Resumed");
        });

        post("/setNumGhosts", (request, response) -> {
            int ghosts = Integer.parseInt(request.queryParams("ghosts"));
            dis.setNumGhosts(ghosts);
            return gson.toJson("Number of ghosts set to " + ghosts);
        });

        post("/update", (request, response) -> {
            String direction = request.queryParams("direction");
            dis.updateGameState(direction);
            return gson.toJson("Updated state");
        });

        get("/getPacmanDirection", (request, response) -> {
            JsonObject result = new JsonObject();
            result.addProperty("x", dis.getPacmanDir().x);
            result.addProperty("y", dis.getPacmanDir().y);
            return gson.toJson(result);
        });

        get("/getGameLevel", (request, response) -> {
            JsonObject result = new JsonObject();
            result.addProperty("gameLevel", dis.getGameLevel());
            return gson.toJson(result);
        });

        get("/getLives", (request, response) -> {
            JsonObject result = new JsonObject();
            result.addProperty("lives", dis.getLives());
            return gson.toJson(result);
        });

        get("/getScore", (request, response) -> {
            JsonObject result = new JsonObject();
            result.addProperty("score", dis.getScore());
            return gson.toJson(result);
        });

        get("/getGameStatus", (request, response) -> {
            JsonObject result = new JsonObject();
            result.addProperty("getGameStatus", dis.getGameStatus());
            return gson.toJson(result);
        });

        get("/getBoard", (request, response) -> {
            int[][] board = dis.getBoard();
            return gson.toJson(board);
        });

        get("/getPacman", (request, response) -> {
            Pacman pacman = dis.getPacman();

            JsonObject result = new JsonObject();
            result.addProperty("x", pacman.getLoc().x);
            result.addProperty("y", pacman.getLoc().y);
            result.addProperty("dx", pacman.getDir().x);
            result.addProperty("dy", pacman.getDir().y);

            return gson.toJson(result);
        });

        get("/getGhosts", (request, response) -> {
            JsonArray result = new JsonArray();

            for (Ghost ghost: dis.getGhosts()) {
                JsonObject ghostJson = new JsonObject();
                ghostJson.addProperty("x", ghost.getLoc().x);
                ghostJson.addProperty("y", ghost.getLoc().y);
                ghostJson.addProperty("color", ghost.getColor());
                result.add(ghostJson);
            }

            return gson.toJson(result);
        });
    }

    /**
     * Get the Heroku assigned port number.
     * @return The port number
     */
    private static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}

