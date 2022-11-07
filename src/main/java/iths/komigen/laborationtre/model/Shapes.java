package iths.komigen.laborationtre.model;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.util.Objects;

import static iths.komigen.laborationtre.model.ShapeSize.SMALL;

public abstract class Shapes extends Shape {
    @Override
    public Node getStyleableNode() {
        return super.getStyleableNode();
    }

    private String name;

    private Position position;

    private ShapeSize size;

    private Color color;

    public Shapes(Position position, ShapeSize size, Color color) {
        this.position = position;
        this.size = size;
        this.color = color;
    }

    @Override
    public String toString() {
        return position + ", size= " + size + ", color= " + color;
    }


    public ShapeSize getSize() {
        return size;
    }

    public void setSize(ShapeSize size) {
        this.size = size;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shapes shapes)) return false;
        return Objects.equals(name, shapes.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }


    public boolean checkArea(double x, double y) {
        boolean answer = false;




        return answer;
    }

}
