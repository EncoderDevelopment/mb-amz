package api.entity;

import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import api.enumerator.PointEnum;
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
@Table(name="point")
public class Point{	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Getter private Long idpoint;
		
	@Getter @Setter private Integer value;
	@Getter @Setter private Calendar billed;
	@Getter @Setter private PointEnum type;	
	@Getter @Setter private String description;	
	
	@OneToOne @JoinColumn(name = "iduser")
	@Getter @Setter private User user;	

}
