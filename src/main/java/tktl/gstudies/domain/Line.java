
package tktl.gstudies.domain;

public class Line {
    private String pathString;
    private int weight;
    private int leftX;
    private int leftY;
    private int rightX;
    private int rightY;
    private Text weightText;

    public Line(int leftX, int leftY, int rightX, int rightY) {
        this.weight = 1;
        this.leftX = leftX;
        this.leftY = leftY;
        this.rightX = rightX;
        this.rightY = rightY;
        this.generatePathString();
        this.weightText = new Text(leftX+5, leftY, Integer.toString(weight));
    }
 
    private void generatePathString(){
        StringBuilder sb = new StringBuilder();
        sb.append("M");
        sb.append(this.leftX);
        sb.append(",");
        sb.append(this.leftY);
        sb.append("L");
        sb.append(this.rightX);
        sb.append(",");
        sb.append(this.rightY);
        this.pathString = sb.toString();
    }


    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
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

    public String getPathString() {
        return pathString;
    }

    public void setPathString(String pathString) {
        this.pathString = pathString;
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

    public Text getWeightText() {
        return weightText;
    }

    public void setWeightText(Text weightText) {
        this.weightText = weightText;
    }
    
    
}
