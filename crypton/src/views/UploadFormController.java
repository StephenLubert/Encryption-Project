/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import Database.TmpFileTable;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import models.tmpFile;

/**
 * FXML Controller class
 *
 * @author BasicPanda
 */
public class UploadFormController implements Initializable {

    @FXML
    private TextField filepathInput;
    @FXML
    private Button selectBtn;
    @FXML
    private TextField filerenameInput;
    @FXML
    private TextArea filenotesInput;
    @FXML
    private Button submitBtn;
    @FXML
    private Label account_id;

    private JFileChooser selectFile;
    private int accountID;
    private TmpFileTable db = new TmpFileTable("/Database/Crypton.db");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void selectBtnPress(ActionEvent event) {
        selectFile = new JFileChooser();
        int file = selectFile.showOpenDialog(selectFile);

        if (file == JFileChooser.APPROVE_OPTION) {
            filepathInput.setText(selectFile.getSelectedFile().toString());
            filerenameInput.setText(selectFile.getSelectedFile().getName());
        } else {
            System.out.println("No file chosen.");
        }
    }

    @FXML
    private void submitBtnPress(ActionEvent event) throws IOException {
        if (filepathInput != null) {
            String[] tmp = filepathInput.getText().split("\\.");
            if (filerenameInput != null || filerenameInput.equals("")) {
                if (filerenameInput.getText().contains(".")) {
                    System.out.println("Condition 1: " + filerenameInput.getText());
                } else {
                    filerenameInput.setText(filerenameInput.getText() + "." + tmp[1]);
                    System.out.println("Condition 2: " + filerenameInput.getText() + "." + tmp[1]);
                }
            } else {
                filerenameInput.setText(tmp[0] + "." + tmp[1]);
                System.out.println("Condition 3: " + filerenameInput.getText());
            }
            if (filenotesInput == null || filenotesInput.equals("")) {
                this.filenotesInput.setText("Entry Description");
            }
            tmpFile temp = new tmpFile(filepathInput.getText(), filerenameInput.getText(), filenotesInput.getText(), Integer.parseInt(account_id.getText()));
            db.addNewTmp(temp);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Upload.fxml"));
            fxmlLoader.getNamespace().put("labelText", "" + Integer.parseInt(account_id.getText()));
            Parent hom_page_parent = fxmlLoader.load();

            Scene hom_page_scene = new Scene(hom_page_parent);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(hom_page_scene);
            app_stage.show();
        } else {
            System.out.println("Problems with path");
        }
    }

}
