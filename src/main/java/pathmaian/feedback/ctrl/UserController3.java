/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathmaian.feedback.ctrl;

import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author pathmaian
 */


@ManagedBean(name = "UserCtrl")
@SessionScoped
public class UserController3 {
    private String comment;
    
    public void sendFeedback(){
        
        System.out.println("The feedback given is :"+comment);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    
}
