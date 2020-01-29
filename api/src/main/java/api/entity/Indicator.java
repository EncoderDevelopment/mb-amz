package api.entity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import api.enumerator.PointEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity @Data
@NoArgsConstructor
@ToString @EqualsAndHashCode 
@Table(name="indicator")
public class Indicator{	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Getter private Long idindicator;
			
	@Getter @Setter private String type;
	@Getter @Setter private Integer value;
	@Getter @Setter private String description;
	
	public Indicator(String type, Integer value, String description){				
		this.type = type;
		this.value = value;
		this.description = description;
	}

}
