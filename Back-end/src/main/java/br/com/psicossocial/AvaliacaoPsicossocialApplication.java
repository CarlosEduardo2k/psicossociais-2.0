package br.com.psicossocial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AvaliacaoPsicossocialApplication {

	public static void main(String[] args) {
		SpringApplication.run(AvaliacaoPsicossocialApplication.class, args);
	}
}
