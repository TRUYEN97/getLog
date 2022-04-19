/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Base.ReadAble.AbstractStream.SubClass;

import Base.ReadAble.AbstractStream.AbsStreamReadable;
import Base.Time.WaitTime.AbsTime.Class.TimeS;
import java.io.InputStream;

/**
 *
 * @author Administrator
 */
public class ReadStreamOnTime extends AbsStreamReadable {

    private static final int MAX = Integer.MAX_VALUE;

    public ReadStreamOnTime(InputStream reader) {
        super(reader);
    }

    public ReadStreamOnTime() {
    }
    
    @Override
    public String readAll() {
        return readAll(new TimeS(MAX));
    }

    @Override
    public String readUntil(String regex) {
        return readUntil(regex, new TimeS(MAX));
    }

    @Override
    public String readLine() {
        return readUntil("\r\n", new TimeS(MAX));
    }

    @Override
    public AbsStreamReadable getReader() {
       return this;
    }
}
