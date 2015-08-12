package LibGdxExample.POJO;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by alexbuicescu on 12.08.2015.
 */
public class Segment {

    private Point point1;
    private Point point2;

    private ShapeRenderer shapeRenderer;

    public Segment(Point point1, Point point2)
    {
        this.point1 = point1;
        this.point2 = point2;

        initShapeRenderer();
    }

    public Point getPoint1() {
        return point1;
    }

    public void setPoint1(Point point1) {
        this.point1 = point1;
    }

    public Point getPoint2() {
        return point2;
    }

    public void setPoint2(Point point2) {
        this.point2 = point2;
    }

    private void initShapeRenderer()
    {
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setColor(Color.BLACK);
    }

    public void render(OrthographicCamera camera)
    {
        Gdx.gl.glLineWidth(2);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.line(
                (float) getPoint1().getxCoordinate(),
                (float) getPoint1().getyCoordinate(),
                (float) getPoint2().getxCoordinate(),
                (float) getPoint2().getyCoordinate());
        shapeRenderer.end();
    }

}
