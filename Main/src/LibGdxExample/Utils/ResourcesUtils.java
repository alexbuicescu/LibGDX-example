package LibGdxExample.Utils;

import LibGdxExample.POJO.Point;
import LibGdxExample.POJO.Polygon;

import java.util.ArrayList;

/**
 * Created by alexbuicescu on 12.08.2015.
 */
public class ResourcesUtils {

    private static final String POLYGONS_PATH = "resources/polygons.txt";

    public static ArrayList<Polygon> readPolygons()
    {
        String polygonsFile = FileUtils.readFile(POLYGONS_PATH);
        ArrayList<Polygon> polygonArrayList = new ArrayList<Polygon>();

        if(polygonsFile != null) {
            String[] polygonsString = polygonsFile.split("\n\n");

            for(String polygonString : polygonsString)
            {
                String[] polygonVertices = polygonString.split("\n");
                ArrayList<Point> polygonVerticesArrayList = new ArrayList<Point>();

                for(String polygonVertexString : polygonVertices) {
                    String[] polygonVertex = polygonVertexString.split(" ");
                    polygonVerticesArrayList.add(
                            new Point(
                                    Float.parseFloat(polygonVertex[0]),
                                    Float.parseFloat(polygonVertex[1])
                            )
                    );
                }
                polygonArrayList.add(new Polygon(polygonVerticesArrayList, true));
            }
        }

        return polygonArrayList;
    }
}
