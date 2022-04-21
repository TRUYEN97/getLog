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
public class IQData extends AbsIQ {

    private final RowAnalysis rowAnalysis;
    public IQData(String name, LogAnalysis ui) {
        super(name, ui);
        this.rowAnalysis = new RowAnalysis();
    }

    @Override
    protected boolean isItemOK(String line) {
        return isItemTest(line) && service.isContainOneOf(wareHouse.getItemKey(), getItem(line));
    }

    @Override
    protected boolean isRow(String line) {
        return rowAnalysis.isTitle(line) || isItemTest(line);
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
        nodeResult.setLowLimit(getLowLimit(line));
        nodeResult.setUpLimit(getUpLimit(line));
    }

    @Override
    protected boolean isTrueItem(String line, String item) {
        return isItemTest(line) && getItem(line).equals(item);
    }

    private boolean isItemTest(String line) {
        if (line == null) {
            return false;
        }
        line = line.trim();
        int index = line.indexOf(':');
        return index != -1 && index == 50 && index == line.lastIndexOf(':');
    }

    @Override
    protected String getItem(String line) {
        return rowAnalysis.subAndTrim(line, null, ":");
    }

    private String getValue(String line) {
        String data = rowAnalysis.subAndTrim(line, ":", null);
        return rowAnalysis.getValue(data);
    }

    private String getLowLimit(String line) {
        String data = rowAnalysis.subAndTrim(line, ":", null);
        return rowAnalysis.getLowLimit(data);
    }

    private String getUpLimit(String line) {
        String data = rowAnalysis.subAndTrim(line, ":", null);
        return rowAnalysis.getUpLimit(data);
    }

    private String getUnit(String line) {
        String data = rowAnalysis.subAndTrim(line, ":", null);
        return rowAnalysis.getUnit(data);
    }

    @Override
    protected String getTitle(String line) {
        return rowAnalysis.getTitle(line);
    }

    @Override
    protected boolean isTitle(String line) {
        return rowAnalysis.isTitle(line);
    }

}
