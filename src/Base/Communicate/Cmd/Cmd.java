/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Base.Communicate.Cmd;

import Base.Time.WaitTime.AbsTime.Class.ITimer;
import Base.Communicate.ISender;
import Base.ReadAble.AbstractStream.AbsStreamReadable;
import Base.ReadAble.IReadable;
import Base.Time.WaitTime.AbsTime.Class.TimeS;
import java.io.IOException;

/**
 *
 * @author Administrator
 */
public class Cmd implements ISender, IReadable {

    private Process process = null;
    private ProcessBuilder builder = null;
    private AbsStreamReadable reader = null;

    public Cmd(AbsStreamReadable reader) {
        this.reader = reader;
    }

    @Override
    public boolean sendCommand(String command) {
        reader.clearResult();
        return insertCommand(command);
    }

    public boolean pingTo(String url) {
        String cmd = "ping ".concat(url);
        if (sendCommand(cmd)) {
            return reader.readUntil("TTL=", new TimeS(5)).contains("TTL=");
        }
        return false;
    }

    public boolean pingUntilConnect(String url, ITimer timer) {
        String cmd = String.format("ping %s -t", url);
        if (sendCommand(cmd)) {
            return reader.readUntil("TTL=", timer).contains("TTL=");
        }
        return false;
    }

    public boolean onInternet() {
        return pingTo("10.90.0.15");
    }

    public void resetUi() {
        insertCommand("ambitui_new_SQT.bat");
        System.exit(0);
    }

    @Override
    public boolean insertCommand(String command) {
        try {
            destroy();
            builder = new ProcessBuilder("cmd.exe", "/c", command);
            process = builder.start();
            reader.setReader(process.getInputStream());
            Thread.sleep(200);
            return true;
        } catch (InterruptedException ex) {
            return false;
        } catch (IOException ex) {
            return false;
        }
    }

    private void destroy() {
        if (process != null) {
            process.destroy();
        }
    }

    @Override
    public String readLine() {
        return reader.readLine();
    }

    @Override
    public String readAll() {
        return reader.readAll();
    }

    @Override
    public String readAll(ITimer tiker) {
        return reader.readAll(tiker);
    }

    @Override
    public String readUntil(String regex) {
        return reader.readUntil(regex);
    }

    @Override
    public String readUntil(String regex, ITimer tiker) {
        return reader.readUntil(regex, tiker);
    }

}
