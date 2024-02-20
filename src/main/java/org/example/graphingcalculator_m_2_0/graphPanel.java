package org.example.graphingcalculator_m_2_0;

import com.ezylang.evalex.EvaluationException;
import com.ezylang.evalex.data.EvaluationValue;
import com.ezylang.evalex.parser.ParseException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
//import com.ezylang.evalex.Expression;
import com.ezylang.evalex.Expression;

/**
 * updated by timurguler27 on 2/12/2024.
 */
public class graphPanel extends Stage
{
    //sets the boundaries for the window
    private int xMax,xMin,yMax,yMin;
    //sets the accuracy of the graph; a lower increment yields a more solid graph but takes more processing power .007 is recommended
    private double increment;
    //initializes the equation that will include x as input by the user
    public String equationAsString = "";
    //converts the equation with x to the current graphPoint value to be plotted
    public String equationWithX = "";
    //holds the calculations data for the data window
    public String data = "";
    //zoom; larger zoom value produces a more zoomed image
    private double zoom =12.5;//25.0;
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public graphPanel(int xMax, int xMin, int yMax, int yMin, double increment,String equationAsString)
    {
        //this method recieves the variables passed in the main window of the application
        this.xMax = xMax;
        this.xMin = xMin;
        this.yMax = yMax;
        this.yMin = yMin;
        this.increment = increment;
        this.equationAsString = equationAsString;
        //initializes the graph
        initializeElements(this);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void initializeElements(Stage graphPanel)
    {
        //this initializes the elements in the panel
        StackPane graphPane = new StackPane();
        Scene graphScene = new Scene(graphPane,500,500);
        graphPanel.setScene(graphScene);

        updateGraph(graphPanel, graphPane, graphScene);
        calculationsPanel displayCalc = new calculationsPanel(equationAsString,data);
        displayCalc.show();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void updateGraph(Stage graphPanel, StackPane graphPane, Scene graphScene)
    {
        //updated the stage contents and the graph based on user interaction
        Button zoomIn = new Button();
        zoomIn.setPrefSize(25,25);
        zoomIn.setText("+");
        zoomIn.setTranslateX(230);
        zoomIn.setTranslateY(-230);
        graphPane.getChildren().add(zoomIn);

        Button zoomOut = new Button();
        zoomOut.setPrefSize(25,25);
        zoomOut.setText("-");
        zoomOut.setTranslateX(230);
        zoomOut.setTranslateY(-195);
        graphPane.getChildren().add(zoomOut);

        //draw the xAxis line
        Line xAxis = new Line();
        xAxis.setStartX(0);
        xAxis.setStartY(graphPane.getHeight()/2);
        xAxis.setEndX(graphPane.getWidth());
        xAxis.setEndY(graphPane.getHeight()/2);
        xAxis.setStroke(Color.BLACK);
        xAxis.setStrokeWidth(2.0);
        graphPane.getChildren().add(xAxis);

        //draw the yAxis line
        Line yAxis = new Line();
        yAxis.setStartX(graphPane.getWidth()/2);
        yAxis.setStartY(0);
        yAxis.setEndX(graphPane.getWidth()/2);
        yAxis.setEndY(graphPane.getHeight());
        yAxis.setStroke(Color.BLACK);
        yAxis.setStrokeWidth(2.0);
        graphPane.getChildren().add(yAxis);

        //Declaration of the array that stores the elements that will be graphed
        ArrayList<Double> lineMakerArrayList = new ArrayList<Double>();
        //decides how long the array of line objects must be
        double ArrayAmount = (Math.abs(xMax)+Math.abs(xMin))/increment;
        //declares the array of line objects
        lineMaker[] lineMakerArray = new lineMaker[(int) ArrayAmount];
        //shows the current place in the array
        int ArrayValue = 0;
        //decides the graphing value for x
        double graphPoint = 0;
        //i really dont know
        double xPointTranslater = -250;



        for(double dotPlace = xMin; dotPlace <= xMax; dotPlace+=increment)
        {
            try
            {
                //starting x for the line
                graphPoint = dotPlace + 250;
                //this allows the line to be drawn with the right slope rather than placement
                double xStart = graphPoint;
                double yStart = findYVal((graphPoint*-1)+250);
                double xEnd = (graphPoint+increment);
                double yEnd = findYVal((((graphPoint+increment)*-1)+250));
                double xTranslater = 0;//xPointTranslater;

                //adds the values to the lineMakerArrayList
                lineMakerArrayList.add(xStart);
                lineMakerArrayList.add(yStart);
                lineMakerArrayList.add(xEnd);
                lineMakerArrayList.add(yEnd);
                lineMakerArrayList.add(xTranslater);

                //makes a new line with the values of lineMakerArrayList
                lineMakerArray[ArrayValue] = new lineMaker(lineMakerArrayList.get(0),lineMakerArrayList.get(1),lineMakerArrayList.get(2),lineMakerArrayList.get(3),zoom);
                graphPane.getChildren().add(lineMakerArray[ArrayValue]);

                //check if cordinates are necessary
                if((int)xStart%10==0)
                {
                    //System.out.println((int)xStart);

                }

            }
            catch (Exception e){/*if out of bounds exception or other, ignore*/}

            //we want to clear lineMakerArrayList for the next line to be created
            lineMakerArrayList.clear();
            //increment to next x value to add line in
            xPointTranslater+=increment;
            //since the amount of lines to make is predetermined we want to go to the next value
            ArrayValue++;
        }

        zoomIn.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if(zoom < 50)
                {
                    zoom += zoom*2;
                }
                else
                    zoom+=50;

                graphPane.getChildren().clear();
                updateGraph(graphPanel,graphPane,graphScene);
                zoomIn.toFront();
                zoomOut.toFront();
            }
        });
        zoomOut.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if(zoom < 50)
                {
                    zoom -= zoom/2;
                }
                else
                    zoom -= 50;

                graphPane.getChildren().clear();
                updateGraph(graphPanel,graphPane,graphScene);
                zoomIn.toFront();
                zoomOut.toFront();
            }
        });

        //handles mouse scrolling
        graphScene.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event)
            {
                int zoomFactor = 5;
                double deltaY = event.getDeltaY();
                try
                {
                    if (deltaY < 0)
                    {
                        if (zoom < 10)
                        {
                            zoom -= zoom / (zoom * .5);
                            // increment += increment * 50;
                        }
                        if (zoom <= 0)
                        {
                            zoom = .001;
                        } else
                            zoom -= zoomFactor;
                        //increment += increment * 50;
                    } else
                    {
                        if (zoom < 10)
                        {
                            zoom += zoom * .5;
                            //increment -= increment * 50;
                        } else
                            zoom += zoomFactor;
                        //increment -= increment * 50;
                    }
                    graphPane.getChildren().clear();
                    updateGraph(graphPanel, graphPane, graphScene);
                    zoomIn.toFront();
                    zoomOut.toFront();
                    event.consume();
                }
                catch(Exception e)
                {

                }
            }
        });
        /*graphScene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()==KeyCode.ESCAPE) {
                graphPanel.setOnCloseRequest(e -> Platform.exit());
            }
        });*/

        zoomIn.toFront();
        zoomOut.toFront();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public double findYVal(double x) throws EvaluationException, ParseException {
        //uses the expression library to find the value of y at a given x
        double yValue = 0;
        equationWithX = equationAsString.replace("x",String.valueOf(x));
        Expression e = new Expression(equationWithX);
        DecimalFormat formatNum = new DecimalFormat("#.###");
        data = data + "x: " + formatNum.format(x) + "     y: " + formatNum.format(e.evaluate().getNumberValue()) + "\n";
        //System.out.println ("x: " + x +  "   FUNC(" + equationWithX + ") " +"   y: " + e.evaluate().getNumberValue());
        BigDecimal result = e.evaluate().getNumberValue();
        yValue = Double.parseDouble(String.valueOf(result));
        if (yValue == 0)
        {
            System.out.println("zero " + x);
        }
        //yValue = Math.pow(x,2);
        return yValue;
    }

}
