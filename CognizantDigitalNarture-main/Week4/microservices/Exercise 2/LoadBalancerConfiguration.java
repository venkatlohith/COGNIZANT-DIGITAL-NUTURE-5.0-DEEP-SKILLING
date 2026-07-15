package com.example.loadbalancer.config;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.RandomLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

// Custom Load Balancer Configuration
// Overrides the default Round Robin strategy with Random load balancing
// This means requests are distributed randomly across available service instances
@Configuration
public class LoadBalancerConfiguration {

    @Bean
    public ReactorLoadBalancer<ServiceInstance> randomLoadBalancer(
            Environment environment,
            LoadBalancerClientFactory loadBalancerClientFactory) {

        // Get the service name from environment (set by LoadBalancerClientFactory)
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);

        // Use RandomLoadBalancer instead of default RoundRobinLoadBalancer
        // RandomLoadBalancer picks a random instance from the available instances list
        return new RandomLoadBalancer(
                loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class),
                name);
    }
}
