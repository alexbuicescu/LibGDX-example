package LibGdxExample.POJO;

/**
 * Created by alexbuicescu on 12.08.2015.
 */
public class Point {

    private float xCoordinate;
    private float yCoordinate;
    private boolean checked;
    private short index;

    public Point(float xCoordinate, float yCoordinate)
    {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }
    public Point(float xCoordinate, float yCoordinate, short index)
    {
        this(xCoordinate, yCoordinate);
        this.index = index;
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

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public short getIndex() {
        return index;
    }

    public void setIndex(short index) {
        this.index = index;
    }

    @Override
    public String toString()
    {
        return xCoordinate + " " + yCoordinate;
    }
}
