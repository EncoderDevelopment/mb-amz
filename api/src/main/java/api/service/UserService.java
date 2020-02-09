package api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import api.controller.RoleRepository;
import api.entity.Role;
import api.entity.User;
import api.enumerator.RoleEnum;
import api.enumerator.StatusEnum;
import api.exception.PasswordValidationException;
import api.mail.Mail;
import api.repository.PersonRepository;
import api.repository.UserRepository;
import api.response.ResponseError;
import api.response.ResponseUser;
import api.response.ResponseValidate;
import api.strategy.Strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	private PasswordEncoder passwordEncoder;
    
    @Autowired
    PersonRepository personRepository;

    @Autowired
    RoleRepository roleRepository;
    
	@Override
	public UserDetails loadUserByUsername(String nick) throws UsernameNotFoundException {

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String password = request.getParameter("password");
		User user = userRepository.findByNick(nick);

		if (user != null) {
			if (!passwordEncoder.matches(password, user.getPassword()))
				throw new PasswordValidationException(
						String.format("A senha de validação do token é invalida!", user.getPassword()));

		} else {
			throw new UsernameNotFoundException(String.format("O usuário de validação do token não existe!", nick));
		}

		return new UserRepositoryUserDetails(user);
	}

	public ResponseEntity<ResponseUser> loginValidate(String nick, String password) throws UsernameNotFoundException {

		ResponseUser responseUser = new ResponseUser();
		responseUser.setUser(userRepository.findByNickAndStatus(nick, StatusEnum.ACTAVTED));

		if (responseUser.getUser() == null) {
			responseUser.setResponseValidate(new ResponseValidate("Login", "Nick", "Nick de acesso não encontrado!"));
			return new ResponseEntity<ResponseUser>(responseUser, HttpStatus.PRECONDITION_FAILED);
		} else if (responseUser.getUser() != null
				&& !passwordEncoder.matches(password, responseUser.getUser().getPassword())) {
			responseUser.setResponseValidate(new ResponseValidate("Login", "Senha", "A senha de acesso é invalida!"));
			return new ResponseEntity<ResponseUser>(responseUser, HttpStatus.PRECONDITION_FAILED);
		} else {
			responseUser.setResponseValidate(new ResponseValidate("Login", "Sucesso",
					"Login realizado com sucesso, seja bem vindo " + responseUser.getUser().getNick()));
			return new ResponseEntity<ResponseUser>(responseUser, HttpStatus.OK);
		}		
		
	}

	public ResponseEntity<List<ResponseError>> save(User user) {
		List<ResponseError> errors = new ArrayList<ResponseError>();

		if (userRepository.findByNick(user.getPerson().getSponsor()) == null)
			errors.add(new ResponseError("sponsor", "Patrocinador", "Patrocinador não existe na base de dados"));
		if (userRepository.findByEmail(user.getEmail()) != null)
			errors.add(new ResponseError("email", "Email", "Email já existe na base de dados"));
		if (userRepository.findByNick(user.getNick()) != null)
			errors.add(new ResponseError("nick", "Nick", "Nick já existe na base de dados"));

		if (errors.size() > 0) 			
			return new ResponseEntity<List<ResponseError>>(errors, HttpStatus.PRECONDITION_FAILED);
		
		createUser(user);
		return new ResponseEntity<List<ResponseError>>(Arrays.asList(new ResponseError("sucesso","Sucesso","Cadastro efetivado com sucesso")) ,HttpStatus.OK);
	}

	public ResponseEntity<ResponseUser> codeValidated(String code){	
		ResponseUser responseUser = new ResponseUser();
		responseUser.setUser(userRepository.findByTokenAndStatus(code, StatusEnum.DISABLED));
		if (responseUser.getUser() == null) {
			responseUser.setResponseValidate(new ResponseValidate("code", "Code", "O código de verificação é invalido"));
			return new ResponseEntity<ResponseUser>( responseUser, HttpStatus.NOT_FOUND);
		}else {
			responseUser.getUser().setStatus(StatusEnum.ACTAVTED);
			userRepository.save(responseUser.getUser());
			responseUser.setResponseValidate(new ResponseValidate("code", "Code", "Código de verificação validado com sucesso"));		
			return new ResponseEntity<ResponseUser>(responseUser, HttpStatus.OK);
		}
	}
	
	private void createUser(User user) {
    	user.setStatus(StatusEnum.DISABLED);		
        user.setRoles(Arrays.asList(roleRepository.findByRole(RoleEnum.ROLE_CLIENT)));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setToken(Strategy.getCodeValidationToken());
        
        personRepository.save(user.getPerson());        
        userRepository.save(user);
        //Mail.sendMailConfirmUser(user);
    }
	
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	private final static class UserRepositoryUserDetails extends User implements UserDetails {

		private static final long serialVersionUID = 1L;
		private User user;

		private UserRepositoryUserDetails(User user) {
			this.user = user;
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return this.user.getRoles();
		}

		@Override
		public String getUsername() {
			return this.user.getNick();
		}

		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		@Override
		public boolean isEnabled() {
			return true;
		}

		@Override
		public String getPassword() {
			return this.user.getPassword();
		}

	}

}
