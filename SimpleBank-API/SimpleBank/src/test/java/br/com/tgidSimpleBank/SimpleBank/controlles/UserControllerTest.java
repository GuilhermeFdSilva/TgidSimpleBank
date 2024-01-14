package br.com.tgidSimpleBank.SimpleBank.controlles;

import br.com.tgidSimpleBank.SimpleBank.controllers.UserController;
import br.com.tgidSimpleBank.SimpleBank.models.users.User;
import br.com.tgidSimpleBank.SimpleBank.models.users.inherited.Client;
import br.com.tgidSimpleBank.SimpleBank.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    private UserService service;

    @InjectMocks
    private UserController controller;

    @Test
    void testListUsers() {
        List<User> users = new ArrayList<>(List.of(new Client(), new Client()));

        when(service.listAllUsers()).thenReturn(users);

        ResponseEntity<?> response = controller.listUsers();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());

        verify(service, times(1)).listAllUsers();
    }

    @Test
    void testEmptyListUsers() {
        when(service.listAllUsers()).thenThrow(EntityNotFoundException.class);

        ResponseEntity<?> response = controller.listUsers();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}
