package api.controller;

import org.springframework.data.jpa.repository.JpaRepository;

import api.entity.Role;
import api.enumerator.RoleEnum;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Role findByRole(RoleEnum role);	
	
}
