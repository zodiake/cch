package com.by.actor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.UntypedActor;

public class TestActor extends UntypedActor {
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
