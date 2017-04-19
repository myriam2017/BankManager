package GestionCompte;

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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nejoua & Myriam on 28/03/2017.
 */
public class SceneConstructor {

    /* Manager */
    private DBConnection dbc;
    private ExportData exportData;

    /* Field and button */
    private Button btnDeleteOperation, btnDeleteCategory, btnDeleteSubCategory, btnBilan, btnConnexion, btnDeconnexion, btnAddOperation, btnEditOperation,  btnListOperation, btnSaveOperation, btnAddCategory, btnEditCategory, btnSaveCategory, btnListCategory, btnAddSubCategory, btnSaveSubCategory, btnEditSubCategory, btnListSubCategory, btnAddClient, btnSaveClient, btnEditClient, btnListClient, btnDeleteClient, btnAddCompte, btnSaveCompte, btnEditCompte, btnListCompte, btnDeleteCompte, btnExportOperation, btnExportCategorie , btnExportSubCate, btnExportClient, btnExportCompte ;
    private Text scenetitle;
    private Label label;
    private TextField textFieldFirstname, textFieldLastname, textFieldNameOperation, textFieldNameCate, textFieldUser, textFieldUserAccount, textFieldOpType, textFieldAmount, textFieldPoste;
    private TextArea textAreaDescr;
    private PasswordField passField;
    private HBox hbox;
    private Scene scene;
    private GridPane grid;
    private ChoiceBox cbTypePay, cbType,cbCate,cbSubCate,cbClient;
    private Stage thestage;
    private TableView table;
    private VBox vbox;
    private DatePicker dpCreated;
    private int hiddenOperation, hiddenCategory, hiddenSubCategory,hiddenClient, hiddenCompte;
    private Operation rowDataOperation;
    private SubCategorie rowDataSubCate;
    private Categorie rowDataCategorie;
    private Client rowDataClient;
    private Compte rowDataCompte;
    private TextField textFieldUsername;
    private CheckBox checkBoxIsAdmin;

    public SceneConstructor(Stage stage, DBConnection dbc){
        this.dbc = dbc;
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

        this.btnListClient = new Button("Liste des clients");
        this.btnAddClient = new Button("Ajouter des clients");
        this.btnEditClient = new Button("Enregistrer");
        this.btnSaveClient = new Button("Enregistrer");
        this.btnDeleteClient = new Button("Supprimer");

        this.btnListCompte = new Button("Liste des comptes");
        this.btnAddCompte = new Button("Ajouter des comptes");
        this.btnEditCompte = new Button("Enregistrer");
        this.btnSaveCompte = new Button("Enregistrer");
        this.btnDeleteCompte = new Button("Supprimer");

        this.btnExportOperation = new Button("CSV");
        this.btnExportCategorie = new Button("CSV");
        this.btnExportSubCate = new Button("CSV");
        this.btnExportClient = new Button("CSV");
        this.btnExportCompte = new Button("CSV");

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

        this.checkBoxIsAdmin = new CheckBox();

        this.exportData = new ExportData(this.dbc);
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
            try {
                this.dbc = new DBConnection();
            } catch (Exception e1) {
                System.out.println(e1.getMessage());
            }
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
        else if (e.getSource()==this.btnListClient) {
            this.thestage.setScene(this.listClientPage());
        }
        else if (e.getSource()==this.btnListCompte) {
            this.thestage.setScene(this.listComptePage());
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
        else if (e.getSource()==this.btnAddClient) {
            this.thestage.setScene(this.addClientPage());
        }
        else if (e.getSource()==this.btnAddCompte) {
            this.thestage.setScene(this.addComptePage());
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
        else if (e.getSource()==this.btnSaveClient) {
            try {
                int adminSelect = (this.checkBoxIsAdmin.isSelected()) ? 1 : 0;
                this.dbc.setClient(
                        this.textFieldFirstname.getText(),
                        this.textFieldLastname.getText(),
                        this.textFieldUsername.getText(),
                        this.passField.getText(),
                        adminSelect
                );
                this.thestage.setScene(this.listClientPage());
            }catch (Exception ex){
                this.thestage.setScene(this.addClientPage(ex.getMessage()));
            }
        }
        else if (e.getSource()==this.btnSaveCompte) {
            try {
                this.dbc.setCompte(
                        this.textFieldUsername.getText(),
                        this.cbClient.getSelectionModel().getSelectedItem().toString()
                );
                System.out.println("Je rentre pl");
                this.thestage.setScene(this.listComptePage());
            }catch (Exception ex){
                this.thestage.setScene(this.addComptePage(ex.getMessage()));
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
        else if (e.getSource()==this.btnEditClient){
            try {
                int adminSelect = (this.checkBoxIsAdmin.isSelected()) ? 1 : 0;
                this.dbc.setClient(
                        this.hiddenSubCategory,
                        this.textFieldFirstname.getText(),
                        this.textFieldLastname.getText(),
                        this.textFieldUsername.getText(),
                        this.passField.getText(),
                        adminSelect
                );
                this.thestage.setScene(this.listClientPage());
            }catch (Exception ex){
                this.thestage.setScene(this.editClientPage(this.rowDataClient, ex.getMessage()));
            }
        }
        else if (e.getSource()==this.btnEditCompte){
            try {
                this.dbc.setCompte(
                        this.hiddenSubCategory,
                        this.textFieldUsername.getText(),
                        this.cbClient.getSelectionModel().getSelectedItem().toString()
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
        else if (e.getSource()==this.btnDeleteClient){
            try {
                this.dbc.deleteClient(this.hiddenClient);
                this.thestage.setScene(this.listClientPage());
            }catch (Exception ex){
                this.thestage.setScene(this.editClientPage(this.rowDataClient, ex.getMessage()));
            }
        }
        else if (e.getSource()==this.btnDeleteCompte){
            try {
                this.dbc.deleteCompte(this.hiddenCompte);
                this.thestage.setScene(this.listComptePage());
            }catch (Exception ex){
                this.thestage.setScene(this.editComptePage(this.rowDataCompte, ex.getMessage()));
            }
        }
        else if (e.getSource()==this.btnExportClient){
            ArrayList<List<String>> export = this.dbc.getClientCsv();
            this.exportData.exportHasCsv("client", export);
        }
        else if (e.getSource()==this.btnExportOperation){
            ArrayList<List<String>> export = this.dbc.getOperationCsv();
            this.exportData.exportHasCsv("operation", export);
        }
        else if (e.getSource()==this.btnExportCompte){
            ArrayList<List<String>> export = this.dbc.getCompteCsv();
            this.exportData.exportHasCsv("compte", export);
        }
        else if (e.getSource()==this.btnExportCategorie){
            ArrayList<List<String>> export = this.dbc.getCategorieCsv();
            this.exportData.exportHasCsv("categorie", export);
        }
        else if (e.getSource()==this.btnExportSubCate){
            ArrayList<List<String>> export = this.dbc.getSubCategorieCsv();
            this.exportData.exportHasCsv("sous-categorie", export);
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

        if (this.dbc.getConnectedClient().getAdmin() == 1){
            this.hbox = new HBox(20);
            this.hbox.setAlignment(Pos.CENTER);
            this.hbox.getChildren().add(this.btnListCompte);
            this.grid.add(this.hbox, 0, 6);

            this.hbox = new HBox(20);
            this.hbox.setAlignment(Pos.CENTER);
            this.hbox.getChildren().add(this.btnListClient);
            this.grid.add(this.hbox, 0, 7);

            this.btnListCompte.setOnAction(this::ButtonClicked);
            this.btnListClient.setOnAction(this::ButtonClicked);

        }

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
        this.hbox.setAlignment(Pos.CENTER);
        this.hbox.getChildren().add(this.btnAddOperation);
        this.grid.add(this.hbox, 1, 1);
        this.btnAddOperation.setOnAction(this::ButtonClicked);

        this.hbox = new HBox(20);
        this.hbox.setAlignment(Pos.CENTER_RIGHT);
        this.hbox.getChildren().add(this.btnExportOperation);
        this.grid.add(this.hbox, 2, 1);
        this.btnExportOperation.setOnAction(this::ButtonClicked);

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

        this.hbox = new HBox(20);
        this.hbox.setAlignment(Pos.CENTER_RIGHT);
        this.hbox.getChildren().add(this.btnExportCategorie);
        this.grid.add(this.hbox, 2, 1);
        this.btnExportCategorie.setOnAction(this::ButtonClicked);

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

    public Scene listClientPage(){
        this.initGrid();
        this.thestage.setTitle("BankManager | List Client");
        this.scene = new Scene(this.grid, 800, 800);

        this.hbox = new HBox(20);
        this.hbox.setAlignment(Pos.TOP_LEFT);
        this.hbox.getChildren().add(this.btnBilan);
        this.grid.add(this.hbox, 0, 1);
        this.btnBilan.setOnAction(this::ButtonClicked);

        this.hbox = new HBox(20);
        this.hbox.setAlignment(Pos.CENTER_RIGHT);
        this.hbox.getChildren().add(this.btnAddClient);
        this.grid.add(this.hbox, 1, 1);
        this.btnAddClient.setOnAction(this::ButtonClicked);

        this.hbox = new HBox(20);
        this.hbox.setAlignment(Pos.CENTER_RIGHT);
        this.hbox.getChildren().add(this.btnExportClient);
        this.grid.add(this.hbox, 2, 1);
        this.btnExportClient.setOnAction(this::ButtonClicked);

        this.table = new TableView();
        ObservableList<Client> data = null;
        try {
            data = this.dbc.getAllClient();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        TableColumn idCol = new TableColumn("Id");
        idCol.setCellValueFactory(new PropertyValueFactory<Client, String>("idView"));

        TableColumn fnCol = new TableColumn("Prenom");
        fnCol.setCellValueFactory(new PropertyValueFactory<Client, String>("firstnameView"));

        TableColumn lnCol = new TableColumn("Nom");
        lnCol.setCellValueFactory(new PropertyValueFactory<Client, String>("lastnameView"));

        TableColumn unCol = new TableColumn("Nom d'utilisateur");
        unCol.setCellValueFactory(new PropertyValueFactory<Client, String>("usernameView"));

        TableColumn adminCol = new TableColumn("Admin");
        adminCol.setCellValueFactory(new PropertyValueFactory<Client, String>("adminView"));


        this.table.setRowFactory( tv -> {
            TableRow<Client> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    this.rowDataClient = row.getItem();
                    this.thestage.setScene(this.editClientPage(this.rowDataClient));
                }
            });
            return row ;
        });

        if (data != null){
            this.table.setItems(data);
            this.table.getColumns().addAll(idCol, fnCol, lnCol, unCol, adminCol);
        }
        this.vbox = new VBox();
        this.vbox.setSpacing(5);
        this.vbox.setPadding(new Insets(10, 0, 0, 10));
        this.vbox.getChildren().addAll(this.table);
        this.grid.add(this.vbox, 0, 2, 2, 1);

        return this.scene;
    }

    public Scene listComptePage(){
        this.initGrid();
        this.thestage.setTitle("BankManager | List Compte");
        this.scene = new Scene(this.grid, 800, 800);

        this.hbox = new HBox(20);
        this.hbox.setAlignment(Pos.TOP_LEFT);
        this.hbox.getChildren().add(this.btnBilan);
        this.grid.add(this.hbox, 0, 1);
        this.btnBilan.setOnAction(this::ButtonClicked);

        this.hbox = new HBox(20);
        this.hbox.setAlignment(Pos.CENTER_RIGHT);
        this.hbox.getChildren().add(this.btnAddCompte);
        this.grid.add(this.hbox, 1, 1);
        this.btnAddCompte.setOnAction(this::ButtonClicked);

        this.hbox = new HBox(20);
        this.hbox.setAlignment(Pos.CENTER_RIGHT);
        this.hbox.getChildren().add(this.btnExportCompte);
        this.grid.add(this.hbox, 2, 1);
        this.btnExportCompte.setOnAction(this::ButtonClicked);

        this.table = new TableView();
        ObservableList<Compte> data = null;
        try {
            data = this.dbc.getAllCompte();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        TableColumn idCol = new TableColumn("Id");
        idCol.setCellValueFactory(new PropertyValueFactory<Operation, String>("idView"));

        TableColumn userCol = new TableColumn("Nom d'utilisateur");
        userCol.setCellValueFactory(new PropertyValueFactory<Operation, String>("usernameView"));

        TableColumn cliCol = new TableColumn("Lié au compte");
        cliCol.setCellValueFactory(new PropertyValueFactory<Operation, String>("clientView"));

        this.table.setRowFactory( tv -> {
            TableRow<Compte> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    this.rowDataCompte = row.getItem();
                    this.thestage.setScene(this.editComptePage(this.rowDataCompte));
                }
            });
            return row ;
        });

        if (data != null){
            this.table.setItems(data);
            this.table.getColumns().addAll(idCol, userCol, cliCol);
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

        this.hbox = new HBox(20);
        this.hbox.setAlignment(Pos.CENTER_RIGHT);
        this.hbox.getChildren().add(this.btnExportSubCate);
        this.grid.add(this.hbox, 2, 1);
        this.btnExportSubCate.setOnAction(this::ButtonClicked);

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
                "Depense", "Revenue")
        );
        this.grid.add(this.cbType, 1, 2);

        this.label = new Label("Type paiement");
        this.grid.add(this.label, 0, 3);
        this.cbTypePay = new ChoiceBox(FXCollections.observableArrayList(
                "Virement", "Espece", "Retrait", "Cheque", "CB")
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

    public Scene addClientPage(){
        this.initGrid();
        this.thestage.setTitle("BankManager | Ajouter client");
        this.scene = new Scene(this.grid, 800, 800);

        this.scenetitle = new Text("Ajouter un client");
        this.scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        this.grid.add(this.scenetitle, 0, 0, 2, 1);

        this.label = new Label("Nom:");
        this.grid.add(this.label, 0, 1);
        this.textFieldLastname = new TextField();
        this.grid.add(this.textFieldLastname, 1, 1);

        this.label = new Label("Prenom:");
        this.grid.add(this.label, 0, 2);
        this.textFieldFirstname = new TextField();
        this.grid.add(this.textFieldFirstname, 1, 2);

        this.label = new Label("Surnom:");
        this.grid.add(this.label, 0, 3);
        this.textFieldUsername = new TextField();
        this.grid.add(this.textFieldUsername, 1, 3);

        this.label = new Label("Mot de passe:");
        this.grid.add(this.label, 0, 4);
        this.passField = new PasswordField();
        this.grid.add(this.passField, 1, 4);

        this.label = new Label("Admin:");
        this.grid.add(this.label, 0, 5);
        this.grid.add(this.checkBoxIsAdmin, 1, 5);


        this.hbox = new HBox(10);
        this.hbox.setAlignment(Pos.BOTTOM_LEFT);
        this.hbox.getChildren().add(this.btnListClient);
        this.grid.add(this.hbox, 0, 6);
        this.btnListClient.setOnAction(this::ButtonClicked);

        this.hbox = new HBox(10);
        this.hbox.setAlignment(Pos.BOTTOM_RIGHT);
        this.hbox.getChildren().add(this.btnSaveClient);
        this.grid.add(this.hbox, 1, 6);
        this.btnSaveClient.setOnAction(this::ButtonClicked);

        return this.scene;
    }

    public Scene addClientPage(String error) {
        this.scene = addClientPage();

        this.scenetitle = new Text(error);
        this.scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        this.scenetitle.setFill(Color.RED);
        this.scenetitle.setWrappingWidth(400);
        this.grid.add(this.scenetitle, 0, 7, 2, 1);

        return this.scene;
    }

    public Scene editClientPage(Client client){
        this.initGrid();
        this.thestage.setTitle("BankManager | Ajouter client");
        this.scene = new Scene(this.grid, 800, 800);
        
        this.hiddenClient = client.getId();

        this.scenetitle = new Text("Ajouter un client");
        this.scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        this.grid.add(this.scenetitle, 0, 0, 2, 1);

        this.label = new Label("Nom:");
        this.grid.add(this.label, 0, 1);
        this.textFieldLastname = new TextField();
        this.textFieldLastname.setText(client.getLastName());
        this.grid.add(this.textFieldLastname, 1, 1, 2, 1);

        this.label = new Label("Prenom:");
        this.grid.add(this.label, 0, 2);
        this.textFieldFirstname = new TextField();
        this.textFieldFirstname.setText(client.getFirstName());
        this.grid.add(this.textFieldFirstname, 1, 2, 2, 1);

        this.label = new Label("Surnom:");
        this.grid.add(this.label, 0, 3);
        this.textFieldUsername = new TextField();
        this.textFieldUsername.setText(client.getUserName());
        this.grid.add(this.textFieldUsername, 1, 3, 2, 1);

        this.label = new Label("Mot de passe:");
        this.grid.add(this.label, 0, 4);
        this.passField = new PasswordField();
        this.grid.add(this.passField, 1, 4, 2, 1);

        this.label = new Label("Admin:");
        this.grid.add(this.label, 0, 5);
        if (client.getAdmin() == 1){
            this.checkBoxIsAdmin.setSelected(true);
        }else {
            this.checkBoxIsAdmin.setSelected(false);
        }
        this.grid.add(this.checkBoxIsAdmin, 1, 5, 2, 1);


        this.hbox = new HBox(10);
        this.hbox.setAlignment(Pos.BOTTOM_LEFT);
        this.hbox.getChildren().add(this.btnListClient);
        this.grid.add(this.hbox, 0, 6);
        this.btnListClient.setOnAction(this::ButtonClicked);

        this.hbox = new HBox(10);
        this.hbox.setAlignment(Pos.BOTTOM_RIGHT);
        this.hbox.getChildren().add(this.btnDeleteClient);
        this.grid.add(this.hbox, 1, 6);
        this.btnDeleteClient.setOnAction(this::ButtonClicked);

        this.hbox = new HBox(10);
        this.hbox.setAlignment(Pos.BOTTOM_RIGHT);
        this.hbox.getChildren().add(this.btnSaveClient);
        this.grid.add(this.hbox, 2, 6);
        this.btnSaveClient.setOnAction(this::ButtonClicked);

        return this.scene;
    }

    public Scene editClientPage(Client client, String error) {
        this.scene = editClientPage(client);

        this.scenetitle = new Text(error);
        this.scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        this.scenetitle.setFill(Color.RED);
        this.scenetitle.setWrappingWidth(400);
        this.grid.add(this.scenetitle, 0, 5, 2, 1);

        return this.scene;
    }


    public Scene addComptePage(){
        this.initGrid();
        this.thestage.setTitle("BankManager | Ajouter compte");
        this.scene = new Scene(this.grid, 800, 800);

        this.scenetitle = new Text("Ajouter un compte");
        this.scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        this.grid.add(this.scenetitle, 0, 0, 2, 1);

        this.label = new Label("Nom d'utilisateur:");
        this.grid.add(this.label, 0, 1);
        this.textFieldUsername = new TextField();
        this.grid.add(this.textFieldUsername, 1, 1);

        this.label = new Label("Client");
        this.grid.add(this.label, 0, 2);
        try {
            this.cbClient = new ChoiceBox(this.dbc.getAllSubClientChoice());
            this.grid.add(this.cbClient, 1, 2, 2, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.hbox = new HBox(10);
        this.hbox.setAlignment(Pos.BOTTOM_LEFT);
        this.hbox.getChildren().add(this.btnListCompte);
        this.grid.add(this.hbox, 0, 3);
        this.btnListCompte.setOnAction(this::ButtonClicked);

        this.hbox = new HBox(10);
        this.hbox.setAlignment(Pos.BOTTOM_RIGHT);
        this.hbox.getChildren().add(this.btnSaveCompte);
        this.grid.add(this.hbox, 1, 3);
        this.btnSaveCompte.setOnAction(this::ButtonClicked);

        return this.scene;
    }

    public Scene addComptePage(String error) {
        this.scene = addComptePage();

        this.scenetitle = new Text(error);
        this.scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        this.scenetitle.setFill(Color.RED);
        this.scenetitle.setWrappingWidth(400);
        this.grid.add(this.scenetitle, 0, 4, 2, 1);

        return this.scene;
    }

    public Scene editComptePage(Compte compte){
        this.initGrid();
        this.thestage.setTitle("BankManager | Ajouter compte");
        this.scene = new Scene(this.grid, 800, 800);

        this.hiddenCompte = compte.getId();
        
        this.scenetitle = new Text("Ajouter un compte");
        this.scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        this.grid.add(this.scenetitle, 0, 0, 3, 1);

        this.label = new Label("Nom d'utilisateur:");
        this.grid.add(this.label, 0, 1);
        this.textFieldLastname = new TextField();
        this.textFieldLastname.setText(compte.getUserName());
        this.grid.add(this.textFieldLastname, 1, 1, 2, 1);

        this.label = new Label("Client");
        this.grid.add(this.label, 0, 2);
        try {
            this.cbClient = new ChoiceBox(this.dbc.getAllSubClientChoice());
            this.cbClient.setValue(compte.getClient().getUserName());
            this.grid.add(this.cbSubCate, 1, 2, 2, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        this.hbox = new HBox(10);
        this.hbox.setAlignment(Pos.BOTTOM_LEFT);
        this.hbox.getChildren().add(this.btnListCompte);
        this.grid.add(this.hbox, 0, 3);
        this.btnListCompte.setOnAction(this::ButtonClicked);

        this.hbox = new HBox(10);
        this.hbox.setAlignment(Pos.BOTTOM_RIGHT);
        this.hbox.getChildren().add(this.btnSaveCompte);
        this.grid.add(this.hbox, 2, 3);
        this.btnSaveCompte.setOnAction(this::ButtonClicked);

        this.hbox = new HBox(10);
        this.hbox.setAlignment(Pos.BOTTOM_RIGHT);
        this.hbox.getChildren().add(this.btnDeleteCompte);
        this.grid.add(this.hbox, 1, 3);
        this.btnDeleteCompte.setOnAction(this::ButtonClicked);

        return this.scene;
    }

    public Scene editComptePage(Compte compte, String error) {
        this.scene = editComptePage(compte);

        this.scenetitle = new Text(error);
        this.scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        this.scenetitle.setFill(Color.RED);
        this.scenetitle.setWrappingWidth(400);
        this.grid.add(this.scenetitle, 0, 4, 2, 1);

        return this.scene;
    }
}
