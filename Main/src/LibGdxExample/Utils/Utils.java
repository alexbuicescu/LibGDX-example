package LibGdxExample.Utils;

import LibGdxExample.POJO.Point;
import LibGdxExample.POJO.Polygon;
import LibGdxExample.POJO.Segment;
import LibGdxExample.POJO.WorldBoundary;

import java.util.ArrayList;

/**
 * Created by alexbuicescu on 12.08.2015.
 */
public class Utils {

    private static int lastOrientation = 0;


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

    public static ArrayList<Segment> findSegments(ArrayList<Polygon> polygons)
    {
        ArrayList<Segment> segments = new ArrayList<Segment>();
//        polygons = new ArrayList<Polygon>();
        for(Polygon polygon : polygons)
        {
            int i = 0;
            Integer nrOfCheckedPoints = 0;
            while(nrOfCheckedPoints != polygon.getPoints().size()) {

                i=i%polygon.getPoints().size();

                Point point = polygon.getPoints().get(i);
//                System.out.println(nrOfCheckedPoints);

                if(!point.isChecked()) {
                    Segment segmentTop = new Segment(
                            point,
                            new Point(point.getxCoordinate(), WorldBoundary.getTop())
                    );

                    Segment segmentBottom = new Segment(
                            point,
                            new Point(point.getxCoordinate(), WorldBoundary.getBottom())
                    );

                    //TODO check where the 2 segments intersect with other polygons and update them

                    Point point1 = polygon.getPoints().get((i - 1 + polygon.getPoints().size()) % polygon.getPoints().size());
                    Point point2 = polygon.getPoints().get((i + 1) % polygon.getPoints().size());

                    //add the segments only if they exist
                    if (!SegmentUtils.pointsAreTheSame(segmentTop.getPoint1(), segmentTop.getPoint2()) &&
                            canTopSegmentExist(point, point1, point2)) {
                        segments.add(segmentTop);
                    }

                    if (!SegmentUtils.pointsAreTheSame(segmentBottom.getPoint1(), segmentBottom.getPoint2()) &&
                            canBottomSegmentExist(point, point1, point2)) {
                        segments.add(segmentBottom);
                    }

                    if(point.isChecked())
                    {
                        nrOfCheckedPoints++;
                    }
                }
                i++;
            }
        }
        return segments;
    }

//    private static boolean canTopSegmentExist(Point point1, Point point2, Point point3) {
//
//        if (orientation(point1, point2, point3) == 1) {
//            lastOrientation = 1;
//        } else if (orientation(point1, point2, point3) == -1) {
//            lastOrientation = -1;
//        }
//
//        if (lastOrientation == -1 &&
//                (point1.getyCoordinate() < point2.getyCoordinate() ||
//                        point1.getyCoordinate() < point3.getyCoordinate())) {
//            point1.setChecked(true);
//            return true;
//        } else if (lastOrientation == 1 &&
//                (point1.getyCoordinate() > point2.getyCoordinate() ||
//                        point1.getyCoordinate() > point3.getyCoordinate())) {
//            point1.setChecked(true);
//            return true;
//        }
//        return false;
//    }

    private static boolean canBottomSegmentExist(Point point1, Point point2, Point point3) {//}, int orientation) {
        Point equalPoint = null;

        if (point1.getxCoordinate() == point2.getxCoordinate()) {
            equalPoint = point2;
        } else if (point1.getxCoordinate() == point3.getxCoordinate()) {
            equalPoint = point3;
        }

        if (equalPoint != null) {
            point1.setChecked(true);
            if (point1.getyCoordinate() < equalPoint.getyCoordinate() &&
                    point1.getyCoordinate() > WorldBoundary.getBottom()) {
                return true;
            }
            return false;
        }

        point1.setChecked(true);
        return false;//polygonVerticesValid(point1, point2, point3, -1);
    }

    private static boolean canTopSegmentExist(Point point1, Point point2, Point point3) {//}, int orientation) {
        Point equalPoint = null;

        if (point1.getxCoordinate() == point2.getxCoordinate()) {
            equalPoint = point2;
        } else if (point1.getxCoordinate() == point3.getxCoordinate()) {
            equalPoint = point3;
        }

        if (equalPoint != null) {
            point1.setChecked(true);
            if (point1.getyCoordinate() > equalPoint.getyCoordinate() &&
                    point1.getyCoordinate() < WorldBoundary.getTop()) {
                return true;
            }
            return false;
        }

        point1.setChecked(true);
        return false;//polygonVerticesValid(point1, point2, point3, 1);
    }

    private static boolean worldVerticesValid(Point point1, Point point2, Point point3) {//}, int orientation) {

        if (orientation(point1, point2, point3) == 0) {
//            if (orientation == 1 && ) {
                return true;
//            }
        }
        return false;
    }

    /**
     *
     * @param point1
     * @param point2
     * @param point3
     * @return true if
     */
    private static boolean polygonVerticesValid(Point point1, Point point2, Point point3, int orientation)
    {
        if (orientation(point1, point2, point3) == 1) {
            lastOrientation = 1;
        } else if (orientation(point1, point2, point3) == -1) {
            lastOrientation = -1;
        }

        System.out.println(point1.getxCoordinate() + " " + point1.getyCoordinate() + ";" +
                point2.getxCoordinate() + " " + point2.getyCoordinate() + ";" +
                point3.getxCoordinate() + " " + point3.getyCoordinate() + "; " + lastOrientation + " " + orientation);

        if (lastOrientation == -1 && 1 == orientation) {// &&
//                (point1.getyCoordinate() > point2.getyCoordinate() ||
//                        point1.getyCoordinate() > point3.getyCoordinate())) {
//            System.out.println(point1.getyCoordinate() + " " + point2.getyCoordinate() + " " + point3.getyCoordinate());
            point1.setChecked(true);
            return true;
        } else if (lastOrientation == 1 && -1 == orientation) {// &&
//                (point1.getyCoordinate() < point2.getyCoordinate() ||
//                        point1.getyCoordinate() < point3.getyCoordinate())) {
//            System.out.println(point1.getyCoordinate() + " " + point2.getyCoordinate() + " " + point3.getyCoordinate());
            point1.setChecked(true);
            return true;
        }
        return false;
    }

    /**
     * Given three colinear points point1, point2, point3, the function checks if point1 lies on line segment [point2-point3]
     * @param point1
     * @param point2
     * @param point3
     * @return
     */
    private boolean onSegment(Point point1, Point point2, Point point3) {
        if (point2.getxCoordinate() <= Math.max(point1.getxCoordinate(), point3.getxCoordinate()) &&
                point2.getxCoordinate() >= Math.min(point1.getxCoordinate(), point3.getxCoordinate()) &&
                point2.getyCoordinate() <= Math.max(point1.getyCoordinate(), point3.getyCoordinate()) &&
                point2.getyCoordinate() >= Math.min(point1.getyCoordinate(), point3.getyCoordinate()))
            return true;

        return false;
    }

    /**
     * To find orientation of ordered triplet (point1, point2, point3).
     * @param point1
     * @param point2
     * @param point3
     * @return 0 if the 3 points are colinear; 1 if the points are clockwise; -1 if the points are counterclockwise
     */
    private static int orientation(Point point1, Point point2, Point point3)
    {
        int val = (int) ((point2.getyCoordinate() - point1.getyCoordinate()) * (point3.getxCoordinate() - point2.getxCoordinate()) -
                        (point2.getxCoordinate() - point1.getxCoordinate()) * (point3.getyCoordinate() - point2.getyCoordinate()));

        if (val == 0) {
            return 0;  // colinear
        }

        return (val > 0)? 1: -1; // clock or counterclock wise
    }
}
