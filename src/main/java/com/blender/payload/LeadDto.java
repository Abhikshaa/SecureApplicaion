package com.blender.payload;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class LeadDto {

    private  long id;
@NotEmpty
@Size(min=7,max = 14,message = "character should be 14")
    private String name;
@NotEmpty
@Size(min=12,max = 16,message = "character should be 16")
    private String email;

    private long mobile;
}
