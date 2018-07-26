package models;

public class tmpFile {

    private String filePath;
    private String fileName;
    private String fileNotes;
    private int accountID;
    private int fileID;

    public int getFileID() {
        return fileID;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileNotes() {
        return fileNotes;
    }

    public void setFileNotes(String fileNotes) {
        this.fileNotes = fileNotes;
    }

    public tmpFile(String filePath, String fileName, String fileNotes, int accountID) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.fileNotes = fileNotes;
        this.accountID = accountID;
    }

    public tmpFile(int fileID, String filePath, String fileName, String fileNotes) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.fileNotes = fileNotes;
        this.fileID = fileID;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }
}
