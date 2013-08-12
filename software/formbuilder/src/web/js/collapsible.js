/* CLOSED_IMAGE - the image to be displayed when the sublists are closed
 * OPEN_IMAGE   - the image to be displayed when the sublists are opened
 */
CLOSED_IMAGE='i/plus.png';
OPEN_IMAGE='i/minus.png';

CLOSED_VVs='i/closeAllVVs.gif';
OPEN_VVs='i/openAllVVs.gif';

CLOSED_STATUS='collapsibleClosed';
OPEN_STATUS='collapsibleOpen';

var collapsibleNodes = new Array();
var collapsedStatus = OPEN_STATUS;

/* makeCollapsible - makes a list have collapsible sublists
 * 
 * listElement - the element representing the list to make collapsible
 */
function makeCollapsible(listElements, idVal){
var i=0;
collapsibleNodes = new Array();
while (i<listElements.length) {

	var listElement = listElements.item(i);
	var attrs = listElement.attributes;
	if (attrs.length > 0) {
		if (attrs.getNamedItem('id') != null && attrs.getNamedItem('id').nodeValue==idVal) {

		  // loop over all child elements of the list
		  var fstChild=listElement.firstChild.firstChild;
		  if (fstChild!=null){

			// only process li elements (and not text elements)
			if (fstChild.nodeType==1){

				var sblng = fstChild;
			  // build a list of child ol and ul elements and hide them
			  var list=new Array();
			  while (sblng!=null){
				if (sblng.tagName=='TR'){
				  sblng.style.display='none';
				  list.push(sblng);
				}
				sblng=sblng.nextSibling;
			  }

			  // add toggle buttons
			  
			var row = document.createElement('tr');
			row.setAttribute('class','OraTabledata');
			var col = document.createElement('td');
			col.setAttribute('class','OraFieldText'); 
			col.setAttribute('colspan','2');

			var clsdNode = getClosedNode(list);
			col.appendChild(clsdNode);
			collapsibleNodes.push(clsdNode);
			  
			row.appendChild(col);
			listElement.firstChild.insertBefore(row, listElement.firstChild.firstChild);
			}
		  }
		}
	}
	i++;
}
  

}

function getClosedNode(list) {

	var node = createAnchorTag(CLOSED_STATUS);
	node.appendChild(createImgTag(CLOSED_IMAGE));
	node.appendChild(createVVTextTag());

	node.onclick=createToggleFunction(node,list);
	
	return node;
}

function getOpenNode(list) {

	var node = createAnchorTag(OPEN_STATUS);
	node.appendChild(createImgTag(OPEN_IMAGE));

	node.onclick=createToggleFunction(node,list);
	
	return node;
}

function createAnchorTag(clazz) {
	var aTag=document.createElement('div');
	aTag.setAttribute('class',clazz);
	aTag.onmouseover="javascript:this.style.cursor='pointer'";
	
	return aTag;
}

function createVVTextTag() {
	var txtNode = document.createElement('a');
	txtNode.appendChild(document.createTextNode("Valid Values..."));

	return txtNode; 
}

function createImgTag(imgType) {
	var imgTag=document.createElement('img');
	imgTag.setAttribute('src',imgType);

	return imgTag;
}

/* createToggleFunction - returns a function that toggles the sublist display
 * 
 * toggleElement  - the element representing the toggle gadget
 * sublistElement - an array of elements representing the sublists that should
 *                  be opened or closed when the toggle gadget is clicked
 */
function createToggleFunction(toggleElement,sublistElements){

  return function(){

	  var parent = toggleElement.parentNode;
	  var newNode = null;
    // toggle status of toggle gadget
    if (toggleElement.getAttribute('class')==CLOSED_STATUS){
		newNode = getOpenNode(sublistElements);
    }else{
		newNode = getClosedNode(sublistElements);
    }

	parent.replaceChild(newNode, toggleElement);
	swapCollapsibleNodes(toggleElement, newNode);

    // toggle display of sublists
    for (var i=0;i<sublistElements.length;i++){
      sublistElements[i].style.display=
          (sublistElements[i].style.display=='block')?'none':'block';
    }
  }
}

function swapCollapsibleNodes(existingNode, newNode) {
	for (i=0;i<collapsibleNodes.length;i++) {
		if (collapsibleNodes[i] == existingNode) {
			collapsibleNodes[i] = newNode;
			return;
		}
	}
	collapsibleNodes.push(newNode);
}

function toggleAllCollapsible() {
	if (document.createEventObject){ 
		toggleInIE();
	}
	else {
		toggleInMozilla();
	}
	return swapStatus();
}

function toggleInIE() {
	for (i=0;i<collapsibleNodes.length;i++) {
		if (collapsibleNodes[i].parentNode != null && collapsibleNodes[i].getAttribute('class') == collapsedStatus) {
			var clickevent=document.createEventObject();
			collapsibleNodes[i].fireEvent('onClick', clickevent);
		}
	}
}

function toggleInMozilla() {
	for (i=0;i<collapsibleNodes.length;i++) {
		if (collapsibleNodes[i].parentNode != null && collapsibleNodes[i].getAttribute('class') == collapsedStatus) {
			var clickevent=document.createEvent("MouseEvents");
			clickevent.initEvent("click", true, true);
			collapsibleNodes[i].dispatchEvent(clickevent);
		}
	}
}

function swapStatus() {
	if (collapsedStatus == CLOSED_STATUS){
	  collapsedStatus = OPEN_STATUS;
	  return "Close All";
	}
	else {
		collapsedStatus = CLOSED_STATUS;
		return "open All";
	}
}

function getToggleDisplay(elem) {
	toggleAllCollapsible();
	var imgNode = null;
	if (collapsedStatus == CLOSED_STATUS) {
		imgNode = createImgTag(OPEN_VVs);
	}
	else {
		imgNode = createImgTag(CLOSED_VVs);
	}
	imgNode.setAttribute('border', '0');
	if (elem.hasChildNodes()) {
		elem.replaceChild(imgNode, elem.firstChild);
	}
	else {
		elem.appendChild(imgNode);
	}
}