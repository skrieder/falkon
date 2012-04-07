
package org.globus.GenericPortal.common;

import java.util.regex.*;
import java.io.*;
import java.lang.*;
import org.globus.GenericPortal.common.*;

public class StringUtil
{

    public StringUtil()
    {

    }

    //indexOf(String 
    public static boolean contains(String str, String patternStr)
    {
        if (str == null || patternStr == null)
        {
            return false;
        }

        if (str.indexOf(patternStr) == -1)
        {
            return false;
        }
        else
            return true;

    }



    public static boolean contains_matcher(String str, String patternStr)
    {

        if (str == null || patternStr == null)
        {
            return false;
        }

// Compile regular expression
    //String patternStr = "b";
    Pattern pattern = Pattern.compile(patternStr);
    
    // Determine if there is an exact match
    //CharSequence inputStr = "a b c";
    Matcher matcher = pattern.matcher((CharSequence)str);
    boolean matchFound = matcher.matches(); // false
    return matchFound;

    }



}
