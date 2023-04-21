package com.blender.controller;

import com.blender.payload.LeadDto;
import com.blender.service.LeadService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/rest")
public class LeadController {

    private LeadService leadService;
    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }
    //http://localhost:8080/api/rest
    @PostMapping
    public ResponseEntity<?> createLead(@Valid @RequestBody LeadDto leadDto, BindingResult result){
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
