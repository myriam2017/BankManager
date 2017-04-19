package GestionCompte;

import com.sun.tools.javac.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class DBConnection {

    private String dbDriver;
    private String dbUrl;
    private String user;
    private String userAccount;
    private String password;
    private String client;
    private String clientPassword;
    private Client clientPop;
    private Compte compte;
    private Properties props;
    private Connection conn;
    private Operation operation;
    private Categorie categorie;
    private SubCategorie subCategorie;
    private Client connectedClient;
    private Compte connectedCompte;

    public DBConnection() throws Exception {
        this.dbDriver = "com.mysql.jdbc.Driver";
        this.dbUrl = "jdbc:mysql://localhost";
        this.user = "root";
        this.password = "epharma";
        this.operation = null;
        this.categorie = null;
        this.subCategorie = null;

        this.setProperties();
        this.getSQLConnection();
        this.initDatatable();
    }

    public String hashingPass256(String password) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());

        byte byteData[] = md.digest();

        //convert the byte to hex format method 2
        StringBuilder hexString = new StringBuilder();
        for (byte aByteData : byteData) {
            String hex = Integer.toHexString(0xff & aByteData);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString();
    }

    public boolean checkPasswordClient(String pwd) throws NoSuchAlgorithmException {
        return Objects.equals(this.hashingPass256(pwd), this.connectedClient.getPassword());
    }

    public void connect(String user, String pwd, String userAccount) throws Exception {
        try {
            String hash = "";
            try {
                hash = this.hashingPass256(pwd);
            } catch (NoSuchAlgorithmException e) {
                System.out.println(e.getMessage());
            }

            //initClientInfo
            this.client = user;
            this.clientPassword = pwd;
            this.userAccount = userAccount;

            this.connectedClient = this.getOneClient("username", user);
            if (this.connectedClient == null){
                throw new Exception("Le client " + user + " n'éxiste pas");
            }
            if (!this.checkPasswordClient(pwd)){
                throw new Exception("Le mot de passe pour le client  " + user + " n'est pas correct");
            }

            this.connectedCompte = this.getOneCompteByClient(userAccount, String.valueOf(this.connectedClient.getId()));
            if (this.connectedCompte == null){
                throw new Exception("Le client " + this.connectedClient.getUserName() + " ne possede pas de compte " + userAccount);
            }


        } catch (Exception e){
            throw new Exception(e);
        }
    }

    public Properties setProperties(){
        this.props = new Properties();
        props.put("user", this.user);
        props.put("password", this.password);
        props.put("autoReconnect", "true");
        props.put("useSSL", "false");

        return props;
    }

    public Boolean testConnection(){
        try {
            Class driverObject = Class.forName(this.dbDriver);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private void getSQLConnection() throws Exception {
        if (testConnection()) {
            this.conn = DriverManager.getConnection(this.dbUrl, this.props);
        }else {
            this.conn = null;
        }
    }

    public Boolean initDatatable() throws Exception{
        java.sql.Statement statement = null;
        try{
            if (checkDatabaseExist()) {
                this.dbUrl = this.dbUrl + "/bankManager";
                getSQLConnection();
                System.out.println("La base de donnée bankManager a deja été créée");
                return false;
            }

            statement = this.conn.createStatement();
            System.out.println("Creating database...");
            String sql = "CREATE DATABASE bankManager";
            System.out.println(sql);
            statement.executeUpdate(sql);
            System.out.println("La base de donnée a été créée");
            this.dbUrl = this.dbUrl + "/bankManager";
            getSQLConnection();
            statement = this.conn.createStatement();

            sql = "CREATE TABLE `client` (\n" +
                    "  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,\n" +
                    "  `firstname` varchar(255) DEFAULT '',\n" +
                    "  `lastname` varchar(255) DEFAULT '',\n" +
                    "  `username` varchar(255) DEFAULT '',\n" +
                    "  `password` varchar(255) NOT NULL DEFAULT '',\n" +
                    "  `is_admin` tinyint(1) DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`id`)\n" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;";
            statement.executeUpdate(sql);
            System.out.println("La table client a été créée");

            sql = "CREATE TABLE `compte` (\n" +
                    "  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,\n" +
                    "  `id_client` int(11) unsigned NOT NULL,\n" +
                    "  `username` varchar(255) DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  KEY `id_client` (`id_client`),\n" +
                    "  CONSTRAINT `compte_ibfk_1` FOREIGN KEY (`id_client`) REFERENCES `client` (`id`) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;";
            statement.executeUpdate(sql);
            System.out.println("La table compte a été créée");


            sql = "CREATE TABLE `categorie` (\n" +
                    "  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` varchar(255) DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`id`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=latin1;";
            statement.executeUpdate(sql);
            System.out.println("La table categorie a été créée");

            sql = "CREATE TABLE `sous_categorie` (\n" +
                    "  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` varchar(255) DEFAULT NULL,\n" +
                    "  `categorie` int(11) unsigned NOT NULL,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  KEY `categorie` (`categorie`),\n" +
                    "  CONSTRAINT `sous_categorie_ibfk_1` FOREIGN KEY (`categorie`) REFERENCES `categorie` (`id`) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=latin1;";
            statement.executeUpdate(sql);
            System.out.println("La table sous_categorie a été créée");

            sql = "CREATE TABLE `operation` (\n" +
                    "  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,\n" +
                    "  `id_compte` int(11) unsigned NOT NULL,\n" +
                    "  `name` varchar(255) DEFAULT NULL,\n" +
                    "  `description` text,\n" +
                    "  `type` varchar(255) NOT NULL DEFAULT '',\n" +
                    "  `payment_type` varchar(255) DEFAULT NULL,\n" +
                    "  `categorie` int(11) DEFAULT NULL,\n" +
                    "  `sous_categorie` int(11) DEFAULT NULL,\n" +
                    "  `created_at` datetime DEFAULT NULL,\n" +
                    "  `updated_at` datetime DEFAULT NULL,\n" +
                    "  `amount` float DEFAULT NULL,\n" +
                    "  `poste` varchar(255) DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  KEY `id_compte` (`id_compte`),\n" +
                    "  CONSTRAINT `operation_ibfk_1` FOREIGN KEY (`id_compte`) REFERENCES `compte` (`id`) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=latin1;";
            statement.executeUpdate(sql);
            System.out.println("La table operation a été créée");


            sql = "INSERT INTO `client` (`id`, `firstname`, `lastname`, `username`, `password`, `is_admin`)\n" +
                    "VALUES\n" +
                    "\t(1, 'Super', 'Admin', 'admin', 'f3029a66c61b61b41b428963a2fc134154a5383096c776f3b4064733c5463d90', 1);\n";
            statement.executeUpdate(sql);
            System.out.println("Client Admin créer|Utilisateur: admin , pass:azerty123|");

            sql = "INSERT INTO `compte` (`id`, `id_client`, `username`)\n" +
                    "VALUES\n" +
                    "\t(1, 1, 'Myriam');\n";
            statement.executeUpdate(sql);
            System.out.println("Compte Myriam");

            sql = "INSERT INTO `compte` (`id`, `id_client`, `username`)\n" +
                    "VALUES\n" +
                    "\t(1, 1, 'Nedjoua');\n";
            statement.executeUpdate(sql);
            System.out.println("Compte Nedjoua");

        }catch (Exception e){
            throw new Exception(e);
        }finally{
            try{
                if(statement != null)
                    statement.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        return true;
    }

    public Boolean checkDatabaseExist() throws SQLException {

        ResultSet resultSet = this.conn.getMetaData().getCatalogs();

        while (resultSet.next()) {
            String databaseName = resultSet.getString(1);

            if (Objects.equals(databaseName, "bankManager")){
                return true;
            }
        }
        resultSet.close();

        return false;
    }

    public Client getOneClient(String col, String value){
        Client oneClient = null;
        java.sql.Statement statement = null;
        try{
            statement = this.conn.createStatement();
            String sql = "SELECT * FROM `client` WHERE " + col + " = '" + value + "'";
            System.out.println(sql);
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                oneClient  = new Client(
                        rs.getInt("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("is_admin")
                );
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null)
                    statement.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        return oneClient;
    }

    public Compte getOneCompte(String col, String value){
        Compte oneCompte = null;
        java.sql.Statement statement = null;
        try{
            statement = this.conn.createStatement();
            String sql = "SELECT * FROM `compte` WHERE " + col + " = '" + value + "'";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                oneCompte  = new Compte(
                        rs.getInt("id"),
                        rs.getString("username"),
                        this.getOneClient("id", String.valueOf(rs.getInt("id_client")))
                );
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null)
                    statement.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        return oneCompte;
    }

    public Compte getOneCompteByClient(String valueName, String valueClient){
        Compte oneCompte = null;
        java.sql.Statement statement = null;
        try{
            statement = this.conn.createStatement();
            String sql = "SELECT * FROM `compte` WHERE username = '" + valueName + "' AND id_client = " + valueClient;
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                oneCompte  = new Compte(
                        rs.getInt("id"),
                        rs.getString("username"),
                        this.getOneClient("id", String.valueOf(rs.getInt("id_client")))
                );
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null)
                    statement.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        return oneCompte;
    }

    public ObservableList<Operation> getAllOperation() throws Exception{
        ObservableList<Operation> operations = FXCollections.observableArrayList();
        java.sql.Statement statement = null;
        try{
            statement = this.conn.createStatement();
            String sql = "SELECT * FROM `operation` WHERE id_compte = " + this.connectedCompte.getId();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                this.operation = new Operation(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("type"),
                        rs.getString("payment_type"),
                        this.getOneCategory("id", String.valueOf(rs.getInt("categorie"))),
                        this.getOneSubCategory("id", String.valueOf(rs.getInt("sous_categorie"))),
                        rs.getDate("created_at"),
                        rs.getDate("updated_at"),
                        rs.getFloat("amount"),
                        rs.getString("poste")
                );
                operations.add(this.operation);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null)
                    statement.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        return operations;
    }

    public ObservableList<Categorie> getAllCategory() throws Exception{
        ObservableList<Categorie> categories = FXCollections.observableArrayList();
        java.sql.Statement statement = null;
        try{
            statement = this.conn.createStatement();
            String sql = "SELECT * FROM `categorie`";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                this.categorie = new Categorie(
                        rs.getInt("id"),
                        rs.getString("name")
                );
                categories.add(this.categorie);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null)
                    statement.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        return categories;
    }

    public ObservableList<Client> getAllClient() throws Exception{
        ObservableList<Client> clients = FXCollections.observableArrayList();
        java.sql.Statement statement = null;
        try{
            statement = this.conn.createStatement();
            String sql = "SELECT * FROM `client`";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                this.clientPop = new Client(
                        rs.getInt("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("id")
                );
                clients.add(this.clientPop);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null)
                    statement.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        return clients;
    }

    public ObservableList<Compte> getAllCompte() throws Exception{
        ObservableList<Compte> comptes = FXCollections.observableArrayList();
        java.sql.Statement statement = null;
        try{
            statement = this.conn.createStatement();
            String sql = "SELECT * FROM `compte`";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                this.compte = new Compte(
                        rs.getInt("id"),
                        rs.getString("username"),
                        this.getOneClient("id", String.valueOf(rs.getInt("id_client")))
                );
                comptes.add(this.compte);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null)
                    statement.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        return comptes;
    }

    public ObservableList<SubCategorie> getAllSubCategory() throws Exception{
        ObservableList<SubCategorie> subCategories = FXCollections.observableArrayList();
        java.sql.Statement statement = null;
        try{
            statement = this.conn.createStatement();
            String sql = "SELECT * FROM `sous_categorie`";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                this.subCategorie = new SubCategorie(
                        rs.getInt("id"),
                        rs.getString("name"),
                        this.getOneCategory("id", String.valueOf(rs.getInt("categorie")))
                );
                subCategories.add(this.subCategorie);
            }
        }catch (Exception e){
            throw new Exception(e);
        }finally{
            try{
                if(statement != null)
                    statement.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        return subCategories;
    }

    public Operation getOneOperation(String col, String value){
        Operation oneOperation = null;
        java.sql.Statement statement = null;
        try{
            statement = this.conn.createStatement();
            String sql = "SELECT * FROM `operation` WHERE " + col + " = " + value + "AND id_compte = " + this.connectedCompte.getId();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                oneOperation  = new Operation(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("type"),
                        rs.getString("payment_type"),
                        this.getOneCategory("id", String.valueOf(rs.getInt("categorie"))),
                        this.getOneSubCategory("id", String.valueOf(rs.getInt("sous_categorie"))),
                        rs.getDate("created_at"),
                        rs.getDate("updated_at"),
                        rs.getFloat("amount"),
                        rs.getString("poste")
                );
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null)
                    statement.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        return oneOperation;
    }

    public Categorie getOneCategory(String col, String value){
        Categorie oneCategorie = null;
        java.sql.Statement statement = null;
        try{
            statement = this.conn.createStatement();
            String sql = "SELECT * FROM `categorie` WHERE " + col + " = " + value;
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                oneCategorie= new Categorie(
                        rs.getInt("id"),
                        rs.getString("name")
                );
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null)
                    statement.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        return oneCategorie;
    }

    public SubCategorie getOneSubCategory(String col, String value){
        SubCategorie oneSubCategorie = null;
        java.sql.Statement statement = null;
        try{
            statement = this.conn.createStatement();
            String sql = "SELECT * FROM `sous_categorie` WHERE " + col + " = " + value;
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                oneSubCategorie = new SubCategorie(
                        rs.getInt("id"),
                        rs.getString("name"),
                        this.getOneCategory("id", String.valueOf(rs.getInt("categorie")))
                );
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null)
                    statement.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }

        return oneSubCategorie;
    }

    public ObservableList getAllCategoryChoice() throws Exception{
        ObservableList categories = FXCollections.observableArrayList();
        java.sql.Statement statement = null;
        try{
            statement = this.conn.createStatement();
            String sql = "SELECT * FROM `categorie`";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                categories.add(rs.getString("name"));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null)
                    statement.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        return categories;
    }

    public ObservableList getAllSubCategoryChoice() throws Exception{
        ObservableList subCategories = FXCollections.observableArrayList();
        java.sql.Statement statement = null;
        try{
            statement = this.conn.createStatement();
            String sql = "SELECT * FROM `sous_categorie`";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                subCategories.add(rs.getString("name"));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null)
                    statement.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        return subCategories;
    }

    public ObservableList getAllSubClientChoice() throws Exception{
        ObservableList comptes = FXCollections.observableArrayList();
        java.sql.Statement statement = null;
        try{
            statement = this.conn.createStatement();
            String sql = "SELECT * FROM `client`";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                comptes.add(rs.getString("username"));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null)
                    statement.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        return comptes;
    }

    public void setOperation(String name, String description, String type, String payType, String subCategorie, LocalDate created_at, String amount,  String poste) throws Exception {
        java.sql.Statement statement = null;
        try{
            statement = this.conn.createStatement();
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateFormatCreated = formatter.format(created_at);
            String dateFormatUpdated = formatter.format(now);
            SubCategorie subCateO = this.getOneSubCategory("name", "\"" + subCategorie + "\"");
            Categorie cateO = subCateO.getCategorie();
            String sql = "INSERT INTO `operation` (`id`, `id_compte`, `name`, `description`, `type`, `payment_type`, `categorie`, `sous_categorie`, `created_at`, `updated_at`, `amount`, `poste`)\n" +
                    "VALUES\n" +
                    "\t(DEFAULT, " + this.connectedCompte.getId() + ", '"+ name +"' , '"+ description +"' , '"+ type +"' , '"+ payType +"', "+ cateO.getId() +", "+ subCateO.getId() +", '" + dateFormatCreated + "', '" + dateFormatUpdated + "', " + amount + ", '" + poste + "');\n";

            statement.executeUpdate(sql);

        }catch (Exception e){
            throw new Exception(e);
        }finally{
            try{
                if(statement != null)
                    statement.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void setSubCategory(String name, String categorie) throws Exception {
        java.sql.Statement statement = null;
        try{
            statement = this.conn.createStatement();
            Categorie cateO = this.getOneCategory("name", "\"" + categorie + "\"");
            String sql = "INSERT INTO `sous_categorie` (`id`, `name`, `categorie`)\n" +
                    "VALUES\n" +
                    "\t(DEFAULT, '" + name + "', " + cateO.getId() + ");\n";
            statement.executeUpdate(sql);
        }catch (Exception e){
            throw new Exception(e);
        }finally{
            try{
                if(statement != null)
                    statement.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void setCategory(String name) throws Exception {
        java.sql.Statement statement = null;
        try{
            statement = this.conn.createStatement();
            String sql = "INSERT INTO `categorie` (`id`, `name`)\n" +
                    "VALUES\n" +
                    "\t(DEFAULT, '" + name + "');\n";
            statement.executeUpdate(sql);
        }catch (Exception e){
            throw new Exception(e);
        }finally{
            try{
                if(statement != null)
                    statement.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void setClient(String firstname, String lastname, String username, String password, int admin) throws Exception {
        java.sql.Statement statement = null;
        try{
            statement = this.conn.createStatement();
            String sql = "INSERT INTO `client` (`id`, `firstname`, `lastname`, `username`, `password`, `is_admin`)\n" +
                    "VALUES\n" +
                    "\t(DEFAULT, '"+firstname+"', '"+lastname+"', '"+username+"', '"+this.hashingPass256(password)+"', "+admin+");\n";
            statement.executeUpdate(sql);
        }catch (Exception e){
            throw new Exception(e);
        }finally{
            try{
                if(statement != null)
                    statement.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void setCompte(String username, String client) throws Exception {
        java.sql.Statement statement = null;
        System.out.println("oook");
        try{
            statement = this.conn.createStatement();
            System.out.println("oook");
            Client client1 = this.getOneClient("username",  client);
            System.out.println(client1);
            String sql = "INSERT INTO `compte` (`id`, `id_client`, `username`)\n" +
                    "VALUES\n" +
                    "\t(DEFAULT, "+client1.getId()+", '"+username+"');\n";
            System.out.println(sql);
            statement.executeUpdate(sql);
        }catch (Exception e){
            throw new Exception(e);
        }finally{
            try{
                if(statement != null)
                    statement.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void setOperation(int id, String name, String description, String type, String payType, String subCategorie, LocalDate created_at, String amount,  String poste) throws Exception {
        java.sql.Statement statement = null;
        try{
            statement = this.conn.createStatement();
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateFormatCreated = formatter.format(created_at);
            String dateFormatUpdated = formatter.format(now);
            SubCategorie subCateO = this.getOneSubCategory("name", "\"" + subCategorie + "\"");
            Categorie cateO = subCateO.getCategorie();

            String sql = "UPDATE `operation` SET id='"+id+"', name='"+ name +"' , description='"+ description +"', type='"+ type +"',payment_type='"+ payType +"',categorie='"+cateO.getId()+"',sous_categorie='"+ subCateO.getId() +"', created_at='" + dateFormatCreated + "', updated_at='" + dateFormatUpdated + "', amount='"+amount+"', poste='"+poste+"'  WHERE id="+id;
            System.out.println(sql);

            statement.executeUpdate(sql);
        }catch (Exception e){
            throw new Exception(e);
        }finally{
            try{
                if(statement != null)
                    statement.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void setSubCategory(int id, String name, String categorie) throws Exception {
        java.sql.Statement statement = null;
        try{
            statement = this.conn.createStatement();
            Categorie cateO = this.getOneCategory("name", "\"" + categorie + "\"");
            String sql = "UPDATE `sous_categorie` SET id='"+ id + "', name='" + name + "', categorie='" + cateO.getId() + "' WHERE id="+ id;

            statement.executeUpdate(sql);
        }catch (Exception e){
            throw new Exception(e);
        }finally{
            try{
                if(statement != null)
                    statement.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void setCategory(int id, String name) throws Exception {
        java.sql.Statement statement = null;
        try{
            statement = this.conn.createStatement();
            String sql = "UPDATE `categorie` SET id='"+ id + "', name='" + name + "' WHERE id="+ id;
            statement.executeUpdate(sql);
        }catch (Exception e){
            throw new Exception(e);
        }finally{
            try{
                if(statement != null)
                    statement.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void setClient(int id, String firstname, String lastname, String username, String password, int admin) throws Exception {
        java.sql.Statement statement = null;
        try{
            statement = this.conn.createStatement();
            String sql = "UPDATE `client` SET id='"+ id + "', firstname='" + firstname + "', lastname='" + lastname + ", username='" + username + ", password='" + password + ", is_admin='" + admin + " WHERE id="+ id;
            System.out.println(sql);
            statement.executeUpdate(sql);
        }catch (Exception e){
            throw new Exception(e);
        }finally{
            try{
                if(statement != null)
                    statement.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void setCompte(int id, String username, String client) throws Exception {
        java.sql.Statement statement = null;
        try{
            Client client1 = this.getOneClient("username", "\"" + client + "\"");
            statement = this.conn.createStatement();
            String sql = "UPDATE `compte` SET id='"+ id + "', username='" + username + "', id_client='" + client1.getId() + " WHERE id="+ id;
            System.out.println(sql);
            statement.executeUpdate(sql);
        }catch (Exception e){
            throw new Exception(e);
        }finally{
            try{
                if(statement != null)
                    statement.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void deleteOperation(int id) throws Exception {
        java.sql.Statement statement = null;
        try{
            statement = this.conn.createStatement();

            String sql = "DELETE FROM `operation` WHERE id="+id;
            statement.executeUpdate(sql);
        }catch (Exception e){
            throw new Exception(e);
        }finally{
            try{
                if(statement != null)
                    statement.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void deleteSubCategory(int id) throws Exception {
        java.sql.Statement statement = null;
        try{
            statement = this.conn.createStatement();
            String sql = "DELETE FROM `sous_categorie` WHERE id="+id;

            statement.executeUpdate(sql);
        }catch (Exception e){
            throw new Exception(e);
        }finally{
            try{
                if(statement != null)
                    statement.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void deleteCategory(int id) throws Exception {
        java.sql.Statement statement = null;
        try{
            statement = this.conn.createStatement();
            String sql = "DELETE FROM `categorie` WHERE id="+id;
            statement.executeUpdate(sql);
            sql = "SELECT * FROM `sous_categorie` WHERE categorie = " + id;
            System.out.println(sql);
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                statement = this.conn.createStatement();
                sql = "DELETE FROM `sous_categorie` WHERE id="+String.valueOf(rs.getInt("id"));
                statement.executeUpdate(sql);
                statement.close();
            }
            sql = "SELECT * FROM `operation` WHERE categorie = " + id;
            System.out.println(sql);
            statement = this.conn.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                statement = this.conn.createStatement();
                sql = "DELETE FROM `operation` WHERE id="+String.valueOf(rs.getInt("id"));
                statement.executeUpdate(sql);
                statement.close();
            }
        }catch (Exception e){
            throw new Exception(e);
        }finally{
            try{
                if(statement != null)
                    statement.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void deleteClient(int id) throws Exception {
        java.sql.Statement statement = null;
        try{
            statement = this.conn.createStatement();

            String sql = "DELETE FROM `client` WHERE id="+id;
            statement.executeUpdate(sql);
        }catch (Exception e){
            throw new Exception(e);
        }finally{
            try{
                if(statement != null)
                    statement.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void deleteCompte(int id) throws Exception {
        java.sql.Statement statement = null;
        try{
            statement = this.conn.createStatement();

            String sql = "DELETE FROM `compte` WHERE id="+id;
            statement.executeUpdate(sql);
        }catch (Exception e){
            throw new Exception(e);
        }finally{
            try{
                if(statement != null)
                    statement.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public ArrayList<List<String>> getOperationCsv(){
        ArrayList<List<String>> export = new ArrayList<List<String>>();
        List<String> data;
        java.sql.Statement statement = null;

        data = new ArrayList<String>();
        data.add("id");
        data.add("name");
        data.add("description");
        data.add("type");
        data.add("payment_type");
        data.add("categorie");
        data.add("sous_categorie");
        data.add("created_at");
        data.add("updated_at");
        data.add("amount");
        data.add("poste");

        export.add(data);

        try{
            statement = this.conn.createStatement();
            String sql = "SELECT * FROM `operation` WHERE id_compte = " + this.connectedCompte.getId();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                data = new ArrayList<String>();
                data.add(String.valueOf(rs.getInt("id")));
                data.add(rs.getString("name"));
                data.add(rs.getString("description"));
                data.add(rs.getString("type"));
                data.add(rs.getString("payment_type"));
                data.add(String.valueOf(rs.getInt("categorie")));
                data.add(String.valueOf(rs.getInt("sous_categorie")));
                data.add(String.valueOf(rs.getDate("created_at")));
                data.add(String.valueOf(rs.getDate("updated_at")));
                data.add(String.valueOf(rs.getFloat("amount")));
                data.add(rs.getString("poste"));

                export.add(data);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null)
                    statement.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        return export;
    }

    public ArrayList<List<String>> getCategorieCsv(){
        ArrayList<List<String>> export = new ArrayList<List<String>>();
        List<String> data;
        java.sql.Statement statement = null;

        try{
            statement = this.conn.createStatement();
            String sql = "SELECT * FROM `categorie` ";
            ResultSet rs = statement.executeQuery(sql);
            data = new ArrayList<String>();
            data.add("id");
            data.add("name");

            export.add(data);
            while (rs.next()) {
                data = new ArrayList<String>();
                data.add(String.valueOf(rs.getInt("id")));
                data.add(rs.getString("name"));

                export.add(data);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null)
                    statement.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        return export;
    }

    public ArrayList<List<String>> getSubCategorieCsv(){
        ArrayList<List<String>> export = new ArrayList<List<String>>();
        List<String> data;
        java.sql.Statement statement = null;

        try{
            statement = this.conn.createStatement();
            String sql = "SELECT * FROM `sous_categorie` ";
            ResultSet rs = statement.executeQuery(sql);
            data = new ArrayList<String>();
            data.add("id");
            data.add("name");
            data.add("Categorie");

            export.add(data);
            while (rs.next()) {
                data = new ArrayList<String>();
                data.add(String.valueOf(rs.getInt("id")));
                data.add(rs.getString("name"));
                data.add(this.getOneCategory("id", String.valueOf(rs.getInt("categorie"))).getName());

                export.add(data);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null)
                    statement.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        return export;
    }

    public ArrayList<List<String>> getClientCsv(){
        ArrayList<List<String>> export = new ArrayList<List<String>>();
        List<String> data;
        java.sql.Statement statement = null;

        try{
            statement = this.conn.createStatement();
            String sql = "SELECT * FROM `client` ";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                data = new ArrayList<String>();
                data.add(String.valueOf(rs.getInt("id")));
                data.add(rs.getString("firstname"));
                data.add(rs.getString("lastname"));
                data.add(rs.getString("username"));
                String is_admin = (rs.getInt("is_admin") == 1) ? "Oui" : "Non";
                data.add(is_admin);

                export.add(data);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null)
                    statement.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        return export;
    }

    public ArrayList<List<String>> getCompteCsv(){
        ArrayList<List<String>> export = new ArrayList<List<String>>();
        List<String> data;
        java.sql.Statement statement = null;

        try{
            statement = this.conn.createStatement();
            String sql = "SELECT * FROM `compte` ";
            data = new ArrayList<String>();

            ResultSet rs = statement.executeQuery(sql);
            data.add("id");
            data.add("username");
            data.add("client");

            export.add(data);

            while (rs.next()) {
                data = new ArrayList<String>();
                data.add(String.valueOf(rs.getInt("id")));
                data.add(rs.getString("username"));
                data.add(this.getOneCategory("id", String.valueOf(rs.getInt("id_client"))).getName());

                export.add(data);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null)
                    statement.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        return export;
    }

    public static String ucfirst(String chaine){
        return chaine.substring(0, 1).toUpperCase()+ chaine.substring(1).toLowerCase();
    }

    public ObservableList<PieChart.Data> generatorPie(){
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        java.sql.Statement statement = null;
        try{
            statement = this.conn.createStatement();
            String sql = "SELECT o.`type`, count(o.`type`) AS numRow\n" +
                    "FROM `operation` o\n" +
                    "WHERE o.`id_compte` = " + this.getConnectedCompte().getId() + "\n" +
                    "GROUP BY o.`type`";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                PieChart.Data data = new PieChart.Data(this.ucfirst(rs.getString("type")), rs.getInt("numRow"));
                pieChartData.addAll(data);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null)
                    statement.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }

        return pieChartData;
    }

    public String getDbDriver() {
        return dbDriver;
    }

    public void setDbDriver(String dbDriver) {
        this.dbDriver = dbDriver;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getClientPassword() {
        return clientPassword;
    }

    public void setClientPassword(String clientPassword) {
        this.clientPassword = clientPassword;
    }

    public Properties getProps() {
        return props;
    }

    public void setProps(Properties props) {
        this.props = props;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public SubCategorie getSubCategorie() {
        return subCategorie;
    }

    public void setSubCategorie(SubCategorie subCategorie) {
        this.subCategorie = subCategorie;
    }

    public Client getConnectedClient() {
        return connectedClient;
    }

    public void setConnectedClient(Client connectedClient) {
        this.connectedClient = connectedClient;
    }

    public Compte getConnectedCompte() {
        return connectedCompte;
    }

    public void setConnectedCompte(Compte connectedCompte) {
        this.connectedCompte = connectedCompte;
    }
}
