import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Alert.*;

public class RegisterController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    // Attributes Register.fxml
    @FXML private Button regButton_SignUp;
    @FXML private CheckBox regCB_Agree, regShowConPassword, regShowPassword;
    @FXML private PasswordField regConpassword, regPassword;
    @FXML private TextField regCpass, regLname, regNname, regPass, regUsername, regPassShow, regConPassShow;
    @FXML private Text regText_Login, regText_Privacy, regText_SignUp, regText_Title, regText_haveACC;

    @FXML
    private void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void SwitchToLogin(MouseEvent event) throws IOException {
        if (event.getSource() == regText_Login) {
            root = FXMLLoader.load(getClass().getResource("/GUI/Login.fxml")); // get url
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // get stage from getwindow()
            scene = new Scene(root); // pakaddd
            stage.setScene(scene); // build
            stage.show(); // show
        }
    }

    @FXML
    public void RegisterAndSwitchToLogin(MouseEvent event) throws IOException {
        // check regCB_Agree
        if (!regCB_Agree.isSelected()) {
            showAlert(AlertType.ERROR, "Error", "Please agree to the Consent Agreement Form.");
            return;
        }

        String firstName = regNname.getText();
        String lastName = regLname.getText();
        String username = regUsername.getText();
        String password = regPassword.getText();
        String confirmPassword = regConpassword.getText();

        // condition all field has text
        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(AlertType.ERROR, "Error", "Please fill in all fields.");
            return;
        }

        // condition password are matching
        if (!password.equals(confirmPassword)) {
            showAlert(AlertType.ERROR, "Error", "Passwords do not match.");
            return;
        }

        try (
                // connect driver
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/logindb", "root", "124578"); 
                // prepared statement (data follow collum in table)
                PreparedStatement pstmt = connection.prepareStatement("INSERT INTO username (fname, lname, uname, pword) VALUES (?, ?, ?, ?)")
                ) 
        {

            if (connection != null) {
                // set data
                pstmt.setString(1, firstName);
                pstmt.setString(2, lastName);
                pstmt.setString(3, username);
                pstmt.setString(4, password);
                // Insert
                pstmt.executeUpdate();
            }

            showAlert(AlertType.INFORMATION, "Success", "Registration successful!");
            root = FXMLLoader.load(getClass().getResource("/GUI/Login.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            connection.close();
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Error", "Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void TextOnMouseEntered(MouseEvent event) {
        if (event.getSource() == regText_Privacy) {
            regText_Privacy.setFill(Color.web("#A1E3F9"));
        } else if (event.getSource() == regText_Login) {
            regText_Login.setFill(Color.web("#A1E3F9"));
        }
    }

    @FXML
    public void TextMouseExited(MouseEvent event) {
        if (event.getSource() == regText_Privacy) {
            regText_Privacy.setFill(Color.web("#3674b5"));
        } else if (event.getSource() == regText_Login) {
            regText_Login.setFill(Color.web("#3674b5"));
        }
    }

    @FXML
    public void ShowPassword(ActionEvent event) {
        if (event.getSource() == regShowPassword) {
            if (regShowPassword.isSelected()) {
                regPassShow.setText(regPassword.getText());
                regPassShow.setVisible(true);
                regPassShow.setEditable(false);
                regPassword.setVisible(false);
            } else {
                regPassword.setText(regPassShow.getText());
                regPassShow.setVisible(false);
                regPassword.setVisible(true);
            }
        } else if (event.getSource() == regShowConPassword) {
            if (regShowConPassword.isSelected()) {
                regConPassShow.setText(regConpassword.getText());
                regConPassShow.setVisible(true);
                regConPassShow.setEditable(false);
                regConpassword.setVisible(false);
            } else {
                regConpassword.setText(regConPassShow.getText());
                regConPassShow.setVisible(false);
                regConpassword.setVisible(true);
            }
        }
    }
}
