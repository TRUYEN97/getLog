/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Base.Time.WaitTime.AbsTime.Class;

import Base.Time.WaitTime.AbsTime.Class.ITimer;

/**
 *
 * @author Administrator
 */
public abstract class AbsTime implements ITimer {

    private long startTime = 0;
    private int spec = 0;

    protected AbsTime() {
    }

    protected AbsTime(int spec) {
        this();
        start(spec);
    }

    @Override
    public void start(int spec) {
        this.spec = spec;
        startTime = getTimeCurrent();
    }

    @Override
    public boolean onTime() {
        return getTimeCurrent() - startTime < spec;
    }

    @Override
    public void update() {
        startTime = getTimeCurrent();
    }

    @Override
    public boolean isTimeBigThan(int time) {
        return getTimeCurrent() - startTime >= time;
    }
}
