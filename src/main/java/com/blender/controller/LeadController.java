package com.blender.controller;


import com.blender.payload.LeadDto;

import com.blender.service.EmailService;
import com.blender.service.LeadService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/rest")
public class LeadController {


    private LeadService leadService;
    private PasswordEncoder passwordEncoder;

    private EmailService emailService;

    public LeadController(LeadService leadService, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.leadService = leadService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    Logger logger = LoggerFactory.getLogger(LeadController.class);

    @GetMapping("/get")
    public String getMessage() {
        logger.info("get info messages");
        logger.trace("get trace message");
        logger.warn("get warn messages");
        logger.debug("get debug messages");
        logger.error("get error messages");
        return "Log messages";
    }


    //http://localhost:8080/api/rest
    @PostMapping
    public ResponseEntity<?> createLead(@Valid @RequestBody LeadDto leadDto, BindingResult result) {

        String encode = passwordEncoder.encode(leadDto.getPassword());
        leadDto.setPassword(encode);
        emailService.sendEmail(leadDto.getEmail(), "verification done", "text");
        LeadDto dto = leadService.createLead(leadDto);
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(dto, HttpStatus.CREATED);

    }

    @GetMapping
    public List<LeadDto> getAllLead() {
        return leadService.getAllLead();

    }

    @GetMapping("/{id}")
    public ResponseEntity<LeadDto> getById(@PathVariable("id") long id) {
        LeadDto dto = leadService.getById(id);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeadDto> updateById(@PathVariable("id") long id, @RequestBody LeadDto leadDto) {
        LeadDto dto = leadService.updateById(id, leadDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") long id) {
        leadService.deleteById(id);

        return new ResponseEntity<String>("deleted!!", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LeadDto loginRequest) {

        LeadDto leadDto = leadService.findByEmail(loginRequest.getEmail(), loginRequest.getPassword());
        if (leadDto != null && leadDto.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.ok(leadDto);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }


    }





