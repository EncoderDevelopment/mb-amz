package api.response;

import api.entity.User;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor @AllArgsConstructor
@ToString @EqualsAndHashCode 
@Getter @Setter
public class ResponseUser {

	private User user;	
	private ResponseValidate responseValidate;
	
}
