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
public class FeedbackForm {
    private String name;
    private String status;
    private String grade;
    private String last;
    private String file;
    private String feedback;
    private String overall;

    public FeedbackForm(String name, String status, String grade, String last, String file, String feedback, String overall) {
        this.name = name;
        this.status= status;
        this.status= grade;
        this.last = last;
        this.file = file;
        this.feedback = feedback;
        this.overall = overall;
    }
    
    public FeedbackForm() {
        
    }


    public String getName() {
        return name;
        
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }
    
    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
    
    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
    
    public String getOverall() {
        return overall;
    }

    public void setOverall(String overall) {
        this.overall = overall;
    }

    @Override
    public String toString() {
        return "FeedbackForm{" + "name=" + name + ", status=" + status + ", grade=" + grade + ", last=" + last + ", file=" + file + ", feedback=" + feedback + ", overall=" + overall + '}';
    }
    
    
}

