package com.blender.repository;

import com.blender.entities.Lead;
import com.blender.payload.LeadDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeadRepository extends JpaRepository<Lead,Long> {

     Lead findByEmailAndPassword(String email, String password);

    // Lead findByEmail(String email);
}
