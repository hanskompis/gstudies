package tktl.gstudies.graphicalObjects;

/**
 * A class to store information on (corner)coordinates of a box to be rendered.
 *
 * @author hkeijone
 */
public class BoxCoordinatesForLines {

    private String courseName;
    private int leftX;
    private int leftY;
    private int rightX;
    private int rightY;

    public BoxCoordinatesForLines(String courseName, int leftX, int leftY, int rightX, int rightY) {
        this.courseName = courseName;
        this.leftX = leftX;
        this.leftY = leftY;
        this.rightX = rightX;
        this.rightY = rightY;
    }

    public BoxCoordinatesForLines() {
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getLeftX() {
        return leftX;
    }

    public void setLeftX(int leftX) {
        this.leftX = leftX;
    }

    public int getLeftY() {
        return leftY;
    }

    public void setLeftY(int leftY) {
        this.leftY = leftY;
    }

    public int getRightX() {
        return rightX;
    }

    public void setRightX(int rightX) {
        this.rightX = rightX;
    }

    public int getRightY() {
        return rightY;
    }

    public void setRightY(int rightY) {
        this.rightY = rightY;
    }

    @Override
    public String toString() {
        return "name: " + this.getCourseName() + " left x: " + this.getLeftX() + " left y: "
                + this.getLeftY() + "rite x: " + this.rightX + " rite y: " + this.getRightY() + "\n";
    }
}
