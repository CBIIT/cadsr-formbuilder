<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%
  	// get parameters
    String treeParams     = (String) request.getParameter("treeParams");
    String skin           = (String) request.getParameter("skin");
    String treeDirective  = (String) request.getParameter("treeDirective");
    String treeAction     = (String) request.getParameter("treeAction");
    String treeName       = (String) request.getParameter("treeName");
    request.getSession().setAttribute("paramsTree", treeParams);
    
    // URL encode parameters
    if (treeParams != null) treeParams = java.net.URLEncoder.encode(treeParams);    
    if (skin != null) skin = java.net.URLEncoder.encode(skin);    
    if (treeDirective != null) treeDirective = java.net.URLEncoder.encode(treeDirective);    
    request.getSession().setAttribute("treeTypeName", treeName);

%>
<script language="JavaScript1.2">
  var now = new Date();
  var glob = now.getHours()+now.getSeconds()+now.getMilliseconds();
  window.document.write("Building tree, please wait...");  
  var targetURL = "tree2.jsf?";
  window.location.href = targetURL;  
</script>
