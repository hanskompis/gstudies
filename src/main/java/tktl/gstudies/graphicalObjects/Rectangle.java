
package tktl.gstudies.graphicalObjects;

import tktl.gstudies.graphicalObjects.AbstractGraphicalObject;

public class Rectangle extends AbstractGraphicalObject{
    private int x;
    private int y;
    private int width;
    private int height;
    private int r;

    public Rectangle(String type, int x, int y, int width, int height, int r) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.r = r;
    }

    public Rectangle() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    
}
