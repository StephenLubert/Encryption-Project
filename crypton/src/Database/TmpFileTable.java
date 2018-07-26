package Database;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import models.User;
import models.tmpFile;

public class TmpFileTable extends Database {

    private int accountID = -1;

    public TmpFileTable(String db) {
        super();
    }

    public void addNewTmp(tmpFile newTmpFile) {
        String sql = "INSERT INTO TmpFiles (FilePath, FileName, FileNotes, AccountID) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newTmpFile.getFilePath());
            pstmt.setString(2, newTmpFile.getFileName());
            pstmt.setString(3, newTmpFile.getFileNotes());
            pstmt.setInt(4, newTmpFile.getAccountID());

            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void removeTmp(int fileID) {
        String sql = "DELETE FROM TmpFiles WHERE TmpFileID = \"" + fileID + "\"";

        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public ArrayList<tmpFile> getFileInfo(int accountID) {
        String filePath = null,
                fileName = null,
                fileNotes = null;
        tmpFile tmp;
        int fileID = 0;
        ArrayList<tmpFile> fileInfo = new ArrayList<tmpFile>();

        String sql = "SELECT FilePath, FileName, FileNotes, TmpFileID FROM TmpFiles WHERE accountID = \"" + accountID + "\"";

        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                filePath = rs.getString("FilePath");
                fileName = rs.getString("FileName");
                fileNotes = rs.getString("FileNotes");
                fileID = rs.getInt("TmpFileID");
                tmp = new tmpFile(fileID, filePath, fileName, fileNotes);
                fileInfo.add(tmp);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return fileInfo;
    }

    public void getTmpFile(int accountID) {

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

}
