package views;

import Database.FileTable;
import Database.TmpFileTable;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.myFile;
import models.tmpFile;

public class UploadController implements Initializable {

    @FXML
    private Button bkBtn;
    @FXML
    private Button uploadBtn;
    @FXML
    private Button addBtn;
    @FXML
    private Button rmvBtn;
    @FXML
    private ScrollPane uploadMenu;
    @FXML
    private Label account_id;
    @FXML
    private TableView<tmpFile> tmpfileTable;
    @FXML
    private TableColumn<tmpFile, String> fileName;

    @FXML
    private TableColumn<tmpFile, String> filePath;

    private tmpFile temp;
    private myFile fileTemp;
    private ArrayList<tmpFile> tempArr;
    private ArrayList<myFile> mainArr;
    private TmpFileTable db_temp = new TmpFileTable("/Database/Crypton.db");
    private FileTable db_main = new FileTable("/Database/Crypton.db");

    private int accountID;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        verifyFolder();
        accountID = Integer.parseInt(account_id.getText());
        loadTable();
    }

    @FXML
    private void bkBtnPress(ActionEvent event) throws IOException {
        loadScreen(event, "Navigation");
    }

    @FXML
    private void uploadBtnPress(ActionEvent event) throws IOException {
        loadScreen(event, "UploadForm");
    }

    @FXML
    private void addBtnPress(ActionEvent event) throws IOException {


        tempArr = db_temp.getFileInfo(Integer.parseInt(account_id.getText()));
        try {
            addToTable(tempArr);
            clearTable(tempArr);
            loadTable();

        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    @FXML
    private void rmvBtnPress(ActionEvent event) {
        try {
            if (db_temp.getFileInfo(Integer.parseInt(account_id.getText())) != null) {
                tmpFile selectedFile = tmpfileTable.getSelectionModel().getSelectedItem();
                db_temp.removeTmp(selectedFile.getFileID());

                loadTable();
            }
        } catch (Exception e) {
            System.out.println("Error loading files from db");
            System.out.println(e.getMessage());
        }
    }

    public void loadTable() {
        try {
            if (db_temp.getFileInfo(Integer.parseInt(account_id.getText())) != null) {
                System.out.println("Start Pull");
                fileName.setCellValueFactory(new PropertyValueFactory<tmpFile, String>("fileName"));
                filePath.setCellValueFactory(new PropertyValueFactory<tmpFile, String>("filePath"));
                tempArr = db_temp.getFileInfo(Integer.parseInt(account_id.getText()));
                tmpfileTable.getItems().setAll(tempArr);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void loadScreen(ActionEvent loadevent, String loadscreen) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/" + loadscreen + ".fxml"));
        fxmlLoader.getNamespace().put("labelText", "" + Integer.parseInt(account_id.getText()));
        Parent hom_page_parent = fxmlLoader.load();

        Scene hom_page_scene = new Scene(hom_page_parent);
        Stage app_stage = (Stage) ((Node) loadevent.getSource()).getScene().getWindow();
        app_stage.setScene(hom_page_scene);
        app_stage.show();
        System.out.println(loadscreen + ": AccountID - " + accountID);
    }

    public void clearTable(ArrayList<tmpFile> tmp) {
        for (tmpFile a : tmp) {
            System.out.println("Removed: " + a.getFileName() + " @ " + a.getFilePath());
            db_temp.removeTmp(a.getFileID());
        }
    }

    public void myTest(ArrayList<myFile> tmp) {
        for (int i = 0; i < tmp.size(); i++) {
            System.out.println(tmp.get(i).getFileName());
            System.out.println(tmp.get(i).getFilePath());
        }

    }

    public void tmpTest(ArrayList<tmpFile> tmp) {
        for (int i = 0; i < tmp.size(); i++) {
            System.out.println(tmp.get(i).getFileName());
            System.out.println(tmp.get(i).getFilePath());
        }
    }

    public void addToTable(ArrayList<tmpFile> tmp) throws IOException {
        for (tmpFile a : tmp) {

            String[] string = a.getFileName().split("\\.");
            Files.copy(Paths.get(a.getFilePath()), Paths.get("/users/" + System.getProperty("user.name") + "/Documents/Crypton/Library/").resolveSibling(string[0] + "." + string[1]), REPLACE_EXISTING);
            System.out.println("Source:" + a.getFilePath());
            System.out.println("Target:" + System.getenv("SystemDrive") + "/users/" + System.getProperty("user.name") + "/Documents/Crypton/" + string[0] + "." + string[1]);
            fileTemp = new myFile("/users/" + System.getProperty("user.name") + "/Documents/Crypton/" + string[0] + "." + string[1],
                    a.getFileName(), string[1], a.getFileNotes(), accountID);
            db_main.addNewFile(fileTemp);
        }
    }

    public void verifyFolder() {
        try {
            String a = "/users/" + System.getProperty("user.name") + "/Documents/Crypton/Library";
            Path b = Paths.get("/users/" + System.getProperty("user.name") + "/Documents/Crypton/Library");
            if (Files.exists(b)) {
                System.out.println("File Exists in Path.");
            } else {
                File tempFile = new File(a);
                boolean bool = tempFile.mkdirs();

                System.out.print("Directory created? " + bool);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
