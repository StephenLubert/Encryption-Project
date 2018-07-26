package views;

import Database.UserTable;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    @FXML
    private GridPane loginPane;
    @FXML
    private TextField userInput;
    @FXML
    private TextField passInput;
    @FXML
    private Hyperlink signup;
    @FXML
    private Hyperlink forgotPass;
    @FXML
    private Button loginBtn;
    @FXML
    private Label loginFailure;
    @FXML
    private Label account_id;

    private UserTable db = new UserTable("/Database/Crypton.db");
    private String username;
    private String password;
    private int accountID = -1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loginFailure.setVisible(false);
    }

    @FXML
    private void signupPress(ActionEvent event) throws IOException {
        Parent hom_page_parent = FXMLLoader.load(getClass().getResource("/views/Registration.fxml"));
        Scene hom_page_scene = new Scene(hom_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(hom_page_scene);
        app_stage.show();
    }

    @FXML
    private void forgotPassPress(ActionEvent event) {
    }

    @FXML
    private void loginBtnPress(ActionEvent event) throws IOException {
        accountID = authenticationProcess();
        if (accountID > -1) {
            System.out.println("Authentication Success.");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Navigation.fxml"));
            fxmlLoader.getNamespace().put("labelText", "" + accountID);
            Parent hom_page_parent = fxmlLoader.load();

            Scene hom_page_scene = new Scene(hom_page_parent);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(hom_page_scene);
            app_stage.show();
        } else {
            System.out.println("Authentication Error.");
            loginFailure.setVisible(true);
        }
    }

    public int authenticationProcess() {
        username = userInput.getText();
        password = passInput.getText();
        int accountID = db.authenticate(username, password);
        System.out.println("AccountID: " + accountID);
        return accountID;
    }
}
