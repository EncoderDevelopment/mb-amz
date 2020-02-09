package api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import api.entity.User;
import api.enumerator.StatusEnum;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByEmailAndPassword(String email, String password);
	
	User findByEmail(String email);
	
	User findByNick(String nick);
		
	User findByNickAndStatus(String nick , StatusEnum status);	
	
	User findByToken(String token);
		
	User findByTokenAndStatus(String token, StatusEnum status);

}
