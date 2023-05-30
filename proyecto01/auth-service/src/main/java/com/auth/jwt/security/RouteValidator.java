package com.auth.jwt.security;


import com.auth.jwt.controller.request.ServiceRequest;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

@Component
@ConfigurationProperties(prefix = "admin-paths")
public class RouteValidator {

    private List<ServiceRequest> paths;

    public boolean isAdmin(ServiceRequest request) {
        return paths.stream()
                .anyMatch(
                        p -> Pattern.matches(p.getUri(), request.getUri())
                                && p.getMethod().equals(request.getMethod())
                );
    }

    public List<ServiceRequest> getPaths() {
        return paths;
    }

    public void setPaths(List<ServiceRequest> paths) {
        this.paths = paths;
    }
}
