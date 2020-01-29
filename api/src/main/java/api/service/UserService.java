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

import api.entity.User;
import api.exception.PasswordValidationException;
import api.repository.UserRepository;
import api.response.ResponseError;
import api.response.ResponseUser;
import api.response.ResponseValidate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	private PasswordEncoder passwordEncoder;

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
		responseUser.setUser(userRepository.findByNick(nick));

		if (responseUser.getUser() == null) {
			responseUser.setResponseValidate(new ResponseValidate("Login", "Nick", "Nick de acesso não encontrado!"));
		} else if (responseUser.getUser() != null
				&& !passwordEncoder.matches(password, responseUser.getUser().getPassword())) {
			responseUser.setResponseValidate(new ResponseValidate("Login", "Senha", "A senha de acesso é invalida!"));
		} else {
			responseUser.setResponseValidate(new ResponseValidate("Login", "Sucesso",
					"Login realizado com sucesso, seja bem vindo " + responseUser.getUser().getNick()));
		}

		return new ResponseEntity<ResponseUser>(responseUser, HttpStatus.OK);
	}

	public ResponseEntity<List<ResponseError>> validateNewUser(User user) {
		List<ResponseError> errors = new ArrayList<ResponseError>();

		if (userRepository.findByNick(user.getPerson().getSponsor()) == null)
			errors.add(new ResponseError("sponsor", "Patrocinador", "Patrocinador não existe na base de dados"));
		if (userRepository.findByEmail(user.getEmail()) != null)
			errors.add(new ResponseError("email", "Email", "Email já cadastrado na base de dados"));
		if (userRepository.findByNick(user.getNick()) != null)
			errors.add(new ResponseError("nick", "Nick", "Nick já existe na base de dados"));

		return errors.size() > 0 ? new ResponseEntity<List<ResponseError>>(errors, HttpStatus.PRECONDITION_FAILED)
				: new ResponseEntity<List<ResponseError>>(HttpStatus.OK);
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
