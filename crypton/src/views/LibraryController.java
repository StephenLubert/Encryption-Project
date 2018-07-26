/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import Database.FileTable;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import java.util.ArrayList;
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
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.myFile;

/**
 * FXML Controller class
 *
 * @author BasicPanda
 */
public class LibraryController implements Initializable {

    @FXML
    private Label account_id;
    @FXML
    private Label library_item_name;
    @FXML
    private TextArea library_item_notes;
    @FXML
    private Button rmvBtn;
    @FXML
    private Button decBtn;
    @FXML
    private Button encBtn;
    @FXML
    private Label library_item_date;
    @FXML
    private ImageView library_item_img;
    @FXML
    private AnchorPane scrollPane;
    @FXML
    private ScrollBar scrollbar;
    @FXML
    private TableView<myFile> libraryTable;
    @FXML
    private TableColumn<myFile, String> library_status;
    @FXML
    private TableColumn<myFile, String> library_name;
    @FXML
    private TableColumn<myFile, String> library_date;
    @FXML
    private TableColumn<myFile, String> library_path;
    @FXML
    private Button bkBtn;
    @FXML
    private TextField search;

    private FileTable db = new FileTable("/Database/Crypton.db");
    private int accountID;
    private ArrayList<myFile> files;
    myFile selectedFile;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        accountID = Integer.parseInt(account_id.getText());
        loadTable();
        files = db.getFileInfo(accountID);
        for (myFile a : files) {
            System.out.println("------------------------");
            System.out.println("Path: " + a.getFilePath());
            System.out.println("Date: " + a.getFilePath());
            System.out.println("Enc: " + a.getFilePath());
            System.out.println("Notes: " + a.getFilePath());
            System.out.println("Name: " + a.getFilePath());
            System.out.println("------------------------");
        }
    }

    @FXML
    private void rmvBtnPress(ActionEvent event) {
        try {
            if (db.getFileInfo(Integer.parseInt(account_id.getText())) != null) {
                myFile selectedFile = libraryTable.getSelectionModel().getSelectedItem();
                db.removeFile(selectedFile.getFileID());

                loadTable();
            }
        } catch (Exception e) {
            System.out.println("Error loading files from db");
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void decBtnPress(ActionEvent event) throws IOException {
        System.out.println("DEC PRESSED");
        selectedFile = libraryTable.getSelectionModel().getSelectedItem();
        System.out.println("SelectedFileITem"+selectedFile.getFileItemName());

        File file = new File(selectedFile.getFilePath());
        File file2 = new File(file.getPath()+"."+selectedFile.getFileExt());
        file.renameTo(file2);

        selectedFile.setFilePath(file2.getPath());
        System.out.println("SELECTED FILE PATH: "+file2.getPath());
        selectedFile.setEnc("DEC");
        selectedFile.setFileName(selectedFile.getFileItemName());
        System.out.println("SELECTED FILE ITEM NAME:"+selectedFile.getFileItemName());
        db.updateEntry(selectedFile.getFilePath(),selectedFile.getFileName(),selectedFile.getEnc(),selectedFile.getFileID());
   loadTable();
    }

    @FXML
    private void encBtnPress(ActionEvent event) throws IOException {
        System.out.println("ENC PRESSED");
        selectedFile = libraryTable.getSelectionModel().getSelectedItem();
        String[] tempPath = selectedFile.getFilePath().split("\\.");
        String[] tempName= selectedFile.getFileName().split("\\.");
        System.out.println("SelectedFileITem"+selectedFile.getFileItemName());

        File file = new File(selectedFile.getFilePath());
        File file2 = new File(tempPath[0]);
        file.renameTo(file2);

        selectedFile.setFilePath(file2.getPath());
        System.out.println("SELECTED FILE PATH: "+file2.getPath());
        selectedFile.setEnc("ENC");
        selectedFile.setFileName(tempName[0]);
        db.updateEntry(selectedFile.getFilePath(),selectedFile.getFileName(),selectedFile.getEnc(),selectedFile.getFileID());
    loadTable();
    }

    @FXML
    private void scrollbarPress(MouseEvent event) {
    }

    @FXML
    private void mouseClick(MouseEvent event) {
        selectedFile = libraryTable.getSelectionModel().getSelectedItem();
        library_item_name.setText(selectedFile.getFileItemName());
        library_item_date.setText(selectedFile.getDate());
        library_item_notes.setText(selectedFile.getFileNotes());

        File file = new File(selectedFile.getFilePath());

    }

    @FXML
    private void bkBtnPress(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Navigation.fxml"));
        fxmlLoader.getNamespace().put("labelText", "" + Integer.parseInt(account_id.getText()));
        Parent hom_page_parent = fxmlLoader.load();

        Scene hom_page_scene = new Scene(hom_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(hom_page_scene);
        app_stage.show();
    }

    private void loadTable() {
        try {
            if (db.getFileInfo(Integer.parseInt(account_id.getText())) != null) {
                System.out.println("Start Pull");
                library_status.setCellValueFactory(new PropertyValueFactory<myFile, String>("Enc"));
                library_name.setCellValueFactory(new PropertyValueFactory<myFile, String>("FileItemName"));
                library_date.setCellValueFactory(new PropertyValueFactory<myFile, String>("Date"));
                library_path.setCellValueFactory(new PropertyValueFactory<myFile, String>("FilePath"));
                files = db.getFileInfo(Integer.parseInt(account_id.getText()));
                libraryTable.getItems().setAll(files);
                System.out.println("File: " + files.get(1).getFilePath());
            }
        } catch (Exception e) {
            System.out.println("Error loading files from db");
        }
    }
}
