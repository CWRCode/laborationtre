package iths.komigen.laborationtre.controller;

import iths.komigen.laborationtre.model.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


public class HelloController {

    public ChoiceBox<String> sizeButton;
    ObservableList<String> shapeSizes = FXCollections.observableArrayList("SMALL", "MEDIUM", "LARGE");
    public ChoiceBox<String> shapeButton;
    ObservableList<String> shapes = FXCollections.observableArrayList("Circle", "Square");
    public ColorPicker colorButton;
    public Button undoButton;
    public Button selectButton;
    public Canvas canvas;

    public Boolean selected = false;
    public Shapes shapes2;
    int shapesInt;
    public boolean customizeFigure = false;


    public Shapes lastShape;
    public int listLocationLastShape;

    public  boolean shapeChanged;




    ObservableList<Shapes> shapesOnCanvas = FXCollections.observableArrayList();

    canvasMyAnimation canvasMyAnimation = new canvasMyAnimation() {
        float time;
        @Override
        public void tick(float secondsSinceLastFrame) {
            time += secondsSinceLastFrame;
            if(time < 0.01) {
                return;
            }
            renderShapes();
            time = 0.0f;
        }
    };



    public GraphicsContext context;


    public void initialize() {

        sizeButton.setItems(shapeSizes);
        //sizeButton.setValue(SMALL); //optional

        shapeButton.setItems(shapes);
        //shapeButton.setValue("Circle"); //optional

        context = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);

        canvasMyAnimation.start();

    }


    public void renderShapes() {
        context.setFill(Color.ALICEBLUE);
        context.fillRect(0,0,600,450);


        for (var segment : shapesOnCanvas) {
            fillShape(segment);
        }

    }


    public void selectShape(MouseEvent mouseEvent) {

        if (selected) {

            selected = false;
            customizeFigure = false;
            selectButton.setText("Select");

            if(shapes2 != null) {
                updateShapeAfterChanges(shapes2);
            }
            //colorButton.valueProperty().unbind();
            //sizeButton.valueProperty().unbind();


        }else {
            selectButton.setText("Unselect");
            selected = true;
        }
    }


    public void canvasClicked(MouseEvent mouseEvent) {


        if (!selected) {
            makeShape(mouseEvent);
        }


        if (selected && !customizeFigure) {

            customizeFigure = true;

            new Thread(() -> {
                Platform.runLater(() ->
                        checkTheArea(mouseEvent));
            }).start();

        }

    }

    public void checkTheArea(MouseEvent mouseEvent) {

        shapes2 = shapeForChange(mouseEvent);

        if(shapes2 != null) {
            if(shapes2.getName().equals("Circle")) {
                lastShape = cloneCircle((Circle) shapes2);
            }
            else if (shapes2.getName().equals("Square")) {
                lastShape = cloneSquare((Square) shapes2);
            }

            shapeChanged = true;

        }else {
            selected = false;
            customizeFigure = false;
            selectButton.setText("Select");
        }



        System.out.println(shapes2);

    }

    private Shapes shapeForChange(MouseEvent mouseEvent) {

        for (int i = shapesOnCanvas.size() - 1; i >= 0 ; i--) {

            if (shapesOnCanvas.get(i).checkArea(mouseEvent.getX(), mouseEvent.getY())) {
                shapeButton.setValue(shapesOnCanvas.get(i).getName());
                sizeButton.setValue(shapesOnCanvas.get(i).getShapeSize());
                colorButton.setValue(shapesOnCanvas.get(i).getColor());

                shapesInt = i;
                listLocationLastShape = shapesInt;


                return shapesOnCanvas.get(i);
            }


        }

        System.out.println("Missade en figur");
        return null;
    }


    private void fillShape(Shapes shapes) {

        if (shapes.getName().equals("Circle")) {
            if (shapes.getShapeSize().equals("SMALL")) {
                fillSmallCircle((Circle) shapes);
            }
            if (shapes.getShapeSize().equals("MEDIUM")) {
                fillMediumCircle((Circle) shapes);
            }
            if (shapes.getShapeSize().equals("LARGE")) {
                fillLargeCircle((Circle) shapes);
            }
        }

        if (shapes.getName().equals("Square")) {
            if (shapes.getShapeSize().equals("SMALL")) {
                fillSmallSquare((Square) shapes);
            }
            if (shapes.getShapeSize().equals("MEDIUM")) {
                fillMediumSquare((Square) shapes);
            }
            if (shapes.getShapeSize().equals("LARGE")) {
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
        context.setFill(circle.colorProperty().getValue());
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


    public void makeShape(MouseEvent mouseEvent) {

        if((sizeButton.getValue() != null) && (shapeButton.getValue() != null)) {

            if (shapeButton.getValue().equals("Circle")) {
                makeCircle(mouseEvent.getX(), mouseEvent.getY());
            }
            if (shapeButton.getValue().equals("Square")) {
                makeSquare(mouseEvent.getX(), mouseEvent.getY());
            }
        }
    }

    public void makeSquare(double x, double y) {
        Square square = new Square(new Position(x, y));
        square.setColor(colorButton.getValue());
        square.setShapeSize(sizeButton.getValue());
        //fillShape(square);
        shapesOnCanvas.add(square);

    }

    public void makeCircle(double x, double y) {
        Circle circle = new Circle(new Position(x, y));
        circle.setColor(colorButton.getValue());
        circle.setShapeSize(sizeButton.getValue());
        //fillShape(circle);
        shapesOnCanvas.add(circle);
    }


    public void updateShapeAfterChanges(Shapes shapes) {

        if(shapes.getName().equals("Circle")) {
            shapesOnCanvas.add(shapesInt,cloneCircle((Circle) shapes));

            //---------
            /*lastShape = shapes;
            //lastShape = shapesOnCanvas.get(listLocation + 1); Måste hamna tidigare innan förändring sker
            listLocationLastShape = listLocation;
            shapeChanged = true;*/
            //---------


            shapesOnCanvas.remove(shapesInt + 1);
        }
        if(shapes.getName().equals("Square")) {
            shapesOnCanvas.add(shapesInt,cloneSquare((Square) shapes));

            //---------
            /*lastShape = shapes;
            //lastShape = shapesOnCanvas.get(listLocation + 1);
            listLocationLastShape = listLocation;
            shapeChanged = true;*/
            //---------


            shapesOnCanvas.remove(shapesInt + 1);
        }
    }

    public Circle cloneCircle(Circle circle) {
        Circle cloneCircle = new Circle(new Position(circle.getPosition().x(),circle.getPosition().y()));
        cloneCircle.setColor(circle.getColor());
        cloneCircle.setShapeSize(circle.getShapeSize());

        return cloneCircle;
    }
    public Square cloneSquare(Square square) {
        Square cloneSquare = new Square(new Position(square.getPosition().x(),square.getPosition().y()));
        cloneSquare.setColor(square.getColor());
        cloneSquare.setShapeSize(square.getShapeSize());

        return cloneSquare;
    }












    public void colorPicker(ActionEvent actionEvent) {

        /*if((!customizeFigure) && (shapes2 != null)) {
            colorButton.valueProperty().unbindBidirectional((shapes2.colorProperty()));
        }*/


        if ((customizeFigure) && (shapes2 != null)) {
            shapes2.setColor(colorButton.getValue());
            colorButton.valueProperty().bindBidirectional(shapes2.colorProperty());

            //shapes2.colorProperty().bind(colorButton.valueProperty());
        }


        //shapesOnCanvas.get(shapesOnCanvas.size()-1).setColor(colorButton.getValue());


    }

    public void sizePicker(ActionEvent actionEvent) {


        /*if((!customizeFigure) && (shapes2 != null)) {
            sizeButton.valueProperty().unbindBidirectional(shapes2.shapeSizeProperty());
        }*/

        if ((customizeFigure) && (shapes2 != null)) {
            shapes2.setShapeSize(sizeButton.getValue());
            sizeButton.valueProperty().bindBidirectional(shapes2.shapeSizeProperty());



            //shapes2.colorProperty().bind(colorButton.valueProperty());
        }

    }

    public void undoClicked(MouseEvent mouseEvent) {


        if(shapeChanged) {

            shapesOnCanvas.add(listLocationLastShape,lastShape);
            shapesOnCanvas.remove(listLocationLastShape + 1);
            colorButton.setValue(lastShape.getColor());
            sizeButton.setValue(lastShape.getShapeSize());

            shapeChanged = false;

            //kul grej  sätt 1 eller 0
            //undoButton.setOpacity(1/0);



        } else if (!shapeChanged) {





        }


    }


    //TODO: Move everything to ShapesModel so that it have a more clean view
    //TODO: Put shapes in List/Array to be able for undo
    //TODO: Make select option available to press on most recent color(shape) to...
    //TODO: ...be able to change size, color and possible place?
    //TODO: Replace names on files to more suiting ones
}