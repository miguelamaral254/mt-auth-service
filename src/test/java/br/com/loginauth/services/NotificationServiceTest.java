package br.com.loginauth.services;

import br.com.loginauth.domain.entities.Notification;
import br.com.loginauth.domain.entities.User;
import br.com.loginauth.domain.enums.Role;
import br.com.loginauth.dto.NotificationDTO;
import br.com.loginauth.dto.NotificationUpdateRequestDTO;
import br.com.loginauth.exceptions.NotificationNotFoundException;
import br.com.loginauth.exceptions.UserNotFoundException;
import br.com.loginauth.repositories.NotificationRepository;
import br.com.loginauth.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationServiceTest {

    @InjectMocks
    private NotificationService notificationService;

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private UserRepository userRepository;

    private User user;
    private Notification notification;
    private NotificationDTO notificationDTO;
    private NotificationUpdateRequestDTO notificationUpdateRequestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setCpf("12345678900");
        user.setRole(Role.PROFESSOR);

        notification = new Notification();
        notification.setId(1L);
        notification.setHeader("Test Header");
        notification.setMessage("Test Message");
        notification.setTimestamp(LocalDateTime.now());
        notification.setUser(user);
        notification.setRead(false);

        notificationDTO = new NotificationDTO(
                notification.getId(),
                notification.getHeader(),
                notification.getMessage(),
                notification.getTimestamp(),
                notification.isRead()
        );

        notificationUpdateRequestDTO = new NotificationUpdateRequestDTO(notification.getId(), true);
    }

    @Test
    void testSendNotificationToRole() {
        List<User> usersWithRole = List.of(user);

        when(userRepository.findByRole(Role.PROFESSOR)).thenReturn(usersWithRole);

        notificationService.sendNotificationToRole(Role.PROFESSOR, "Test Header", "Test Message");

        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    void testGetNotificationsForUserSuccess() {
        when(userRepository.findByCpf(user.getCpf())).thenReturn(Optional.of(user));
        when(notificationRepository.findByUser(user)).thenReturn(List.of(notification));

        List<NotificationDTO> notifications = notificationService.getNotificationsForUser(user.getCpf());

        assertNotNull(notifications);
        assertEquals(1, notifications.size());
        assertEquals(notificationDTO.header(), notifications.get(0).header());
        verify(notificationRepository, times(1)).findByUser(user);
    }

    @Test
    void testGetNotificationsForUserUserNotFound() {
        when(userRepository.findByCpf("00000000000")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> notificationService.getNotificationsForUser("00000000000"));
    }

    @Test
    void testUpdateNotificationReadStatusSuccess() {
        when(notificationRepository.findById(notification.getId())).thenReturn(Optional.of(notification));

        NotificationDTO updatedNotification = notificationService.updateNotificationReadStatus(notificationUpdateRequestDTO);

        assertNotNull(updatedNotification);
        assertTrue(updatedNotification.read());
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    void testUpdateNotificationReadStatusNotificationNotFound() {
        when(notificationRepository.findById(2L)).thenReturn(Optional.empty());

        NotificationUpdateRequestDTO requestDTO = new NotificationUpdateRequestDTO(2L, true);

        assertThrows(NotificationNotFoundException.class, () -> notificationService.updateNotificationReadStatus(requestDTO));
    }
}