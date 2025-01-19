package com.msa.membership;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@NoArgsConstructor @AllArgsConstructor
public class SecurityKeyConfig {
    @Value("${spring.security.algorithm}")
    private String algorithm;

    @Value("${spring.security.transformation}")
    private String transformation;

    @Value("${spring.security.key}")
    private String key;

}
