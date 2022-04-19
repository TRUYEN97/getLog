/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.Controller;

import SQT.Model.MyFileFilter;
import SQT.Model.MyNodeTree;
import SQT.Model.WareHouse;
import SQT.View.LogAnalysis;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.Timer;

/**
 *
 * @author 21AK22
 */
public class FileScans {

    private boolean stopScan;
    private Thread scanFile;
    private final WareHouse wareHouse;
    private final LogAnalysis ui;
    private final Timer timer;

    public FileScans(LogAnalysis _ui) {
        this.stopScan = false;
        this.wareHouse = WareHouse.getInstance();
        this.ui = _ui;
        this.timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.getListSun().setText(wareHouse.getSizeToString());
            }
        });
        this.timer.start();
    }

    public boolean isFileScanning() {
        return this.scanFile != null && this.scanFile.isAlive();
    }

    public void stopScanFile() {
        if (this.isFileScanning()) {
            stopScan = true;
        }
    }

    public void scanAllFle() {
        if (isFileScanning()) {
            return;
        }
        this.scanFile = new Thread() {
            @Override
            public void run() {
                MyFileFilter filter = new MyFileFilter();
                filter.setContain(ui.getTxtContain().getText());
                filter.setMatch(ui.getTxtMatch().getText());
                ui.getTableService().clear();
                stopScan = false;
                wareHouse.clearListFile();
                MyNodeTree<File> root = (MyNodeTree<File>) ui.getTree().getModel().getRoot();
                visitAllNodes(root, filter);
                ui.getTableService().addAll(wareHouse.getAllFile());
            }

        };
        this.scanFile.start();
    }

    private void visitAllNodes(MyNodeTree<File> node, MyFileFilter filter) {
        if (node.getValue() == null || this.stopScan) {
            return;
        }
        if (node.getChildCount() == 1 && ((MyNodeTree<File>) node.getLastChild()).getValue() == null) {
            getAllFile(node.getValue(), filter);
        } else {
            getFile(node.getValue(), filter);
        }
        for (Enumeration enumeration = node.children(); enumeration.hasMoreElements();) {
            MyNodeTree<File> nodeFile = (MyNodeTree<File>) enumeration.nextElement();
            visitAllNodes(nodeFile, filter);
        }

    }

    public ArrayList<File> getFile(File follder, MyFileFilter filter) {
        ArrayList<File> listFile = new ArrayList<>();
        if (follder == null || !follder.exists()) {
            return listFile;
        }
        if (follder.isDirectory() && !follder.isHidden()) {
            for (File file : follder.listFiles()) {
                if (filter.getFilterJustFile().accept(file)) {
                    wareHouse.add(file);
                }
            }
        }
        return listFile;
    }

    private ArrayList<File> getAllFile(File folder, MyFileFilter filter) {
        ArrayList<File> listFile = new ArrayList<>();
        if (folder == null || !folder.exists()) {
            return listFile;
        }
        for (File file : folder.listFiles()) {
            if (file.isHidden()) {
                continue;
            }
            if (file.isDirectory()) {
                getAllFile(file, filter);
            } else if(filter.getFilterJustFile().accept(file)){
                wareHouse.add(file);
            }
        }
        return listFile;
    }
}
