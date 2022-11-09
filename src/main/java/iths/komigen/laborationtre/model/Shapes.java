package iths.komigen.laborationtre.model;

import javafx.beans.property.*;
import javafx.scene.paint.Color;

import java.util.Objects;

public abstract class Shapes {
    private String name;

    private Position position;

    private StringProperty shapeSize;

    private Property<Color> color;


    public Shapes(Position position) {
        this.position = position;
        shapeSize = new SimpleStringProperty();
        color = new SimpleObjectProperty<>();
    }

    public Color getColor() {
        return color.getValue();
    }

    public Property<Color> colorProperty() {
        return color;
    }

    public void setColor(Color color) {
        this.color.setValue(color);
    }


    public String getShapeSize() {
        return shapeSize.get();
    }

    public StringProperty shapeSizeProperty() {
        return shapeSize;
    }

    public void setShapeSize(String shapeSize) {
        this.shapeSize.set(shapeSize);
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
    public String toString() {
        return "Shapes{" +
                "position=" + position +
                ", size=" + shapeSize.getValue() +
                ", color=" + color +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shapes shapes = (Shapes) o;
        return Objects.equals(getName(), shapes.getName()) && Objects.equals(getPosition(), shapes.getPosition()) && Objects.equals(getShapeSize(), shapes.getShapeSize()) && Objects.equals(getColor(), shapes.getColor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPosition(), getShapeSize(), getColor());
    }

    public abstract boolean checkArea(double x, double y);


}
