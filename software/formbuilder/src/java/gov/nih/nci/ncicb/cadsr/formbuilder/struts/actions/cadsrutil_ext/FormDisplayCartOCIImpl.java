package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions.cadsrutil_ext;

import gov.nih.nci.ncicb.cadsr.common.util.logging.Log;
import gov.nih.nci.ncicb.cadsr.common.util.logging.LogFactory;
import gov.nih.nci.ncicb.cadsr.objectCart.FormDisplayCartTransferObject;
import gov.nih.nci.objectCart.client.ObjectCartClient;
import gov.nih.nci.objectCart.client.ObjectCartException;
import gov.nih.nci.objectCart.domain.Cart;
import gov.nih.nci.objectCart.domain.CartObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FormDisplayCartOCIImpl implements
		Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Cart oCart;
	//private CDECartItemComparator itemComparator;
	protected ObjectCartClient cartClient;	
	private String userId;
	private String cartName;
	private Class CDECartObjectType;
	
	// This holds forms display cart information temporarily until the user is ready to
	// save the form display objects. Once the user is ready to save the contents
	// in this cart will be added to the contents of the oCart. - Sula
	private Map formDisplayCart;
	

    private static Log log = LogFactory.getLog(FormDisplayCartOCIImpl.class.getName());

    
	public static ArrayList<FormDisplayCartOCIImpl> getAllCarts(ObjectCartClient client, String uid) {
		ArrayList<FormDisplayCartOCIImpl> ret = new ArrayList<FormDisplayCartOCIImpl>();
		try {
			List<Cart> carts = client.retrieveUserCarts(uid);
			for (Cart c: carts) {
				FormDisplayCartOCIImpl retCart = new FormDisplayCartOCIImpl(client, uid, c.getName());
				ret.add(retCart);
			}
		} catch (ObjectCartException oce) {
			throw new RuntimeException("Constructor: Error creating the Object Cart ", oce);
		}
		
		return ret;
	}

	public FormDisplayCartOCIImpl(ObjectCartClient client, String uid, String cName) {
		oCart = new Cart();
		//itemComparator = new CDECartItemComparator();
		userId = uid;
		cartName = cName;
		cartClient = client;
		CDECartObjectType = FormDisplayCartTransferObject.class;
		
		try {
			oCart = cartClient.createCart(userId, cartName);
			log.debug("oCart " + oCart + " with id " + oCart.getId() + " created using  " + client + " for uid " + uid + " and cartName " + cName + " in CDECartOCImpl " + this);
		} catch (ObjectCartException oce) {
			throw new RuntimeException("Constructor: Error creating the Object Cart ", oce);
		}
		
		formDisplayCart = new HashMap();
	}  
	
	public Collection getDisplayForms() {
		log.debug("CDECartOCImpl getDisplayForms this = " + this);		
		return getElements(FormDisplayCartTransferObject.class);
	}
	
	protected Comparator getComparator(Class type) {
		   if (type.getName().equalsIgnoreCase("FormDisplayCartTransferObject")) {
				return new Comparator() {
					public int compare(Object o1, Object o2) {
						FormDisplayCartTransferObject form1 = (FormDisplayCartTransferObject)o1;
						FormDisplayCartTransferObject form2 = (FormDisplayCartTransferObject)o2;
						return form1.getLongName().compareToIgnoreCase(form2.getLongName());
					}
				};
			}
			else {
				return new Comparator() {	
					public int compare(Object o1, Object o2) {
						return 0;
					}
				};
			}
		}

	private Collection getElements(Class type) {
		try {
			Collection cartElements = cartClient.getObjectsByType(oCart, type);
			if (cartElements != null){
				Collection items = ObjectCartClient.getPOJOCollection(type, cartElements);
				List itemList = new ArrayList(items);
				Collections.sort(itemList,getComparator(type));
				return itemList;
			} else 
				return new ArrayList();
		} catch (ObjectCartException oce) {
			oce.printStackTrace();
			throw new RuntimeException("getElements: Error restoring the POJO Collection", oce);
		}
	}
	
	private CartObject getNativeObject(String id) {
		if (oCart.getCartObjectCollection() == null)
			return null;
		
		for(CartObject co: oCart.getCartObjectCollection()){
			if (co.getNativeId().equals(id))
				return co;
		}
		return null;
	}
	
	private List<CartObject> getNativeObjects(Collection ids) {
		List<CartObject> list = new ArrayList<CartObject>();
		if (oCart.getCartObjectCollection() == null)
			return list;
		for(CartObject co: oCart.getCartObjectCollection()){
			if (ids.contains(co.getNativeId()))
				list.add(co);
		}
		return list;
	}


	public void setForms(Collection displayforms) {
		setElements(displayforms);
	}
	
	private void setElements(Collection items) {
	
		Map<String, String> objectDisplayNames = new HashMap<String, String> ();
		Map<String, Object>  objects = new HashMap<String, Object>();

		for(Object o: items) {
			FormDisplayCartTransferObject item = (FormDisplayCartTransferObject) o;
			if(getNativeObject(item.getIdseq()) == null){
				objectDisplayNames.put(item.getIdseq(), item.getLongName());
				objects.put(item.getIdseq(), item);
			}			
		}
		try {
			oCart = cartClient.storePOJOCollection(oCart, CDECartObjectType, objectDisplayNames, objects);
		} catch (ObjectCartException oce) {
			throw new RuntimeException("getDataElements: Error restoring the POJO Collection", oce);
		}
	}	

	public Object findElement(String itemId, Class objectType) {
		CartObject item = getId(oCart, itemId);
		if ( item != null) {
			try {
				return cartClient.getPOJO(objectType, item);
			} catch (ObjectCartException oce) {
				throw new RuntimeException("findDataElement: Error finding objects with native Id:"+itemId, oce);
			}
		}

		return null; 
	}
	
	private CartObject getId(Cart cart, String id) {
		for(CartObject co: cart.getCartObjectCollection()){
			if (co.getNativeId().equals(id))
				return co;
		}
		return null;
	}
	
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the cartName
	 */
	public String getCartName() {
		return cartName;
	}

	/**
	 * @param cartName the cartName to set
	 */
	public void setCartName(String cartName) {
		this.cartName = cartName;
	}	
	
	public String getCartId() {
		return oCart.getId().toString();	
	}
	
	public void addForm(Object form) {
		if (!formDisplayCart.containsKey(((FormDisplayCartTransferObject)form).getIdseq()))
			formDisplayCart.put(((FormDisplayCartTransferObject)form).getIdseq(), form);
	}

	public void addForms(Collection forms) {
		Iterator itemIter = forms.iterator();
		while (itemIter.hasNext()) {
		    addForm((FormDisplayCartTransferObject)itemIter.next());
		}
	}

	public void mergeFormCart() {
		Collection formColl = formDisplayCart.values();
		mergeElements(formColl);
		    //Collection formColl = cart.getForms();
		    //setForms(formColl);
	}
	
	public void mergeElements(Collection items) {
		Map<String, String> objectDisplayNames = new HashMap<String, String> ();
		Map<String, Object>  objects = new HashMap<String, Object>();
		HashSet<CartObject> forRemoval = new HashSet<CartObject>();
		
		for(Object o: items) {
			FormDisplayCartTransferObject item = (FormDisplayCartTransferObject) o;
			CartObject co = getNativeObject(item.getIdseq());
			
			objectDisplayNames.put(item.getIdseq(), item.getLongName());
			objects.put(item.getIdseq(), item);
			
			if(co != null)
				forRemoval.add(co);
		}
		try {
			oCart = cartClient.removeObjectCollection(oCart, forRemoval);
			oCart = cartClient.storePOJOCollection(oCart, FormDisplayCartTransferObject.class, objectDisplayNames, objects);
		} catch (ObjectCartException oce) {
			throw new RuntimeException("mergeElements: Error restoring the POJO Collection", oce);
		}
	}

}
