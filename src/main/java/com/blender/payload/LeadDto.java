package com.blender.payload;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class LeadDto {

    private  long id;
@NotEmpty
@Size(min=4,max = 50,message = "character should be 14")
    private String name;
@NotEmpty
@Size(min=3,max = 50,message = "character should be 16")
    private String email;

    private long mobile;
    private String password;
}
