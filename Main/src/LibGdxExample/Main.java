package LibGdxExample;

import LibGdxExample.POJO.Point;
import LibGdxExample.POJO.Polygon;
import LibGdxExample.POJO.Segment;
import LibGdxExample.POJO.WorldBoundary;
import LibGdxExample.Utils.ResourcesUtils;
import LibGdxExample.Utils.SegmentUtils;
import LibGdxExample.Utils.Utils;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Main extends Game {
    private SpriteBatch batch;
    private BitmapFont font;
    PolygonSprite poly;
    PolygonSpriteBatch polyBatch;
    Texture textureSolid;
    private OrthographicCamera cam;
    Vector2 cameraZoom;
    Vector2 targetZoom;

    ArrayList<Polygon> polygons;
    ArrayList<Segment> segments;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        cameraZoom = new Vector2(1, 0);
        targetZoom = new Vector2(1, 0);
        initPoly();
        initCamera();

        polygons = ResourcesUtils.readPolygons();
        Utils.setWorldContainer(polygons);
        segments = Utils.findSegments(polygons);
        tests();
    }

    private void tests()
    {
        Segment segment1 = new Segment(new Point(0, 0), new Point(10, 10));
        Segment segment2 = new Segment(new Point(5, 5), new Point(15, 15));

        Segment segment3 = new Segment(new Point(0, 0), new Point(10, 0));
        Segment segment4 = new Segment(new Point(5, 0), new Point(15, 0));

        Segment segment5 = new Segment(new Point(0, 0), new Point(10, 10));
        Segment segment6 = new Segment(new Point(10, 0), new Point(0, 10));

        Segment segment7 = new Segment(new Point(0, 0), new Point(10, 10));
        Segment segment8 = new Segment(new Point(10, 10), new Point(15, 15));

        Segment segment9 = new Segment(new Point(200.0f, 200.0f), new Point(200.0f, 350.0f));
        Segment segment10 = new Segment(new Point(100.0f, 100.0f), new Point(300.0f, 100.0f));

        Segment segment11 = new Segment(new Point(0.0f, 1.0f), new Point(10.0f, 1.0f));
        Segment segment12 = new Segment(new Point(5.0f, 5.0f), new Point(5.0f, 10.0f));

        Point intersection1 = Polygon.intersection(segment1, segment2);
        Point intersection2 = Polygon.intersection(segment3, segment4);
        Point intersection3 = Polygon.intersection(segment5, segment6);
        Point intersection4 = Polygon.intersection(segment7, segment8);
        Point intersection5 = Polygon.intersection(segment9, segment10);
        Point intersection6 = Polygon.intersection(segment11, segment12);

        System.out.println(intersection1);
        System.out.println(intersection2);
        System.out.println(intersection3);
        System.out.println(intersection4);
        System.out.println(intersection5);
        System.out.println(intersection6);
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    @Override
    public void render() {
        handleInput();
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        polyBatch.setProjectionMatrix(cam.combined);

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.draw(batch, "Hello World", 200, 200);
        batch.end();

//        polyBatch.begin();
//        poly.draw(polyBatch);
//        polyBatch.end();

        for(Polygon polygon : polygons)
        {
            polygon.render(cam);
        }

        WorldBoundary.render(cam);
        SegmentUtils.renderSegments(segments, cam);
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            targetZoom.x += 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            targetZoom.x -= 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            cam.translate(-3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            cam.translate(3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            cam.translate(0, -3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            cam.translate(0, 3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
//            cam.rotate(-rotationSpeed, 0, 0, 1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
//            cam.rotate(rotationSpeed, 0, 0, 1);
        }


//        Vector2 zoomVec = new Vector2(zoom)

        cameraZoom.x = cam.zoom;
        cam.zoom = MathUtils.clamp(cameraZoom.interpolate(targetZoom, 0.25f, Interpolation.exp5).x, 1.f, 100);

        float effectiveViewportWidth = cam.viewportWidth * cam.zoom;
        float effectiveViewportHeight = cam.viewportHeight * cam.zoom;

//        cam.position.x = MathUtils.clamp(cam.position.x, effectiveViewportWidth / 2f, 100 - effectiveViewportWidth / 2f);
//        cam.position.y = MathUtils.clamp(cam.position.y, effectiveViewportHeight / 2f, 100 - effectiveViewportHeight / 2f);
    }

    @Override
    public void resize(int width, int height) {
        cam.viewportWidth = width;
        cam.viewportHeight = height;
        cam.position.set(width / 2, height / 2, 0);
        cam.update();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    void initPoly() {
        // Creating the color filling (but textures would work the same way)
        Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(0xDEADBEFF); // DE is red, AD is green and BE is blue.
        pix.fill();
        textureSolid = new Texture(pix);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        Polygon polygon = new Polygon();
        polygon.addPoint(new Point(100, 100));
        polygon.addPoint(new Point(300, 100));
        polygon.addPoint(new Point(300, 300));
        polygon.addPoint(new Point(200, 200));
        polygon.addPoint(new Point(100, 300));

        float a = w/2;
        float b = h/2;
        PolygonRegion polyReg = new PolygonRegion(new TextureRegion(textureSolid),
                new float[] {      // Four vertices
                        a - 100, b - 100,          // Vertex 0         3--2
                        a + 100, b - 100,          // Vertex 1         | /|
                        a + 100, b + 100,          // Vertex 2         |/ |
                        a - 100, b + 100           // Vertex 3         0--1
                }, new short[] {
                0, 1, 2,         // Two triangles using vertex indices.
                0, 3, 2          // Take care of the counter-clockwise direction.
        });

        PolygonRegion polyReg2 = new PolygonRegion(new TextureRegion(textureSolid),
                polygon.getPointsAsFloatArray(),
                polygon.getTrianglesAsShortArray().toArray());
        poly = new PolygonSprite(polyReg2);
        poly.setOrigin(a, b);
        polyBatch = new PolygonSpriteBatch();
    }

    void initCamera()
    {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        cam = new OrthographicCamera(w, h);
        cam.position.set(w / 2, h / 2, 0);
        cam.update();
    }
}