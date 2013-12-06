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

function checkDisabled() {

	if (this.disabled == true) {
		alert("Have you selected any form?");
		return false;
	} else
		return true;
  	
  
}