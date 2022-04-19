/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.Controller.Core;

import SQT.View.LogAnalysis;

/**
 *
 * @author 21AK22
 */
public class Engine implements Runnable {

    private final LogAnalysis ui;
    private AbsMode mode;
    private Thread thread;

    public Engine(LogAnalysis ui) {
        this.ui = ui;
    }

    public String getName() {
        if (mode == null) {
            return null;
        }
        return mode.getName();
    }

    public boolean setMode(AbsMode mode) {
        if (mode == null) {
            return false;
        }
        this.mode = mode;
        return true;
    }

    @Override
    public synchronized void run() {
        if (isRun()) {
            return;
        }
        if (!init()) {
            return;
        }
        this.thread = new Thread(mode);
        this.thread.start();
    }

    private boolean init() {
        this.mode = this.ui.getMode();
        this.mode.setExcellType(this.ui.getExcelType());
        if (this.mode != null) {
            this.mode.setItem();
            this.mode.setTittle();
            return true;
        }
        return false;
    }

    public boolean isRun() {
        return this.thread != null && this.thread.isAlive();
    }

    public void stop() {
        if (isRun() && mode != null) {
            mode.stopScan();
        }
    }
}
