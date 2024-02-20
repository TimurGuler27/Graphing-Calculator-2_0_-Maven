module org.example.graphingcalculator_m_2_0 {
    requires javafx.controls;
    requires javafx.fxml;
    requires EvalEx;


    opens org.example.graphingcalculator_m_2_0 to javafx.fxml;
    exports org.example.graphingcalculator_m_2_0;
}