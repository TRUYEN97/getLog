/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.Controller.TxtLog;

import SQT.Controller.Core.AbsMode;
import SQT.Model.ResultTest.Sheet.Proxy.UnitProxy;
import SQT.Model.ResultTest.baseResult.NodeResult;
import SQT.Model.ResultTest.baseResult.ResultTest;
import SQT.View.LogAnalysis;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class CycleTimeTxt extends AbsMode {

    private static final String STATION_KEY = "Station =";
    private static final String DEVICSN = "DEVICSN =";
    private static final String HHSN = "HHSN =";
    private static final String START_TIME = "Start at";

    public CycleTimeTxt(String name, LogAnalysis _ui) {
        super(name, _ui);
    }

    @Override
    protected boolean dataAnalysis(BufferedReader input, String nameFile) {
        try {
            ResultTest resultTest = new ResultTest(nameFile);
            getTimeValue(input, resultTest);
            if (resultTest.isEmpty()) {
                return false;
            }
            this.excell.addRow(resultTest);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private void getTimeValue(BufferedReader input, ResultTest resultTest) throws IOException {
        String line;
        NodeResult node;
        boolean itemOk = false;
        try {
            while ((line = input.readLine()) != null) {
                if (isTitleFormat(line) && isTitleKeyword(line, this.inputKey.getTittleKey())) {
                    resultTest.setCurrTitle(getTitleName(line));
                    itemOk = true;
                } else if (itemOk && isCycleTime(line)) {
                    node = getItemTime(resultTest, line);
                    resultTest.add(node);
                } else if (isTestTime(line)) {
                    node = getTestTime(line);
                    resultTest.add(node);
                } else if (isStartTime(line)) {
                    node = getValue(line, "Start time", START_TIME, "");
                    resultTest.add(node);
                } else if (isStaion(line)) {
                    node = getValue(line, "Station name", STATION_KEY, "");
                    resultTest.add(node);
                } else if (isDeviceSN(line)) {
                    node = getValue(line, "Device SN", DEVICSN, "");
                    resultTest.add(node);
                } else if (isHHSN(line)) {
                    node = getValue(line, "HHSN", HHSN, "");
                    resultTest.add(node);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static boolean isTestTime(String line) {
        return line.contains("Total test time:")
                && (line.endsWith(";")
                || line.endsWith("S")
                || line.endsWith("s"));
    }

    private NodeResult getTestTime(String line) {
        NodeResult node;
        node = new NodeResult("Cycle time");
        node.setItem(" ");
        node.setValue(getValue(line).
                replace('S', ' ').
                replace('s', ' ').trim());
        node.setUnit("S");
        return node;
    }

    private NodeResult getItemTime(ResultTest resultTest, String line) {
        NodeResult node;
        node = new NodeResult(resultTest.getCurrTitle());
        node.setItem("");
        node.setValue(getValue(line).replace('S', ' ').trim());
        node.setUnit("S");
        return node;
    }

    private static boolean isTitleFormat(String line) {
        return line.matches(".+ITEM=ID\\[[0-9]+\\]NAME\\[.+\\];");
    }

    @Override
    protected boolean init() {
        this.excell.start(new UnitProxy());
        return true;
    }

    @Override
    protected void end() {
        this.excell.end();
    }

    private boolean isTitleKeyword(String line, List<List<String>> tittleKey) {
        if (tittleKey.isEmpty()) {
            return true;
        }
        for (List<String> keys : tittleKey) {
            if (service.compareContain(line, keys)) {
                return true;
            }
        }
        return false;
    }

    private boolean isCycleTime(String line) {
        return line.contains("Item test time:");
    }

    private String getTitleName(String line) {
        return line.substring(line.lastIndexOf("]NAME[") + 6,
                line.lastIndexOf("];"));
    }

    private String getValue(String line) {
        if (line.trim().endsWith(";")) {
            return service.subStringLastIndex(line, ":", ";");
        } else if (line.trim().endsWith("S")) {
            return service.subStringLastIndex(line, ":", "S");
        } else if (line.trim().endsWith("s")) {
            return service.subStringLastIndex(line, ":", "s");
        } else {
            return service.subStringLastIndex(line, ":", null);
        }
    }

    private boolean isStartTime(String line) {
        return line.contains(START_TIME);
    }

    private boolean isDeviceSN(String line) {
        return line.contains(DEVICSN);
    }

    private boolean isStaion(String line) {
        return line.contains(STATION_KEY);
    }

    private boolean isHHSN(String line) {
        return line.contains(HHSN);
    }

    private NodeResult getValue(String line, String item, String key, String unit) {
        NodeResult node;
        node = new NodeResult(item);
        node.setItem("");
        node.setValue(service.subStringLastIndex(line, key, ";"));
        node.setUnit(unit);
        return node;
    }
}
