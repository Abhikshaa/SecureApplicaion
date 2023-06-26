package com.blender.controller;

import com.blender.Security.Student;
import com.blender.payload.LeadDto;
import com.blender.service.LeadService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/rest")
public class LeadController {
    @Autowired

    private Student student;

    private LeadService leadService;
    private PasswordEncoder passwordEncoder;

    public LeadController(LeadService leadService, PasswordEncoder passwordEncoder) {
        this.leadService = leadService;
        this.passwordEncoder = passwordEncoder;
    }

    Logger logger =LoggerFactory.getLogger(LeadController.class);

    @GetMapping("/get")
    public String getMessage(){
        logger.info("get info messages");
        logger.trace("get trace message");
        logger.warn("get warn messages");
        logger.debug("get debug messages");
        logger.error("get error messages");
        return "Log messages";
    }
    @GetMapping("/student")
    public String getStudent(){
        System.out.println(student);

        return "studentinfo";
    }

    @Lookup
    public String get(){
        System.out.println(student);
        return "Info messages";
    }

    //http://localhost:8080/api/rest
    @PostMapping
    public ResponseEntity<?> createLead(@Valid @RequestBody LeadDto leadDto, BindingResult result){

        String encode = passwordEncoder.encode(leadDto.getPassword());
        leadDto.setPassword(encode);
        LeadDto dto = leadService.createLead(leadDto);
        if(result.hasErrors()){
            return   new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

           return new ResponseEntity<>(dto, HttpStatus.CREATED);

    }
    @GetMapping
    public List<LeadDto> getAllLead(){
      return   leadService.getAllLead();

    }
    @GetMapping("/{id}")
    public ResponseEntity<LeadDto> getById(@PathVariable("id") long id){
        LeadDto dto=leadService.getById(id);

        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<LeadDto> updateById(@PathVariable("id") long id,@RequestBody LeadDto leadDto){
     LeadDto dto=   leadService.updateById(id,leadDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") long id){
        leadService.deleteById(id);

        return new ResponseEntity<String>("deleted!!",HttpStatus.OK);
    }
}
