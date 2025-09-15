/*
  Program: vypocet a vykreslenie kvadratickej rovnice
  Autor: Martin Zbojovsky
  Trieda: 3.A
*/

import javafx.application.Application;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.RadioButton;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.event.EventHandler;
import javafx.event.Event;
import javafx.geometry.Insets; 
import javafx.scene.text.Text; 
import javafx.scene.image.Image;
import javafx.scene.image.ImageView; 
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.File; 
import java.io.IOException;  
import java.io.FileWriter;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class QuadraticApp extends Application{ 
  private Label lb_title, lb_values, lb_disc, lb_x1, lb_x2, lb_coefA, lb_coefB, lb_coefC, lb_result;
  private TextField tf_coefA, tf_coefB, tf_coefC;
  private Button bt_calc; 
  
  private final int VGAP = 30;
  private final int HGAP = 15;
  
  public void initGUI(Stage stage){
    //na rozlozenie aplikácie pouzivam grid pane, v nom nasledne vyuzivam vertical a horizontal box
    GridPane gridPane = new GridPane(); 
    gridPane.setVgap(VGAP); 
    gridPane.setHgap(HGAP); 
    gridPane.setId("grid-body");
    
    Alert error_input = new Alert(AlertType.NONE);
    
    lb_title = new Label("Quadratic Equation");
    lb_title.getStyleClass().add("title");  
    lb_title.setAlignment(Pos.TOP_CENTER);    
    gridPane.add(lb_title, 1, 1);
    
    lb_values = new Label("Values");
    lb_values.getStyleClass().add("values"); 
    
    lb_coefA = new Label("Coefficient a");
    lb_coefA.getStyleClass().add("value-name");
    
    tf_coefA = new TextField();
    tf_coefA.getStyleClass().add("coef");
    
    lb_coefB = new Label("Coefficient b");
    lb_coefB.getStyleClass().add("value-name");
    
    tf_coefB = new TextField();
    tf_coefB.getStyleClass().add("coef");
    
    lb_coefC = new Label("Coefficient c");
    lb_coefC.getStyleClass().add("value-name");
    
    tf_coefC = new TextField();
    tf_coefC.getStyleClass().add("coef");
    
    bt_calc = new Button("Calculate");
    bt_calc.getStyleClass().add("button-calculate");
    
    //graf pre vykreslenie kv. rovnice
    NumberAxis xAxis = new NumberAxis();
    NumberAxis yAxis = new NumberAxis();
    LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);
    lineChart.setMinWidth(700);
    XYChart.Series quadratic_eq = new XYChart.Series();
    
    //event po kliknuti tlacidla calculate - vypocita a vykresli kv. rovnicu
    bt_calc.setOnAction(new EventHandler(){
      public void handle(Event event){
        try {
          //vypocet diskriminantu
          float disc = QuadraticCalculator.getDiscriminant(Float.parseFloat(tf_coefA.getText()), Float.parseFloat(tf_coefB.getText()), Float.parseFloat(tf_coefC.getText()));
          //ak je diskriminant vacsi ako 0, kv. rovnica ma 2 rozne riesenia
          if(disc > 0){
              lb_x2.setVisible(true);
              lb_disc.setText("Discriminant: " + Float.toString(disc));
              lb_x1.setText("x1: " + Float.toString(QuadraticCalculator.getX1(Float.parseFloat(tf_coefA.getText()), Float.parseFloat(tf_coefB.getText()), Math.abs(disc))));
              lb_x2.setText("x2: " + Float.toString(QuadraticCalculator.getX2(Float.parseFloat(tf_coefA.getText()), Float.parseFloat(tf_coefB.getText()), Math.abs(disc))));
          //ak sa diskriminant rovna 0, kv. rovnica ma jedno riesenie = dvojnasobny koren  
          }else if(disc == 0){
              lb_disc.setText("Discriminant: " + Float.toString(disc));
              lb_x1.setText("x1 = x2: " + Float.toString(QuadraticCalculator.getX1(Float.parseFloat(tf_coefA.getText()), Float.parseFloat(tf_coefB.getText()), Math.abs(disc))));  
              lb_x2.setVisible(false);  
          //ak je diskriminant mensi, nema riesenie  
          }else{
            lb_disc.setText("Discriminant: " + Float.toString(disc));
            lb_x2.setVisible(true);
            lb_x1.setText("x1: NaN");
            lb_x2.setText("x2: NaN");
          }
          
          //hodnoty zapisem do grafu cez for cyklus, ktory na graf priradi kazdemu x->y
          float coefA = Float.parseFloat(tf_coefA.getText());
          float coefB = Float.parseFloat(tf_coefB.getText());
          float coefC = Float.parseFloat(tf_coefC.getText());
          quadratic_eq.getData().clear();
          for(int x = -10; x<=10; x++){
            quadratic_eq.getData().add(new XYChart.Data(x, x*x*coefA +coefB*x+coefC));
          }
          //osetrenie neplatneho inputu
        }catch(Exception e) {
          error_input.setAlertType(AlertType.WARNING);
          error_input.show();
          error_input.setContentText("Invalid input. Input must be number!");
        } 
      }
    });
    
    lb_result = new Label("Result");
    lb_result.getStyleClass().add("values");
    
    lb_disc = new Label("Discriminant: ");
    lb_disc.getStyleClass().add("result-value");
    
    lb_x1 = new Label("x1: ");
    lb_x1.getStyleClass().add("result-value");
    
    lb_x2 = new Label("x2: ");
    lb_x2.getStyleClass().add("result-value");
    
    VBox vbox_values = new VBox(15);
    vbox_values.getChildren().addAll(lb_values, lb_coefA, tf_coefA, lb_coefB, tf_coefB, lb_coefC, tf_coefC, bt_calc);
    vbox_values.getStyleClass().add("values-parent");
    
    VBox vbox_result = new VBox(15);
    vbox_result.getChildren().addAll(lb_result, lb_disc, lb_x1, lb_x2);
    vbox_result.getStyleClass().add("values-parent");
    
    VBox vbox_left = new VBox(10);
    vbox_left.getChildren().addAll(vbox_values, vbox_result);  

    lineChart.getData().add(quadratic_eq);
    lineChart.setLegendVisible(false);
    lineChart.setCreateSymbols(false);
    
    HBox hbox_main = new HBox(10);
    hbox_main.getChildren().addAll(vbox_left, lineChart);
     
         
    gridPane.add(hbox_main, 1, 2);
    
    //nastavenie aplikacie
    Scene scene = new Scene(gridPane, 1000, 650);
    scene.getStylesheets().add("/css/style.css");
    stage.setTitle("Quadratic Equation @Zbojovsky");
    stage.setScene(scene);
    stage.show();
    stage.setResizable(false);
    stage.getIcons().add(new Image("img/logo.png"));  
    Stage alert_stage = (Stage) error_input.getDialogPane().getScene().getWindow();
    alert_stage.getIcons().add(new Image("img/logo.png"));
  }

  
  public void start(Stage stage){
    initGUI(stage);
  }
  
  public static void main(String arg[]){
    Application.launch();
  }
}
