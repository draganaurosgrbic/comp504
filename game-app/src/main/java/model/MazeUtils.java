package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This class includes methods and inner classes used to search and create paths in a maze/board from one point to another.
 */
public class MazeUtils {

    /**
     * Private static class that stores one step in a path in the maze.
     */
    private static class Coordinate {

        public Point loc;   // location of the step
        public Coordinate parent;   // location of the previous step

        /**
         * Constructor.
         */
        public Coordinate(Point loc, Coordinate parent) {
            this.loc = loc;
            this.parent = parent;
        }

    }

    /**
     * Generate a list of steps needed to follow in order to achieve the final destination.
     * @param destination Final destination
     * @return list of steps to reach to final destination
     */
    private static LinkedList<Point> createPath(Coordinate destination) {
        LinkedList<Point> path = new LinkedList<>();

        while (destination != null) {
            path.add(destination.loc);
            destination = destination.parent;
        }

        Collections.reverse(path);
        return path;
    }

    /**
     * Check whether a specific location has already been visited.
     * @param visitedList list of visited locations
     * @param location location being checked
     * @return flag indicating whether the location has already been visited
     */
    private static boolean locationVisited(List<Point> visitedList, Point location) {
        for (Point point: visitedList) {
            if (point.x == location.x && point.y == location.y) {
                return true;
            }
        }
        return false;
    }

    /**
     * Search for a path (list of steps) in the maze (board) from one point to another.
     * @param board pacman board (maze)
     * @param startLoc starting location
     * @param endLoc final location
     */
    public static LinkedList<Point> searchPath(int[][] board, Point startLoc, Point endLoc) {
        LinkedList<Coordinate> nextToVisit = new LinkedList<>();
        nextToVisit.add(new Coordinate(startLoc, null));

        List<Point> visitedList = new ArrayList<>();
        Point[] directions = new Point[]{
            new Point(0, 1),
            new Point(1, 0),
            new Point(0, -1),
            new Point(-1, 0),
        };

        while (!nextToVisit.isEmpty()) {
            Coordinate cur = nextToVisit.remove();

            if (cur.loc.x == endLoc.x && cur.loc.y == endLoc.y) {
                return createPath(cur);
            }

            if (cur.loc.x < 0 || cur.loc.x >= board.length || cur.loc.y < 0 || cur.loc.y >= board[0].length || board[cur.loc.x][cur.loc.y] == 1) {
                continue;
            }

            if (locationVisited(visitedList, cur.loc)) {
                continue;
            }

            for (Point direction: directions) {
                Point location = new Point(cur.loc.x + direction.x, cur.loc.y + direction.y);
                nextToVisit.add(new Coordinate(location, cur));
                visitedList.add(cur.loc);
            }

        }

        return new LinkedList<>();
    }

}
