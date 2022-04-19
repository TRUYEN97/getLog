/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.View;

import SQT.Model.ServiceClass.Service;
import SQT.Model.WareHouse;
import SQT.View.MyChooser.MyChooser;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 21AK22
 */
public class TableService {

    private final JTable table;
    private final DefaultTableModel model;
    private final MyChooser chooser;
    private final WareHouse wareHouse;
    private final LogAnalysis ui;
    private final Service service;
    private final ArrayList<File> listTemp;
    private ArrayList<File> currList;

    public TableService(LogAnalysis ui) {
        this.ui = ui;
        this.table = ui.getTable();
        this.wareHouse = WareHouse.getInstance();
        this.chooser = new MyChooser();
        this.model = (DefaultTableModel) table.getModel();
        this.service = new Service();
        this.listTemp = new ArrayList<>();
        this.currList = new ArrayList<>();
    }

    public boolean append(Object[] value) {
        if (table == null) {
            return false;
        }
        int index = model.getRowCount();
        Object[] dataRow = new Object[value.length + 1];
        dataRow[0] = index;
        System.arraycopy(value, 0, dataRow, 1, value.length);
        model.addRow(dataRow);
        return true;
    }

    public boolean setData(Object[][] row) {
        if (table == null) {
            return false;
        }
        for (Object[] value : row) {
            if (!append(value)) {
                return false;
            }
        }
        return true;
    }

    public boolean clear() {
        if (table == null) {
            return false;
        }
        model.setRowCount(0);
        return true;
    }

    public Object[] getSelectedRows(int col) {
        int rows = this.table.getSelectedRowCount();
        if (rows > 0) {
            Object[] result = new Object[rows];
            int i = 0;
            for (int index : this.table.getSelectedRows()) {
                result[i++] = this.model.getValueAt(index, col);
            }
            return result;
        }
        return null;
    }

    public void openFile() {
        if (getSelectedRowsCount() > 0) {
            if (getSelectedRowsCount() > 5) {
                JOptionPane.showMessageDialog(null, "File < 5!!");
                return;
            }
            for (Integer index : getSelectedRows()) {
                File fileTemp = this.currList.get(index);
                service.openFile(fileTemp);
            }
        }
    }

    public int[] getSelectedRows() {
        int rows = this.table.getSelectedRowCount();
        if (rows > 0) {
            return this.table.getSelectedRows();
        }
        return new int[0];
    }

    public int getSelectedRowsCount() {
        return this.table.getSelectedRowCount();
    }

    public void searchFile() {
        if (wareHouse.getSize() == 0) {
            return;
        }
        String keyWord = JOptionPane.showInputDialog("What are you looking for?");
        if (keyWord == null || keyWord.isBlank()) {
            return;
        }
        this.listTemp.clear();
        for (File file : wareHouse.getAllFile()) {
            if (service.compareContain(file.getName(), service.slipKeyWord(keyWord))) {
                this.listTemp.add(file);
            }
        }
        this.setCurrList(listTemp);
        this.refreshCurr();
        this.listTemp.clear();
    }

    public void deletefile() {
        if (getSelectedRowsCount() > 0) {
            if (JOptionPane.showConfirmDialog(null,
                    "Bạn chuẩn bị xóa những file này ra khỏi danh sách!",
                    "Warning", JOptionPane.YES_NO_OPTION)
                    != JOptionPane.OK_OPTION) {
                return;
            }
            this.listTemp.clear();
            this.listTemp.addAll(this.currList);
            for (Integer index : getSelectedRows()) {
                File fileTemp = this.listTemp.get(index);
                wareHouse.getAllFile().remove(fileTemp);
                this.currList.remove(fileTemp);
            }
            this.refreshCurr();
            this.listTemp.clear();
        }
    }

    public void addAll(ArrayList<File> files) {
        setCurrList(files);
        refreshCurr();
    }

    public void add(File file) {
        String dateTime = new Service().getDate(file.lastModified());
        append(new Object[]{file.getParent(), file.getName(), dateTime});
    }

    public void copyFile() {
        if (getSelectedRowsCount() > 0) {
            if (chooser.showSelectFolder(ui) == JFileChooser.APPROVE_OPTION) {
                copyFrame frameCopy = new copyFrame();
                frameCopy.setVisible(true);
                frameCopy.run(getSelectedRows(), chooser.getSelectedFile().getPath());
            }
        }
    }

    private void refreshCurr() {
        new Thread() {
            @Override
            public void run() {
                clear();
                for (File file : currList) {
                    add(file);
                }
            }
        }.start();
    }

    private void setCurrList(ArrayList<File> listTemp) {
        this.currList = (ArrayList<File>) listTemp.clone();
    }

    public void refresh() {
        setCurrList(wareHouse.getAllFile());
        refreshCurr();
    }
}
