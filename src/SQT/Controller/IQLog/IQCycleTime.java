/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.Controller.IQLog;

import SQT.Model.ResultTest.baseResult.NodeResult;
import SQT.View.LogAnalysis;

/**
 *
 * @author 21AK22
 */
public class IQCycleTime extends AbsIQ {

    public IQCycleTime(String name, LogAnalysis _ui) {
        super(name, _ui);
    }

    @Override
    protected boolean isItemOK(String line) {
        return isTestTime(line) || isCycleTime(line);
    }

    @Override
    protected boolean isRow(String line) {
        return rowAnalysis.isTitle(line) || isItemOK(line);
    }

    @Override
    protected boolean titleMath(String line, String title) {
        return rowAnalysis.getTitle(line).equals(title);
    }

    @Override
    protected void setValueToNodeResult(NodeResult nodeResult, String line) {
        nodeResult.setItem(getItem(line));
        nodeResult.setValue(getValue(line));
        nodeResult.setUnit(getUnit(line));
    }

    @Override
    protected boolean isTrueItem(String line, String item) {
        return isItemOK(line);
    }

    private boolean isTestTime(String line) {
        return line.startsWith("Test Time =");
    }

    private boolean isCycleTime(String line) {
        return line.startsWith("    Test Function Time            :");
    }

    @Override
    protected String getItem(String line) {
        if (isCycleTime(line)) {
            return "Cycle time";
        }
        return "";
    }

    private String getValue(String line) {
        if (isCycleTime(line)) {
            String data = rowAnalysis.subAndTrim(line, ":", null);
            return rowAnalysis.getValue(data);
        }
        String data = rowAnalysis.subAndTrim(line, "=", null);
        return rowAnalysis.getValue(data);
    }

    private String getUnit(String line) {
        if (isCycleTime(line)) {
            String data = rowAnalysis.subAndTrim(line, ":", null);
            return rowAnalysis.getUnit(data);
        }
        String data = rowAnalysis.subAndTrim(line, "=", null);
        return rowAnalysis.getUnit(data);
    }

}
