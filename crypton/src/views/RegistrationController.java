package views;

import Database.UserTable;
import java.awt.AWTException;
import java.awt.Robot;
import static java.awt.event.KeyEvent.VK_BACK_SPACE;
import java.io.IOException;
import static java.lang.Integer.parseInt;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import models.User;

public class RegistrationController implements Initializable {

    @FXML
    private TextField pinInput;
    @FXML
    private TextField emailInput;
    @FXML
    private TextField passInpute;
    @FXML
    private TextField passInput;
    @FXML
    private TextField userInput;
    @FXML
    private Button regBtn;
    @FXML
    private Button cnclBtn;
    @FXML
    private TextField fnameInput;
    @FXML
    private TextField lnameInput;
    @FXML
    private Label registrationFailure;
    @FXML
    private Label account_id;

    private UserTable db = new UserTable("/Database/Crypton.db");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        registrationFailure.setVisible(false);
    }

    @FXML
    private void regBtnPress(ActionEvent event) throws IOException {
        if (passInput.getText().equals(passInpute.getText()) && testInput()) {
            System.out.println("Successful Condition");
            User newUser = new User(userInput.getText(), passInput.getText(), parseInt(pinInput.getText()), emailInput.getText(),
                    fnameInput.getText(), lnameInput.getText());
            System.out.println("User Working");
            db.addNewUser(newUser);
            System.out.println("DB Success");
            System.out.println("Done");
        } else {
            System.out.println("Pass: " + passInput.getText() + " Pass2:" + passInpute.getText());
            System.out.println("Retry");
            registrationFailure.setVisible(true);
        }
        Parent hom_page_parent = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
        Scene hom_page_scene = new Scene(hom_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(hom_page_scene);
        app_stage.show();
    }

    @FXML
    private void cnclBtnPress(ActionEvent event) throws IOException {
        Parent hom_page_parent = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
        Scene hom_page_scene = new Scene(hom_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(hom_page_scene);
        app_stage.show();
    }

    @FXML
    private void intVerify(KeyEvent event) throws AWTException {
        Robot robot = new Robot();

        try {
            String a = pinInput.getText();
            if (pinInput != null) {
                char b = a.charAt(a.length() - 1);
                String c = "" + b;
                System.out.println(Integer.valueOf(c));
            }
        } catch (NumberFormatException e) {
            robot.keyPress(VK_BACK_SPACE);
            robot.keyRelease(VK_BACK_SPACE);
        }
    }

    @FXML
    private void textCheck(InputMethodEvent event
    ) {
    }

    private boolean testInput() {
        if ((userInput.getText() != null && passInput.getText() != null)
                && ((emailInput.getText() != null)
                && (fnameInput.getText() != null && lnameInput.getText() != null))) {
            return true;
        } else {
            return false;
        }
    }
}
