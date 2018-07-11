/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oyei.SUMS.Registration.controller;

import oyei.SUMS.Registration.entities.Email;
import oyei.SUMS.Registration.entities.Person;
import Bus.BusinessExceptions;
import Entities.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
//import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Ifeoluwa
 */
@Named(value = "personController")
@SessionScoped
public class PersonController implements Serializable{

    private Person persons =new Person();
    private List<Person>personss= new ArrayList();
    private Email email = new Email();
    private Phonenumber phones = new Phonenumber();
    private PersonStatus personStatus = new PersonStatus();
    private Organisation organisations = new Organisation();
    private Student students = new Student();  
    private List<Email> emails;
     private Date currentDate;
     private List<Cohort> displaycohorts = new ArrayList();
     private Cohort cohorts;
     private int selectedcohortid;

    public int getSelectedcohortid() {
        return selectedcohortid;
    }

    public void setSelectedcohortid(int selectedcohortid) {
        this.selectedcohortid = selectedcohortid;
    }
     
    public Cohort getCohorts() {
        return cohorts;
    }

    public void setCohorts(Cohort cohorts) {
        this.cohorts = cohorts;
    }
    public List<Cohort> getDisplaycohorts() {
        return displaycohorts;
    }
   
    public void setDisplaycohorts(List<Cohort> displaycohorts) {
        this.displaycohorts = displaycohorts;
    }

    public List<Person> getPersonss() {
        return personss;
    }

    public void setPersonss(List<Person> personss) {
        this.personss = personss;
    }

   

    
    public List<Email> getEmails() {
        return emails;
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }

   
public Date getCurrentDate() 
    {   
        Calendar currentDate2=Calendar.getInstance();
        currentDate2.add(Calendar.HOUR_OF_DAY, 1);
        currentDate= currentDate2.getTime();
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

       
    public Phonenumber getPhones() {
        return phones;

    }

    public void setPhones(Phonenumber phones) {
        this.phones = phones;
    }

    public Student getStudents() {
        return students;
    }

    public void setStudents(Student students) {
        this.students = students;
    }

   

    public Person getPersons() {
        return persons;
    }

    public void setPersons(Person persons) {
        this.persons = persons;
    }

    public PersonStatus getPersonStatus() {
        return personStatus;
    }

    public void setPersonStatus(PersonStatus personStatus) {
        this.personStatus = personStatus;
    }

    public Organisation getOrganisations() {
        return organisations;
    }

    public void setOrganisations(Organisation organisations) {
        this.organisations = organisations;
    }


    @EJB
    private oyei.SUMS.Registration.Bus.PersonBusiness ps;
  
    public void getpersons() throws BusinessExceptions {
        setPersonss(ps.personss());
    }
    @PostConstruct
    public void init()
    {
        try {
            getcohorts();
            getpersons();
        } catch (BusinessExceptions ex) {
            Logger.getLogger(PersonController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void getcohorts() throws BusinessExceptions {
        setDisplaycohorts(ps.displaycohorts());
    }
    
    public void sendEmail(){
    
    final String username = "username@gmail.com";
    final String password = "password";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
                props.put("mail.smtp.ssl.trust","smtp.gmail.com");

		Session session = Session.getInstance(props, new GmailAuthenticator( username,password)); 
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("daraviace@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("up815039@myport.ac.ukcom"));
			message.setSubject("Testing Subject");
			message.setText("Dear Mail Crawler,"
				+ "\n\n No spam to my email, please!");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
     class GmailAuthenticator extends Authenticator {
         String user;
         String pw;
         public GmailAuthenticator (String username, String password)
         {
             super();
             this.user = username;
             this.pw = password;
                      }
         @Override
         public PasswordAuthentication getPasswordAuthentication (){
         return new PasswordAuthentication(user,pw);
         }
                 
     }

    public  String Save () throws BusinessExceptions {
           
    try{
         Person person2 = new Person();
         person2=persons;
            ps.add(person2,students,Collections.singletonList(email),personStatus,organisations,phones,getCurrentDate()) ;    
    }catch (BusinessExceptions ex) {
            Logger.getLogger(PersonController.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage("something went wrong"));
}
    persons= null;
    email = null;
    phones = null;
    personStatus = null;
    organisations = null;
    students = null;  
     
    return "";
    }
    
 
}
