/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.Controller.IQLog;

import SQT.Model.ResultTest.Sheet.Proxy.UnitProxy;
import SQT.Model.ResultTest.Sheet.Proxy.UpLowUnitProxy;
import SQT.Model.ResultTest.baseResult.NodeResult;
import SQT.View.LogAnalysis;
import javax.swing.JOptionPane;

/**
 *
 * @author 21AK22
 */
public class IQCycleTime extends AbsIQ {

    private final RowAnalysis rowAnalysis;

    public IQCycleTime(String name, LogAnalysis _ui) {
        super(name, _ui);
        this.rowAnalysis = new RowAnalysis();
    }

    @Override
    protected boolean init() {
        if (inputKey.getItemKey().isEmpty() && inputKey.getTittleKey().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Key word is empty!!");
            return false;
        }
        this.excell.start(new UnitProxy());
        this.formats.clear();
        return true;
    }
    

    @Override
    protected boolean isItemOK(String line) {
        return isTestTime(line) || isFlowFuncTime(line) || isTestFuncTime(line);
    }

    @Override
    protected boolean isRow(String line) {
        return rowAnalysis.isTitle(line) || isItemOK(line) || isTitleCycleTime(line);
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

    private boolean isTestFuncTime(String line) {
        return line.startsWith("    Test Function Time            :");
    }

    @Override
    protected String getItem(String line) {
        if (isTestFuncTime(line)) {
            return "Test Function Time";
        }
        if (isFlowFuncTime(line)) {
            return "Flow Run Time";
        }
        return "";
    }

    private String getValue(String line) {
        if (isFlowFuncTime(line) || isTestFuncTime(line)) {
            String data = rowAnalysis.subAndTrim(line, ":", null);
            return rowAnalysis.getValue(data);
        }
        String data = rowAnalysis.subAndTrim(line, "=", null);
        return rowAnalysis.getValue(data);
    }

    private String getUnit(String line) {
        if (isFlowFuncTime(line) || isTestFuncTime(line)) {
            String data = rowAnalysis.subAndTrim(line, ":", null);
            return rowAnalysis.getUnit(data);
        }
        String data = rowAnalysis.subAndTrim(line, "=", null);
        return rowAnalysis.getUnit(data);
    }

    private boolean isTitleCycleTime(String line) {
        return line.startsWith("In This Run:");
    }

    private boolean isFlowFuncTime(String line) {
        return line.startsWith("    Flow Run Time                 :");
    }

    @Override
    protected String getTitle(String line) {
        if (isTitleCycleTime(line)) {
            return "Cycle time";
        }
        return rowAnalysis.getTitle(line);
    }

    @Override
    protected boolean isTitle(String line) {
        return rowAnalysis.isTitle(line) || isTitleCycleTime(line);
    }

}
