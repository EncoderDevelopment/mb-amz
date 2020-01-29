package api.controller;

import org.springframework.data.jpa.repository.JpaRepository;

import api.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
