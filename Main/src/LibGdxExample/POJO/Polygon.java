package LibGdxExample.POJO;

import com.badlogic.gdx.Gdx;
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

    public ShortArray getTriangles()
    {
        if(indices == null)
        {
            indices = LibGdxExample.Utils.GeometryUtils.triangulateIndices(new com.badlogic.gdx.math.Polygon(getPointsAsFloatArray()));
        }
        return indices;
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
                getTriangles().toArray());
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
}
