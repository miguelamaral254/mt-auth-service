package br.com.loginauth.repositories;

import br.com.loginauth.domain.entities.Notification;
import br.com.loginauth.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUser(User user);
}