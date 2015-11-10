package com.by.controller;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.actor.TestActor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Inbox;
import akka.actor.Props;
import scala.concurrent.duration.Duration;

@Controller
@RequestMapping("/akka")
public class AkkaController {
	final ActorSystem system = ActorSystem.create("MySystem");
	final ActorRef child = system.actorOf(Props.create(TestActor.class), "test");

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public String test() {
		final Inbox inbox = Inbox.create(system);
		inbox.send(child, "hello");
		try {
			return (String) inbox.receive(Duration.create(1, TimeUnit.SECONDS));
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		return null;
	}
}
