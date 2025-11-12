/* --------------------------------------------------------------------------------
 * WoE Tools
 * 
 * Ecole Centrale Nantes - Septembre 2022
 * Equipe pédagogique Informatique et Mathématiques
 * JY Martin
 * -------------------------------------------------------------------------------- */
package fr.centrale.nantes.worldofecn;


import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;

/**
 * Manage database connectio, saves and retreive informations
 * @author ECN
 */
public class DatabaseTools {

    private String login;
    private String password;
    private String url;
    private Connection connection;

    /**
     * Load infos
     */
    public DatabaseTools() {
        try {
            // Get Properties file
            ResourceBundle properties = ResourceBundle.getBundle(DatabaseTools.class.getPackage().getName() + ".database");

            // USE config parameters
            login = properties.getString("login");
            password = properties.getString("password");
            String server = properties.getString("server");
            String database = properties.getString("database");
            url = "jdbc:postgresql://" + server + "/" + database;

            // Mount driver
            Driver driver = DriverManager.getDriver(url);
            if (driver == null) {
                Class.forName("org.postgresql.Driver");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DatabaseTools.class.getName()).log(Level.SEVERE, null, ex);
            // If driver is not found, cancel url
            url = null;
        }
        this.connection = null;
    }

    /**
     * Get connection to the database
     */
    public void connect() {
        if ((this.connection == null) && (url != null) && (! url.isEmpty())) {
            try {
                this.connection = DriverManager.getConnection(url, login, password);
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Disconnect from database
     */
    public void disconnect() {
        if (this.connection != null) {
            try {
                this.connection.close();
                this.connection = null;
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public Integer getcorresid(String nom,String prenom,String entreprise){
        try{
            String idcorres ="SELECT id FROM correspondant WHERE nom=? AND prenom=? AND entreprise=?";
            PreparedStatement stmt =connection.prepareStatement( idcorres);
            stmt.setString(1, nom);
            stmt.setString(2,prenom);
            stmt.setString(3, entreprise);
            ResultSet rs=stmt.executeQuery();
            if (rs != null){
                if (rs.next()!= false){
                    return (rs.getInt("id"));
                }
            }
        }catch(SQLException ex){
            System.getLogger(DatabaseTools.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }return null;
    }
        
    public void insertSujet(String intitule,String entreprise,String nom,String prenom){
        try{
            Integer idcorres;
            if (getcorresid(nom,prenom,entreprise)!=null){
                idcorres=getcorresid(nom,prenom,entreprise);
            }else{
                String que="INSERT INTO correspondant (nom,prenom,entreprise) VALUES (?,?,?) RETURNING id";
                PreparedStatement st =connection.prepareStatement(que);
                st.setString(1,nom);
                st.setString(2,prenom);
                st.setString(3,entreprise);
                st.executeQuery();
                idcorres=getcorresid(nom,prenom,entreprise);
            }
            String query ="INSERT INTO sujet (intitule,id_correspondant) VALUES (?,?) RETURNING numero";
            PreparedStatement stmt=connection.prepareStatement(query);
            stmt.setString(1,intitule);
            stmt.setInt(2,idcorres);
            stmt.executeQuery();
        }catch(SQLException ex){
            System.getLogger(DatabaseTools.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
}
