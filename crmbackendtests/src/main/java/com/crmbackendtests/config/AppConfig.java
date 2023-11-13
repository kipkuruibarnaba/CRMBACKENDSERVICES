package com.crmbackendtests.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author BARNABA
 * @created 13/11/2023 - 12:08 PM
 * @project crmbackendtests
 */
@Configuration
@Data
public class AppConfig {
    @Value("${logging_file_name}")
    private String loggingpath;
}
