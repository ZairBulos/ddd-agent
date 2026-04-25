package com.zair.ddd_agent;

import com.zair.ddd_agent.agent.AgentProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AgentProperties.class)
public class DddAgentApplication {

	public static void main(String[] args) {
		SpringApplication.run(DddAgentApplication.class, args);
	}

}
