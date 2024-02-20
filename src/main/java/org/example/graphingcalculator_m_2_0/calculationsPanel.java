package org.example.graphingcalculator_m_2_0;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
public class calculationsPanel extends Stage
{
    public String equationAsString = "";

    public String calcData = "";
    public calculationsPanel(String equationAsString, String data)
    {
        this.equationAsString = equationAsString;
        this.calcData = data;
        showCalculations(this);
    }
    public void showCalculations(Stage calcPanel)
    {
        calcPanel.setTitle(equationAsString);

        // Create a TextArea to hold the long text document
        TextArea textArea = new TextArea();
        textArea.setWrapText(true); // Enable text wrapping

        textArea.setText(calcData);

        // Create a ScrollPane to display the TextArea with scrollbars
        ScrollPane scrollPane = new ScrollPane(textArea);

        //sets scroll pane size to fit stage demensions
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        // Set scroll policy to ensure both horizontal and vertical scrollbars appear as needed
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        // Create a StackPane to hold the ScrollPane
        StackPane root = new StackPane();
        root.getChildren().add(scrollPane);

        // Create a scene and add it to the stage
        Scene scene = new Scene(root, 600, 400);
        calcPanel.setScene(scene);

        calcPanel.show();
    }
}
