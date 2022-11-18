/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQT.Controller.Json;

import SQT.Controller.Core.AbsMode;
import SQT.Model.ResultTest.Sheet.Proxy.SimpleProxy;
import SQT.Model.ResultTest.baseResult.NodeResult;
import SQT.Model.ResultTest.baseResult.ResultTest;
import SQT.View.LogAnalysis;
import java.io.BufferedReader;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import netscape.javascript.JSObject;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author ADMIN
 */
public class JsonDataSpeed extends AbsMode {

    private ResultTest resultTest;
    private NodeResult nodeResult;
    private JSONObject dataJson;
    private static final String TEST_NAME = "test_name";
    private static final String KEY_TESTS = "tests";

    public JsonDataSpeed(String name, LogAnalysis _ui) {
        super(name, _ui);
    }

    @Override
    protected boolean dataAnalysis(BufferedReader input, String nameFile) {
        try {
            dataJson = new JSONObject(service.readFile(nameFile));
            resultTest = new ResultTest(nameFile);
            for (String key : dataJson.keySet()) {
                if (!key.equals(KEY_TESTS)) {
                    getValue(key, null, dataJson.getString(key));
                }
            }
            checkItemTest();
            excell.addRow(resultTest);
            return true;
        } catch (JSONException e) {
            return false;
        }
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

    private void checkItemTest() throws JSONException {
        Set<String> titles = inputKey.getLineItemKey();
        for (Object elem : dataJson.getJSONArray(KEY_TESTS)) {
            if (elem instanceof JSObject) {
                JSONObject itemTest = ((JSONObject) elem);
                String nameItem = itemTest.getString(TEST_NAME);
                if (nameItem == null || titles.contains(nameItem)) {
                    continue;
                }
                getAllValueOfTest(itemTest);
            }
        }
    }

    private void getAllValueOfTest(JSONObject itemTest) throws JSONException {
        String nameItem = itemTest.getString(TEST_NAME);
        for (String itemKey : inputKey.getLineTittleKey()) {
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

}
