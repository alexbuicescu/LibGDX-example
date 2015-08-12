package LibGdxExample.Utils;

import LibGdxExample.POJO.Point;
import LibGdxExample.POJO.Segment;
import com.badlogic.gdx.graphics.OrthographicCamera;

import java.util.ArrayList;

/**
 * Created by alexbuicescu on 12.08.2015.
 */
public class SegmentUtils {

    public static Point segmentsIntersects(Segment segment1, Segment segment2)
    {
        Point intersectionPoint = segmentsIntersectAtEdges(segment1, segment2);

        if(intersectionPoint != null)
        {
            return intersectionPoint;
        }



        return intersectionPoint;
    }

    public static boolean pointsAreTheSame(Point point1, Point point2) {
        if (point1.getxCoordinate() == point2.getxCoordinate() &&
                point1.getyCoordinate() == point2.getyCoordinate()) {
            return true;
        }
        return false;
    }

    private static Point segmentsIntersectAtEdges(Segment segment1, Segment segment2) {

        if (pointsAreTheSame(segment1.getPoint1(), segment2.getPoint1())) {
            return segment1.getPoint1();
        } else if (pointsAreTheSame(segment1.getPoint1(), segment2.getPoint2())) {
            return segment1.getPoint1();
        } else if (pointsAreTheSame(segment1.getPoint2(), segment2.getPoint1())) {
            return segment1.getPoint2();
        } else if (pointsAreTheSame(segment1.getPoint2(), segment2.getPoint2())) {
            return segment1.getPoint2();
        }

        return null;
    }

    public static void renderSegments(ArrayList<Segment> segments, OrthographicCamera camera)
    {
        for(Segment segment : segments)
        {
            segment.render(camera);
        }
    }
}
