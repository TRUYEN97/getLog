/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import javax.swing.JOptionPane;

/**
 *
 * @author 21AK22
 */
public class IOFile {

    public FileInputStream getFileInputStream(File file) {
        return getFileInputStream(file.toPath());
    }

    public FileInputStream getFileInputStream(Path path) {
        return getFileInputStream(path.toString());
    }

    public FileInputStream getFileInputStream(String dir) {
        File file = new File(dir);
        if (!file.isFile()) {
            return null;
        }
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.toString());
        }
        return null;
    }
    
    public FileOutputStream getFileOutStream(String dir) {
        File file = new File(dir);
        new File(file.getParent()).mkdirs();
        try {
            return new FileOutputStream(file);
        } catch (FileNotFoundException ex) {
           ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.toString());
        }
        return null;
    }
    

    public FileWriter getFileWriter(String dir) {
        File file = new File(dir);
        new File(file.getParent()).mkdirs();
        try {
            return new FileWriter(file);
        } catch (IOException ex) {
            System.err.println(ex);
            JOptionPane.showMessageDialog(null, ex.toString());
        }
        return null;
    }

    public BufferedReader getBufferedReader(File file) {
        return getBufferedReader(file.getPath());
    }

    public BufferedReader getBufferedInputStream(Path path) {
        return getBufferedReader(path.toString());
    }

    public BufferedReader getBufferedReader(String dir) {
        FileInputStream input = getFileInputStream(dir);
        if (input == null) {
            return null;
        }
        return new BufferedReader(new InputStreamReader(input));
    }
    
    public BufferedWriter getBufferedWriter(String dir) {
        FileWriter outPut = getFileWriter(dir);
        if (outPut == null) {
            return null;
        }
        return new BufferedWriter(outPut);
    }
    
    

}
