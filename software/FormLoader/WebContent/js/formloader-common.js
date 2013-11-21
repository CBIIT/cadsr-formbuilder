function resetAllCheckboxesByName(name, val) {

	var cbs = document.getElementsByName(name);
  
  	for(var i=0; i < cbs.length; i++) {
    	cbs[i].checked = val;
 	}
  	
  return false;
}