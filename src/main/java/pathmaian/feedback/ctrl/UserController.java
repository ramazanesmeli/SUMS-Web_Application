/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathmaian.feedback.ctrl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author pathmaian
 */
@SessionScoped
@ManagedBean
public class UserController {
    
    public void download(){
        try{
         
         FacesContext context = FacesContext.getCurrentInstance();
         ExternalContext externalContext = context.getExternalContext();
         
         externalContext.responseReset();
         externalContext.setResponseContentType("image/jpg");
         externalContext.setResponseHeader("Context-Disposition", "attachment;filename=\"image.jpg\"");
         
         FileInputStream inputStream = new FileInputStream(new File("/Users/pathmaian/Desktop/pdf.jpg"));
         OutputStream outputStream=externalContext.getResponseOutputStream();
         
         byte[] buffer=new byte[1024];
         int length;
         while((length=inputStream.read(buffer))>0){
             outputStream.write(buffer, 0, length);
             
         }
    
         inputStream.close();
         context.responseComplete();
        
    } catch (Exception e) {
        e.printStackTrace(System.out);
    }
    
}

}
