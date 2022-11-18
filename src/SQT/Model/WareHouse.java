/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.Model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 21AK22
 */
public class WareHouse {

    private static volatile WareHouse instance = new WareHouse();
    private final ArrayList<File> allFile;
    private String pathExcellTemp;

    private WareHouse() {
       
        this.allFile = new ArrayList<>();
    }

    public static WareHouse getInstance() {
        return instance;
    }

   

    public ArrayList<File> getAllFile() {
        return this.allFile;
    }

    public int addAll(List<File> files) {
        this.allFile.addAll(files);
        return this.allFile.size();
    }

    public void clearListFile() {
        this.allFile.clear();
    }

    public File getFileAt(int index) {
        if (this.allFile.size() > index && index > -1) {
            return this.allFile.get(index);
        }
        return null;
    }

    public void setPathExcellFileTemp(String path) {
        this.pathExcellTemp = path;
    }

    public String getPathExcellTemp() {
        return pathExcellTemp;
    }

    public String getSizeToString() {
        return String.valueOf(this.allFile.size());
    }
    public int getSize() {
        return this.allFile.size();
    }

    public void add(File file) {
        if (file != null) {
            this.allFile.add(file);
        }
    }
}
