package api.enumerator;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SecurityServerEnum {

	CLIENT ("fbf50455be15b488f6ac555eeb5ef16c"),
	PASS ("48dbc81d8d9a9cb3bf29c2aadcc10ea3"),
	RESOURCE_ID ("restservice"),
	TYPE ("password"),
	CODE ("authorization_code"),
	REFRESH ("refresh_token"),
	SCOPE ("all");
	  	
	public String TOKEN;
}
