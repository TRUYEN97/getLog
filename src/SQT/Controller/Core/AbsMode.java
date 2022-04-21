/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.Controller.Core;

import SQT.Model.IOFile;
import SQT.Model.ResultTest.Sheet.AbsSheetType;
import SQT.Model.ResultTest.Excell;
import SQT.Model.ServiceClass.Service;
import SQT.Model.WareHouse;
import SQT.View.LogAnalysis;
import SQT.View.RunFrame;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

/**
 *
 * @author 21AK22
 */
public abstract class AbsMode implements IMode {

    protected static final String NEW_LINE = "\r\n";
    protected final IOFile ioFile;
    protected final Service service;
    private final String name;
    protected final WareHouse wareHouse;
    protected final Excell excell;
    private final LogAnalysis ui;
    private final List<File> files;
    private final RunFrame runFrame;
    private boolean stop;

    protected AbsMode(String name, LogAnalysis _ui) {
        this.name = name;
        this.ioFile = new IOFile();
        this.wareHouse = WareHouse.getInstance();
        service = new Service();
        this.ui = _ui;
        stop = false;
        this.excell = new Excell(_ui);
        this.files = new ArrayList<>();
        this.runFrame = new RunFrame(this);
    }

    public void stopScan() {
        this.stop = true;
    }

    public void setExcellType(AbsSheetType sheetType) {
        if (sheetType == null) {
            return;
        }
        this.excell.setSheetType(sheetType);
    }

    @Override
    public void run() {
        try {
            long begin = System.currentTimeMillis();
            this.files.addAll(wareHouse.getAllFile());
            this.runFrame.run(this.files.size());
            this.stop = false;
            if (!init()) {
                return;
            }
            BufferedReader input;
            for (File file : this.files) {
                input = this.ioFile.getBufferedReader(file);
                if (input != null && dataAnalysis(input, file.getPath())) {
                    this.runFrame.success();
                    try {
                        input.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                if (this.stop) {
                    break;
                }
                this.runFrame.nextIndex();
            }
        } finally {
            this.runFrame.stop();
            this.files.clear();
            end();
        }

    }

    @Override
    public String getName() {
        return name;
    }

    protected abstract boolean dataAnalysis(BufferedReader input, String nameFile);

    protected abstract boolean init();

    protected abstract void end();

    public boolean setTittle() {
        this.wareHouse.clearTittle();
        String data = this.ui.getDataTittle();
        if (data == null || data.isEmpty()) {
            return false;
        }
        for (String line : data.split(";")) {
            if (line.isEmpty()) {
                continue;
            }
            this.wareHouse.addTittle(service.slipKeyWord(line.trim()));
        }
        return true;
    }

    public boolean setItem() {
        this.wareHouse.clearItem();
        String data = this.ui.getDataItem();
        if (data == null || data.isEmpty()) {
            return false;
        }
        for (String line : data.split(";")) {
            if (line.isEmpty()) {
                continue;
            }
            this.wareHouse.addItem(service.slipKeyWord(line.trim()));
        }
        return true;
    }
}
