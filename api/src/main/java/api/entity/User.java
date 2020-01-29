package api.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import api.enumerator.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity 
@NoArgsConstructor @AllArgsConstructor
@ToString @EqualsAndHashCode 
@Table(name="user")
public class User{	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Getter private Long iduser;
		
	@Column(unique = true)
	@Getter @Setter private String  email;
	
	@Column(unique = true)
	@Getter @Setter private String  nick;
		
	@Getter @Setter private String password;
		
	@Column(unique = true)
	@Getter @Setter private String token;
		
	@Enumerated(EnumType.STRING)
	@Getter @Setter private StatusEnum status;
	
	 @ManyToMany(fetch = FetchType.EAGER)
	    @JoinTable(name="user_role",
	            joinColumns=@JoinColumn(name="iduser"),
	            inverseJoinColumns=@JoinColumn(name="idrole")
	    )
	 @Getter @Setter private List<Role> roles;
	
	@OneToOne @JoinColumn(name = "idperson")
	@Getter @Setter private Person person;	
	
	 public User(String email, String nick, String password, String token, StatusEnum status, List<Role> roles, Person person) {	        
	        this.nick = nick;
	        this.email = email;
	        this.roles = roles;
	        this.password = password;
	        this.token = token;
	        this.status = status;
	        this.person = person;
	    }

	public User(User user) {
		// TODO Auto-generated constructor stub
	}

}
