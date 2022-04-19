/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Base.Time.WaitTime.AbsTime.Class;

/**
 *
 * @author Administrator
 */
public interface ITimer {

    void start(int spec);

    boolean onTime();

    long getTimeCurrent();

    public void update();
    
    boolean isTimeBigThan(int time);
}
