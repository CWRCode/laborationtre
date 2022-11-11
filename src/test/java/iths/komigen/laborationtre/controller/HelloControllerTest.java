package iths.komigen.laborationtre.controller;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HelloControllerTest {

    Color color;
    String size;



    @Test
    void confirmUndoRemoveNewShapeFromList(){
        HelloController helloController = new HelloController();
        color = Color.AQUA;
        size = "SMALL";

        var expected = helloController.shapesOnCanvas.size();

        helloController.makeSquare(100,100 , color, size);

        helloController.undoShape();

        var actual = helloController.shapesOnCanvas.size();

        assertEquals(expected,actual);


    }

    @Test
    void checkThatSelectFindShape(){

        HelloController helloController = new HelloController();

        color = Color.CYAN;
        size = "LARGE";
        helloController.makeCircle(100,100,color,size);

        var shape = helloController.shapesOnCanvas.get(0);

        var actual = shape.checkArea(100,100);

        assertTrue(actual);


    }




}