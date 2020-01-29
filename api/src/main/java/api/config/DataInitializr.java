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
    	
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
        	Person person = new Person();
        	person.setName("Enoder");
        	person.setTelephone("Tokenize");        	
        	person.setGender("Tokenize");        	
        	person.setDoc("Tokenize");      
        	
            createUser("tokenize@encoder.com.br", "46ba9d9e87eafb1cb338498c35b0e925" , passwordEncoder.encode("3d3be52677dec456a1272f4c1c4b8cf7"), Strategy.getCodeValidationToken(), StatusEnum.ACTAVTED, RoleEnum.ROLE_ADMIN, person);
            
            person = new Person();
        	person.setName("Davi Roberto");
        	person.setTelephone("92991578721");        	
        	person.setGender("Masculino");        	
        	person.setDoc("86314424291");      
        	
            createUser("signa.roberto@gmail.com", "macross7" , passwordEncoder.encode("dra228609"), Strategy.getCodeValidationToken(), StatusEnum.DISABLED, RoleEnum.ROLE_ADMIN, person);           
        }

    }

    private void createUser(String email, String username, String password, String token , StatusEnum status, RoleEnum autority, Person person) {

    	personRepository.save(person);
    	
        Role role = new Role(autority);

        this.roleRepository.save(role);
        User user = new User(email, username,  password, token, status, Arrays.asList(role), person);
        userRepository.save(user);
        Mail.sendMailConfirmUser(user);
    }
    
    /**
     * inicializa os indicadores de pontuação iniciais da aplicação
     */
    private void initializeIndicator() {
    	
		indicatorRepository
				.saveAll(Arrays.asList(new Indicator(PointEnum.RGISTER.name(), PointEnum.RGISTER.VALUE,PointEnum.RGISTER.DESCRIPTION),
									   new Indicator(PointEnum.INDICATION.name(), PointEnum.INDICATION.VALUE,PointEnum.INDICATION.DESCRIPTION),
									   new Indicator(PointEnum.PURCHASE.name(), PointEnum.PURCHASE.VALUE,PointEnum.PURCHASE.DESCRIPTION),
									   new Indicator(PointEnum.SALE.name(), PointEnum.SALE.VALUE,PointEnum.SALE.DESCRIPTION)));
    	
    	
    }

}

