package com.pfe.socket;

import com.pfe.Controller.DepartementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
  /*  @Autowired
    DepartementService der ;*/
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(@Payload String message) throws Exception {
        Thread.sleep(1000); // simulated delay
       // der.addepp(message);
        return new Greeting("Hello, " + message+ "!");
    }
   /* @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting1(@Payload String message) throws Exception {
        Thread.sleep(1000); // simulated delay
        // der.addepp(message);
        return new Greeting("Hello, " + message+ "!");
    }*/
}
