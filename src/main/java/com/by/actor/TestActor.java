package com.by.actor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.by.service.CardService;

import akka.actor.UntypedActor;

@Component
@Scope("prototype")
public class TestActor extends UntypedActor {
	@Autowired
	private CardService service;

	private Logger log = LoggerFactory.getLogger(TestActor.class);

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof String) {
			log.info("received String message:{}", message);
			sender().tell("hello", null);
		} else {
			unhandled(message);
		}
	}
}
