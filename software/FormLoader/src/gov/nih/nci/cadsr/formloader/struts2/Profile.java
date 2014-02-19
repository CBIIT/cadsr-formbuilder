package gov.nih.nci.cadsr.formloader.struts2;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;

public class Profile {

public String execute(){
	Map session=ActionContext.getContext().getSession();

String s=(String)session.get("login");
if(s!=null && !s.equals("")){
	return "success";
}
else{
	return "error";
}

}
}
