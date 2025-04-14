package com.example.demo.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

@Component
public class SecretsManagerUtil {

    public String getRDSPassword() {
        String secretName = "myappsecret1"; // Replace with actual secret name
        Region region = Region.US_EAST_1; // Replace with your AWS region

        SecretsManagerClient client = SecretsManagerClient.builder()
                .region(region)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();

        GetSecretValueRequest request = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

        GetSecretValueResponse response = client.getSecretValue(request);
        String secretJson = response.secretString();

        // Parse JSON and return only the password
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(secretJson);
            return rootNode.get("password").asText(); // 👈 Just the password
        } catch (Exception e) {
            e.printStackTrace();
            return "Error parsing secret";
        }
    }
}
