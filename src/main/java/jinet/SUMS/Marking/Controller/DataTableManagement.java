/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jinet.SUMS.Marking.Controller;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import jinet.SUMS.Marking.Bus.DataTableBusiness;

/**
 *
 * @author jinet
 */
@ManagedBean
@SessionScoped
public class DataTableManagement {
    private List<DataTableBusiness> datatablebusiness=null;
    
    @PostConstruct
    public void init(){
        datatablebusiness = new ArrayList<DataTableBusiness>();
        datatablebusiness.add(new DataTableBusiness("Project's context, aims and objectives \n" +"(How difficult is the issue shown to be? What scale are the issue subject and analysis very much encircled in the report?)",2,
                "(Very poor statement with unclear aims or objectives)",
                "(Very general statement with their aims and objectives)",
                "(Very general statement with their aims and objectives)",
                "(Lack of explanation with their goals and objectives)",
                "(Insufficient clarification of understanding and analysis of their problem topic)",
                "(Understanding and analysis of their problem topic are clearly stated, including the fundamental elements of the context)",
                "(Problem topic of their aims and objectives thoroughly constructed and perceived in broader context)",
                "(Problem topic is distinctly proved to be imposing or demonstrate originality and assertion in critically evaluating ideas)",
                "(Excellent understanding and analysis of their problem topic and explained distinctly in wider context)",
                "(Outstanding grade of justifying the understanding and analysis of their problem topic) ",
                "",""));
           
        datatablebusiness.add(new DataTableBusiness ("Literature Review \n" + "(How thoroughly is the literature integrated with this review? Is the review critical, analytical, appropriate and considerable? How reliable are the sources used in this review?)",2,
                "(No apparent review of the sources mentioned)", 
                "(No apparent review of the sources mentioned)",
                "(No apparent review of the sources mentioned)",
                "(Insignificant amount of research and meaningless review of the subject stated )",
                "(Standard understanding of literature which is cited and stated)",
                "(Relevant issues and critical contextual summary of the literature are reviewed comprehensively)",
                "(The context of the relevant and current literature are analysed distinctly to interpret the issue in perception)",
                "(Demonstrating the adeptness to explore extent and complexity of sources and develop new theories and assumptions)",
                "(Excellent comprehending the review of literature in distinctive detail)",
                "(Outstanding grade of reviewing the arguments in the literature review)",
                "",""));
           
        datatablebusiness.add(new DataTableBusiness("Methodological approach\n" +"(Are the methodological tools and methods stated in the report described and justified appropriately?)",1,
 "(The procedure of method or life cycle is designated with insufficient or no evidence)",
 "(The procedure of method or life cycle is designated with insufficient or no evidence)",
 "(The procedure of method or life cycle is designated with insufficient or no evidence)",
 "(The selection of methodology and life cycle is mediocre with no demonstration of use)",
 "(Demonstration of conducting methodology or life cycle with explanations)",
 "(Illustration of using the methodology or life cycle with comprehensive understanding of theory and methods)",
 "(The arguments and conclusions of using methodology or life cycle emphasised thoroughly)",
 "(The theory of applying the methodology clearly justified with broad knowledge)",
 "(Excellent demonstration and explanation of applying the innovative methodology)",
 "(Outstanding grade of demonstration and explanation of applying the innovative methodology)", "", ""));             
        
        datatablebusiness.add(new DataTableBusiness("Specification and discussion of the requirements \n" +"(How well does the report describe and explain the importance of the requirements? What influenced to discover the solution for the specification of the problem stated in the report? )",3,
"(Lack of explanation on the requirements)",
"(Lack of explanation on the requirements)",
"(Lack of explanation on the requirements)",
"(Clarification of each requirement is unclear or unspecified)",
"(Insufficient explanation of where the problem derived from)",
"(Discussion of requirements and analysis are not fully clarified)",
"(Justification of how crucial each requirement is)",
"(Showed how comprehensive and reliable their requirements are)",
"(Excellent justification and representation of their requirements specification)",  
"(Outstanding grade of justifying and representing their requirements specification and the solution to the problem)","",""));     
        
        datatablebusiness.add(new DataTableBusiness("Analysis and Design\n" + "(What kind of design methods and design processes are demonstrated in the report?)",3,
"(Lack of designs shown on the report)",
"(Lack of designs shown on the report)",
"(Lack of designs shown on the report)",
"(Poor illustration and explanation of the designs methods stated)",
"(Satisfactory explanation on design methods, processes and outcomes with few demonstration of design)",
"(Evaluation on the verdicts of why specific design is selected)",
"(Clear Justification on all design methods, processes and out comes)",
"(Critically evaluating and justifying the chosen design)",
"(Excellent illustration on every part of design methods and design processes)",
"(Outstanding grade of illustrating every design methods and design processes)","",""));       
        
        datatablebusiness.add(new DataTableBusiness("Implementation \n" + "(How well does the report describe and justify the discussion of implementing the artefact?)",3,
"(Insignificant or poor statement of implementation)",
"(Insignificant or poor statement of implementation)",
"(Insignificant or poor statement of implementation)", 
"(Satisfactory level of description on implementation)",
"(No justification or critical view on the implementation description)",
"(Justification on effective uses of implementation tools/techniques stated)",
"(Decisions of using particular implementation techniques / tools are distinctly justified)",
"(Clear justification on choosing certain implementation techniques / tools and thoroughly explained in detail)",
"(Excellent conclusion on choosing the correct implementation techniques / tools and critically justified)",
"(Outstanding conclusion on choosing the correct implementation techniques / tools and critically justified)","",""));         
        
        datatablebusiness.add(new DataTableBusiness("Verification and validation\n" + "(Is the report clearly described and justified about the verification and validation after the implementation stage?)",1,
"(Insufficient evidence and explanation of testing or debugging)",
"(Insufficient evidence and explanation of testing or debugging)",
"(Insufficient evidence and explanation of testing or debugging)",
"(Testing and debugging explained in poor detail)",
"(Simple explanation of testing and debugging carried out)",
"(Approaches used for testing and debugging are justified)",
"(Good understanding of verification and validation) ",
"(Demonstrates a clear explanation of well-planned tests and debugging) ",
"(Excellent critical discussion of the outcome from the thoroughly organised testing and debugging)",
"(Outstanding critical debate of the outcome from the thoroughly organised testing and debugging)","",""));      
        
        datatablebusiness.add(new DataTableBusiness("Evaluation \n" + "(Does the report explain whether the specification of the requirements has been accomplished successfully (or not)?)",2,
"(Not enough evidence of evaluation on the requirements)",
"(Not enough evidence of evaluation on the requirements)",
"(Not enough evidence of evaluation on the requirements)",
"(Made poor effort on evaluating the requirements)",
"(Shows evidence that final artefact meets requirements with a plan for evaluating outcome)",
"(Shows evidence and explains each part of final artefact whether it met the requirements (or not) )",
"(Shows good understanding of using correct evaluation method)",
"(Shows substantial evidence of knowledge of using certain evaluation method with explanation that the final artefact meets its purposes)",
"(Shows excellent justification of the evaluation method select and includes original understandings into evaluation)",
"(Shows outstanding justification of the evaluation method select and includes original understandings into evaluation)","",""));
               
        datatablebusiness.add(new DataTableBusiness("Project planning and management \n" + "(How clearly does the report represent an overall project plan with the evidence that it has been followed throughout the whole project?)",1,
"(Insufficient or not enough description on the project plan)",
"(Insufficient or not enough description on the project plan)",
"(Insufficient or not enough description on the project plan)",
"(Poor and unclear project plan)",
"(Evidence of using project plan in slight detail)",
"(Presents evidence of a detailed project plan with schedules, resources and a work programme)",
"(The project plan is evaluated on every stages of the plan)",
"(Shows evidence of whether there were issues when following the project plan)",
"(Excellent evaluation and justification on what went wrong when following the project plan and what actions could have been taken to prevent it)",
"(Outstanding evaluation and justification on what went wrong when following the project plan and what actions could have been taken to prevent it)","",""));
                
        datatablebusiness.add(new DataTableBusiness("Final Artefact \n" + "(How is the quality of the final artefact (software) based on the demonstration of their artefact? Did the final artefact solve the solution of the problem topic that was mentioned at the beginning of the project?)",3,          
"(Shows that the final artefact is only partially completed)",
"(Shows that the final artefact is only partially completed)",
"(Shows that the final artefact is only partially completed)",
"(The final artefact is completed but doesn’t function properly or only succeeded few of the requirements)",
"(The final artefact is completed meeting the requirements but it contains substantial errors)",
"(The final artefact meets nearly all of the requirements but has minimal errors)",
"(The final artefact is completed meeting all the requirements and doesn’t contain any errors )",
"(The final artefact displays high quality of attributes such as dependability, timeliness, maintainability and consistency)",
"(Demonstrate excellent grade of artefact satisfying all the requirements and could be creditable of actual use for marketing)",
"(Demonstrate outstanding grade of artefact satisfying all the requirements and could be creditable of actual use for marketing)","",""));
        
        datatablebusiness.add(new DataTableBusiness("Summary, conclusions and recommendations \n" + "(How clearly does the report summarise, conclude and recommend at the end of the project?)",2,
"(Lack of explanation or attempt to refer the question or problem that was declared at the beginning of the project)",
"(Lack of explanation or attempt to refer the question or problem that was declared at the beginning of the project)",
"(Lack of explanation or attempt to refer the question or problem that was declared at the beginning of the project)",
"(Poor and unclear explanation of conclusion)",
"(Shows summary and conclusions that are slightly related to the problem being addressed)",
"(Outcome of the final artefact summarised and concluded in precise detail)",
"(Summary and conclusions explained referring to all the objectives and also some recommendations)",
"(Shows the knowledge of understanding the challenging topic in broad context)",
"(Demonstrate excellent summarisation of the whole project, shows perceptive conclusions and (stating gratifying recommendations)",
"(Demonstrate outstanding summarisation of the whole project, shows perceptive conclusions and stating gratifying recommendations)","",""));  
        
        
        
        datatablebusiness.add(new DataTableBusiness("Structure and presentation\n" +"(Is the project report well presented with use of high quality structures such as page layout, sections, spelling, grammar and syntax, punctuation and many more?)",2,
"(The project report contains poor structure and presentation)",
"(The project report contains poor structure and presentation)",
"(The project report contains poor structure and presentation)",
"(Poor style of structures and presentation used)",
"(Good use of language used with reasonable and clear structure)",
"(Report inscribed in satisfying style of structure and presentation in terms of language, grammar, spelling, format and many more)",
"(Report inscribed in good quality style of structure and presentation such as language, grammar, spelling, format and many more)",
"(Report inscribed in high quality style of structure and presentation such as language, grammar, spelling, format and many more)",
"(Report inscribed in excellent quality style of structure and presentation such as language, grammar, spelling, format and many more)",
"(Report inscribed in outstanding quality style of structure and presentation such as language, grammar, spelling, format and many more)","",""));   
        
    }

    public List<DataTableBusiness> getDataTableBusiness() {
        return datatablebusiness;
    }

    public void setDataTableBusiness(List<DataTableBusiness> datatablebusiness) {
        this.datatablebusiness = datatablebusiness;
    
    }
    
}
    





