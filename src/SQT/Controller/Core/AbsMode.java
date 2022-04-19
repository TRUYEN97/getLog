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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private final Timer timer;
    private int index = 0;
    private int success = 0;
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
        this.timer = new Timer(500, new ActionListener() {
            private LogAnalysis ui;

            @Override
            public void actionPerformed(ActionEvent e) {
                this.ui.setValueProcessBar(index, success);
            }

            public ActionListener setUi(LogAnalysis ui) {
                this.ui = ui;
                return this;
            }
        }.setUi(this.ui)) {
            @Override
            public void start() {
                super.start();
                files.clear();
                files.addAll(wareHouse.getAllFile());
                ui.initProcessBar(files.size() - 1);
                ui.setValueProcessBar(0, 0);
            }

            @Override
            public void stop() {
                super.stop();
                ui.setValueProcessBar(index, success);
                ui.endProcessBar();
            }

        };
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
            this.timer.start();
            this.stop = false;
            if (!init()) {
                return;
            }
            BufferedReader input;
            for (File file : this.files) {
                input = this.ioFile.getBufferedReader(file);
                if (input != null && dataAnalysis(input, file.getPath())) {
                    this.success += 1;
                    try {
                        input.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                this.index++;
                if (this.stop) {
                    break;
                }
            }
            System.out.println(System.currentTimeMillis() - begin);
            end();
        } finally {
            this.timer.stop();
            this.success = 0;
            this.index = 0;
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
