package com.diabgnozscreennotesservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class DiabgnozscreenNotesServiceConfiguration {
	
	@Bean
    public CommonsRequestLoggingFilter commonsRequestLoggingFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setMaxPayloadLength(10000);
        filter.setIncludePayload(true);
        filter.setIncludeQueryString(true);
        filter.setIncludeHeaders(false);
        filter.setAfterMessagePrefix("Request success : ");
        filter.setBeforeMessagePrefix("User has entered the request : ");
        return filter;
    }

}
