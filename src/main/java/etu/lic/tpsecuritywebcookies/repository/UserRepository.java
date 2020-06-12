package etu.lic.tpsecuritywebcookies.repository;

import etu.lic.tpsecuritywebcookies.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}