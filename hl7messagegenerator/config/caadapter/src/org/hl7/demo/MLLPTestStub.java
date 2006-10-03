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

package org.hl7.demo;

import java.io.*;


public class MLLPTestStub{



public static void main(String[] args){
	String msg, msgtype, msgfile;
	msgfile = args[0];
	msgtype = args[1];
  try {  FileInputStream isFile = new FileInputStream(msgfile);
    int nChar;
    StringBuffer sbRead = new StringBuffer();
    nChar = isFile.read();
    while (nChar > -1) {    
      sbRead.append((char) nChar);
      nChar = isFile.read();
    } 
    msg = sbRead.toString();
    DemoDriver.parseMLLP(msg, msgtype);
  } catch (java.io.IOException ioe) {  ioe.printStackTrace();}
  }// main
  
}// class
