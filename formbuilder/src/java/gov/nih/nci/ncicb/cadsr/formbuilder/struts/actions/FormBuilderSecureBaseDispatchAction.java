package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.common.exception.FatalException;
import gov.nih.nci.ncicb.cadsr.common.exception.InvalidUserException;
import gov.nih.nci.ncicb.cadsr.common.formbuilder.common.FormElementLocker;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.AbstractDAOFactory;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.UserManagerDAO;
import gov.nih.nci.ncicb.cadsr.common.resource.Form;
import gov.nih.nci.ncicb.cadsr.common.resource.NCIUser;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocatorFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * Base DispatchAction for all formbuilder DispatchActions
 */
public class FormBuilderSecureBaseDispatchAction extends FormBuilderBaseDispatchAction
  {
  protected static Log log = LogFactory.getLog(FormAction.class.getName());

  /**
   * Sets default method name if no method is specified
   *
   * @return ActionForward
   *
   * @throws Exception
   */
  protected ActionForward dispatchMethod(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response,
    String name) throws Exception {
    
    try {
      String username = getLoggedInUsername(request);
      if ((username == null) || username.equals("")) {
        throw new InvalidUserException("Null username");
      }
      else {
        NCIUser currentuser =
          (NCIUser) request.getSession().getAttribute(USER_KEY);
  
        if (currentuser != null) {
          if (!currentuser.getUsername().equalsIgnoreCase(username)) {
            throw new InvalidUserException("Invalid user state");
          }
        }
        else {
          setApplictionUser(username, request);
        }
      }
      return super.dispatchMethod(mapping, form, request, response, name);
    }
    catch(InvalidUserException userExp)
    {
      request.getSession().invalidate();
      if(log.isErrorEnabled())
      {
        log.error("Inconsistant user",userExp);
      }
      throw userExp;
    }
    catch (Throwable throwable) {
      if (log.isFatalEnabled()) {
        NCIUser user = (NCIUser) getSessionObject(request, USER_KEY);

        if (user != null) {
          if(log.isFatalEnabled())
          {
            log.fatal(user.getUsername(), throwable);
          }          
        }
        else
        {
          if(log.isFatalEnabled())
          {
            log.fatal("Null User", throwable);
          }
        }
      }
      saveMessage(ERROR_FATAL, request);
      throw new FatalException(throwable);      
    }
  }
  protected void setApplictionUser(
    String username,
    HttpServletRequest request) {
    NCIUser newuser = getNCIUser(username);
    request.getSession().setAttribute(this.USER_KEY, newuser);
  }
  
    /**This method returns the login user information in NCIUser object
     * */
     protected NCIUser getApplictionUser(HttpServletRequest request){      
      return (NCIUser)(request.getSession().getAttribute(this.USER_KEY));
    }

  protected NCIUser getNCIUser(String username) {
    String locatorClassName =
      servlet.getInitParameter(ServiceLocator.SERVICE_LOCATOR_CLASS_KEY);
    ServiceLocator locator = ServiceLocatorFactory.getLocator(locatorClassName);
    AbstractDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory(locator);
    UserManagerDAO dao = daoFactory.getUserManagerDAO();
    NCIUser user = dao.getNCIUser(username);

    if (log.isInfoEnabled()) {
      log.info("getNCIUser()=" + user);
    }

    return user;
  }

  protected String getLoggedInUsername(HttpServletRequest request) {
    
    String debugLogin = servlet.getInitParameter("debugLogin");
      if(debugLogin!=null&&debugLogin.equalsIgnoreCase("Y"))
      {
        String username =  servlet.getInitParameter("debugUsername");
        log.info("debugUser=" + username);
        return username;
      }
      else
      {
        return request.getRemoteUser();
      }  
  }
  
    public  boolean isFormLocked(String formIdSeq,
                                       String userId) {
        return getApplicationServiceLocator().findLockingService().isFormLocked(formIdSeq, userId);                                   
    }

    public  boolean lockForm(String formIdSeq, NCIUser user, String sessionId) {
       return getApplicationServiceLocator().findLockingService().lockForm(formIdSeq, user, sessionId);
    }

    public  void unlockForm(String formIdSeq,
                                  String userId) {
        getApplicationServiceLocator().findLockingService().unlockForm(formIdSeq, userId);
        return;
    }  
    
    public  void unlockCRFInSession(HttpServletRequest request){
        Form crf = (Form)getSessionObject(request, CRF);
        unlockForm(crf.getIdseq(), request.getRemoteUser());
        return;
    }
    
    public NCIUser getFormLockedBy(String formIdSeq, HttpServletRequest request){
        FormElementLocker locker = getApplicationServiceLocator().findLockingService().getFormLocker(formIdSeq);
        return locker.getNciUser();
    }
    
}
