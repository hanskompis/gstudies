package tktl.gstudies.graphicsServices;

import java.util.List;
import tktl.gstudies.graphicalObjects.BoxCoordinatesForLines;
import tktl.gstudies.graphicalObjects.Line;

/**
 * An interface class for line service.
 *
 * @author hkeijone
 */
public interface LineService {

    public void addLineString(int mx, int my, int lx, int ly);

    public List<List<BoxCoordinatesForLines>> getCoords();

    public void setCoords(List<List<BoxCoordinatesForLines>> coords);

    public List<Line> getSumPathData();

    public List getStuds();

    public void setStuds(List studs);
}
