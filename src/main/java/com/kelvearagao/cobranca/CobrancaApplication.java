package com.kelvearagao.cobranca;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

@SpringBootApplication
public class CobrancaApplication {
	
	/**
	 * Starter da aplicação.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(CobrancaApplication.class, args);
	}
	
	/**
	 * Configura a localização.
	 * 
	 * @return FixedLocaleResolver
	 */
	@Bean
	public LocaleResolver localeResolver() {
		return new FixedLocaleResolver(new Locale("pt", "BR"));
	}
	
}
