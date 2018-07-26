package views;

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
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class NavigationController implements Initializable {

    @FXML
    private ImageView uploadImg;
    @FXML
    private Button logoutBtn;
    @FXML
    private ImageView libraryImg;
    @FXML
    private Label account_id;

    private int accountID;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        accountID = Integer.parseInt(account_id.getText());
    }

    @FXML
    private void uploadImgPress(MouseEvent event) throws IOException {
        loadScreen(event, "Upload");
    }

    @FXML
    private void logoutBtnPress(ActionEvent event) throws IOException {
        System.out.println("Logout Pressed");
        Parent hom_page_parent = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
        Scene hom_page_scene = new Scene(hom_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(hom_page_scene);
        app_stage.show();
    }

    @FXML
    private void libraryImgPress(MouseEvent event) throws IOException {
        loadScreen(event, "Library");
    }

    public void loadScreen(MouseEvent loadevent, String loadscreen) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/" + loadscreen + ".fxml"));
        fxmlLoader.getNamespace().put("labelText", "" + Integer.parseInt(account_id.getText()));
        Parent hom_page_parent = fxmlLoader.load();

        Scene hom_page_scene = new Scene(hom_page_parent);
        Stage app_stage = (Stage) ((Node) loadevent.getSource()).getScene().getWindow();
        app_stage.setScene(hom_page_scene);
        app_stage.show();
        System.out.println(loadscreen + ": AccountID - " + accountID);
    }
}
