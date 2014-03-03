package au.com.numbrcrunchr.web;

import java.text.DecimalFormat;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.apache.commons.lang.StringUtils;

public class PercentageConverter implements Converter {
    public static final DecimalFormat PERCENTAGE_FORAMT = new DecimalFormat(
            "##.##%");
    static {
        PERCENTAGE_FORAMT.setMultiplier(1);
    }

    @Override
    public Object getAsObject(FacesContext facesContext,
            UIComponent uiComponent, String value) {
        FacesMessage message = new FacesMessage("Conversion error occurred. ",
                "Invalid percentage. ");
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        if (value == null) {
            throw new ConverterException(message);
        }
        String theValue = value.trim();
        if (StringUtils.isEmpty(theValue)) {
            return 0;
        }
        theValue = StringUtils.strip(theValue, "%");
        try {
            return Double.valueOf(theValue);
        } catch (NumberFormatException e) {
            throw new ConverterException(message, e);
        }
    }

    @Override
    public String getAsString(FacesContext facesContext,
            UIComponent uiComponent, Object value) {
        if (value == null) {
            return StringUtils.EMPTY;
        }
        if (value instanceof Number) {
            return PERCENTAGE_FORAMT.format(value);
        }
        return null;
    }
}
