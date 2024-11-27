package com.aluracursos.litealura;

import com.aluracursos.litealura.principal.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LitealuraApplication implements CommandLineRunner {

	@Autowired
	private Principal principal;

	public static void main(String[] args) {
		SpringApplication.run(LitealuraApplication.class, args);
	}

	@Override
	public void run(String... args) {
		principal.muestraElMenu();
	}

}
