<%--GF32932 begin D.An, 20130825 --%>
<%
String showMessageUsername = (String) session.getAttribute("myUsername");
String myFormAdded = (String) session.getAttribute("myFormAdded");
System.out.println("additionalMessages.jsp: myFormAdded : " + myFormAdded );

System.out.println("additionalMessages.jsp:" + showMessageUsername);
	String formCartInfo;
	if( session.getAttribute("myFormCartInfo") != null )
		formCartInfo =(String) session.getAttribute("myFormCartInfo");
	else
		formCartInfo = "0";
	
System.out.println("additionalMessages.jsp :  " + formCartInfo);
%>
<input type="hidden" id="myInputUserName" name="myInputUserName" value=<%=showMessageUsername %>/>
<input type="hidden" id="myInputAdditionalMessage" name="myInputAdditionalMessage" value=<%=formCartInfo %>/>
<input type="hidden" id="myInputMyFormAdded" name="myInputMyFormAdded" value=<%=myFormAdded %>/>

<!-- <p id="marqueeAdditionalMessage" style="display:none">&nbsp</p>-->

<marquee width="600" height="30" style="font-family:arial;color:green;font-size:18px;" scrollamount="1" direction="up" behavior="scroll">
<p class="marqeeMessages" id="p1Message">&nbsp</p>
<p class="marqeeMessages" id="p2Message">&nbsp</p>
</marquee>

<script type="text/javascript">
	
var myInputun = $("#myInputUserName").val();				
if( myInputun != "viewer/" )  //None Viewer
{
	displayAdditionalMessage();
}	

</script>
 <%--GF32932 D.An, 20130825 --%>
  
