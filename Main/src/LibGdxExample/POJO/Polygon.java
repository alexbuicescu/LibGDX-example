package LibGdxExample.POJO;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ShortArray;

import java.util.ArrayList;

/**
 * Created by alexbuicescu on 12.08.2015.
 */
public class Polygon {
    private ArrayList<Point> points;
    private ArrayList<Triangle> triangles;
    private ArrayList<Segment> segments;
    private ShortArray indices;

    PolygonSprite poly;
    PolygonSpriteBatch polyBatch;
    Texture textureSolid;

    public Polygon()
    {
        points = new ArrayList<Point>();
    }

    public Polygon(ArrayList<Point> points, boolean... updateTriangles)
    {
        setPoints(points, updateTriangles);
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public Point[] getPointsAsArray() {
        return (Point[]) points.toArray();
    }

    public float[] getPointsAsFloatArray() {

        float[] pointsArray = new float[points.size() * 2];

        for(int i = 0; i < points.size() * 2; i++)
        {
            pointsArray[i] = points.get(i / 2).getxCoordinate();
            pointsArray[++i] = points.get(i / 2).getyCoordinate();
        }

        return pointsArray;
    }

    public void setPoints(ArrayList<Point> points, boolean... updateTriangles) {
        this.points = points;

        if(updateTriangles.length > 0 && updateTriangles[0])
        {
            initPoly();
        }
    }

    public void addPoint(Point point, boolean... updateTriangles)
    {
        points.add(point);

        if(updateTriangles.length > 0 && updateTriangles[0])
        {
            initPoly();
        }
    }

    public ShortArray getTrianglesAsShortArray()
    {
        if(indices == null)
        {
            indices = LibGdxExample.Utils.GeometryUtils.triangulateIndices(new com.badlogic.gdx.math.Polygon(getPointsAsFloatArray()));
        }
        return indices;
//        GeometryUtils.to
//        short[] triangles = new short[];
    }

    public ArrayList<Triangle> getTrianglesAsPolys()
    {
        ShortArray indices = getTrianglesAsShortArray();
        triangles = new ArrayList<Triangle>();
        for(int i = 0; i < indices.size; i++)
        {
            Triangle currentTriangle = new Triangle();
//            for(int j = i; j < i + 3 && j < indices.size; j++)
//            {
                currentTriangle.addIndex(indices.get(i));
                currentTriangle.addIndex(indices.get(++i));
                currentTriangle.addIndex(indices.get(++i));
//            }
//            if(i%3 == 0 && i != 0)
            {
                triangles.add(currentTriangle);
            }
        }
        return triangles;
//        GeometryUtils.to
//        short[] triangles = new short[];
    }

    public void initPoly()
    {
        // Creating the color filling (but textures would work the same way)
        Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(0xDEADBEFF); // DE is red, AD is green and BE is blue.
        pix.fill();
        textureSolid = new Texture(pix);

        PolygonRegion polyReg = new PolygonRegion(new TextureRegion(textureSolid),
                getPointsAsFloatArray(),
                getTrianglesAsShortArray().toArray());
        poly = new PolygonSprite(polyReg);
        poly.setOrigin(points.get(0).getxCoordinate(), points.get(0).getyCoordinate());
        polyBatch = new PolygonSpriteBatch();

    }

    public void render(Camera camera)
    {
        polyBatch.setProjectionMatrix(camera.combined);
        polyBatch.begin();
        poly.draw(polyBatch);
        polyBatch.end();
    }

    public static Point colinearIntersection(Segment segment1, Segment segment2, int orientation) {
        if (segment1.getPoint1().getxCoordinate() == segment2.getPoint1().getxCoordinate() &&
                segment1.getPoint1().getxCoordinate() == segment2.getPoint2().getxCoordinate()) {

            if (orientation == -1) {

                if (segment2.getPoint1().getyCoordinate() > segment2.getPoint2().getyCoordinate()) {
                    return new Point(segment2.getPoint2().getxCoordinate(), segment2.getPoint2().getyCoordinate());
                }
                return new Point(segment2.getPoint1().getxCoordinate(), segment2.getPoint1().getyCoordinate());
            } else {
                if (segment2.getPoint1().getyCoordinate() < segment2.getPoint2().getyCoordinate()) {
                    return new Point(segment2.getPoint2().getxCoordinate(), segment2.getPoint2().getyCoordinate());
                }
                return new Point(segment2.getPoint1().getxCoordinate(), segment2.getPoint1().getyCoordinate());
            }
        } else if (segment1.getPoint1().getxCoordinate() == segment2.getPoint1().getxCoordinate()) {
            return new Point(segment2.getPoint1().getxCoordinate(), segment2.getPoint1().getyCoordinate());
        } else if (segment1.getPoint1().getxCoordinate() == segment2.getPoint2().getxCoordinate()) {
            return new Point(segment2.getPoint1().getxCoordinate(), segment2.getPoint2().getyCoordinate());
        }

        return null;
    }

    public static Point intersection(Segment segment1, Segment segment2)
    {
        float d = (segment1.getPoint1().getxCoordinate() - segment1.getPoint2().getxCoordinate()) *
                (segment2.getPoint1().getyCoordinate() - segment2.getPoint2().getyCoordinate()) -
                (segment1.getPoint1().getyCoordinate() - segment1.getPoint2().getyCoordinate()) *
                (segment2.getPoint1().getxCoordinate() - segment2.getPoint2().getxCoordinate());

        if(d == 0)
        {
            return null;
        }

        float xi = ((segment2.getPoint1().getxCoordinate() - segment2.getPoint2().getxCoordinate()) *
                    (segment1.getPoint1().getxCoordinate() * segment1.getPoint2().getyCoordinate() -
                    segment1.getPoint1().getyCoordinate() * segment1.getPoint2().getxCoordinate()) -
                    (segment1.getPoint1().getxCoordinate() - segment1.getPoint2().getxCoordinate()) *
                    (segment2.getPoint1().getxCoordinate() * segment2.getPoint2().getyCoordinate() -
                    segment2.getPoint1().getyCoordinate() * segment2.getPoint2().getxCoordinate())) / d;

        float yi = ((segment2.getPoint1().getyCoordinate() - segment2.getPoint2().getyCoordinate()) *
                    (segment1.getPoint1().getxCoordinate() * segment1.getPoint2().getyCoordinate() -
                    segment1.getPoint1().getyCoordinate() * segment1.getPoint2().getxCoordinate()) -
                    (segment1.getPoint1().getyCoordinate() - segment1.getPoint2().getyCoordinate()) *
                    (segment2.getPoint1().getxCoordinate() * segment2.getPoint2().getyCoordinate() -
                    segment2.getPoint1().getyCoordinate() * segment2.getPoint2().getxCoordinate())) / d;

        if(xi < Math.min(segment1.getPoint1().getxCoordinate(), segment1.getPoint2().getxCoordinate()) ||
                xi > Math.max(segment1.getPoint1().getxCoordinate(), segment1.getPoint2().getxCoordinate()))
        {
            return null;
        }

        if(xi < Math.min(segment2.getPoint1().getxCoordinate(), segment2.getPoint2().getxCoordinate()) ||
                xi > Math.max(segment2.getPoint1().getxCoordinate(), segment2.getPoint2().getxCoordinate()))
        {
            return null;
        }

        if(yi < Math.min(segment1.getPoint1().getyCoordinate(), segment1.getPoint2().getyCoordinate()) ||
                yi > Math.max(segment1.getPoint1().getyCoordinate(), segment1.getPoint2().getyCoordinate()))
        {
            return null;
        }

        if(yi < Math.min(segment2.getPoint1().getyCoordinate(), segment2.getPoint2().getyCoordinate()) ||
                yi > Math.max(segment2.getPoint1().getyCoordinate(), segment2.getPoint2().getyCoordinate()))
        {
            return null;
        }

        return new Point(xi, yi);
    }

    private ArrayList<Segment> getSegments()
    {
        if(segments == null)
        {
            segments = new ArrayList<Segment>();
            for(int i = 0; i < points.size(); i++)
            {
                segments.add(new Segment(points.get(i), points.get((i + 1)%points.size())));
            }
        }
        return segments;
    }

    public boolean segmentIsInside(Point polyPoint, Point otherPoint)
    {
        Segment segment = new Segment(polyPoint, otherPoint);

        Segment intersectionSegment = null;
        Point intersectionPoint = null;
        segments = getSegments();

        intersectionSegment = doesSegmentIntersectSidesOfPolygon(segments, segment);

        if(intersectionSegment != null)
        {
            System.err.println("1: " + polyPoint.getIndex() + " " + intersectionSegment.getPoint1().getIndex() + " " + intersectionSegment.getPoint2().getIndex());
            System.err.println("2: " + polyPoint.getxCoordinate() + " " + polyPoint.getyCoordinate() + ";" +
                    otherPoint.getxCoordinate() + " " + otherPoint.getyCoordinate() + ";" +
                    intersectionSegment.getPoint1().getxCoordinate() + " " + intersectionSegment.getPoint1().getyCoordinate() + ";" +
                    intersectionSegment.getPoint2().getxCoordinate() + " " + intersectionSegment.getPoint2().getyCoordinate());
            if(doesSegmentIntersectSidesOfPolygon(segments, new Segment(polyPoint, intersectionSegment.getPoint1())) == null &&
                    doesSegmentIntersectSidesOfPolygon(segments, new Segment(polyPoint, intersectionSegment.getPoint2())) == null)
            {
                System.err.println("in");
                return true;
            }
            else
            {
                if(doesSegmentIntersectSidesOfPolygon(segments, new Segment(polyPoint, intersectionSegment.getPoint1())) != null) {
                    System.err.println(new Segment(polyPoint, intersectionSegment.getPoint1()).toString() + ":::" + doesSegmentIntersectSidesOfPolygon(segments, new Segment(polyPoint, intersectionSegment.getPoint1())).toString());
                }
                if(doesSegmentIntersectSidesOfPolygon(segments, new Segment(polyPoint, intersectionSegment.getPoint2())) != null) {
                    System.err.println(new Segment(polyPoint, intersectionSegment.getPoint2()).toString() + ":::" + doesSegmentIntersectSidesOfPolygon(segments, new Segment(polyPoint, intersectionSegment.getPoint2())).toString());
                }
            }
//            if(getTrianglesAsPolys().contains(
//                    new Triangle(
//                            new short[] {
//                                    polyPoint.getIndex(),
//                                    intersectionSegment.getPoint1().getIndex(),
//                                    intersectionSegment.getPoint2().getIndex()})))
//            {
//                System.err.println("in");
//                return true;
//            }
        }else{

//            System.err.println("3: " + polyPoint.getIndex() + " " + intersectionSegment.getPoint1().getIndex() + " " + intersectionSegment.getPoint2().getIndex());
        }
        return false;
    }

    private Segment doesSegmentIntersectSidesOfPolygon(ArrayList<Segment> segments, Segment segment)
    {
        for(Segment tempSegment : segments)
        {
            /*
            (x1 == x2 && y1 == y2) || (x1 == x3 && y1 == y3)
             */
            Point intersectionPoint = intersection(tempSegment, segment);
            if(intersectionPoint != null)
            {
                if((intersectionPoint.getxCoordinate() != segment.getPoint1().getxCoordinate() ||
                        intersectionPoint.getyCoordinate() != segment.getPoint1().getyCoordinate()) &&
                        (intersectionPoint.getxCoordinate() != segment.getPoint2().getxCoordinate() ||
                                intersectionPoint.getyCoordinate() != segment.getPoint2().getyCoordinate()))
                {
                    System.err.println("lol: " + intersectionPoint.toString() + "|||" + tempSegment.toString() + "|||" + segment.toString());
                    return tempSegment;
                }
            }
        }
        return null;
    }
}
