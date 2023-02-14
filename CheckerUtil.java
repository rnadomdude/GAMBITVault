package application;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CheckerUtil {
	 public static String checkPassword(String input)
	    {
	        // Checking lower alphabet in string
	        int n = input.length();
	        boolean hasLower = false, hasUpper = false,
	                hasDigit = false, specialChar = false;
	        Set<Character> set = new HashSet<Character> (
	            Arrays.asList('!', '@', '#', '$', '%', '^', '&',
	                          '*', '(', ')', '-', '+'));
	        for (char i : input.toCharArray())
	        {
	            if (Character.isLowerCase(i))
	                hasLower = true;
	            if (Character.isUpperCase(i))
	                hasUpper = true;
	            if (Character.isDigit(i))
	                hasDigit = true;
	            if (set.contains(i))
	                specialChar = true;
	        }
	       
	        if (hasDigit && hasLower && hasUpper && specialChar
	            && (n >= 8))
	            return "Strong";
	        else if ((hasLower || hasUpper || specialChar)
	                 && (n >= 6))
	        	return "Moderate";
	        else
	        	return "Weak";
	    }
	 
	    
}
