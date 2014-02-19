$(document).ready(function(){
	 $("#filter").keyup(function () {
	var words = this.value.toLowerCase().split(" ");
	
	var tabIdx = $("ul li.ui-state-active").index();
	var tableId = "collectionsTab";
	if (tabIdx == 1)
		tableId = "loadedFormsTab";
	
	var table = document.getElementById(tableId);
	var ele;
	for (var r = 1; r < table.rows.length; r++){
		
		ele = table.rows[r].innerHTML.replace(/<[^>]+>/g,"");
	        var displayStyle = 'none';
	        for (var i = 0; i < words.length; i++) {
		    if (ele.toLowerCase().indexOf(words[i])>=0)
			displayStyle = '';
		    else {
			displayStyle = 'none';
			break;
		    }
	        }
	        
	        //In Collection View tab, we only show parent rows
	        if (tabIdx == 0 && table.rows[r].id.indexOf("child")>=0)
	        	displayStyle = 'none';
	        
	        //Always keep the row with buttons
	        if (table.rows[r].id == 'buttonRow')
	        	displayStyle = '';
		table.rows[r].style.display = displayStyle;
	}
   });
});
