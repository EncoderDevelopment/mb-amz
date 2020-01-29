package api.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor @AllArgsConstructor
@ToString @EqualsAndHashCode 
@Getter @Setter
public class ResponseValidate {

	private String item;
	private String tittle;
	private String description;
	
}
