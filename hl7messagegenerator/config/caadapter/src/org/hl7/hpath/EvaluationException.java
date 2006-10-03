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
 * The Initial Developer of the Original Code is Gunther Schadow.
 * Portions created by Initial Developer are Copyright (C) 2002-2005 
 * Health Level Seven, Inc. All Rights Reserved.
 *
 * Contributor(s): 
 */
package org.hl7.hpath;

public class EvaluationException extends RuntimeException {
  /*package*/ EvaluationException(Expression expression, Object context, String message) { super(message + " evaluating " + expression + " in context " + context); }
  /*package*/ EvaluationException(Expression expression, Object context, String message, Throwable ex) { super(message + " evaluating " + expression + " in context " + context, ex); }
  /*package*/ EvaluationException(Expression expression, Object context, Throwable ex) { super("evaluating " + expression + " in context " + context, ex); }
}
