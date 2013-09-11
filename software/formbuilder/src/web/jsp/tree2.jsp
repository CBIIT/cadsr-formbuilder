<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="http://ajaxanywhere.sourceforge.net/" prefix="aa" %>
<%@ taglib uri="http://jsf-comp.sourceforge.net/aa" prefix="jcaa" %>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.util.* "%>
<%@ page import="java.util.*"%>
<%@page import="gov.nih.nci.ncicb.cadsr.contexttree.TreeConstants " %>
<%@ page import="net.sf.jsfcomp.aa.tree.AaTreeTag"%>
<%@ page import="org.owasp.esapi.ESAPI"%>

<%@page import="java.util.regex.Pattern"%>


<%! 
	private static final Pattern AUTOSCROLL_PATTERN = Pattern.compile("[0-9]*[,][0-9]*");
	private static final Pattern NAVCMD_PATTERN = Pattern.compile("[0-9:]*");
	private static final Pattern INT_PATTERN = Pattern.compile("[0-9]*");
	private static final Pattern LINK_HIDDEN_PATTERN = Pattern.compile("[cdeBrowserTree:]*([[0-9]*[:]]*t2g|_idJsp3)");
	private static final Pattern TREE_PARAMS_PATTERN = Pattern.compile("[[a-zA-Z0-9]*[;]?[:]?[a-zA-Z0-9]*]*");
	
	private void filterHiddenVariables(HttpServletRequest request, HttpServletResponse response) throws java.io.IOException{
		boolean valid = true;
		
		String autoScroll = request.getParameter("autoScroll");
		String jsfSeq = request.getParameter("jsf_sequence");
		String linkHidden = request.getParameter("cdeBrowserTree:_link_hidden_");
		String navCmd = request.getParameter("cdeBrowserTree:org.apache.myfaces.tree.NAV_COMMAND");
		String submit = request.getParameter("cdeBrowserTree_SUBMIT");
		String treeParams = request.getParameter("treeParams");
		
		if (autoScroll != null && !AUTOSCROLL_PATTERN.matcher(autoScroll).matches()) {
			System.out.println("Auto Scroll:"+autoScroll);
    		valid = false;
    	}
		if (jsfSeq != null && valid && !INT_PATTERN.matcher(jsfSeq).matches()) {
			System.out.println("JSF Seq:"+jsfSeq);
			valid = false;
		}
		if (linkHidden != null && valid && !LINK_HIDDEN_PATTERN.matcher(linkHidden).matches()) {
			System.out.println("Link Hidden:"+linkHidden);
			valid = false;
		}
		if (navCmd != null && valid && !NAVCMD_PATTERN.matcher(navCmd).matches()) {
			System.out.println("Nav Cmd:"+navCmd);
			valid = false;
		}
		if (submit != null && valid && !INT_PATTERN.matcher(submit).matches()) {
			System.out.println("Submit:"+submit);
			valid = false;
		}
		if (treeParams != null && valid && !TREE_PARAMS_PATTERN.matcher(treeParams).matches()) {
			System.out.println("Tree params:"+treeParams);
			valid = false;
		}
		
		if (!valid) {
			response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
		}
	}
%>

<f:view>
    <t:document>
        <t:documentHead>
            <%
            
            filterHiddenVariables(request, response);
            
// get parameters
    String treeParams = request.getParameter("treeParams");
    if (treeParams == null || treeParams.equals(""))
    	treeParams = (String)request.getSession().getAttribute("paramsTree");
    String treeName = null;
    String callerParams = "";
    Hashtable params = null;
    try {
        params = TreeUtils.parseParameters(treeParams);
        String src = null;
        if (params.containsKey("src")) {
          src = (String)params.get("src");
          String modIndex = (String)params.get("moduleIndex");
          String quesIndex = (String)params.get("questionIndex");
          callerParams += "&src="+src+"&moduleIndex="+modIndex+"&questionIndex="+quesIndex;
          }
          
         treeName =(String) request.getSession().getAttribute("treeTypeName");
    } 
    catch (Exception ex) {
      System.out.println("Error: "+ex.getMessage());;
    } 
	String treeDir = request.getContextPath() + "/html/common/";    
%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/TreeBrowser.css"/>
<script language="JavaScript1.2"
        src="<%=treeDir%>skins/CDEBrowser1/JavaScript.js"></script>
<script src="<%=request.getContextPath()%>/js/aa.js"></script>
<script language="JavaScript1.2">
  <!--
  function performAction(urlParams){
    var frm = findFrameByName('body');
    document.body.style.cursor = "wait";
    frm.document.body.style.cursor = "wait";
    if ("<%=treeName%>" == "formTree")
     frm.document.location = "/FormBuilder/formSearchAction.do?method=getAllFormsForTreeNode&"+urlParams;
    else
     frm.document.location = "<%=request.getContextPath()%>" + "/search?" + urlParams + "<%=callerParams%>";
   }
  function performFormAction(urlParams){
    var frm = findFrameByName('body');
    document.body.style.cursor = "wait";
    frm.document.body.style.cursor = "wait";
    if ("<%=treeName%>" == "formTree")
     top.document.location = "/FormBuilder/formDetailsAction.do?method=getFormDetails&"+urlParams;
    else
     frm.document.location = "<%=request.getContextPath()%>" + "/search?" + urlParams + "<%=callerParams%>";
   }

  //-->
  </script>
        </t:documentHead>
        <t:documentBody>
            <h:form id="cdeBrowserTree">
                <h:commandLink value="Refresh tree"
                               action="#{treeBacker.refreshTree}"/>
                <br/>
                <br/>
                <!-- Expand/Collapse Handled By Server -->
 <aa:zoneJSF id="treeZone">
      <jcaa:aaTree id="cdeBrowserTree" value="#{treeBacker.treeModel}"
            ajaxZone="treeZone"
            var="node" varNodeToggler="t" clientSideToggle="false"
                         binding="#{treeBacker.tree}">
                    <f:facet name="Context Folder">
                        <h:panelGroup style="white-space:nowrap;">
                            <t:graphicImage alt="Context" title="Context"
                                            value="/i/yellow-folder-open.png"
                                            rendered="#{t.nodeExpanded}"
                                            border="0"/>
                            <t:graphicImage value="/i/yellow-folder-closed.png"
                                            alt="Context" title="Context"
                                            rendered="#{!t.nodeExpanded}"
                                            border="0"/>
                            <h:outputLink 
                                          value="#{node.action}">
                                <h:outputText value="#{node.description}"
                                              styleClass="treeNode"/>
                            </h:outputLink>
                        </h:panelGroup>
                    </f:facet>
                    <f:facet name="Context">
                        <h:panelGroup style="white-space:nowrap;">
                            <h:commandLink immediate="true"
                                           action="#{treeBacker.selectedNode}"
                                           actionListener="#{t.setNodeSelected}">
                                <t:graphicImage value="/i/yellow-folder-closed.png"
                                                border="0"/>
                                <f:param name="docNum"
                                         value="#{node.identifier}"/>
                            </h:commandLink>
                            <h:outputLink 
                                          value="#{node.action}">
                                <h:outputText value="#{node.description}"
                                              styleClass="treeNode"/>
                            </h:outputLink>
                        </h:panelGroup>
                    </f:facet>
                    <f:facet name="Folder">
                        <h:panelGroup style="white-space:nowrap;">
                            <h:commandLink immediate="true"
                                           action="#{treeBacker.selectedNode}"
                                           actionListener="#{t.setNodeSelected}">
                                <t:graphicImage value="/i/yellow-folder-closed.png"
                                                border="0"/>
                                <f:param name="docNum"
                                         value="#{node.identifier}"/>
                            </h:commandLink>
                            <h:outputText value="#{node.description}"
                                          styleClass="treeNode"/>
                        </h:panelGroup>
                    </f:facet>
                    <f:facet name="Template">
                        <h:panelGroup style="white-space:nowrap;">
                            <t:graphicImage value="/i/leaf.gif" alt="Template"
                      title="Template" border="0"/>
                            <h:outputLink id="templateLink"
                                          value="#{node.action}">
                                <h:outputText value="#{node.description}"
                                              styleClass="treeNode"/>
                            </h:outputLink>
                        </h:panelGroup>
                    </f:facet>
                    <f:facet name="Classifications">
                        <h:panelGroup style="white-space:nowrap;">
                           <t:graphicImage alt="Classification" title="Classification"
                                            value="/i/yellow-folder-open.png"
                                            border="0"/>
                            <h:outputLink id="csLink"
                                          value="#{node.action}">
                                <h:outputText value="#{node.description}"
                                 styleClass="treeNode" title="#{node.toolTip}"/>
                            </h:outputLink>
                        </h:panelGroup>
                    </f:facet>
                    <f:facet name="Container">
                        <h:panelGroup style="white-space:nowrap;">
                           <t:graphicImage alt="Container" title="Container"
                                            value="/i/container.png"
                                            border="0"/>
                            <h:outputLink id="csLink"
                                          value="#{node.action}">
                                <h:outputText value="#{node.description}"
                                 styleClass="treeNode" title="#{node.toolTip}"/>
                            </h:outputLink>
                        </h:panelGroup>
                    </f:facet>
                    <f:facet name="Classification Scheme Item">
                        <h:panelGroup style="white-space:nowrap;">
                           <t:graphicImage alt="Classification Scheme Item" title="CSI"
                                            value="/i/csi.png"
                                            border="0"/>
                            <h:outputLink id="csiLink"
                                          value="#{node.action}">
                                <h:outputText value="#{node.description}"
                                 styleClass="treeNode" title="#{node.toolTip}"/>
                            </h:outputLink>
                        </h:panelGroup>
                    </f:facet>
                    <f:facet name="Registration Status">
                        <h:panelGroup style="white-space:nowrap;">
                            <t:graphicImage value="/i/regStatus.gif"
                                            title="Registration Staus"
                                            alt="Registration Staus"
                                            border="0"/>
                            <h:outputLink id="rsLink"
                                          value="#{node.action}">
                                <h:outputText value="#{node.description}"
                                              styleClass="treeNode"/>
                            </h:outputLink>
                        </h:panelGroup>
                    </f:facet>
                    <f:facet name="Form">
                        <h:panelGroup style="white-space:nowrap;">
                            <t:graphicImage value="/i/leaf.gif" title="Form"
                                            alt="Form" border="0"/>
                            <h:outputLink id="formLink"
                                          value="#{node.action}">
                                <h:outputText value="#{node.description}"
                                              styleClass="treeNode"/>
                            </h:outputLink>
                        </h:panelGroup>
                    </f:facet>
                    <f:facet name="Protocol">
                        <h:panelGroup style="white-space:nowrap;">
                            <t:graphicImage value="/i/protocol.gif"
                                            title="Protocol" alt="Protocol"
                                            border="0"/>
                            <h:outputLink id="protocolLink"
                                          value="#{node.action}">
                                <h:outputText value="#{node.description}"
                                              styleClass="treeNode"/>
                            </h:outputLink>
                        </h:panelGroup>
                    </f:facet>
                </jcaa:aaTree>
          </aa:zoneJSF>
       </h:form>
<script  language="JavaScript1.2"> 
ajaxAnywhere.getZonesToReload = function(url, submitButton) {
  return "treeZone"
}

ajaxAnywhere.formName = "cdeBrowserTree"; 
ajaxAnywhere.bindById();
//ajaxAnywhere.substituteFormSubmitFunction();
//ajaxAnywhere.substituteSubmitButtons();
</script>
        </t:documentBody>
    </t:document>
</f:view>

<script  language="JavaScript1.2"> 

	function addLoadEvent(func) {
	  var oldonload = window.onload;
	  if (typeof window.onload != 'function') {
	    window.onload = func;
	  } else {
	    window.onload = function() {
	      if (oldonload) {
	        oldonload();
	      }
	      func();
	    }
	  }
	}

  // Fix autoscroll for frame
  <%
    String autoScroll = request.getParameter("autoScroll");
    if (autoScroll != null && !"".equals(autoScroll)) {
        %>
	    addLoadEvent(function() {
  			parent.frames['tree'].scrollTo(<%=ESAPI.encoder().encodeForJavaScript(autoScroll) %>);
  		});
        <%
    }
  %>
</script>

<!--
<span id=cnt>0</span> seconds since last page refresh. 
<script> 

var sec=0; function counter(){ 

setTimeout("counter();",1000); document.getElementById("cnt").innerHTML = sec++; 

} counter(); 

</script> 

-->

