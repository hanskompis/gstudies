
package tktl.gstudies.graphicalObjects;

public class Text extends AbstractGraphicalObject{
    private int x;
    private int y;
    private String text;

    public Text(int x, int y, String text) {
        this.type = "text";
        this.x = x;
        this.y = y;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
    @Override
    public String toString(){
        return "x:"+this.getX()+" y:"+this.getY()+" text: "+this.getText()+" type: "+this.getType();
    }
}
