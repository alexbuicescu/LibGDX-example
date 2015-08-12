package LibGdxExample.POJO;

import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by alexbuicescu on 12.08.2015.
 */
public class WorldBoundary {
    private static float left = Float.MAX_VALUE;
    private static float top = Float.MIN_VALUE;
    private static float bottom = Float.MAX_VALUE;
    private static float right = Float.MIN_VALUE;
    private static Segment segmentLeft;
    private static Segment segmentTop;
    private static Segment segmentBottom;
    private static Segment segmentRight;

    public static float getLeft() {
        return left;
    }

    public static void setLeft(float left) {
        WorldBoundary.left = left;
    }

    public static float getTop() {
        return top;
    }

    public static void setTop(float top) {
        WorldBoundary.top = top;
    }

    public static float getBottom() {
        return bottom;
    }

    public static void setBottom(float bottom) {
        WorldBoundary.bottom = bottom;
    }

    public static float getRight() {
        return right;
    }

    public static void setRight(float right) {
        WorldBoundary.right = right;
    }

    public static void init()
    {
        float width = right - left;
        float height = top - bottom;

        left -= width / 4;
        top += height / 4;
        bottom -= height / 4;
        right += width / 4;

        segmentLeft = new Segment(new Point(left, bottom), new Point(left, top));
        segmentTop = new Segment(new Point(left, top), new Point(right, top));
        segmentBottom = new Segment(new Point(left, bottom), new Point(right, bottom));
        segmentRight = new Segment(new Point(right, bottom), new Point(right, top));
    }

    public static void render(OrthographicCamera camera)
    {
        segmentLeft.render(camera);
        segmentTop.render(camera);
        segmentBottom.render(camera);
        segmentRight.render(camera);
    }
}
