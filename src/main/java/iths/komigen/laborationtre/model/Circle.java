package iths.komigen.laborationtre.model;

import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;

import java.util.Objects;

public class Circle extends Shapes{

    private String name = "Circle";


/*    public Circle(Position position, ShapeSize size, Color color) {
        super(position, size, color);
    }*/


    public Circle(Position position) {
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
        return super.toString();
    }


    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Position getPosition() {
        return super.getPosition();
    }


    public boolean checkArea(double x, double y) {

        boolean answer = false;

        int circleArea = 0;

        if(super.getShapeSize().equals("SMALL")) {
            circleArea = 25;
        }
        if(super.getShapeSize().equals("MEDIUM")) {
            circleArea = 50;
        }
        if(super.getShapeSize().equals("LARGE")) {
            circleArea = 75;
        }


        if(Math.pow((x - super.getPosition().x()), 2) + Math.pow((y - super.getPosition().y()) , 2) <= circleArea * circleArea) {
            System.out.println("YESYESYES");
            answer = true;
        }


        return answer;
    }



}
