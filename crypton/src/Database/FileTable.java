package Database;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import models.myFile;

public class FileTable extends Database {

    private int accountID = -1;

    public FileTable(String db) {
        super();
    }

    public void addNewFile(myFile newFile) {
        String sql = "INSERT INTO Files (FilePath, FileName, FileExt, FileNotes, Date, Status, AccountID) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newFile.getFilePath());
            pstmt.setString(2, newFile.getFileName());
            pstmt.setString(3, newFile.getFileExt());
            pstmt.setString(4, newFile.getFileNotes());
            pstmt.setString(5, newFile.getDate());
            pstmt.setString(6, newFile.getEnc());
            pstmt.setInt(7, newFile.getAccountID());

            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void removeFile(int fileID) {
        String sql = "DELETE FROM Files WHERE FileID = \"" + fileID + "\"";
        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public ArrayList<myFile> getFileInfo(int accountID) {
        String filePath = null,
                fileName = null,
                fileExt = null,
                fileNotes = null,
                date = null,
                status = null;
        int fileID = 0;

        ArrayList<myFile> fileInfo = new ArrayList<myFile>();

        String sql = "SELECT FilePath, FileName, FileExt, FileNotes, Date, Status, FileID FROM Files WHERE accountID = \"" + accountID + "\"";

        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                filePath = rs.getString("FilePath");
                fileName = rs.getString("FileName");
                fileExt = rs.getString("FileExt");
                fileNotes = rs.getString("FileNotes");
                date = rs.getString("Date");
                status = rs.getString("Status");
                fileID = rs.getInt("FileID");
                System.out.println("STATUS:" + status);

                fileInfo.add(new myFile(fileID, filePath, fileName, fileExt, fileNotes, date, status));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return fileInfo;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    @Override
    public String getEntry(String entry) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void encrypt(myFile file) throws IOException {
        if (file.getFileItemName().contains(".")) {
            String[] tmp = file.getFileItemName().split("\\.");

            String newExt = tmp[1];
            String newPath = file.getFilePath().replace(file.getFileName(), tmp[0]);

            String sql = "INSERT INTO Files (FilePath, FileName, FileExt, FileNotes, Date, Status, AccountID) WHERE FileID = \"" + file.getFileID() + "\""
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = this.connect();
                    PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, newPath);
                pstmt.setString(2, tmp[0]);
                pstmt.setString(3, tmp[1]);
                pstmt.setString(4, file.getFileNotes());
                pstmt.setString(5, file.getDate());
                pstmt.setString(6, file.getEnc());
                pstmt.setInt(7, file.getAccountID());

                Files.move(Paths.get(file.getFilePath()), FileSystems.getDefault().getPath("/users/" + System.getProperty("user.name") + "/Documents/Crypton/Library").resolveSibling(tmp[0]), REPLACE_EXISTING);
                System.out.println("tmp[0]: " + tmp[0]);
                System.out.println("tmp[1]: " + tmp[1]);
                System.out.println("Path: " + file.getFilePath());
                System.out.println("Target: " + FileSystems.getDefault().getPath("/users/" + System.getProperty("user.name") + "/Documents/Crypton/Library"));

                pstmt.executeUpdate();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            System.out.println("Encryption not possible.");
        }
    }

    public void decrypt() {

    }
    
    public void updateEntry(String path, String name,String enc, int fileID){
        String sql = "UPDATE Files SET FilePath = \"" + path + "\", FileName = \"" + name + "\", Status =\"" +enc+"\" WHERE FileID = \"" + fileID + "\"";
        
        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
