/* The contents of this file are subject to the Health Level-7 Public
 * License Version 1.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the
 * License at http://www.hl7.org/HPL/
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
 * the License for the specific language governing rights and
 * limitations under the License.
 *
 * The Original Code is all this file.
 *
 * The Initial Developer of the Original Code is .
 * Portions created by Initial Developer are Copyright (C) 2002-2004
 * Health Level Seven, Inc. All Rights Reserved.
 *
 * Contributor(s):
 */

/* Peter Hendler 12/17/2002
 *
 * The hmd file mentioned above must have its declaration modified to
 * not use the dtd This class will first run the HMDMetaLoader to
 * System.out proving that much works Then it will take the
 * MetaLoader and the MessageType and feeds them as parameters to a
 * modified static parseMessage method in Gunthers HL7Parser class
 * Everything seems to work in that there are no errors thrown but...
 * We never seem to get to the last System.out.println so it quietly
 * dies somewhere in the parser
 */
package org.hl7.demo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;

import org.hibernate.Transaction;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hl7.hibernate.Persistence;
import org.hl7.hpath.Expression;
import org.hl7.meta.LoaderException;
import org.hl7.meta.MessageType;
import org.hl7.meta.MessageTypeLoader;
import org.hl7.meta.impl.HySQLDbMessageTypeLoader;
import org.hl7.meta.impl.JdomMessageTypeLoader;
import org.hl7.meta.impl.MifMessageTypeLoader;
import org.hl7.meta.impl.XmlLoaderException;
import org.hl7.rim.RimObject;
import org.hl7.rim.impl.RimObjectImpl;
import org.hl7.xml.builder.RimGraphXMLSpeaker;
import org.hl7.xml.parser.MessageContentHandler;

import com.geocities.sbridges_geo.debug.Inspector;

public class DemoDriver {
	// -------------------------------------------------------------------------
	private static final Logger LOGGER = Logger.getLogger("org.hl7.demo");

	private static final String USAGE = "java DemoDriver [--hmd <hmd file> <rim file> | "
			+ "--mif <mif file> <rim file> | " + "--newmif | "
			+ "[--gui] --file <message file> --messageType <message type> " + "--hibernate [setup|run|save|load|exec] "
			+ "--build " + "--dumpMessageType " + "--setupDB " + "--hpath path " + "--parseIt";

	// -------------------------------------------------------------------------
	private static interface LoaderConfig {
		// .......................................................................
		public MessageTypeLoader createLoader() throws LoaderException;
	}

	// -------------------------------------------------------------------------
	private static class HmdLoaderConfig implements LoaderConfig {
		// .......................................................................
		private final String hmdFile_;
		private final String rimFile_;

		// .......................................................................
		/* default */HmdLoaderConfig(Iterator<String> args) {
			hmdFile_ = args.next();
			rimFile_ = args.next();			
		}

		// .......................................................................
		public MessageTypeLoader createLoader() throws XmlLoaderException {
			LOGGER.finest("Using JdomTypeMessageLoader with HMD file " + hmdFile_ + " and rimfile " + rimFile_);
			return new JdomMessageTypeLoader(hmdFile_, rimFile_);
		}
	}

	private static class MifLoaderConfig implements LoaderConfig {
		private final String mifFile_;
		private final String rimFile_;

		/* default */MifLoaderConfig(Iterator<String> args) {
			mifFile_ = args.next();
			rimFile_ = args.next();
		}

		public MessageTypeLoader createLoader() throws XmlLoaderException {
			LOGGER.finest("Using MifTypeMessageLoader with MIF file " + mifFile_ + " and rimfile " + rimFile_);
			return new MifMessageTypeLoader(mifFile_, rimFile_);
		}
	}

	private static class NewMifLoaderConfig implements LoaderConfig {
		NewMifLoaderConfig(Iterator<String> args) {}

		public MessageTypeLoader createLoader() throws XmlLoaderException {
			LOGGER.finest("Using mif.MessageTypeLoader");
			return org.hl7.meta.mif.MessageTypeLoaderAdapter.getInstance();
		}
	}
	
	// -------------------------------------------------------------------------
	// Public constructur just for the Process method.
	public DemoDriver() {}

	// -------------------------------------------------------------------------
	public DemoDriver(Iterator<String> it) {		
		while (it.hasNext()) {
			String arg = it.next();
			
			if (arg.equals("--hmd")) {
				loaderConfig_ = new HmdLoaderConfig(it);
			}
			else if (arg.equals("--mif")) {
				loaderConfig_ = new MifLoaderConfig(it);
			}
			else if (arg.equals("--newmif")) {
				loaderConfig_ = new NewMifLoaderConfig(it);
			}
			else if (arg.equals("--file")) {
				msg_ = it.next();
			}
			else if (arg.equals("--messageType")) {
				msgType_ = it.next();
			}
			else if (arg.equals("--dumpMessageType")) {
				dumpMessageType_ = true;
			}
			else if (arg.equals("--gui")) {
				useGui_ = true;
			}
			else if (arg.equals("--build")) {
				doBuild_ = true;
			}
			else if (arg.equals("--setupDB")) {
				doSetup_ = true;
			}
			else if (arg.equals("--hibernate")) {
				useHibernate_ = true;
				String actionString = it.next();
				if ("doNotSave".equals(actionString))
					hibernateAction_ = HibernateAction.doNotSave;
				else if ("save".equals(actionString))
					hibernateAction_ = HibernateAction.save;
				else
					throw new Error("hibernate argument must be one of setup run save load, but was: " + actionString);
			}
			else if (arg.equals("--hpath")) {
				hpathExpressions_.add(Expression.valueOf(it.next()));
			}
			else if (arg.equals("--parseIt")) {
				break;
			}
			else {
				System.out.println("Unknown option: " + arg);
				System.exit(2);
			}
		}

		if (loaderConfig_ == null) {
			System.out.println("No message type loader specified");
			System.exit(3);
		}
	}

	// -------------------------------------------------------------------------
	public static void parseMLLP(String data, String msgtype) {
		DemoDriver dd = new DemoDriver();
		dd.parseStringMsg(data, msgtype);
	}

	// -------------------------------------------------------------------------
	// parseStringMsg only uses Hypersonic now this written for MLLP HIMSS
	public void parseStringMsg(String data, String msgtype) {
		LOGGER.finer("*************** In parseStringMsg used in MLLP demo ************");

		MessageTypeLoader mtl = null;
		try {
			mtl = new HySQLDbMessageTypeLoader("data/hmdDatabase", "sa", "");
			MessageType mt = mtl.loadMessageType(msgtype);

			InputStream in = new ByteArrayInputStream(data.getBytes());
			LOGGER.finer("*************** Now running the parser ************");
			Object graph = MessageContentHandler.parseMessage(in, mt);

			LOGGER.finest("graph.toString()***** " + graph);
			Inspector.inspect(graph);
			LOGGER.finer("*************** Finished parsing ************");
		}
		catch (Throwable t) {
			LOGGER.log(Level.SEVERE, "parseStringMsg(" + msgtype + ") failed", t);
		}
		finally {
			if (mtl != null)
				mtl.close();
		}
	}

	private void hibernateSetup() {
		try {
			LOGGER.info("Create hibernate tables.");
			new SchemaExport(Persistence.getConfiguration()).create(true, true);
		}
		catch (Throwable ex) {
			ex.printStackTrace();
		}
	}

	// -------------------------------------------------------------------------
	public void parseIt() {
		if (doSetup_)
			hibernateSetup();

		LOGGER.finest("Using msgtype: " + msgType_);

		MessageTypeLoader mtl = null; // is this an interface that works for all?
		try {
			mtl = loaderConfig_.createLoader();
			MessageType messageType = mtl.loadMessageType(msgType_);

			// peter.. This can inspect the meta classes after they
			// are loaded
			// org.hl7.meta.MetaMsgDumper.dump(messageType);
			if (dumpMessageType_) {
				MetadataDumper.dumpMessageType(messageType);
			}

			LOGGER.finest("*********************************************************");
			File f = new File(msg_);

			LOGGER.finest("message file is " + msg_);
			FileInputStream in = new FileInputStream(f);
			LOGGER.finer("********* Starting to parse ***************");

			Object graph = MessageContentHandler.parseMessage(in, messageType);
			LOGGER.finest("graph.toString()***** " + graph.toString());

			if (useGui_) {
				Inspector.inspectAndWait(graph);
			}

			LOGGER.finer("********* Finished parsing ************");

			if (hpathExpressions_ != null && hpathExpressions_.size() > 0) {
				for (Expression expression : hpathExpressions_) {
					System.out.println(expression.toString());
					Iterator iter = expression.iterate(graph);
					while (iter.hasNext())
						System.out.println(iter.next().toString());
				}
			}

			if (useHibernate_ && (hibernateAction_ == HibernateAction.save)) {
				try {

					LOGGER.info("********** Hibernating ************");
					Transaction transaction = Persistence.getSession().beginTransaction();
					Persistence.save(graph);

					Class clazz = graph.getClass();
					String id = ((RimObjectImpl) graph).getInternalId();

					transaction.commit();
					Persistence.close();

					LOGGER.finer("********* Finished Hibernating, reading it back *******");

					graph = Persistence.load(clazz, id);

					/**
					 * Some examples of HQL querys: a way to get the graph from HQL query.
					 * In our example for our message type this works.
					 * 
					 * graph = Persistence.createHQLQuery("from org.hl7.rim.impl.ActImpl a
					 * where a.cloneCode .code ='AdverseEventInvestigation'
					 * ").list().iterator().next();
					 * 
					 * In the real world it might be useful to retrieve a patient object
					 * from an identifier like MRN Assuming the Hibernate database is
					 * fully back linked This code will retrieve the Patient object, and
					 * then back track to the root RIM graph Act
					 * 
					 * org.hl7.rim.impl.RoleImpl pat =
					 * (org.hl7.rim.impl.RoleImpl)Persistence.createHQLQuery( " from
					 * org.hl7.rim.impl.RoleImpl r where r.cloneCode.code =
					 * 'patientRole'") .list().iterator().next(); graph =
					 * pat.getParticipation().iterator().next().getAct().getInboundRelationship()
					 * .iterator().next().getSource();
					 * 
					 */

					LOGGER.finer("********* Finished Hibernating ************");
				}
				catch (Throwable ex) {
					graph = null;
					ex.printStackTrace();
				}
			}

			if (doBuild_) {
				LOGGER.finer("********* Generating XML ************");

				Transformer transformer = TransformerFactory.newInstance().newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");

				RimGraphXMLSpeaker speaker = new RimGraphXMLSpeaker();
				Source source = new SAXSource(speaker, new RimGraphXMLSpeaker.InputSource((RimObject) graph, messageType
						.getRootClass()));
				StringWriter sw = new StringWriter();

				transformer.transform(source, new StreamResult(sw));

				LOGGER.info("----------------------------------------\n" + sw.toString() + "\n");

				LOGGER.finer("********* Finished ************");

				FileOutputStream out;
				PrintStream p;
				out = new FileOutputStream(new File("out//" + msgType_ + "_out.xml"));

				p = new PrintStream(out);
				p.println(sw);
				p.close();
				LOGGER.info("Please consult 'out' directory for output xml instance.");

				if (useGui_) {
					Inspector.inspectAndWait(graph);
				}
			}
		}
		catch (Throwable t) {
			LOGGER.log(Level.SEVERE, "parseIt() failed", t);
			t.printStackTrace();
		}
		finally {
			if (mtl != null)
				mtl.close();
		}
	}
	
  //-------------------------------------------------------------------------
	private static String msgType_;
	private static String msg_;
	private static boolean useGui_;
	private static boolean dumpMessageType_;
	private static boolean doBuild_;
	private static boolean doSetup_;
	private static boolean useHibernate_;

	private static enum HibernateAction {
		save, doNotSave
	};
	
	private static HibernateAction hibernateAction_;
	private static List<Expression> hpathExpressions_;

	private static LoaderConfig loaderConfig_;
	
	private static void resetParameters() {		
		msgType_ = null;
		msg_ = null;
		useGui_ = false;
		dumpMessageType_ = false;
		doBuild_ = false;
		doSetup_ = false;
		useHibernate_ = false;
		
		hibernateAction_ = null;
		hpathExpressions_ = new ArrayList<Expression>();

		loaderConfig_ = null;
	}

	// -------------------------------------------------------------------------
	
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println(USAGE);
			System.exit(1);
		}
		
		for (Iterator<String> it = Arrays.asList(args).iterator(); it.hasNext();) {
			resetParameters();
			DemoDriver dd = new DemoDriver(it);
			dd.parseIt();
		}
	}
}

/**
 * HISTORY : $Log: not supported by cvs2svn $
 * HISTORY : Revision 1.17  2006/03/30 15:43:13  echen
 * HISTORY : Rename MessageTypeLoaderImpl to MessageTypeLoaderAdapter
 * HISTORY :
 * HISTORY : Revision 1.16  2006/03/10 14:02:26  gschadow
 * HISTORY : New --parseIt command allows reuse of DemoDriver instances for loading db's
 * HISTORY : HISTORY : Revision 1.15 2006/03/03
 * 14:54:48 gschadow HISTORY : *** empty log message *** HISTORY : HISTORY :
 * Revision 1.14 2006/02/09 14:08:38 gschadow HISTORY : no change. HISTORY :
 * HISTORY : Revision 1.13 2006/02/08 23:56:41 gschadow HISTORY : fixed problem
 * with CloneClass feature list (was TreeSet now just List HISTORY : that is
 * sorted) and problem with association sortKey HISTORY : HISTORY : Revision
 * 1.12 2005/11/14 20:56:11 echen HISTORY : add history comments HISTORY :
 * HISTORY : Revision 1.27 2005/11/07 20:13:57 chene
 */
