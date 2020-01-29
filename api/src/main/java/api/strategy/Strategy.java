package api.strategy;

import org.apache.commons.lang.RandomStringUtils;

public class Strategy {
	
	public static String getEmailValidationToken() {
		return RandomStringUtils.random(50, true, true);
	}
	
	public static String getCodeValidationToken() {
		return RandomStringUtils.random(6, false, true);
	}
}
