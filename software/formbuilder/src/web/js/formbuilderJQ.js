
var un = $.cookie('FormbuilderUsername');
var pw;
var nun = $.cookie('newFormbuilderUsername');

$(function () {
////alert("function()");		
	$('#marqueeAdditionalMessage').marquee('pointer').mouseover(function () {
        $(this).trigger('stop');
    }).mouseout(function () {
        $(this).trigger('start');
    }).mousemove(function (event) {
        if ($(this).data('drag') == true) {
            this.scrollLeft = $(this).data('scrollX') + ($(this).data('x') - event.clientX);
        }
    }).mousedown(function (event) {
        $(this).data('drag', true).data('x', event.clientX).data('scrollX', this.scrollLeft);
    }).mouseup(function () {
        $(this).data('drag', false);
    });
});    
//-->


function setupUser()
{
	var myInputun = $("#myInputUserName").val();
	////alert(myInputun);
		
	if( myInputun != "viewer/" )  //logout
    {
		$(".viewer").hide();
		$(".noneViewer").show();

		$("#urViewer").hide();
		$("#noneViewer").show();

		$("#idLogout").show();
    }
	else  //login
    {
		$(".noneViewer").hide();
		$(".viewer").show();

		$("#noneViewer").hide();
		$("#urViewer").show();
		
		$("#idLogin").show();
		
	    $("input.viewerDisable").attr("disabled", true);
    }
	
	var myFirstTime = $("#myInputFirstTime").val();
	if( myFirstTime == "Y" && myInputun != "viewer/" )
		$(".noneViewerAddFormCart").show();
	else
		$(".noneViewerAddFormCart").hide();
			
}

function setupLink()
{
	var cdeBrowserlink = $("#idCDEBrowser").attr('href');
	
	var strAdm="cadsradmin";
	var n = cdeBrowserlink.search("-");
	
	if( n == 18 )
	{
		var cadsrAdminLink = "http://" + strAdm + cdeBrowserlink.substring(n);
		////alert(cadsrAdminLink);
		$("#idCaDSRAdmin").attr('href', cadsrAdminLink);		
	}
}

function displayAdditionalMessage( )
{
	var myInputun = $("#myInputUserName").val();		
	var myInfo = $("#myInputAdditionalMessage").val();		
	var currentPage = $("#myInputCurrentPage").val();
	var firstTime = $("#myInputFirstTime").val();

	var info = "No form is added in the Form Cart.";
	var p1 = "No form is added in the Form Cart.";
	var p2 = "No form is added in the Form Cart.";
	if( currentPage == "Result" )
	{	
		var n = myInfo.substring(0,1);
		if( n != "0" )
		{
			if( n == "1" )
			{
				info = n + " form queued in Form Cart for saving. Please go to Form Cart to save it.";

				p1 = n + " form queued in Form Cart for saving."
				p2 = "Please go to Form Cart to save it.";
			}
			else
			{
				info = n + " forms queued in Form Cart for saving. Please go to Form Cart to save them.";

				p1 =  n + " forms queued in Form Cart for saving."
				p2 = "Please go to Form Cart to save them."
			}
			//$('#marqueeAdditionalMessage').show().delay( 200 );
			//$('#marqueeAdditionalMessage').html(info);
			
			$('#p1Message').show().delay( 200 );
			$('#p2Message').show().delay( 200 );
			$('#p1Message').html(p1);
			$('#p2Message').html(p2);
		}
		else
		{
			//$('#marqueeAdditionalMessage').hide().delay( 200 );
			
			$('#p1Message').hide().delay( 200 );
			$('#p2Message').hide().delay( 200 );
		}
	}
	else 
	{
		if( firstTime != "Y" )
		{		
			info = "This form has bee added in Form Cart for saving. Please go to Form Cart to save it.";
			
			p1 = "This form has bee added in Form Cart for saving."
			p2 = "Please go to Form Cart to save it.";

			//$('#marqueeAdditionalMessage').show().delay( 200 );
			//$('#marqueeAdditionalMessage').html(info);
			
			$('#p1Message').show().delay( 200 );
			$('#p2Message').show().delay( 200 );
			$('#p1Message').html(p1);
			$('#p2Message').html(p2);
		}
		else
		{
			//$('#marqueeAdditionalMessage').hide().delay( 200 );
			
			$('#p1Message').hide().delay( 200 );
			$('#p2Message').hide().delay( 200 );			
		}
	}		
}

