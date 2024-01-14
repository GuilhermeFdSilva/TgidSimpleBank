package br.com.tgidSimpleBank.SimpleBank.controlles;

import br.com.tgidSimpleBank.SimpleBank.controllers.CompanyController;
import br.com.tgidSimpleBank.SimpleBank.models.users.inherited.Company;
import br.com.tgidSimpleBank.SimpleBank.services.CompanyService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CompanyControllerTest {
    @Mock
    private CompanyService service;
    private final Company COMPANY = new Company();

    @InjectMocks
    private CompanyController controller;

    @Test
    void testLoginCompany() {
        when(service.validateLoginByCnpj(COMPANY)).thenReturn(COMPANY);

        ResponseEntity<?> response = controller.loginCompany(COMPANY);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(COMPANY, response.getBody());

        verify(service, times(1)).validateLoginByCnpj(COMPANY);
    }

    @Test
    void testInvalidLoginCompany() {
        when(service.validateLoginByCnpj(COMPANY)).thenThrow(EntityNotFoundException.class);

        ResponseEntity<?> response = controller.loginCompany(COMPANY);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testCreateCompany() {
        when(service.createCompany(COMPANY)).thenReturn(COMPANY);

        ResponseEntity<?> response = controller.createCompany(COMPANY);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(COMPANY, response.getBody());

        verify(service, times(1)).createCompany(COMPANY);
    }

    @Test
    void testCreateInvalidCompany() {
        when(service.createCompany(COMPANY)).thenThrow(IllegalArgumentException.class);

        ResponseEntity<?> response = controller.createCompany(COMPANY);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testUpdateCompany() {
        when(service.updateCompany(COMPANY)).thenReturn(COMPANY);

        ResponseEntity<?> response = controller.updateCompany(COMPANY);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(COMPANY, response.getBody());

        verify(service, times(1)).updateCompany(COMPANY);
    }

    @Test
    void testUpdateInvalidCompany() {
        when(service.updateCompany(COMPANY)).thenThrow(EntityNotFoundException.class);

        ResponseEntity<?> response = controller.updateCompany(COMPANY);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testDeleteCompany() {
        ResponseEntity<?> response = controller.deleteCompany(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());

        verify(service, times(1)).deleteCompany(1L);
    }

    @Test
    void testDeleteInvalidCompany() {
        doThrow(EntityNotFoundException.class).when(service).deleteCompany(1L);

        ResponseEntity<?> response = controller.deleteCompany(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}
