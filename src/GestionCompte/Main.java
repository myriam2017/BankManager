package GestionCompte;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
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
