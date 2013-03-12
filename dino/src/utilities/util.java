package utilities;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;





/**
 * This class provides various types of existence checks
 * @author Don Swartwout
 *
 */

public class util {
	
	public static boolean isGradeValid(String grade)
	{
		String regGrade = "^[Aa]|[Bb]|[Cc]|[Dd]";
		String altGrade = "^[Ee]|[Ff]|[Ii]|[Ww]|[Zz]$";
		String optGradeMod = "([+|-])?";
		String reGradePattern = "((" + regGrade + ")" + optGradeMod + "$)|(" + altGrade + ")"; 
		Pattern pattern = Pattern.compile(reGradePattern);

		Matcher matcher = pattern.matcher(grade);

		boolean isValid = false;
		while (matcher.find()) {
			
			isValid = true;
		}		
		return isValid;
	}
	
	public static boolean isMissing(String s) {
		return ( s == null || s.length() < 1 );
	}
	@SuppressWarnings("unchecked")
	public static boolean isMissing(Collection c) {
		return ( c == null || c.size() == 0 );
	}
	public static boolean isMissing(Object o) {
		return ( o == null );
	}
	public static boolean isPresent(String s) {
		return !isMissing(s);
	}
	@SuppressWarnings("unchecked")
	public static boolean isPresent(Collection c) {
		return !isMissing(c);
	}
	public static boolean isPpresent(Object o) {
		return !isMissing(o);
	}
}
