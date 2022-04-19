/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Base.ReadAble.AbstractStream;

import Base.Time.WaitTime.AbsTime.Class.TimeS;
import Base.ReadAble.IReadable;
import Base.Time.WaitTime.AbsTime.Class.ITimer;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Administrator
 */
public abstract class AbsStreamReadable implements IReadable {

    protected  InputStream reader;

    protected  AbsStreamReadable() {
    }
    
    protected  AbsStreamReadable(InputStream reader) {
        this();
        this.reader = reader;
    }
    public abstract AbsStreamReadable getReader();
    
    public void setReader(InputStream reader)
    {
        this.reader = reader;
    }

    public String readLine(ITimer time) {
        return readUntil("\r\n", time);
    }

    @Override
    public String readAll(ITimer tiker) {
        return readUntil(null, tiker);
    }

    @Override
    public String readUntil(String regex, ITimer tiker) {
        StringBuffer result = null;
        ITimer theEnd = null;
        try {
            result = new StringBuffer();
            theEnd = new TimeS(5);
            char kiTu;
            while (tiker.onTime() && reader != null) {
                if (reader.available() > 0) {
                    kiTu = (char) reader.read();
                    result.append(kiTu);
                    if (isKeyWord(result.toString(), regex)) {
                        break;
                    }
                    theEnd.update();
                    continue;
                }
                if (!theEnd.onTime()) {
                    break;
                }
            }
            return result.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    protected boolean isKeyWord(String result, String keyWord) {

        if (keyWord != null && result.trim().endsWith(keyWord)) {
            return true;
        }
        return false;
    }

    protected boolean stringNotNull(String string) {
        return string != null;
    }

    public void clearResult() {
        try {
            while (reader != null && reader.available() > 0) {
                reader.read();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
