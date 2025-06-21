package com.jobapp.notification;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class JobEventConsumer {
    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void handleJob(Map<String,Object> jobAddEvent) {
        System.out.println("Received job event: "+ jobAddEvent);
    }
}
