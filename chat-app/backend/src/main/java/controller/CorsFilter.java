package controller;

import spark.Filter;
import spark.Spark;

/**
 * Class for enabling cors.
 */

public class CorsFilter {
    private static final String ORIGIN = "*";
    private static final String METHODS = "GET, POST, PUT, DELETE, OPTIONS";
    private static final String HEADERS = "Origin, X-Requested-With, Content-Type, Accept";

    /**
     * Apply cors enabling.
     */
    public static void apply() {
        Filter filter = (request, response) -> {
            response.header("Access-Control-Allow-Origin", ORIGIN);
            response.header("Access-Control-Request-Method", METHODS);
            response.header("Access-Control-Allow-Headers", HEADERS);
        };

        Spark.after(filter);
    }
}