package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    private SceneConstructor sc;
    private GridPane grid;
    private DBConnection dbConnection;
    private BankManager bankManager;

    public Main() throws Exception {
        this.dbConnection = new DBConnection();
        this.bankManager = new BankManager(dbConnection);
    }

    @Override
    public void start(Stage stage) throws Exception{
        stage.setWidth(600);
        stage.setHeight(600);

        this.sc = new SceneConstructor(stage, this.dbConnection, this.bankManager);
        Scene scene = this.sc.connectingPage();
        stage.setScene(scene);
        stage.show();

    }


    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
