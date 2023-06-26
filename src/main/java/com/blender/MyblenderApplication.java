package com.blender;

import com.blender.Security.Student;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyblenderApplication {
	@Bean
	public ModelMapper modelMapper(){

		return new ModelMapper();
	}
	@Bean
	public Student student(){

		return new Student();
	}

	public static void main(String[] args) {

		SpringApplication.run(MyblenderApplication.class, args);
	}

}
