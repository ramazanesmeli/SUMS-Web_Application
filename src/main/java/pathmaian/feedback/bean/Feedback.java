/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathmaian.feedback.bean;

/**
 *
 * @author pathmaian
 */
public class Feedback {
    private String name;
    private String surname;
    private String feedbackcomments;

    public Feedback(String name, String surname, String feedbackcomments) {
        this.name = name;
        this.surname = surname;
        
        this.feedbackcomments = feedbackcomments;
    }
    
    public Feedback() {
        
    }
    
    

    public String getName() {
        return name;
        
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

   

    public String getFeedbackcomments() {
        return feedbackcomments;
    }

    public void setFeedbackcomments(String feedbackcomments) {
        this.feedbackcomments = feedbackcomments;
    }

    @Override
    public String toString() {
        return "Feedback{" + "name=" + name + ", surname=" + surname +  ", feedbackcomments=" + feedbackcomments + '}';
    }
    
    
   
    
}
