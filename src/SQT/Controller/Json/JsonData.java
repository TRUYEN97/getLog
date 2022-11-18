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
import javax.swing.JOptionPane;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Administrator
 */
public class JsonData extends AbsMode {

    private ResultTest resultTest;
    private NodeResult nodeResult;
    private JSONObject dataJson;

    public JsonData(String name, LogAnalysis _ui) {
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

    private void checkItemTest() throws JSONException {
        for (Object elem : dataJson.getJSONArray(KEY_TESTS)) {
            JSONObject itemTest = ((JSONObject) elem);
            if (service.isContainOneOf(inputKey.getTittleKey(), itemTest.getString(TEST_NAME))) {
                getAllValueOfTest(itemTest);
            }
        }
    }

    private void getAllValueOfTest(JSONObject itemTest) throws JSONException {
        String nameItem = itemTest.getString(TEST_NAME);
        for (String itemKey : itemTest.keySet()) {
            if (!itemKey.equals(TEST_NAME)) {
                if (service.isContainOneOf(inputKey.getItemKey(), itemKey)) {
                    getValue(nameItem, itemKey, itemTest.getString(itemKey));
                }
            }
        }
    }

    private void getValue(String title, String nameItem, String value) throws JSONException {
        nodeResult = new NodeResult(title);
        nodeResult.setItem(nameItem);
        nodeResult.setValue(value);
        resultTest.add(nodeResult);
    }
    private static final String TEST_NAME = "test_name";
    private static final String KEY_TESTS = "tests";

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
