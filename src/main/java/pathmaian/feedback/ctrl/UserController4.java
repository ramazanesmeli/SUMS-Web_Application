/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathmaian.feedback.ctrl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import pathmaian.feedback.bean.FeedbackForm;



@ManagedBean
@SessionScoped
    public class UserController4 {
       private List<FeedbackForm> feedbackforms=null;
       private FeedbackForm selectedFeedbackForm=null;
       
        
               
@PostConstruct       
public void init(){
            feedbackforms=new ArrayList<FeedbackForm>();
            feedbackforms.add(new FeedbackForm("Ramazan","Submitted","Graded","Not Modified","PDF","This project was completed on time...","45/50"));
            feedbackforms.add(new FeedbackForm("Jinet","Submitted","Graded","Not Modified","PDF","This project was...","47/50"));
            feedbackforms.add(new FeedbackForm("Ife","Submitted","Graded","Not Modified","PDF","This project was...","35/50"));
            feedbackforms.add(new FeedbackForm("Ike","Submitted","Graded","Not Modified","PDF","This project was...","41/50"));
            feedbackforms.add(new FeedbackForm("Russell","Submitted","Graded","Not Modified","PDF","This project was...","39/50"));
            
            selectedFeedbackForm=new FeedbackForm();
            }
    
    public void select(FeedbackForm e){
       selectedFeedbackForm=e;
    }
    
    public void update(){
        selectedFeedbackForm=new FeedbackForm();
    }
   
    public void delete() {
        feedbackforms.remove(selectedFeedbackForm);
        selectedFeedbackForm=new FeedbackForm();
    }

   
                    
        public List<FeedbackForm> getFeedbackforms() {
            return feedbackforms;
        }

        public void setFeedbackforms(List<FeedbackForm> feedbackforms) {
            this.feedbackforms = feedbackforms;
        }
        
        public FeedbackForm getSelectedFeedbackForm() {
        return selectedFeedbackForm;
    }
        public void setSelectedFeedbackForm(FeedbackForm selectedFeedbackForm) {
        this.selectedFeedbackForm = selectedFeedbackForm;
    }
        
}
    