package iths.komigen.laborationtre.model;


import javafx.scene.paint.Color;

public class Square extends Shapes{

    private String name = "Square";

    public Square(Position position, ShapeSize size, Color color) {
        super(position, size, color);
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
    public ShapeSize getSize() {
        return super.getSize();
    }

    @Override
    public void setSize(ShapeSize size) {
        super.setSize(size);
    }

    @Override
    public Color getColor() {
        return super.getColor();
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
    }

    @Override
    public String getName() {
        return name;
    }

    public boolean checkArea(double x, double y) {
        boolean answer = false;
        //medelpunkg (a,b)
        // radie 75


        //(x-a)^2 + (y-b)^2 = r2

        if(Math.pow((x - super.getPosition().x()), 2) + Math.pow((y - super.getPosition().y()) , 2) == 75*75) {
            System.out.println("YESYESYES");
            answer = true;
        }



        return answer;
    }


}
