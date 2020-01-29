package api.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import api.enumerator.RoleEnum;
import javax.persistence.*;

@Entity
@NoArgsConstructor
@ToString @EqualsAndHashCode 
@Getter @Setter
public class Role implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	public Role(RoleEnum role) {
        this.role = role;
    }
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idrole;
    
	@Enumerated(EnumType.STRING)
    private RoleEnum role;

    @Override
    public String getAuthority() {
        return  this.role.AUTORITY;
    }
}
