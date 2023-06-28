package com.blender.service;

import com.blender.payload.LeadDto;

import java.util.List;

public interface LeadService {

        LeadDto createLead(LeadDto leadDto);

    List<LeadDto> getAllLead();

    LeadDto getById(long id);

    LeadDto updateById(long id, LeadDto leadDto);

    void deleteById(long id);



     LeadDto findByEmail(String email, String password);
}
