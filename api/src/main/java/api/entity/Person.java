package api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity @Data
@NoArgsConstructor @AllArgsConstructor
@ToString @EqualsAndHashCode 
@Table(name="person")
public class Person{	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Getter private Long idperson;
		
	@Getter @Setter private String  name;
	@Getter @Setter private String  gender; 	
	@Getter @Setter private String  doc;
	@Getter @Setter private String  telephone;	
	@Getter @Setter private String 	sponsor;
	
	public Person(String name, String telephone, String gender, String doc, String sponsor) {
		this.name = name;
		this.telephone = telephone;
		this.gender = gender;
		this.doc = doc;
		this.sponsor = sponsor;		
	}
}
