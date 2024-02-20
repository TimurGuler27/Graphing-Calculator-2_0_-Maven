package org.example.graphingcalculator_m_2_0;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * DOC INFORMATION
 *
 * updated by gulertimur27 on 02/02/2024.
 * THIS APPLICATION IS OWNED BY TIMUR GULER AND IS DISTRIBUTED AS AN OPEN SOURCE GRAPHING CALCULATOR
 * PLEASE DO NOT USE WITHOUT PERMISSION
 */

/**
 * DESCRIPTION
 *
 * WAVÉ GRAPHING CALCULATOR v 2.0
 *
 * WHATS NEW?
 * -added support for zoom functionality
 * -expression library finally working!!!!!
 *
 * WHAT TO ADD IN NEXT VERSION v 2.5
 * -cleaned up source
 * -support for trig functions
 * -dynamic window ranging ... if xMax/xMin too small for function to appear continuous, add points to make continuous
 * -coordinate mapping
 *
 *
 *
 *
 */
public class appFrame extends Application
{
    public int xMax,xMin,yMax,yMin;
    public double increment;
    public String equationAsString;
    public void start(Stage Frame)
    {
        StackPane mainPane = new StackPane();
        Scene mainScene = new Scene(mainPane, 700, 700);
        Frame.setResizable(false);

        //equation label and text field
        Label equationLabel = new Label();
        equationLabel.setPrefSize(40, 25);
        equationLabel.setTranslateY(-320);
        equationLabel.setTranslateX(180);
        equationLabel.setText("y =");
        mainPane.getChildren().add(equationLabel);

        TextField equationField = new TextField();
        equationField.setPrefSize(150, 25);
        equationField.setMaxWidth(150);
        equationField.setTranslateX(265);
        equationField.setTranslateY(-320);
        equationField.setText("x^2");
        mainPane.getChildren().add(equationField);

        //Window Settings separator and Label
        //will add the separator later

        Label windowSettingsLabel = new Label();
        windowSettingsLabel.setPrefSize(120, 25);
        windowSettingsLabel.setTranslateY(-280);
        windowSettingsLabel.setTranslateX(200);
        windowSettingsLabel.setText("Window Settings");
        mainPane.getChildren().add(windowSettingsLabel);

        //x right and x left label and text field
        Label xMaxLabel = new Label();
        xMaxLabel.setPrefSize(50, 25);
        xMaxLabel.setTranslateY(-250);
        xMaxLabel.setTranslateX(200);
        xMaxLabel.setText("X Max");
        mainPane.getChildren().add(xMaxLabel);

        TextField xMaxField = new TextField();
        xMaxField.setPrefSize(50, 25);
        xMaxField.setMaxWidth(50);
        xMaxField.setTranslateY(-250);
        xMaxField.setTranslateX(260);
        xMaxField.setText("20");
        mainPane.getChildren().add(xMaxField);

        Label xMinLabel = new Label();
        xMinLabel.setPrefSize(50, 25);
        xMinLabel.setTranslateY(-220);
        xMinLabel.setTranslateX(200);
        xMinLabel.setText("X Min");
        mainPane.getChildren().add(xMinLabel);

        TextField xMinField = new TextField();
        xMinField.setPrefSize(50, 25);
        xMinField.setMaxWidth(50);
        xMinField.setTranslateY(-220);
        xMinField.setTranslateX(260);
        xMinField.setText("-20");
        mainPane.getChildren().add(xMinField);

        //y right and y left label and text field
        Label yMaxLabel = new Label();
        yMaxLabel.setPrefSize(50, 25);
        yMaxLabel.setTranslateY(-180);
        yMaxLabel.setTranslateX(200);
        yMaxLabel.setText("Y Max");
        mainPane.getChildren().add(yMaxLabel);

        TextField yMaxField = new TextField();
        yMaxField.setPrefSize(50, 25);
        yMaxField.setMaxWidth(50);
        yMaxField.setTranslateY(-180);
        yMaxField.setTranslateX(260);
        yMaxField.setText("20");
        mainPane.getChildren().add(yMaxField);

        Label yMinLabel = new Label();
        yMinLabel.setPrefSize(50, 25);
        yMinLabel.setTranslateY(-150);
        yMinLabel.setTranslateX(200);
        yMinLabel.setText("Y Min");
        mainPane.getChildren().add(yMinLabel);

        TextField yMinField = new TextField();
        yMinField.setPrefSize(50, 25);
        yMinField.setMaxWidth(50);
        yMinField.setTranslateY(-150);
        yMinField.setTranslateX(260);
        yMinField.setText("-20");
        mainPane.getChildren().add(yMinField);

        //increment scale
        /**a smaller increment yields a more accurate result but longer process**/
        Label incrementLabel = new Label();
        incrementLabel.setPrefSize(50, 25);
        incrementLabel.setTranslateY(300);
        incrementLabel.setTranslateX(-170);
        incrementLabel.setText("inc");
        mainPane.getChildren().add(incrementLabel);

        TextField incrementField = new TextField();
        incrementField.setPrefSize(100, 25);
        incrementField.setMaxWidth(100);
        incrementField.setTranslateY(300);
        incrementField.setTranslateX(-120);
        incrementField.setText(".01");
        mainPane.getChildren().add(incrementField);

        //graph button
        Button graphCommand = new Button();
        graphCommand.setPrefSize(100, 25);
        graphCommand.setTranslateY(300);
        graphCommand.setText("Graph");
        mainPane.getChildren().add(graphCommand);

        graphCommand.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                //defaults the variables
                if(xMaxField.getText().equals(""))
                {
                    xMax = 0;
                }
                if(xMinField.getText().equals(""))
                {
                    xMin = 0;
                }
                if(yMaxField.getText().equals(""))
                {
                    yMax = 0;
                }
                if(yMinField.getText().equals(""))
                {
                    yMin = 0;
                }
                if(incrementField.getText().equals(""))
                {
                    increment = .01;
                }
                if(equationField.getText().equals(""))
                {
                    equationAsString = "0";
                }
                //sets the variables if boxes filled
                xMax = Integer.parseInt(xMaxField.getText());
                xMin = Integer.parseInt(xMinField.getText());
                yMax = Integer.parseInt(yMaxField.getText());
                yMin = Integer.parseInt(yMinField.getText());
                increment = Double.parseDouble(incrementField.getText());
                equationAsString = equationField.getText();

                graphPanel displayGraph = new graphPanel(xMax,xMin,yMax,yMin,increment,equationAsString);
                displayGraph.show();


            }
        });


        Frame.setScene(mainScene);
        Frame.setResizable(true);
        Frame.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
