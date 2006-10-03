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
 * The Initial Developer of the Original Code is Eric Chen.
 * Portions created by Initial Developer are Copyright (C) 2002-2004
 * Health Level Seven, Inc. All Rights Reserved.
 *
 * Contributor(s):
 */

package org.hl7.util;

import org.hl7.meta.Association;
import org.hl7.meta.JavaIts;
import org.hl7.meta.LoaderException;
import org.hl7.meta.impl.JavaItsImpl;
import org.hl7.util.StringUtils;

import /*org.hl7.rim.AssociationSet*/java.util.Collection;

import org.hl7.rim.RimObject;
import org.hl7.rim.impl.AssociationSetImpl;
import org.hl7.types.CS;
import org.hl7.types.impl.CSimpl;
import org.hl7.xml.validator.FeatureCardinalityException;
import org.hl7.xml.validator.CardinalityValidator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;


public class RimUtil
{
    private static final JavaIts javaIts = new JavaItsImpl();

    public static void add(RimObject rimObject, Association association, RimObject value) throws FeatureCardinalityException
    {
        try
        {

            CS cloneCode = CSimpl.valueOf(association.getName(), "UNKNOWN_CODE_SYSTEM");
            value.setCloneCode(cloneCode);

            String rimPropertyName = association.getRimPropertyName();

            String methodNameStem = StringUtils.forceInitialCap(rimPropertyName);

            Class parameterType = javaIts.getRIMDataType(rimObject, methodNameStem);

            // this should work for any collection types
            if (/*AssociationSet*/Collection.class.isAssignableFrom(parameterType))
            {

                String addMethodName = ("add" + methodNameStem).intern();
                Method methods[] = rimObject.getClass().getMethods();
                int max = methods.length;
                for (int i = max - 1; i >= 0; i--)
                {
                    Method method = methods[i];
                    if (method.getName() == addMethodName)
                    {
                        Class fargs[] = method.getParameterTypes();
                        if (fargs.length == 1
                            && fargs[0].isAssignableFrom(value.getClass()))
                        {
                            // now we really got something useful
                            method.invoke(rimObject, value);
                            return;
                        }
                    }
                }

                // use the new addXyz(obj) method
                //javaIts.getAssociationAdder(rimObject.getClass(),association.getRimPropertyName(),association.getTarget().getRimClass()).invoke(rimObject, value);

                /* GS commented out because this is probably not a good idea to do here
                // TODO: refactor to validation service
                // ------  check cardinality  ------------
                Iterator iterator = associationCollection.iterator();
                CardinalityValidator.checkAssocaitonMaxCardinality(iterator, value, association);
                */

            }
            else
            { // cardinality is max 1

                CardinalityValidator.checkMaxOneCardinality(rimObject, methodNameStem, association);

                String distalClassName = association.getTarget().getRimClass();
                if (distalClassName.equals("ActHeir"))
                    distalClassName = "Act";
                else if (distalClassName.equals("RoleHeir"))
                    distalClassName = "Role";
                else if (distalClassName.equals("EntityHeir"))
                    distalClassName = "Entity";

                Class distalClass = Class.forName("org.hl7.rim." + distalClassName);

                // FIXME: use the JavaIts tool for this

                String setMethodName = ("set" + methodNameStem).intern();
                try
                {
                    rimObject.getClass()
                        .getMethod(setMethodName, new Class[]{distalClass})
                        .invoke(rimObject, new Object[]{value});
                }
                catch (NoSuchMethodException ex)
                {
                    // if the method isn't found, it could be that we didn't have
                    // the exact formal argument type right. Sadly enough we have
                    // to manually search for it. Yuck!
                    Method methods[] = rimObject.getClass().getMethods();
                    int max = methods.length;
                    for (int i = max - 1; i >= 0; i--)
                    {
                        Method method = methods[i];
                        if (method.getName() == setMethodName)
                        {
                            Class fargs[] = method.getParameterTypes();
                            if (fargs.length == 1
                                && fargs[0].isAssignableFrom(value.getClass()))
                            {
                                // now we really got something useful
                                method.invoke(rimObject, new Object[]{value});
                                return;
                            }
                        }
                    }
                    // if the for loop is exhausted, throw hands up in the air
                    throw ex;
                }
            }
        }
        catch (LoaderException ex)
        {
            throw new Error(ex);
        }
        catch (ClassNotFoundException ex)
        {
            throw new Error(ex);
        }
        catch (NoSuchMethodException ex)
        {
            throw new Error(ex);
        }
        catch (IllegalAccessException ex)
        {
            throw new Error(ex);
        }
        catch (InvocationTargetException ex)
        {
            throw new Error(ex);
        }
    }
}
