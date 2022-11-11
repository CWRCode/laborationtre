package iths.komigen.laborationtre.controller;

import iths.komigen.laborationtre.model.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class HelloController {

    public ChoiceBox<String> sizeButton;
    public Button saveButton;
    ObservableList<String> shapeSizes = FXCollections.observableArrayList("SMALL", "MEDIUM", "LARGE");
    public ChoiceBox<String> shapeButton;
    ObservableList<String> shapes = FXCollections.observableArrayList("Circle", "Square");
    public ColorPicker colorButton;
    public Button undoButton;
    public Button selectButton;
    public Canvas canvas;

    ObservableList<Shapes> shapesOnCanvas = FXCollections.observableArrayList();

    public Boolean selected = false;
    public Shapes lastCustomizedShape;
    int shapesInt;
    public boolean customizeFigure = false;
    public Shapes lastCreatedShape;
    public int listLocationLastShape;
    public  boolean shapeChanged;
    public boolean shapeCreated;
    public Stage stage;
    public GraphicsContext context;


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





    public void initialize() {

        sizeButton.setItems(shapeSizes);
        sizeButton.setValue("SMALL"); //optional

        shapeButton.setItems(shapes);
        shapeButton.setValue("Circle"); //optional


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





    public void saveFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save as");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SVG","*.svg"));

        File filePath = fileChooser.showSaveDialog(stage);

        if(filePath != null) {
            convertingImages(filePath.toPath());
        }

    }

    public void convertingImages(Path file){
        StringBuffer outPut = new StringBuffer();

        outPut.append("<svg width=\"600\" height=\"500\" xmlns=\"http://www.w3.org/2000/svg\">");

        for (var s : shapesOnCanvas) {

            if(s.getName().equals("Circle")) {
                outPut.append("<circle cx=\"" + s.getPosition().x() +
                        "\" cy=\"" + s.getPosition().y() +
                        "\" r=\"" + s.sizeToInt() +
                        "\" fill=\"#" +
                        s.getColor().toString().substring(2,8) +
                        "\" />");

            }
            if (s.getName().equals("Square")) {
                outPut.append("<rect x=\"" + (s.getPosition().x() - s.sizeToInt()) +
                        "\" y=\"" + (s.getPosition().y() - s.sizeToInt()) +
                        "\" width=\"" + (s.sizeToInt() * 2) +
                        "\" height=\"" + (s.sizeToInt() * 2) +
                        "\" fill=\"#" +
                        s.getColor().toString().substring(2,8) +
                        "\" />");
            }



        }
        outPut.append("</svg>");

        try {
            Files.writeString(file,outPut.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public void selectShape(MouseEvent mouseEvent) {


        if (selected) {

            selected = false;
            customizeFigure = false;
            selectButton.setText("Select");


            if(lastCustomizedShape != null) {
                updateShapeAfterChanges(lastCustomizedShape);
            }


        }else {
            selectButton.setText("Unselect");
            selected = true;
        }
    }


    public void canvasClicked(MouseEvent mouseEvent) {


        double valueX = mouseEvent.getX();
        double valueY = mouseEvent.getY();


        if (!selected) {
            Color color = colorButton.getValue();
            String size = sizeButton.getValue();
            makeShape(valueX,valueY,color,size);
        }


        if (selected && !customizeFigure) {

            customizeFigure = true;

            new Thread(() -> {
                Platform.runLater(() ->
                        checkTheArea(valueX, valueY));
            }).start();

        }

    }

    public void checkTheArea(double x, double y) {

        lastCustomizedShape = shapeForChange(x, y);

        if(lastCustomizedShape != null) {
            if(lastCustomizedShape.getName().equals("Circle")) {
                lastCreatedShape = cloneCircle((Circle) lastCustomizedShape);
            }
            else if (lastCustomizedShape.getName().equals("Square")) {
                lastCreatedShape = cloneSquare((Square) lastCustomizedShape);
            }

            shapeChanged = true;
            shapeCreated = false;

        }else {
            selected = false;
            customizeFigure = false;
            selectButton.setText("Select");
        }

    }

    private Shapes shapeForChange(double x, double y) {

        for (int i = shapesOnCanvas.size() - 1; i >= 0 ; i--) {

            if (shapesOnCanvas.get(i).checkArea(x, y)) {
                shapeButton.setValue(shapesOnCanvas.get(i).getName());
                sizeButton.setValue(shapesOnCanvas.get(i).getShapeSize());
                colorButton.setValue(shapesOnCanvas.get(i).getColor());

                shapesInt = i;
                listLocationLastShape = shapesInt;


                return shapesOnCanvas.get(i);
            }

        }

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


    public void makeShape(double x, double y, Color color, String size) {

        if((sizeButton.getValue() != null) && (shapeButton.getValue() != null)) {

            if (shapeButton.getValue().equals("Circle")) {
                makeCircle(x, y, color, size);
            }
            if (shapeButton.getValue().equals("Square")) {
                makeSquare(x, y, color, size);
            }

        }
    }

    public void makeSquare(double x, double y, Color color, String size) {
        Square square = new Square(new Position(x, y));
        square.setColor(color);
        square.setShapeSize(size);

        shapesOnCanvas.add(square);
        lastCreatedShape = cloneSquare(square);

        listLocationLastShape = shapesOnCanvas.size() - 1;
        shapeCreated = true;
        shapeChanged = false;
    }

    public void makeCircle(double x, double y, Color color, String size) {
        Circle circle = new Circle(new Position(x, y));
        circle.setColor(color);
        circle.setShapeSize(size);

        shapesOnCanvas.add(circle);
        lastCreatedShape = cloneCircle(circle);


        listLocationLastShape = shapesOnCanvas.size() - 1;
        shapeCreated = true;
        shapeChanged = false;

    }


    public void updateShapeAfterChanges(Shapes shapes) {

        if(shapes.getName().equals("Circle")) {
            shapesOnCanvas.add(shapesInt,cloneCircle((Circle) shapes));
            shapesOnCanvas.remove(shapesInt + 1);
        }
        if(shapes.getName().equals("Square")) {
            shapesOnCanvas.add(shapesInt,cloneSquare((Square) shapes));
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

        if ((customizeFigure) && (lastCustomizedShape != null)) {
            lastCustomizedShape.setColor(colorButton.getValue());
            colorButton.valueProperty().bindBidirectional(lastCustomizedShape.colorProperty());
        }
    }

    public void sizePicker(ActionEvent actionEvent) {

        if ((customizeFigure) && (lastCustomizedShape != null)) {
            lastCustomizedShape.setShapeSize(sizeButton.getValue());
            sizeButton.valueProperty().bindBidirectional(lastCustomizedShape.shapeSizeProperty());

        }

    }




    public void undoClicked(MouseEvent mouseEvent) {

        undoShape();
    }



    public void undoShape() {

        if(!selected) {
            undoChangedShape();
            undoNewShape();
        }
    }

    public void undoChangedShape() {

        if(shapeChanged) {

            shapesOnCanvas.add(listLocationLastShape, lastCreatedShape);
            shapesOnCanvas.remove(listLocationLastShape + 1);
            colorButton.setValue(lastCreatedShape.getColor());
            sizeButton.setValue(lastCreatedShape.getShapeSize());

            shapeChanged = false;
        }
    }

    public void undoNewShape() {
        if (shapeCreated) {

            shapesOnCanvas.remove(listLocationLastShape);
            shapeCreated= false;

        }
    }

    public void saveProgram(MouseEvent mouseEvent) {

        if(shapesOnCanvas.size() > 0) {
            saveFile();
        }
    }


    //TODO: Move everything to ShapesModel so that it have a more clean view
    //TODO: Put shapes in List/Array to be able for undo
    //TODO: Make select option available to press on most recent color(shape) to...
    //TODO: ...be able to change size, color and possible place?
    //TODO: Replace names on files to more suiting ones
}