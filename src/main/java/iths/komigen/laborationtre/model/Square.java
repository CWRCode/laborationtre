package iths.komigen.laborationtre.model;


import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;

public class Square extends Shapes{

    private String name = "Square";


    public Square(Position position) {
        super(position);
    }

    @Override
    public String getShapeSize() {
        return super.getShapeSize();
    }

    @Override
    public StringProperty shapeSizeProperty() {
        return super.shapeSizeProperty();
    }

    @Override
    public void setShapeSize(String shapeSize) {
        super.setShapeSize(shapeSize);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public Color getColor() {
        return super.getColor();
    }

    @Override
    public Property<Color> colorProperty() {
        return super.colorProperty();
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
    }



    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public String toString() {
        return "Square: " + super.toString();
    }

    @Override
    public Position getPosition() {
        return super.getPosition();
    }

    @Override
    public void setPosition(Position position) {
        super.setPosition(position);
    }



    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String getName() {
        return name;
    }

    public boolean checkArea(double x, double y) {

        boolean answer = false;

        int squareArea = 0;

        if(super.getShapeSize().equals("SMALL")) {
            squareArea = 25;
        }
        if(super.getShapeSize().equals("MEDIUM")) {
            squareArea = 50;
        }
        if(super.getShapeSize().equals("LARGE")) {
            squareArea = 75;
        }


        if(((((x >= super.getPosition().x()) && (x <= super.getPosition().x() + squareArea))
                || ((x <= super.getPosition().x()) && (x >= super.getPosition().x() - squareArea)))
                && (((y >= super.getPosition().y()) && (y <= super.getPosition().y() + squareArea))
                || ((y <= super.getPosition().y()) && (y >= super.getPosition().y() - squareArea)))))
        {

            System.out.println("YESYESYES");
            answer = true;
        }


        return answer;
    }



}
