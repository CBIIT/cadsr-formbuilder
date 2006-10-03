package org.hl7.demo;

import org.hl7.meta.*;
import java.util.*;

class MetadataDumper {

	static void dumpMessageType(MessageType messageType) throws LoaderException {
		CloneClass msgRoot = messageType.getRootClass();
		Iterator<Feature> itrChilds = msgRoot.iterateChildren();
		while(itrChilds.hasNext()){
			Feature feature = itrChilds.next();
			System.out.println(feature.getRimClass() +
												 " ->> " +
												 feature.getName() +
												 " ->> " +
												 feature.getRimPropertyName() +
												 " ->> " +
												 feature.getSortKey());
			
			if(feature instanceof Attribute) {
				Attribute attr = (Attribute)feature;
				
			} else if(feature instanceof Association) {
				Association asso = (Association)feature;
				CloneClass msgRoot2 = asso.getTarget();
				Iterator<Feature> itrChilds2 = msgRoot2.iterateChildren();
				
				while(itrChilds2.hasNext()){
					Feature feature2 = itrChilds2.next();
					System.out.println(feature2.getRimClass() +
														 " ->> " +
														 feature2.getName() +
														 " ->> " +
														 feature2.getRimPropertyName() +
														 " ->> " +
														 feature2.getSortKey());
				}
			}
		}		
	}
}
