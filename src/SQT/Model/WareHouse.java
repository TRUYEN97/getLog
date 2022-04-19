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
    private final List<List<String>> tittle;
    private final List<List<String>> item;
    private final ArrayList<File> allFile;
    private String pathExcellTemp;

    private WareHouse() {
        this.tittle = new ArrayList<>();
        this.item = new ArrayList<>();
        this.allFile = new ArrayList<>();
    }

    public static WareHouse getInstance() {
        return instance;
    }

    public boolean addTittle(List<String> title) {
        if (title == null) {
            return false;
        }
        return this.tittle.add(title);
    }

    public boolean addItem(List<String> item) {
        if (item == null) {
            return false;
        }
        return this.item.add(item);
    }

    public List<List<String>> getTittleKey() {
        return tittle;
    }

    public List<List<String>> getItemKey() {
        return item;
    }

    public void clearItem() {
        this.item.clear();
    }

    public void clearTittle() {
        this.tittle.clear();
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
