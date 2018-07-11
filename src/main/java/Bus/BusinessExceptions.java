/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bus;

/**
 *
 * @author a
 */

public class BusinessExceptions extends Exception {

    
    public BusinessExceptions() {
    }


    public BusinessExceptions(String msg) {
        super(msg);
    }
    
    public BusinessExceptions(String msg, Throwable T) {
        super(msg, T);
    }
}
