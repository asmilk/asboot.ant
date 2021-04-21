package asboot.auth.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class KaptchaConfig {

	@Autowired
	@Qualifier("producerConfig")
	private Properties config;

	@Bean
	@ConfigurationProperties("google.code")
	public Properties producerConfig() {
		return new Properties();
	}

	@Bean
	public DefaultKaptcha producer() {
		this.config.forEach((key, value) -> log.info("{}:{}", key, value));
		DefaultKaptcha producer = new DefaultKaptcha();
		producer.setConfig(new Config(this.config));
		return producer;
	}

}
