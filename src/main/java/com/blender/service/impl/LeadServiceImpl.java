package com.blender.service.impl;

import com.blender.entities.Lead;
import com.blender.exception.ResourceNotFound;
import com.blender.payload.LeadDto;
import com.blender.repository.LeadRepository;
import com.blender.service.LeadService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeadServiceImpl implements LeadService {

    private LeadRepository leadRepository;
    private ModelMapper mapper;
    private PasswordEncoder passwordEncoder;

    public LeadServiceImpl(LeadRepository leadRepository, ModelMapper mapper, PasswordEncoder passwordEncoder) {
        this.leadRepository = leadRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LeadDto createLead(LeadDto leadDto) {

        Lead lead = mapToEntity(leadDto);
        String encode = passwordEncoder.encode(lead.getPassword());
        leadDto.setPassword(encode);

        Lead save = leadRepository.save(lead);
        LeadDto dto = mapToDto(save);

        return dto;
    }

    private Lead mapToEntity(LeadDto leadDto) {

        Lead lead = mapper.map(leadDto, Lead.class);
        return lead;
    }

    @Override
    public List<LeadDto> getAllLead() {
        List<Lead> lead = leadRepository.findAll();
        return lead.stream().map(lead1->mapToDto(lead1)).collect(Collectors.toList());
    }

    @Override
    public LeadDto getById(long id) {
        Lead lead = leadRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Lead", "ID", id));

        return mapToDto(lead);
    }

    @Override
    public LeadDto updateById(long id, LeadDto leadDto) {
        Lead lead = leadRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Lead", "ID", id));


        lead.setName(leadDto.getName());
        lead.setEmail(leadDto.getEmail());
        lead.setMobile(leadDto.getMobile());

        if(leadDto.getPassword() !=null){
            String encode = passwordEncoder.encode(leadDto.getPassword());
            leadDto.setPassword(encode);
        }
        Lead update = leadRepository.save(lead);
        return mapToDto(update);
    }


    @Override
    public void deleteById(long id) {
        Lead lead = leadRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Lead", "ID", id));
        leadRepository.deleteById(id);

    }
    LeadDto mapToDto(Lead lead){
        LeadDto dto = mapper.map(lead, LeadDto.class);
        return dto;
    }
    @Override
    public LeadDto findByEmail(String email, String password) {
        Lead lead = leadRepository.findByEmailAndPassword(email, password);
        if (lead != null) {
            return mapToDto(lead);
        } else {
            return null;
        }
    }
         @Override
         public void resetPassword(String email, String newPassword) {
              Lead lead = leadRepository.findByEmail(email);
              if (lead != null) {
                   lead.setPassword(passwordEncoder.encode(newPassword));
                   leadRepository.save(lead);
              }

    }
}
