package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.Icon;
//
public class myFile {

    private String filePath;
    private String fileName;
    private String fileExt;
    private String fileNotes;
    private String date;
    private String enc;
    private int fileID;
    private int accountID;

    private String fileItemName;

    public myFile() {

    }

    public myFile(String filePath, String fileName, String fileExt, String fileNotes, int accountID) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();

        this.filePath = filePath;
        this.fileName = fileName;
        this.fileExt = fileExt;
        this.fileNotes = fileNotes;
        this.date = dtf.format(now);
        this.enc = "DEC";
        this.accountID = accountID;
        String[] temp = fileName.split("\\.");
        this.fileItemName=temp[0]+"."+this.fileExt;
    }

    public myFile(int fileID, String filePath, String fileName, String fileExt, String fileNotes, String date, String status) {
        this.fileID = fileID;
        this.filePath = filePath;
        this.fileName = fileName;
        this.fileExt = fileExt;
        this.fileNotes = fileNotes;
        this.date = date;
        this.enc = status;
        String[] temp = fileName.split("\\.");
        this.fileItemName=temp[0]+"."+this.fileExt;
    }

    public String getFileItemName() {
        return fileItemName;
    }

    public void setFileItemName(String fileItemName) {
        this.fileItemName = fileItemName;
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

    public String getFileExt() {
        return fileExt;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public String getEnc() {
        return enc;
    }

    public String getFileNotes() {
        return fileNotes;
    }

    public void setFileNotes(String fileNotes) {
        this.fileNotes = fileNotes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getFileID() {
        return fileID;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public void setEnc(String enc) {
        this.enc = enc;
    }

}
