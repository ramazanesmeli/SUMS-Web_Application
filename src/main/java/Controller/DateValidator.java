/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* this file is changed form of sample in the link(up836794) https://www.mkyong.com/jsf2/custom-converter-in-jsf-2-0/ */
package Controller;

import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("dateValidator")
public class DateValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {

        if (value == null) {
            return; 
        }

        UIInput dateComponent = (UIInput) component.getAttributes().get("dateComponent");
        
        if (dateComponent == null ) {
            throw new ValidatorException(new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "date is NULL !", null));
        }

        if (!dateComponent.isValid()) {
            
            return; 
        }

        Date startDate = (Date) dateComponent.getValue();

        if (startDate == null) {
            return; 
        }

        Date endDate = (Date) value;

        if (startDate.after(endDate)) {
            dateComponent.setValid(false);
            throw new ValidatorException(new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Start date may not be after end date.", null));
        }
    }
}
