package au.com.numbrcrunchr.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

@ManagedBean
@RequestScoped
public class AjaxController {
	public boolean isDisabled() {
		String userAgent = ((HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest())
				.getHeader("user-agent");

		return isIE9(userAgent);
	}

	public boolean isIE9(String userAgent) {
		return userAgent == null || StringUtils.isEmpty(userAgent) ? false
				: userAgent.toUpperCase().indexOf("MSIE 9.0") != -1;
	}
}