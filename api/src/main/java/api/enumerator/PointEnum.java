package api.enumerator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PointEnum {

	RGISTER(0, "New signup bonus"),
	INDICATION(10,"Direct Referral Bonus"),
	PURCHASE(1, "Purchase from affiliated supplier"),
	SALE(1, "Sell ​​by affiliate supplier");
	  	
	public Integer VALUE;	
	public String DESCRIPTION;
}
