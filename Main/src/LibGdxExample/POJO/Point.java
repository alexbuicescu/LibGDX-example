package LibGdxExample.POJO;

/**
 * Created by alexbuicescu on 12.08.2015.
 */
public class Point {

    private float xCoordinate;
    private float yCoordinate;

    public Point(float xCoordinate, float yCoordinate)
    {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public float getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(float xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public float getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(float yCoordinate) {
        this.yCoordinate = yCoordinate;
    }
}
