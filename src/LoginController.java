import java.io.*;
import java.sql.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.scene.control.Alert.*;

public class LoginController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    // Attributes Login.fxml
    @FXML private PasswordField login_Password;
    @FXML private Text loginText_NeedACC, loginText_SignIn;
    @FXML private TextField login_Username, passwordText;
    @FXML private CheckBox login_Showpassword;
    @FXML private Button loginButton_Login;

    @FXML
    public void switchToRegister(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/GUI/Register.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
     }

    @FXML
    public void SignInOnMouseEntered(MouseEvent event) {
        if (event.getSource().equals(loginButton_Login)) {
            loginText_SignIn.setFont(Font.font("System", FontWeight.BOLD, 12));
        }
    }

    @FXML
    public void SignInOnMouseExited(MouseEvent event) {
        if (event.getSource().equals(loginText_SignIn)) {
            loginText_SignIn.setFont(Font.font("System", FontWeight.NORMAL, 12));
        }
    }

    @FXML
    public void ShowPassword(ActionEvent event) {
        if (event.getSource().equals(login_Showpassword)){

            if (login_Showpassword.isSelected()) {
                passwordText.setText(login_Password.getText());
                passwordText.setVisible(true);
                passwordText.setEditable(false);
                login_Password.setVisible(false);
            }  
            else {
                passwordText.setText(login_Password.getText());
                passwordText.setVisible(false);
                login_Password.setVisible(true);
            }
        }
    }
    
    @FXML
    private void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    @FXML
    public void Login(MouseEvent event) throws IOException, SQLException {
        String username = login_Username.getText();
        String password = login_Password.getText();
        //check field
        if ( username.isEmpty() || password.isEmpty()) {
            showAlert(AlertType.ERROR, "Error", "Please fill Username and Password");
            return;
        }
        
        //find username and password In database
        //try to connect Database
        try (
                // connect driver
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/logindb", "root", "124578");
                // SELECT statement
                PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM username WHERE uname = ? AND pword = ?")
        ) {
            
            // IN DATABASE
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                root = FXMLLoader.load(getClass().getResource("/GUI/Application.fxml"));
                stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            else {
                showAlert(AlertType.ERROR, "Error", "Invalid email or password.");
            }
        }
        catch ( SQLException ex) {
            showAlert(AlertType.ERROR, "Error", "Database error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}