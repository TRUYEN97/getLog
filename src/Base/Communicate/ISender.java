/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Base.Communicate;

/**
 *
 * @author Administrator
 */
public interface ISender {
    
    boolean sendCommand(String command);
    boolean insertCommand(String command);
}
