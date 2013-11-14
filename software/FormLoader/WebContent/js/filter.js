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
		table.rows[r].style.display = displayStyle;
	}
   });
});
