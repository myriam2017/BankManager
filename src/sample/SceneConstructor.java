package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Created by Nefast on 28/03/2017.
 */
public class SceneConstructor {

    /* Manager */
    private DBConnection dbc;
    private BankManager bm;

    /* Field and button */
    private Button btnDeleteOperation, btnDeleteCategory, btnDeleteSubCategory, btnBilan, btnConnexion, btnDeconnexion, btnAddOperation, btnEditOperation,  btnListOperation, btnSaveOperation, btnAddCategory, btnEditCategory, btnSaveCategory, btnListCategory, btnAddSubCategory, btnSaveSubCategory, btnEditSubCategory, btnListSubCategory;
    private Text scenetitle;
    private Label label;
    private TextField textFieldNameOperation, textFieldNameCate, textFieldUser, textFieldUserAccount, textFieldOpType, textFieldAmount, textFieldPoste;
    private TextArea textAreaDescr;
    private PasswordField passField;
    private HBox hbox;
    private Scene scene;
    private GridPane grid;
    private ChoiceBox cbTypePay, cbType,cbCate,cbSubCate;
    private Stage thestage;
    private Group group;
    private TableView table;
    private VBox vbox;
    private DatePicker dpCreated;
    private int hiddenOperation, hiddenCategory, hiddenSubCategory;
    private Operation rowDataOperation;
    private SubCategorie rowDataSubCate;
    private Categorie rowDataCategorie;

    public SceneConstructor(Stage stage, DBConnection dbc, BankManager bm){
        this.dbc = dbc;
        this.bm = bm;
        this.table = new TableView();

        this.btnConnexion = new Button("Connexion");
        this.btnDeconnexion = new Button("Deconnexion");
        this.btnBilan = new Button("Acceuil");

        this.btnAddOperation = new Button("Ajouter Opération");
        this.btnEditOperation = new Button("Enregistrer");
        this.btnListOperation = new Button("Liste des opération");
        this.btnSaveOperation = new Button("Enregistrer");
        this.btnDeleteOperation = new Button("Supprimer");

        this.btnAddCategory = new Button("Ajouter Catégorie");
        this.btnEditCategory = new Button("Enregistrer");
        this.btnListCategory = new Button("Liste des Catégorie");
        this.btnSaveCategory = new Button("Enregistrer");
        this.btnDeleteCategory = new Button("Supprimer");

        this.btnAddSubCategory = new Button("Ajouter Sous-Catégorie");
        this.btnEditSubCategory = new Button("Enregistrer");
        this.btnListSubCategory = new Button("Liste des Sous-Catégorie");
        this.btnSaveSubCategory = new Button("Enregistrer");
        this.btnDeleteSubCategory = new Button("Supprimer");

        this.thestage = stage;

        this.textFieldNameOperation = new TextField();
        this.textFieldNameCate = new TextField();
        this.textFieldUser = new TextField();
        this.textFieldUserAccount = new TextField();
        this.textFieldOpType = new TextField();
        this.textFieldAmount = new TextField();
        this.textFieldPoste = new TextField();

        this.cbTypePay = new ChoiceBox();
        this.cbType = new ChoiceBox();
        this.cbCate = new ChoiceBox();
        this.cbSubCate = new ChoiceBox();
    }

    public void checkDatabase() throws Exception {
        try {
            this.dbc.connect(this.textFieldUser.getText(), this.passField.getText(), this.textFieldUserAccount.getText());
        }catch (Exception e){
            throw new Exception(e);
        }
    }
    public void ButtonClicked(ActionEvent e) {
        if (e.getSource()==this.btnConnexion) {
            try{
                this.checkDatabase();
                this.thestage.setScene(this.indexPage());
            }catch (Exception ex) {
                this.thestage.setScene(this.connectingPage(ex.getMessage()));
            }
        }
        else if(e.getSource()==this.btnDeconnexion){
            this.dbc = new DBConnection();
            this.bm = new BankManager(this.dbc);
            this.thestage.setScene(this.connectingPage());
        }
        else if (e.getSource()==this.btnBilan) {
            this.thestage.setScene(this.indexPage());
        }
        else if(e.getSource()==this.btnListSubCategory){
            this.thestage.setScene(this.listSubCategoryPage());
        }
        else if (e.getSource()==this.btnListCategory) {
            this.thestage.setScene(this.listCategoryPage());
        }
        else if (e.getSource()==this.btnListOperation) {
            this.thestage.setScene(this.listOperationPage());
        }
        else if(e.getSource()==this.btnAddCategory){
            this.thestage.setScene(this.addCategoryPage());
        }
        else if (e.getSource()==this.btnAddOperation) {
            this.thestage.setScene(this.addOperationPage());
        }
        else if (e.getSource()==this.btnAddSubCategory) {
            this.thestage.setScene(this.addSubCategoryPage());
        }
        else if(e.getSource()==this.btnSaveCategory){
            try {
                this.dbc.setCategory(
                        this.textFieldNameCate.getText()
                );
                this.thestage.setScene(this.listCategoryPage());
            }catch (Exception ex){
                this.thestage.setScene(this.addCategoryPage(ex.getMessage()));
            }
        }
        else if (e.getSource()==this.btnSaveOperation) {
            try {
                this.dbc.setOperation(
                        this.textFieldNameOperation.getText(),
                        this.textAreaDescr.getText(),
                        this.cbType.getSelectionModel().getSelectedItem().toString(),
                        this.cbTypePay.getSelectionModel().getSelectedItem().toString(),
                        this.cbSubCate.getSelectionModel().getSelectedItem().toString(),
                        this.dpCreated.getValue(),
                        this.textFieldAmount.getText(),
                        this.textFieldPoste.getText()
                );
                this.thestage.setScene(this.listOperationPage());
            }catch (Exception ex){
                this.thestage.setScene(this.addOperationPage(ex.getMessage()));
            }
        }
        else if (e.getSource()==this.btnSaveSubCategory) {
            try {
                this.dbc.setSubCategory(
                        this.textFieldNameCate.getText(),
                        this.cbCate.getSelectionModel().getSelectedItem().toString()
                );
                this.thestage.setScene(this.listSubCategoryPage());
            }catch (Exception ex){
                this.thestage.setScene(this.addSubCategoryPage(ex.getMessage()));
            }
        }
        else if (e.getSource()==this.btnEditOperation){
            try {
                this.dbc.setOperation(
                        this.hiddenOperation,
                        this.textFieldNameOperation.getText(),
                        this.textAreaDescr.getText(),
                        this.cbType.getSelectionModel().getSelectedItem().toString(),
                        this.cbTypePay.getSelectionModel().getSelectedItem().toString(),
                        this.cbSubCate.getSelectionModel().getSelectedItem().toString(),
                        this.dpCreated.getValue(),
                        this.textFieldAmount.getText(),
                        this.textFieldPoste.getText()
                );
                this.thestage.setScene(this.listOperationPage());
            }catch (Exception ex){
                this.thestage.setScene(this.editOperationPage(this.rowDataOperation, ex.getMessage()));
            }
        }
        else if (e.getSource()==this.btnEditCategory){
            try {
                this.dbc.setCategory(
                        this.hiddenCategory,
                        this.textFieldNameCate.getText()
                );
                this.thestage.setScene(this.listCategoryPage());
            }catch (Exception ex){
                this.thestage.setScene(this.editCategoryPage(this.rowDataCategorie, ex.getMessage()));
            }
        }
        else if (e.getSource()==this.btnEditSubCategory){
            try {
                this.dbc.setSubCategory(
                        this.hiddenSubCategory,
                        this.textFieldNameCate.getText(),
                        this.cbCate.getSelectionModel().getSelectedItem().toString()
                );
                this.thestage.setScene(this.listSubCategoryPage());
            }catch (Exception ex){
                this.thestage.setScene(this.editSubCategoryPage(this.rowDataSubCate, ex.getMessage()));
            }
        }
        else if (e.getSource()==this.btnDeleteOperation){
            try {
                this.dbc.deleteOperation(this.hiddenOperation);
                this.thestage.setScene(this.listOperationPage());
            }catch (Exception ex){
                this.thestage.setScene(this.editOperationPage(this.rowDataOperation, ex.getMessage()));
            }
        }
        else if (e.getSource()==this.btnDeleteCategory){
            try {
                this.dbc.deleteCategory(this.hiddenCategory);
                this.thestage.setScene(this.listCategoryPage());
            }catch (Exception ex){
                this.thestage.setScene(this.editCategoryPage(this.rowDataCategorie, ex.getMessage()));
            }
        }
        else if (e.getSource()==this.btnDeleteSubCategory){
            try {
                this.dbc.deleteSubCategory(this.hiddenSubCategory);
                this.thestage.setScene(this.listSubCategoryPage());
            }catch (Exception ex){
                this.thestage.setScene(this.editSubCategoryPage(this.rowDataSubCate, ex.getMessage()));
            }
        }
    }

    public void initGrid(){
        //Init Grid
        this.grid = new GridPane();
        this.grid.setAlignment(Pos.CENTER);
        this.grid.setHgap(10);
        this.grid.setVgap(10);
        this.grid.setPadding(new Insets(25, 25, 25, 25));
    }

    public Scene connectingPage(){
        this.initGrid();
        this.thestage.setTitle("BankManager | Connexion");

        this.scene = new Scene(this.grid, 800, 800);

        this.scenetitle = new Text("Bienvenue sur BankManager");
        this.scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        this.grid.add(this.scenetitle, 0, 0, 2, 1);

        this.scenetitle = new Text("Veuiller vous connecter a votre base de donnée");
        this.scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        this.grid.add(this.scenetitle, 0, 1, 2, 1);

        this.label = new Label("Utilisateur:");
        this.grid.add(this.label, 0, 2);

        this.textFieldUser = new TextField();
        this.grid.add(this.textFieldUser, 1, 2);

        this.label = new Label("Mot de passe:");
        this.grid.add(this.label, 0, 3);

        this.passField = new PasswordField();
        this.grid.add(this.passField, 1, 3);

        this.label = new Label("Compte:");
        this.grid.add(this.label, 0, 4);

        this.textFieldUserAccount = new TextField();
        this.grid.add(this.textFieldUserAccount, 1, 4);

        this.hbox = new HBox(10);
        this.hbox.setAlignment(Pos.BOTTOM_RIGHT);
        this.hbox.getChildren().add(this.btnConnexion);
        this.grid.add(this.hbox, 1, 5);

        this.btnConnexion.setOnAction(this::ButtonClicked);

        return this.scene;
    }

    public Scene connectingPage(String error){
        this.scene = connectingPage();

        this.scenetitle = new Text(error);
        this.scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        this.scenetitle.setFill(Color.RED);
        this.scenetitle.setWrappingWidth(400);
        this.grid.add(this.scenetitle, 0, 6, 2, 1);

        return this.scene;
    }

    public Scene indexPage(){
        this.initGrid();
        this.thestage.setTitle("BankManager | Bilan des operation");
        this.scene = new Scene(this.grid, 800, 800);

        this.hbox = new HBox(20);
        this.hbox.setAlignment(Pos.CENTER);
        this.hbox.getChildren().add(this.btnDeconnexion);
        this.grid.add(this.btnDeconnexion, 0, 0);

        this.scenetitle = new Text("Session : " + this.dbc.getConnectedClient().getUserName() + "| Compte : " + this.dbc.getConnectedCompte().getUserName());
        this.scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        this.grid.add(this.scenetitle, 0, 1);

        ObservableList<PieChart.Data> pieChartData = this.dbc.generatorPie();
        if (!pieChartData.isEmpty()){
            final PieChart chart = new PieChart(pieChartData);
            chart.setTitle("Opérations");
            this.grid.add(chart, 0, 2);
        }else {
            this.scenetitle = new Text("Aucune opération n'est disponnible");
            this.scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            this.scenetitle.setFill(Color.GOLD);
            this.scenetitle.setTextAlignment(TextAlignment.CENTER);
            this.scenetitle.setWrappingWidth(600);
            this.grid.add(this.scenetitle, 0, 2);
        }

        this.hbox = new HBox(20);
        this.hbox.setAlignment(Pos.CENTER);
        this.hbox.getChildren().add(this.btnListOperation);
        this.grid.add(this.hbox, 0, 3);

        this.hbox = new HBox(20);
        this.hbox.setAlignment(Pos.CENTER);
        this.hbox.getChildren().add(this.btnListCategory);
        this.grid.add(this.hbox, 0, 4);

        this.hbox = new HBox(20);
        this.hbox.setAlignment(Pos.CENTER);
        this.hbox.getChildren().add(this.btnListSubCategory);
        this.grid.add(this.hbox, 0, 5);

        this.btnListCategory.setOnAction(this::ButtonClicked);
        this.btnListOperation.setOnAction(this::ButtonClicked);
        this.btnListSubCategory.setOnAction(this::ButtonClicked);
        this.btnDeconnexion.setOnAction(this::ButtonClicked);

        return this.scene;
    }

    public Scene listOperationPage() {
        this.initGrid();
        this.thestage.setTitle("BankManager | List Operation");
        this.scene = new Scene(this.grid, 800, 800);

        this.hbox = new HBox(20);
        this.hbox.setAlignment(Pos.CENTER_LEFT);
        this.hbox.getChildren().add(this.btnBilan);
        this.grid.add(this.hbox, 0, 1);
        this.btnBilan.setOnAction(this::ButtonClicked);

        this.hbox = new HBox(20);
        this.hbox.setAlignment(Pos.CENTER_RIGHT);
        this.hbox.getChildren().add(this.btnAddOperation);
        this.grid.add(this.hbox, 1, 1);
        this.btnAddOperation.setOnAction(this::ButtonClicked);

        this.table = new TableView();

        ObservableList<Operation> data = null;
        try {
            data = this.dbc.getAllOperation();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        TableColumn nameCol = new TableColumn("Nom");
        nameCol.setCellValueFactory(new PropertyValueFactory<Operation, String>("nameView"));

        TableColumn typeCol = new TableColumn("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<Operation, String>("typeView"));

        TableColumn typePayCol = new TableColumn("Type paiement");
        typePayCol.setCellValueFactory(new PropertyValueFactory<Operation, String>("typePayView"));

        TableColumn cateCol = new TableColumn("Catégorie");
        cateCol.setCellValueFactory(new PropertyValueFactory<Operation, String>("categoryView"));

        TableColumn subCateCol = new TableColumn("Sous-Catégorie");
        subCateCol.setCellValueFactory(new PropertyValueFactory<Operation, String>("subCategoryView"));

        TableColumn createdCol = new TableColumn("Creation");
        createdCol.setCellValueFactory(new PropertyValueFactory<Operation, String>("creationView"));

        TableColumn posteCol = new TableColumn("Poste");
        posteCol.setCellValueFactory(new PropertyValueFactory<Operation, String>("posteView"));

        if (data != null){
            this.table.setItems(data);
            this.table.getColumns().addAll(nameCol, typeCol, typePayCol, cateCol, subCateCol, createdCol, posteCol);
        }

        this.table.setRowFactory( tv -> {
            TableRow<Operation> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    this.rowDataOperation = row.getItem();
                    this.thestage.setScene(this.editOperationPage(this.rowDataOperation));
                }
            });
            return row ;
        });

        this.vbox = new VBox();
        this.vbox.setSpacing(5);
        this.vbox.setPadding(new Insets(10, 0, 0, 10));
        this.vbox.getChildren().addAll(this.table);

        this.grid.add(this.vbox, 0, 2, 2, 1);

        return this.scene;
    }

    public Scene listCategoryPage(){
        this.initGrid();
        this.thestage.setTitle("BankManager | List Catégorie");
        this.scene = new Scene(this.grid, 800, 800);

        this.hbox = new HBox(20);
        this.hbox.setAlignment(Pos.TOP_LEFT);
        this.hbox.getChildren().add(this.btnBilan);
        this.grid.add(this.hbox, 0, 1);
        this.btnBilan.setOnAction(this::ButtonClicked);

        this.hbox = new HBox(20);
        this.hbox.setAlignment(Pos.CENTER_RIGHT);
        this.hbox.getChildren().add(this.btnAddCategory);
        this.grid.add(this.hbox, 1, 1);
        this.btnAddCategory.setOnAction(this::ButtonClicked);

        this.table = new TableView();
        ObservableList<Categorie> data = null;
        try {
            data = this.dbc.getAllCategory();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        TableColumn idCol = new TableColumn("Id");
        idCol.setCellValueFactory(new PropertyValueFactory<Operation, String>("idView"));

        TableColumn cateCol = new TableColumn("Nom catégorie");
        cateCol.setCellValueFactory(new PropertyValueFactory<Operation, String>("nameView"));

        this.table.setRowFactory( tv -> {
            TableRow<Categorie> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    this.rowDataCategorie = row.getItem();
                    this.thestage.setScene(this.editCategoryPage(this.rowDataCategorie));
                }
            });
            return row ;
        });

        if (data != null){
            this.table.setItems(data);
            this.table.getColumns().addAll(idCol, cateCol);
        }
        this.vbox = new VBox();
        this.vbox.setSpacing(5);
        this.vbox.setPadding(new Insets(10, 0, 0, 10));
        this.vbox.getChildren().addAll(this.table);
        this.grid.add(this.vbox, 0, 2, 2, 1);

        return this.scene;
    }

    public Scene listSubCategoryPage(){
        this.initGrid();
        this.thestage.setTitle("BankManager | List Sous-Catégorie");
        this.scene = new Scene(this.grid, 800, 800);

        this.hbox = new HBox(20);
        this.hbox.setAlignment(Pos.TOP_LEFT);
        this.hbox.getChildren().add(this.btnBilan);
        this.grid.add(this.hbox, 0, 0);
        this.btnBilan.setOnAction(this::ButtonClicked);

        this.hbox = new HBox(20);
        this.hbox.setAlignment(Pos.TOP_LEFT);
        this.hbox.getChildren().add(this.btnAddSubCategory);
        this.grid.add(this.hbox, 1, 0);
        this.btnAddSubCategory.setOnAction(this::ButtonClicked);

        this.table = new TableView();

        ObservableList<SubCategorie> data = null;
        try {
            data = this.dbc.getAllSubCategory();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        TableColumn idCol = new TableColumn("Id");
        idCol.setCellValueFactory(new PropertyValueFactory<Operation, String>("idView"));

        TableColumn cateCol = new TableColumn("Nom catégorie");
        cateCol.setCellValueFactory(new PropertyValueFactory<Operation, String>("categorieView"));

        TableColumn cateSubCol = new TableColumn("Nom sous catégorie");
        cateSubCol.setCellValueFactory(new PropertyValueFactory<Operation, String>("nameView"));

        this.table.setRowFactory( tv -> {
            TableRow<SubCategorie> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    this.rowDataSubCate = row.getItem();
                    this.thestage.setScene(this.editSubCategoryPage(this.rowDataSubCate));
                }
            });
            return row ;
        });

        if (data != null){
            this.table.setItems(data);
            this.table.getColumns().addAll(idCol, cateSubCol, cateCol);
        }

        this.vbox = new VBox();
        this.vbox.setSpacing(5);
        this.vbox.setPadding(new Insets(10, 0, 0, 10));
        this.vbox.getChildren().addAll(this.table);

        this.grid.add(this.vbox, 0, 2, 2, 1);

        return this.scene;
    }

    public Scene addOperationPage(){
        this.initGrid();
        this.thestage.setTitle("BankManager | Ajouter Opération");
        this.scene = new Scene(this.grid, 800, 800);

        this.scenetitle = new Text("Ajouter une opération");
        this.scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        this.grid.add(this.scenetitle, 0, 0, 2, 1);

        this.label = new Label("Nom:");
        this.grid.add(this.label, 0, 1);
        this.textFieldNameOperation = new TextField();
        this.grid.add(this.textFieldNameOperation, 1, 1);

        this.label = new Label("Type:");
        this.grid.add(this.label, 0, 2);
        this.cbType = new ChoiceBox(FXCollections.observableArrayList(
                "Depense", "Revenue", "Echéance Automatique")
        );
        this.grid.add(this.cbType, 1, 2);

        this.label = new Label("Type paiement");
        this.grid.add(this.label, 0, 3);
        this.cbTypePay = new ChoiceBox(FXCollections.observableArrayList(
                "Virement", "Espece", "Retrait", "Cheque")
        );
        this.grid.add(this.cbTypePay, 1, 3);

        this.label = new Label("Sous-categorie");
        this.grid.add(this.label, 0, 4);
        try {
            this.cbSubCate = new ChoiceBox(this.dbc.getAllSubCategoryChoice());
            this.grid.add(this.cbSubCate, 1, 4);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.label = new Label("Date de création");
        this.grid.add(this.label, 0, 5);
        this.dpCreated = new DatePicker();
        this.grid.add(this.dpCreated, 1, 5);

        this.label = new Label("Montant");
        this.grid.add(this.label, 0, 6);
        this.textFieldAmount = new TextField();
        this.grid.add(this.textFieldAmount, 1, 6);

        this.label = new Label("Poste:");
        this.grid.add(this.label, 0, 7);
        this.textFieldPoste = new TextField();
        this.grid.add(this.textFieldPoste, 1, 7);

        textFieldAmount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    textFieldAmount.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        this.label = new Label("Description:");
        this.grid.add(this.label, 0, 8);
        this.textAreaDescr = new TextArea();
        this.grid.add(this.textAreaDescr, 1, 8);


        this.hbox = new HBox(10);
        this.hbox.setAlignment(Pos.BOTTOM_LEFT);
        this.hbox.getChildren().add(this.btnListOperation);
        this.grid.add(this.hbox, 0, 10);
        this.btnListOperation.setOnAction(this::ButtonClicked);

        this.hbox = new HBox(10);
        this.hbox.setAlignment(Pos.BOTTOM_RIGHT);
        this.hbox.getChildren().add(this.btnSaveOperation);
        this.grid.add(this.hbox, 1, 10);
        this.btnSaveOperation.setOnAction(this::ButtonClicked);

        return this.scene;
    }

    public Scene addOperationPage(String error) {
        this.scene = addOperationPage();

        this.scenetitle = new Text(error);
        this.scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        this.scenetitle.setFill(Color.RED);
        this.scenetitle.setWrappingWidth(400);
        this.grid.add(this.scenetitle, 0, 11, 2, 1);

        return this.scene;
    }

    public Scene editOperationPage(Operation operation){
        this.initGrid();
        this.thestage.setTitle("BankManager | Editer Opération");
        this.scene = new Scene(this.grid, 800, 800);

        this.hiddenOperation = operation.getId();

        this.scenetitle = new Text("Edition opération");
        this.scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        this.grid.add(this.scenetitle, 0, 0, 3, 1);

        this.label = new Label("Nom:");
        this.grid.add(this.label, 0, 1);
        this.textFieldNameOperation = new TextField();
        this.textFieldNameOperation.setText(operation.getName());
        this.grid.add(this.textFieldNameOperation, 1, 1, 2, 1);

        this.label = new Label("Type:");
        this.grid.add(this.label, 0, 2);
        this.cbType = new ChoiceBox(FXCollections.observableArrayList(
                "Depense", "Revenue", "Echéance Automatique")
        );
        this.cbType.setValue(operation.getType());
        this.grid.add(this.cbType, 1, 2, 2, 1);

        this.label = new Label("Type paiement");
        this.grid.add(this.label, 0, 3);
        this.cbTypePay = new ChoiceBox(FXCollections.observableArrayList(
                "Virement", "Espece", "Retrait", "Cheque", "Carte")
        );
        this.cbTypePay.setValue(operation.getPaymentType());
        this.grid.add(this.cbTypePay, 1, 3, 2, 1);

        this.label = new Label("Sous-categorie");
        this.grid.add(this.label, 0, 4);
        try {
            this.cbSubCate = new ChoiceBox(this.dbc.getAllSubCategoryChoice());
            this.cbSubCate.setValue(operation.getSubCategorie().getName());
            this.grid.add(this.cbSubCate, 1, 4, 2, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.label = new Label("Date de création");
        this.grid.add(this.label, 0, 5);
        this.dpCreated = new DatePicker();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        this.dpCreated.setValue(LocalDate.parse(df.format(operation.getCreatedAt())));
        this.grid.add(this.dpCreated, 1, 5, 2, 1);

        this.label = new Label("Montant");
        this.grid.add(this.label, 0, 6);
        this.textFieldAmount = new TextField();
        this.textFieldAmount.setText(String.valueOf(operation.getAmount()));
        this.grid.add(this.textFieldAmount, 1, 6, 2, 1);

        this.label = new Label("Poste:");
        this.grid.add(this.label, 0, 7);
        this.textFieldPoste = new TextField();
        this.textFieldPoste.setText(operation.getPoste());
        this.grid.add(this.textFieldPoste, 1, 7, 2, 1);

        textFieldAmount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    textFieldAmount.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        this.label = new Label("Description:");
        this.grid.add(this.label, 0, 8);
        this.textAreaDescr = new TextArea();
        this.textAreaDescr.setText(operation.getDescription());
        this.grid.add(this.textAreaDescr, 1, 8, 2, 1);

        this.hbox = new HBox(10);
        this.hbox.setAlignment(Pos.CENTER_LEFT);
        this.hbox.getChildren().add(this.btnListOperation);
        this.grid.add(this.hbox, 0, 9);
        this.btnListOperation.setOnAction(this::ButtonClicked);

        this.hbox = new HBox(10);
        this.hbox.setAlignment(Pos.CENTER_LEFT);
        this.hbox.getChildren().add(this.btnDeleteOperation);
        this.grid.add(this.hbox, 1, 9);
        this.btnDeleteOperation.setOnAction(this::ButtonClicked);

        this.hbox = new HBox(10);
        this.hbox.setAlignment(Pos.CENTER_RIGHT);
        this.hbox.getChildren().add(this.btnEditOperation);
        this.grid.add(this.hbox, 2, 9);
        this.btnEditOperation.setOnAction(this::ButtonClicked);


        return this.scene;
    }

    public Scene editOperationPage(Operation operation, String error) {
        this.scene = editOperationPage(operation);

        this.scenetitle = new Text(error);
        this.scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        this.scenetitle.setFill(Color.RED);
        this.scenetitle.setWrappingWidth(400);
        this.grid.add(this.scenetitle, 0, 10, 2, 1);

        return this.scene;
    }


    public Scene addCategoryPage(){
        this.initGrid();
        this.thestage.setTitle("BankManager | Ajouter Catégorie");
        this.scene = new Scene(this.grid, 800, 800);

        this.scenetitle = new Text("Ajouter une Catégorie");
        this.scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        this.grid.add(this.scenetitle, 0, 0, 2, 1);

        this.label = new Label("Nom de la catégorie:");
        this.grid.add(this.label, 0, 2);
        this.textFieldNameCate = new TextField();
        this.grid.add(this.textFieldNameCate, 1, 2);

        this.hbox = new HBox(10);
        this.hbox.setAlignment(Pos.BOTTOM_LEFT);
        this.hbox.getChildren().add(this.btnListCategory);
        this.grid.add(this.hbox, 0, 3);
        this.btnListCategory.setOnAction(this::ButtonClicked);

        this.hbox = new HBox(10);
        this.hbox.setAlignment(Pos.BOTTOM_RIGHT);
        this.hbox.getChildren().add(this.btnSaveCategory);
        this.grid.add(this.hbox, 1, 3);
        this.btnSaveCategory.setOnAction(this::ButtonClicked);

        return this.scene;
    }

    public Scene addCategoryPage(String error) {
        this.scene = addCategoryPage();

        this.scenetitle = new Text(error);
        this.scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        this.scenetitle.setFill(Color.RED);
        this.scenetitle.setWrappingWidth(400);
        this.grid.add(this.scenetitle, 0, 4, 2, 1);

        return this.scene;
    }

    public Scene editCategoryPage(Categorie categorie){
        this.initGrid();
        this.thestage.setTitle("BankManager | Editer Catégorie");
        this.scene = new Scene(this.grid, 800, 800);

        this.hiddenCategory = categorie.getId();

        this.scenetitle = new Text("Editer une Catégorie");
        this.scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        this.grid.add(this.scenetitle, 0, 0, 2, 1);

        this.label = new Label("Nom de la catégorie:");
        this.grid.add(this.label, 0, 2);
        this.textFieldNameCate = new TextField();
        this.textFieldNameCate.setText(categorie.getName());
        this.grid.add(this.textFieldNameCate, 1, 2, 2, 1);

        this.hbox = new HBox(10);
        this.hbox.setAlignment(Pos.BOTTOM_LEFT);
        this.hbox.getChildren().add(this.btnListCategory);
        this.grid.add(this.hbox, 0, 3);
        this.btnListCategory.setOnAction(this::ButtonClicked);

        this.hbox = new HBox(10);
        this.hbox.setAlignment(Pos.BOTTOM_RIGHT);
        this.hbox.getChildren().add(this.btnDeleteCategory);
        this.grid.add(this.hbox, 1, 3);
        this.btnDeleteCategory.setOnAction(this::ButtonClicked);

        this.hbox = new HBox(10);
        this.hbox.setAlignment(Pos.BOTTOM_RIGHT);
        this.hbox.getChildren().add(this.btnEditCategory);
        this.grid.add(this.hbox, 2, 3);
        this.btnEditCategory.setOnAction(this::ButtonClicked);

        return this.scene;
    }

    public Scene editCategoryPage(Categorie categorie, String error) {
        this.scene = editCategoryPage(categorie);

        this.scenetitle = new Text(error);
        this.scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        this.scenetitle.setFill(Color.RED);
        this.scenetitle.setWrappingWidth(400);
        this.grid.add(this.scenetitle, 0, 4, 3, 1);

        return this.scene;
    }

    public Scene addSubCategoryPage(){
        this.initGrid();
        this.thestage.setTitle("BankManager | Ajouter Sous-Catégorie");
        this.scene = new Scene(this.grid, 800, 800);

        this.scenetitle = new Text("Ajouter une Sous - Catégorie");
        this.scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        this.grid.add(this.scenetitle, 0, 0, 2, 1);

        this.label = new Label("Nom de la sous-catégorie:");
        this.grid.add(this.label, 0, 2);
        this.textFieldNameCate = new TextField();
        this.grid.add(this.textFieldNameCate, 1, 2);

        this.label = new Label("Categorie");
        this.grid.add(this.label, 0, 3);

        try {
            this.cbCate =  new ChoiceBox(this.dbc.getAllCategoryChoice());
            this.grid.add(this.cbCate, 1, 3);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.hbox = new HBox(10);
        this.hbox.setAlignment(Pos.BOTTOM_LEFT);
        this.hbox.getChildren().add(this.btnListSubCategory);
        this.grid.add(this.hbox, 0, 4);
        this.btnListSubCategory.setOnAction(this::ButtonClicked);

        this.hbox = new HBox(10);
        this.hbox.setAlignment(Pos.BOTTOM_RIGHT);
        this.hbox.getChildren().add(this.btnSaveSubCategory);
        this.grid.add(this.hbox, 1, 4);
        this.btnSaveSubCategory.setOnAction(this::ButtonClicked);

        return this.scene;
    }

    public Scene addSubCategoryPage(String error) {
        this.scene = addSubCategoryPage();

        this.scenetitle = new Text(error);
        this.scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        this.scenetitle.setFill(Color.RED);
        this.scenetitle.setWrappingWidth(400);
        this.grid.add(this.scenetitle, 0, 5, 2, 1);

        return this.scene;
    }

    public Scene editSubCategoryPage(SubCategorie subCategorie){
        this.initGrid();
        this.thestage.setTitle("BankManager | Editer Sous-Catégorie");
        this.scene = new Scene(this.grid, 800, 800);

        this.hiddenSubCategory = subCategorie.getId();

        this.scenetitle = new Text("Editer une Sous - Catégorie");
        this.scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        this.grid.add(this.scenetitle, 0, 0, 2, 1);

        this.label = new Label("Nom de la sous-catégorie:");
        this.grid.add(this.label, 0, 2);
        this.textFieldNameCate = new TextField();
        this.textFieldNameCate.setText(subCategorie.getName());
        this.grid.add(this.textFieldNameCate, 1, 2, 2, 1);

        this.label = new Label("Categorie");
        this.grid.add(this.label, 0, 3);

        try {
            this.cbCate =  new ChoiceBox(this.dbc.getAllCategoryChoice());
            this.cbCate.setValue(subCategorie.getCategorie().getName());
            this.grid.add(this.cbCate, 1, 3, 2, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.hbox = new HBox(10);
        this.hbox.setAlignment(Pos.BOTTOM_LEFT);
        this.hbox.getChildren().add(this.btnListSubCategory);
        this.grid.add(this.hbox, 0, 4);
        this.btnListSubCategory.setOnAction(this::ButtonClicked);

        this.hbox = new HBox(10);
        this.hbox.setAlignment(Pos.BOTTOM_RIGHT);
        this.hbox.getChildren().add(this.btnDeleteSubCategory);
        this.grid.add(this.hbox, 1, 4);
        this.btnDeleteSubCategory.setOnAction(this::ButtonClicked);

        this.hbox = new HBox(10);
        this.hbox.setAlignment(Pos.BOTTOM_RIGHT);
        this.hbox.getChildren().add(this.btnEditSubCategory);
        this.grid.add(this.hbox, 2, 4);
        this.btnEditSubCategory.setOnAction(this::ButtonClicked);

        return this.scene;
    }

    public Scene editSubCategoryPage(SubCategorie subCategorie, String error) {
        this.scene = editSubCategoryPage(subCategorie);

        this.scenetitle = new Text(error);
        this.scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        this.scenetitle.setFill(Color.RED);
        this.scenetitle.setWrappingWidth(400);
        this.grid.add(this.scenetitle, 0, 5, 3, 1);

        return this.scene;
    }
}
