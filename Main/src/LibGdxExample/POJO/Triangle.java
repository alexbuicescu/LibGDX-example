package LibGdxExample.POJO;

/**
 * Created by alexbuicescu on 13.08.2015.
 */
public class Triangle {
    private short[] indices;
    private int currentIndex;

    public Triangle()
    {
    }

    public Triangle(short[] indices)
    {
        setIndices(indices);
    }

    public short[] getIndices() {
        return indices;
    }

    public void setIndices(short[] indices) {
        if(indices.length != 3)
        {
            System.err.println("there must be 3 indices");
            return;
        }
        this.indices = indices;
    }

    public void addIndex(short index)
    {
        if(indices == null)
        {
            indices = new short[3];
        }
        indices[currentIndex++] = index;
    }

    public boolean containsPoint(short index)
    {
        for(short tempIndex : indices)
        {
            if(tempIndex == index)
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object triangle)
    {
        if(triangle != null && triangle instanceof Triangle &&
                indices != null && indices.length == 3 &&
                ((Triangle) triangle).getIndices() != null &&
                ((Triangle) triangle).getIndices().length == 3)
        {
            for(short index1 : indices)
            {
                boolean ok = false;
                for(short index2 : ((Triangle) triangle).getIndices())
                {
                    if(index1 == index2)
                    {
                        ok = true;
                        break;
                    }
                }
                if(!ok)
                {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
