package LibGdxExample;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;

public class Main extends Game {
    private SpriteBatch batch;
    private BitmapFont font;
    PolygonSprite poly;
    PolygonSpriteBatch polyBatch;
    Texture textureSolid;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        initPoly();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.draw(batch, "Hello World", 200, 200);
        batch.end();

        polyBatch.begin();
        poly.draw(polyBatch);
        polyBatch.end();
    }

    @Override
    public void resize(int width, int height) {
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

        float a = 100;
        float b = 100;
        PolygonRegion polyReg = new PolygonRegion(new TextureRegion(textureSolid),
                new float[] {      // Four vertices
                        100, 100,          // Vertex 0         3--2
                        200, 100,          // Vertex 1         | /|
                        200, 200,          // Vertex 2         |/ |
                        100, 200           // Vertex 3         0--1
                }, new short[] {
                0, 1, 2,         // Two triangles using vertex indices.
                0, 3, 2          // Take care of the counter-clockwise direction.
        });
        poly = new PolygonSprite(polyReg);
        poly.setOrigin(a, b);
        polyBatch = new PolygonSpriteBatch();
    }
}