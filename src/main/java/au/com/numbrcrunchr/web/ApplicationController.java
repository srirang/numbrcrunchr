package au.com.numbrcrunchr.web;

import javax.faces.application.ProjectStage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
@ApplicationScoped
public class ApplicationController {
	public boolean isProduction() {
		return FacesContext.getCurrentInstance().getApplication().getProjectStage().equals(ProjectStage.Production);
	}
}
