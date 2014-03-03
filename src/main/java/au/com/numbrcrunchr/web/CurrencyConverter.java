package au.com.numbrcrunchr.web;

import java.text.DecimalFormat;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;

@FacesConverter(forClass = Long.class)
public class CurrencyConverter implements Converter {
    private static final Logger LOGGER = Logger
            .getLogger(CurrencyConverter.class.getName());

    private static final DecimalFormat CURRENCY_FORMAT = new DecimalFormat(
            "$###,###");
    // private static final String CURRENCY_SYMBOL = DecimalFormat
    // .getCurrencyInstance().getCurrency().getSymbol();
    private static final String CURRENCY_SYMBOL_SHORT = "$";
    private static final String CURRENCY_SYMBOL_LONG = "AUD";

    @Override
    public Object getAsObject(FacesContext facesContext,
            UIComponent uiComponent, String value) {
        if (value == null || StringUtils.isEmpty(value)) {
            return Long.valueOf(0);
        }
        String theValue = value.trim();
        theValue = stripCurrencySymbol(theValue);
        theValue = stripCommas(theValue);
        String[] values = StringUtils.split(theValue, '.');
        if (values.length == 0) {
            return Long.valueOf(0);
        }
        FacesMessage message = new FacesMessage("Conversion error occurred. ",
                "Invalid amount. ");
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        if (values.length > 2) {
            throw new ConverterException(message);
        }
        if (values.length != 1) {
            LOGGER.warning("Invalid number " + value + ", truncating to:"
                    + values[0]);
        }
        theValue = values[0];
        if (StringUtils.isNumeric(theValue)) {
            return new Long(theValue);
        }
        throw new ConverterException(message);
    }

    private String stripCurrencySymbol(String value) {
        return StringUtils.remove(
                StringUtils.remove(value, CURRENCY_SYMBOL_LONG),
                CURRENCY_SYMBOL_SHORT).trim();
    }

    private String stripCommas(String value) {
        return StringUtils.remove(value, ",");
    }

    @Override
    public String getAsString(FacesContext facesContext,
            UIComponent uiComponent, Object value) {
        if (value == null) {
            return CURRENCY_FORMAT.format(0);
        }
        if (value instanceof String && StringUtils.isNumeric((String) value)) {
            return CURRENCY_FORMAT.format(new Float((String) value));
        }
        if (value instanceof Number) {
            return CURRENCY_FORMAT.format(value);
        }
        FacesMessage message = new FacesMessage("Conversion error occurred. ",
                "Invalid amount. ");
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        throw new ConverterException(message);
    }
}
