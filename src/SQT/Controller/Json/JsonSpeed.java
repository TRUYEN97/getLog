/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.Controller.Json;

import SQT.Controller.Core.AbsMode;
import SQT.Model.ResultTest.Sheet.Proxy.SimpleProxy;
import SQT.Model.ResultTest.baseResult.NodeResult;
import SQT.Model.ResultTest.baseResult.ResultTest;
import SQT.Model.ServiceClass.Service;
import SQT.View.LogAnalysis;
import java.io.BufferedReader;
import java.util.List;
import javax.swing.JOptionPane;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author ADMIN
 */
public class JsonSpeed extends AbsMode {

    private ResultTest resultTest;
    private NodeResult nodeResult;
    private JSONObject dataJson;
    private static final String TEST_NAME = "test_name";
    private static final String KEY_TESTS = "tests";
    private static final String FIND_ITEM_REGEX = "\\{([\\W|\\w](?!\\{))*\"test_name\"\\s*:\\s*\".*%s.*\"([\\W|\\w](?!\\{))*\\}";

    public JsonSpeed(String name, LogAnalysis _ui) {
        super(name, _ui);
    }

    @Override
    protected boolean dataAnalysis(BufferedReader input, String nameFile) {
        String allText = this.service.readFile(nameFile);
        resultTest = new ResultTest(nameFile);
        boolean rs = false;
        for (String title : inputKey.getLineTittleKey()) {
            List<String> jsonItems = service.findStringGroups(allText,
                    String.format(FIND_ITEM_REGEX, title));
            for (String jsonItem : jsonItems) {
                rs = true;
                getAllValueOfTest(new JSONObject(jsonItem));
            }
        } 
        excell.addRow(resultTest);
        return rs;
    }

    private void getAllValueOfTest(JSONObject itemTest) throws JSONException {
        String nameItem = itemTest.getString(TEST_NAME);
        for (String itemKey : inputKey.getLineItemKey()) {
            if (itemKey == null || !itemTest.has(itemKey)) {
                continue;
            }
            getValue(nameItem, itemKey, itemTest.getString(itemKey));
        }
    }

    private void getValue(String title, String nameItem, String value) throws JSONException {
        nodeResult = new NodeResult(title);
        nodeResult.setItem(nameItem);
        nodeResult.setValue(value);
        resultTest.add(nodeResult);
    }

    @Override
    protected boolean init() {
        if (inputKey.getItemKey().isEmpty() && inputKey.getTittleKey().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Key word is empty!!");
            return false;
        }
        this.excell.start(new SimpleProxy());
        return true;
    }

    @Override
    protected void end() {
        this.excell.end();
        this.dataJson = null;
        this.nodeResult = null;
        this.resultTest = null;
    }
}
