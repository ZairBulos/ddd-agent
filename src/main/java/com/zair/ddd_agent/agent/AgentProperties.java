package com.zair.ddd_agent.agent;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ddd-agent")
public record AgentProperties(String outputDir) {

    public AgentProperties {
        if (outputDir == null || outputDir.isBlank()) {
            outputDir = "linkedin-posts";
        }
    }

}
