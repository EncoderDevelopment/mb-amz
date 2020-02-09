package api.config;

import api.controller.RoleRepository;
import api.entity.Indicator;
import api.entity.Person;
import api.entity.Role;
import api.entity.User;
import api.enumerator.PointEnum;
import api.enumerator.RoleEnum;
import api.enumerator.StatusEnum;
import api.mail.Mail;
import api.repository.IndicatorRepository;
import api.repository.PersonRepository;
import api.repository.UserRepository;
import api.strategy.Strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializr implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PersonRepository personRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	IndicatorRepository indicatorRepository;

	@Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {    	    			
    	initializeIndicator();
    	initializeRole();
    	
        List<User> users = userRepository.findAll();
                
        if (users.isEmpty()) {
        	
            createUser("tokenize@encoder.com.br", "46ba9d9e87eafb1cb338498c35b0e925" , passwordEncoder.encode("3d3be52677dec456a1272f4c1c4b8cf7"), 
            		Strategy.getCodeValidationToken(), StatusEnum.ACTAVTED, RoleEnum.ROLE_ADMIN, 
            		createPerson("Enoder", "Tokenize" , "S" , "Tokenize", "Encoder"));
                                	        	
            createUser("signa.roberto@gmail.com", "macross7" , passwordEncoder.encode("dra228609"), Strategy.getCodeValidationToken(), 
            		StatusEnum.ACTAVTED, RoleEnum.ROLE_ADMIN, 
            		createPerson("Davi Roberto", "92991578721" , "M" , "86314424291", "macross7"));           
        }

    }

	private Person createPerson(String name, String telephone, String gender, String doc, String sponsor) {
		Person person = new Person(name, telephone, gender, doc, sponsor);
		personRepository.save(person);
		return person;
	}

	private void createUser(String email, String nick, String password, String token, StatusEnum status,
			RoleEnum autority, Person person) {
		userRepository.save(new User(email, nick, password, token, status,
				Arrays.asList(roleRepository.findByRole(autority)), person));
		// Mail.sendMailConfirmUser(user);
	}

	private void initializeIndicator() {

		indicatorRepository.saveAll(Arrays.asList(
				new Indicator(PointEnum.RGISTER.name(), PointEnum.RGISTER.VALUE, PointEnum.RGISTER.DESCRIPTION),
				new Indicator(PointEnum.INDICATION.name(), PointEnum.INDICATION.VALUE,
						PointEnum.INDICATION.DESCRIPTION),
				new Indicator(PointEnum.PURCHASE.name(), PointEnum.PURCHASE.VALUE, PointEnum.PURCHASE.DESCRIPTION),
				new Indicator(PointEnum.SALE.name(), PointEnum.SALE.VALUE, PointEnum.SALE.DESCRIPTION)));

	}

	private void initializeRole() {
		roleRepository.saveAll(Arrays.asList(new Role(RoleEnum.ROLE_ADMIN), new Role(RoleEnum.ROLE_CLIENT)));
	}

}
