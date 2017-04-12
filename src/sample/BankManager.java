package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.Group;
import java.sql.Connection;


/**
 * Created by Nefast on 20/03/2017.
 */
public class BankManager {

    private DBConnection conn;

    public BankManager(DBConnection conn){
        this.conn = conn;
    }
}
