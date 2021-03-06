<%
    String treeClass      = (String) request.getParameter("treeClass");
    String treeParams     = (String) request.getParameter("treeParams");
    String skin           = (String) request.getParameter("skin");
    String treeDirective  = (String) request.getParameter("treeDirective");
    
    if (skin != null && skin.equals("null")) skin = null;
    if (skin == null) skin = "default";

    // URL encode parameters
    if (treeClass != null) treeClass = java.net.URLEncoder.encode(treeClass);    
    if (treeParams != null) treeParams = java.net.URLEncoder.encode(treeParams);    
    if (skin != null) skin = java.net.URLEncoder.encode(skin);    
    if (treeDirective != null) treeDirective = java.net.URLEncoder.encode(treeDirective);    

	String treeDir = request.getContextPath() + "/html/common/";    
    
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<HTML>
<script src="<%=treeDir%>skins/<%=skin%>/JavaScript.js"></script>
<HEAD>
<TITLE>NCI Center for Bioinformatics</TITLE>
<META http-equiv="Content-Type" content="text/html;">
</HEAD>
<frameset title="master" name="master" rows="126,*" cols="*" framespacing="0" frameborder="no" border="0" bordercolor="0">
  <frame title="headerFrame" src="<%=treeDir%>skins/<%=skin%>/TopHeader.html" name="headerFrame" frameborder="no" scrolling="no" noresize marginwidth="0" marginheight="0" id="headerFrame">
  <frameset cols="400, *" framespacing="5" frameborder="yes" border="5" bordercolor="#660099">
    <frameset rows="160,*" framespacing="5" frameborder="yes" border="5" bordercolor="#660099">
      <frameset rows="52,*" frameborder="NO" border="0" framespacing="0">
        <frame title="searchHeader" src="<%=treeDir%>skins/<%=skin%>/SearchHeader.html" name="searchHeader" scrolling="NO" noresize id="searchHeader" >
        <frame title="searchInput" src="SearchTree.jsp?skin=<%=skin%>" name="searchInput" frameborder="no" scrolling="no" marginwidth="10" marginheight="10" id="searchContent">
      </frameset>
      <frameset rows="52,*" frameborder="NO" border="0" framespacing="0">
        <frame title="resultsHeader" src="<%=treeDir%>skins/<%=skin%>/ResultsHeader.html" name="resultsHeader" frameborder="no" scrolling="NO" noresize marginwidth="0" id="resultsHeader" >
        <frame title="searchResults" src="SearchResults.jsp?skin=<%=skin%>" name="searchResults" frameborder="no" marginwidth="15" marginheight="10" id="resultsContent">
      </frameset>
    </frameset>
    <frameset rows="52,*" frameborder="NO" border="0" framespacing="0">
      <frame title="treeHeader" src="<%=treeDir%>skins/<%=skin%>/TreeBrowserHeader.html" name="treeHeader" scrolling="NO" noresize id="treeHeader" >
      <frame title="tree" src="WebTreeLoader.jsp?treeClass=<%=treeClass%>&treeParams=<%=treeParams%>&skin=<%=skin%>&treeDirective=<%=treeDirective%>" name="tree" frameborder="no" marginwidth="20" marginheight="10" id="treeContent">
    </frameset>
  </frameset>
</frameset>
<noframes></noframes>    

</HTML>
