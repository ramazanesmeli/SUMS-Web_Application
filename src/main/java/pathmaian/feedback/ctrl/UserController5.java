/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathmaian.feedback.ctrl;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author pathmaian
 */

@SessionScoped
@ManagedBean
public class UserController5 {
    private String resultText;
    
    public void sendAction() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(UserController5.class.getName()).log(Level.SEVERE, null, ex);
        }
        resultText="SENT " + new Date();
    
    
    }

    public String getResultText() {
        return resultText;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText;
    }
    
    
    
}
