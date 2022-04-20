/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.Controller.IQLog;

import SQT.Controller.Core.AbsMode;
import SQT.Model.IQLogVersion.Format;
import SQT.Model.IQLogVersion.LogFormat;
import SQT.Model.IQLogVersion.TitleNode;
import SQT.Model.ResultTest.Sheet.Proxy.UpLowUnitProxy;
import SQT.Model.ResultTest.baseResult.NodeResult;
import SQT.Model.ResultTest.baseResult.ResultTest;
import SQT.View.LogAnalysis;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author 21AK22
 */
public abstract class AbsIQ extends AbsMode {

    private static final int SUCCESS = -1;
    private static final int OUT_SISE = 0;
    private static final int NOT_TRUE = 1;
    private final LogFormat formats;
    private final List<String> log;
    protected final RowAnalysis rowAnalysis;
    private boolean isLogPass;

    protected AbsIQ(String name, LogAnalysis ui) {
        super(name, ui);
        this.formats = new LogFormat();
        this.rowAnalysis = new RowAnalysis();
        this.log = new ArrayList<>();
        this.isLogPass = false;
    }

    protected abstract boolean isItemOK(String line);

    protected abstract boolean isRow(String line);

    @Override
    protected boolean dataAnalysis(BufferedReader input, String nameFile) {
        try {
            if (!getLog(input)) {
                return false;
            }
            return core(nameFile);
        } finally {
            this.log.clear();
        }
    }

    private boolean getLog(BufferedReader input) {
        try {
            String line;
            this.log.clear();
            if (isHasTrueTitle(input)) {
                while ((line = input.readLine()) != null) {
                    if (isRow(line)) {
                        this.log.add(line);
                    }
                    if (!this.isLogPass && line.contains(" *  P A S S  *")) {
                        this.isLogPass = true;
                    }
                }
                return true;
            }
            return false;
        } catch (IOException ex) {
            System.err.println(ex);
            return false;
        }
    }

    private boolean isHasTrueTitle(BufferedReader input) throws IOException {
        String line;
        while ((line = input.readLine()) != null) {
            if (isTitle(line) && isTrueTitle(line)) {
                this.log.add(line);
                return true;
            }
        }
        return false;
    }

    private boolean core(String nameFile) {
        try {
            if (tryWithFormat(nameFile)) {
                return true;
            }
            if (isLogPass) {
                return walkToGetLogPass(nameFile);
            } else {
                return walkToGetLogFalse(nameFile);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.toString());
            return false;
        }
    }

    private boolean tryWithFormat(String nameFile) {
        int indexHave = 0;
        Format format;
        ResultTest resultTest;
        formats.markIndex();
        while ((format = formats.getFormat()) != null) {
            resultTest = new ResultTest(nameFile);
            boolean success = false;
            for (int indexTitleNode = indexHave;
                    format.haveIndex(indexTitleNode) && indexTitleNode < this.log.size();
                    indexTitleNode++) {
                int result = check(format.getNode(indexTitleNode), resultTest);
                if (result == SUCCESS) {
                    indexHave++;
                    success = true;
                } else if (result == OUT_SISE && !isLogPass) {
                    if (resultTest.isEmpty()) {
                        return false;
                    }
                    this.excell.addRow(resultTest);
                    return true;
                } else {
                    success = false;
                    break;
                }
            }
            if (success) {
                if (resultTest.isEmpty()) {
                    return false;
                }
                this.excell.addRow(resultTest);
                return true;
            }
        }
        return false;
    }

    private boolean walkToGetLogPass(String nameFile) {
        Format newFormat = new Format();
        ResultTest resultTest = new ResultTest(nameFile);
        TitleNode newTitleNode = null;
        NodeResult nodeResult;
        for (int index = 0; index < log.size(); index++) {
            String line = log.get(index);
            if (isTitle(line)) {
                if (newTitleNode != null && newTitleNode.isNotEmpty()) {
                    newFormat.addNewNode(newTitleNode);
                }
                if (isTrueTitle(line)) {
                    String title = rowAnalysis.getTitle(line);
                    newTitleNode = new TitleNode(title);
                    newTitleNode.setTitleIndex(index);
                    resultTest.setCurrTitle(title);
                } else {
                    newTitleNode = null;
                }
            } else if (isItemOK(line) && newTitleNode != null) {
                newTitleNode.addFormatRow(index, getItem(line));
                nodeResult = new NodeResult(resultTest.getCurrTitle());
                setValueToNodeResult(nodeResult, line);
                resultTest.add(nodeResult);
            }
        }
        if (newFormat.isNotEmpty()) {
            this.excell.addRow(resultTest);
            this.formats.addFormat(newFormat);
            return true;
        }
        return false;
    }

    private boolean walkToGetLogFalse(String nameFile) {
        ResultTest resultTest = new ResultTest(nameFile);
        NodeResult nodeResult;
        for (String line : this.log) {
            if (isTitle(line)) {
                if (isTrueTitle(line)) {
                    resultTest.setCurrTitle(rowAnalysis.getTitle(line));
                } else {
                    resultTest.setCurrTitle(null);
                }
            } else if (isItemOK(line) && resultTest.getCurrTitle() != null) {
                nodeResult = new NodeResult(resultTest.getCurrTitle());
                setValueToNodeResult(nodeResult, line);
                resultTest.add(nodeResult);
            }
        }
        if (resultTest.isEmpty()) {
            return false;
        }
        this.excell.addRow(resultTest);
        return true;
    }

    private int check(TitleNode node, ResultTest resultTest) {
        int result = checkTitle(node, resultTest);
        if (result == SUCCESS) {
            return checkAllItem(node, resultTest);
        }
        return result;
    }

    private int checkTitle(TitleNode node, ResultTest resultTest) {
        int row = node.getTitleIndex();
        if (row < this.log.size()) {
            String line = this.log.get(row);
            if (isTitle(line) && titleMath(line, node.getTitle())) {
                resultTest.setCurrTitle(node.getTitle());
                return SUCCESS;
            }
            return NOT_TRUE;
        }
        return OUT_SISE;
    }

    private int checkAllItem(TitleNode node, ResultTest resultTest) {
        if (!node.hasNext()) {
            return OUT_SISE;
        }
        try {
            NodeResult nodeResult;
            while (node.hasNext()) {
                nodeResult = new NodeResult(resultTest.getCurrTitle());
                TitleNode.Entry<Integer, String> next = node.next();
                int result = checkItem(next, nodeResult);
                resultTest.add(nodeResult);
                if (result != SUCCESS) {
                    return result;
                }
            }
            return SUCCESS;
        } finally {
            node.remove();
        }
    }

    private int checkItem(TitleNode.Entry<Integer, String> next, NodeResult nodeResult) {
        int row = next.getRow();
        String item = next.getItem();
        if (row < this.log.size()) {
            String line = this.log.get(row);
            if (isTrueItem(line, item)) {
                setValueToNodeResult(nodeResult, line);
                return SUCCESS;
            }
            return NOT_TRUE;
        }
        return OUT_SISE;
    }
    private boolean isTitle(String line) {
        return rowAnalysis.isTitle(line);
    }

    private boolean isTrueTitle(String line) {
        return service.isContainOneOf(wareHouse.getTittleKey(),
                rowAnalysis.getTitle(line));
    }

    @Override
    protected boolean init() {
        if (wareHouse.getItemKey().isEmpty() && wareHouse.getTittleKey().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Key word is empty!!");
            return false;
        }
        this.excell.start(new UpLowUnitProxy());
        this.formats.clear();
        this.isLogPass = false;
        return true;
    }

    @Override
    protected void end() {
        this.excell.end();
        this.formats.clear();
    }

    protected abstract boolean titleMath(String line, String title);

    protected abstract void setValueToNodeResult(NodeResult nodeResult, String line);

    protected abstract boolean isTrueItem(String line, String item);

    protected abstract String getItem(String line);

}
