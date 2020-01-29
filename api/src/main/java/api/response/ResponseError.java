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
public class ResponseError {

	private String field;
	private String name;
	private String description;
}
