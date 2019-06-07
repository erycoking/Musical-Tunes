package com.erycoking.MusicStore;

import com.erycoking.MusicStore.config.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties({FileStorageProperties.class})
public class MusicStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicStoreApplication.class, args);
	}

}
