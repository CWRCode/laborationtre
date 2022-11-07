package iths.komigen.laborationtre.model;

import javafx.scene.Node;
import javafx.scene.paint.Color;

import java.util.Objects;

public class Circle extends Shapes{

    private String name = "Circle";

    public Circle(Position position, ShapeSize size, Color color) {
        super(position, size, color);
    }

    @Override
    public String toString() {
        return "Circle: " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Circle circle)) return false;
        return Objects.equals(name, circle.name);
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

    @Override
    public Node getStyleableNode() {
        return super.getStyleableNode();
    }



    public boolean checkArea(double x, double y) {
        boolean answer = false;
        //medelpunkg (a,b)
        // radie 75


        //(x-a)^2 + (y-b)^2 = r2

        if(Math.pow((x - super.getPosition().x()), 2) + Math.pow((y - super.getPosition().y()) , 2) <= 75*75) {
            System.out.println("YESYESYES");
            answer = true;
        }



        return answer;
    }
}
