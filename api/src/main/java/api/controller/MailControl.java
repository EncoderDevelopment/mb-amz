package api.controller;

import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import api.entity.User;
import api.enumerator.StatusEnum;
import api.repository.UserRepository;
import api.response.ResponseValidate;

@RestController
@RequestMapping("/mail")
public class MailControl {

	@Autowired
	UserRepository userRepository;

	@PostMapping(value = "/validate", params = { "token", "status" })
	public ResponseEntity<ResponseValidate> validator(@RequestParam("token") String token,
			@RequestParam("status") StatusEnum status) throws URISyntaxException {
		User user = userRepository.findByTokenAndStatus(token, StatusEnum.DISABLED);
		if (user == null && userRepository.findByTokenAndStatus(token, StatusEnum.ACTAVTED) == null)
			return new ResponseEntity<ResponseValidate>(new ResponseValidate("[MBA]", "Violação de segurança!!!",
					"Você esta tentando acessar uma área restrita. Por favor, volte para a área de acesso livre do sistema!"),
					HttpStatus.OK);

		if (user != null && StatusEnum.ACTAVTED.equals(status)) {

			user.setStatus(StatusEnum.ACTAVTED);
			userRepository.save(user);
			return new ResponseEntity<ResponseValidate>(
					new ResponseValidate("[MBA]",
							"Você é uma pessoa de muita sorte!!! Obrigado por escolher o Mobiliza Amazonas",
							"Seu email foi ativado com sucesso. Estamos te redirecionando para o acesso ao sistema!"),
					HttpStatus.OK);
		}

		return new ResponseEntity<ResponseValidate>(
				new ResponseValidate("[MBA]", "Esse email já foi validado",
						"Em alguns segundos estaremos te redirecionando para o acesso ao sistema!"),
				HttpStatus.OK);
	}
}
