package com.hellozum.challenge.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.hellozum.challenge.util.Parameters;

@ConfigurationProperties(prefix = "application")
public class ChallengeConfig {
	private String description;
	private Integer maxPageSize = Parameters.DEFAULT_MAX_PAGE_SIZE;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getMaxPageSize() {
		return maxPageSize;
	}

	public void setMaxPageSize(Integer maxPageSize) {
		this.maxPageSize = maxPageSize;
	}
}
