function resetAllCheckboxesByName(name, val) {

	var cbs = document.getElementsByName(name);
  
  	for(var i=0; i < cbs.length; i++) {
    	cbs[i].checked = val;
 	}
  	
  return false;
}

function toggleActionButton(checkboxesNames, actionButtonName) {

	var button = document.getElementsByName(actionButtonName);
	//alert("button value: " + button[0].disabled);
	var hasChecked = false;
	var cbs = document.getElementsByName(checkboxesNames);
	  for(var i=0; i < cbs.length; i++) {
    	if (cbs[i].checked != 0) {
    		hasChecked = true;
    		break;
    	}
 	}
  
	button[0].disabled = !hasChecked;
  	
  return false;
}

function popupvalidationMessages(url) {  
	var w = 800;
	var h = 550;
	var left = (screen.width/2)-(w/2);
	return window.open(url, 'Form DB Validation Messages', 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, copyhistory=no, width='+w+', height='+h+', top=30, left='+left);
    //window.open(url, "Form DB Validation Messages", "status = 1, height=700, width='+w+', top='+top+', resizable = 1, scrollbars=1, titlebar=1" );  
  };
  
  function submitFormSetWaitCursor(formId) {
	  document.body.style.cursor  = 'wait';
	  var form = document.getElementById(formId);
	  form.submit();
  }