package iths.komigen.laborationtre.controller;

import iths.komigen.laborationtre.model.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

import static iths.komigen.laborationtre.model.ShapeSize.*;

public class HelloController {

    public ChoiceBox<ShapeSize> sizeButton;
    ObservableList<ShapeSize> shapeSizes = FXCollections.observableArrayList(SMALL, MEDIUM, LARGE);
    public ChoiceBox<String> shapeButton;
    ObservableList<String> shapes = FXCollections.observableArrayList("Circle", "Square");
    public ColorPicker colorButton;
    public Button undoButton;
    public Button selectButton;

    public Canvas canvas;

    public Boolean selected = false;


    ObservableList<Shapes> shapesOnCanvas = FXCollections.observableArrayList();



    public GraphicsContext context;


    public void initialize() {
        sizeButton.setItems(shapeSizes);
        System.out.println(sizeButton.getValue());
        //sizeButton.setValue(SMALL); //optional

        shapeButton.setItems(shapes);
        shapeButton.setValue("Circle"); //optional

        context = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);

        renderShapes();


    }



    public void renderShapes() {
        for (var segment : shapesOnCanvas) {
            fillShape(segment);
            //System.out.println(segment);

        }
    }


    public void selectShape(MouseEvent mouseEvent) {

        selected = true;

    }

    /*if (sizeButton.getValue().equals(SMALL)){
            System.out.println("BLalblabla");
        }*/

    public void canvasClicked(MouseEvent mouseEvent) {




        if (selected == false) {
            makeShape(mouseEvent);
        }



        if (selected == true) {

            new Thread(() -> {
                Platform.runLater(() ->
                        checkTheArea(mouseEvent));
            }).start();



        }


        selected = false;



    }

    public void checkTheArea(MouseEvent mouseEvent) {

        for (int i = shapesOnCanvas.size() - 1; i >= 0 ; i--) {
            shapesOnCanvas.get(i).checkArea(mouseEvent.getX(),mouseEvent.getY());
        }

    }



    private void fillShape(Shapes shapes) {
        if (shapes.getName().equals("Circle")) {
            if (shapes.getSize().equals(SMALL)) {
                fillSmallCircle((Circle) shapes);
            }
            if (shapes.getSize().equals(MEDIUM)) {
                fillMediumCircle((Circle) shapes);
            }
            if (shapes.getSize().equals(LARGE)) {
                fillLargeCircle((Circle) shapes);
            }
        }

        if (shapes.getName().equals("Square")) {
            if (shapes.getSize().equals(SMALL)) {
                fillSmallSquare((Square) shapes);
            }
            if (shapes.getSize().equals(MEDIUM)) {
                fillMediumSquare((Square) shapes);
            }
            if (shapes.getSize().equals(LARGE)) {
                fillLargeSquare((Square) shapes);
            }
        }
    }

    private void fillSmallSquare(Square square) {

        context.setFill(square.getColor());
        context.fillRect(square.getPosition().x() - 25, square.getPosition().y() - 25, 50, 50);

    }

    private void fillMediumSquare(Square square) {
        context.setFill(square.getColor());
        context.fillRect(square.getPosition().x() - 50, square.getPosition().y() - 50, 100, 100);

    }

    private void fillLargeSquare(Square square) {
        context.setFill(square.getColor());
        context.fillRect(square.getPosition().x() - 75, square.getPosition().y() - 75, 150, 150);

    }

    private void fillSmallCircle(Circle circle) {

        context.setFill(circle.getColor());
        context.fillOval(circle.getPosition().x() - 25, circle.getPosition().y() - 25, 50, 50);
    }

    private void fillMediumCircle(Circle circle) {
        context.setFill(circle.getColor());
        context.fillOval(circle.getPosition().x() - 50, circle.getPosition().y() - 50, 100, 100);

    }

    private void fillLargeCircle(Circle circle) {
        context.setFill(circle.getColor());
        context.fillOval(circle.getPosition().x() - 75, circle.getPosition().y() - 75, 150, 150);
    }


    public void checkAction() {

    }


    public void selectIsClicked(MouseEvent mouseEvent) {

        selected = true;
    }

    public void undoIsClicked() {

    }

    public void makeShape(MouseEvent mouseEvent) {

        if((sizeButton.getValue() != null) && (shapeButton.getValue() != null)) {

            if (shapeButton.getValue().equals("Circle")) {
                switch (sizeButton.getValue()) {
                    case SMALL -> makeCircle(mouseEvent.getX(), mouseEvent.getY(), SMALL, colorButton.getValue());
                    case MEDIUM -> makeCircle(mouseEvent.getX(), mouseEvent.getY(), MEDIUM, colorButton.getValue());
                    case LARGE -> makeCircle(mouseEvent.getX(), mouseEvent.getY(), LARGE, colorButton.getValue());
                }
            }
            if (shapeButton.getValue().equals("Square")) {
                switch (sizeButton.getValue()) {
                    case SMALL -> makeSquare(mouseEvent.getX(), mouseEvent.getY(), SMALL, colorButton.getValue());
                    case MEDIUM -> makeSquare(mouseEvent.getX(), mouseEvent.getY(), MEDIUM, colorButton.getValue());
                    case LARGE -> makeSquare(mouseEvent.getX(), mouseEvent.getY(), LARGE, colorButton.getValue());
                }
            }

        }


    }

    public void makeSquare(double x, double y, ShapeSize shapeSize, Color color) {
        Square square = new Square(new Position(x, y), shapeSize, color);
        shapesOnCanvas.add(square);
        fillShape(square);

        System.out.println(square);
    }

    public void makeCircle(double x, double y, ShapeSize shapeSize, Color color) {
        Circle circle = new Circle(new Position(x, y), shapeSize, color);
        shapesOnCanvas.add(circle);
        fillShape(circle);

        System.out.println(shapesOnCanvas.size());

    }




    //TODO: Move everything to ShapesModel so that it have a more clean view
    //TODO: Put shapes in List/Array to be able for undo
    //TODO: Make select option available to press on most recent color(shape) to...
    //TODO: ...be able to change size, color and possible place?
    //TODO: Replace names on files to more suiting ones
}