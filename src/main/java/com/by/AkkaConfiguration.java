package com.by;

import static com.by.SpringExtension.SpringExtProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import akka.actor.ActorSystem;

/**
 * Created by yagamai on 15-11-30.
 */
@Configuration
public class AkkaConfiguration {
    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public ActorSystem actorSystem() {
        ActorSystem system = ActorSystem.create("AkkaJavaSpring");
        SpringExtProvider.get(system).initialize(applicationContext);
        return system;
    }
}
