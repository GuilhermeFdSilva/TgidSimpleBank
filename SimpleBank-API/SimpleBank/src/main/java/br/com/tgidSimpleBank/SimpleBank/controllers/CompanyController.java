package br.com.tgidSimpleBank.SimpleBank.controllers;

import br.com.tgidSimpleBank.SimpleBank.models.users.inherited.Company;
import br.com.tgidSimpleBank.SimpleBank.services.CompanyService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    private CompanyService service;

    @GetMapping
    public ResponseEntity<?> loginCompany(@RequestBody Company company) {
        try {
            Company companyOnDB = service.validateLoginByCnpj(company);
            return new ResponseEntity<>(companyOnDB, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createCompany(@RequestBody Company company) {
        try {
            Company companyCreated = service.createCompany(company);
            return new ResponseEntity<>(companyCreated, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateCompany(@RequestBody Company company) {
        try {
            Company upToDateCompany = service.updateCompany(company);
            return new ResponseEntity<>(upToDateCompany, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        try {
            service.deleteCompany(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
