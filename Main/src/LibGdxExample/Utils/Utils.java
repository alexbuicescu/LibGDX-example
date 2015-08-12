package LibGdxExample.Utils;

import LibGdxExample.POJO.Point;
import LibGdxExample.POJO.Polygon;
import LibGdxExample.POJO.WorldBoundary;

import java.util.ArrayList;

/**
 * Created by alexbuicescu on 12.08.2015.
 */
public class Utils {

    public static void setWorldContainer(ArrayList<Polygon> polygons) {
        for (Polygon polygon : polygons) {
            updateWorldContainer(polygon.getPoints());
        }
        WorldBoundary.init();
    }

    private static void updateWorldContainer(ArrayList<Point> points) {

        for (Point point : points) {
            WorldBoundary.setLeft(Math.min(point.getxCoordinate(), WorldBoundary.getLeft()));
            WorldBoundary.setRight(Math.max(point.getxCoordinate(), WorldBoundary.getRight()));
            WorldBoundary.setTop(Math.max(point.getyCoordinate(), WorldBoundary.getTop()));
            WorldBoundary.setBottom(Math.min(point.getyCoordinate(), WorldBoundary.getBottom()));
        }
    }
}
