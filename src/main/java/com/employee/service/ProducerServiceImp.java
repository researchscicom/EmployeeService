package com.employee.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProducerServiceImp implements ProducerService {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value( "${rabbitmq.exchange}" )
    private String exchange;

    @Value( "${rabbitmq.routingkey}" )
    private String routingkey;

    @Override
    public Object sendMsg(Long proId) throws Exception{
        Object response = amqpTemplate.convertSendAndReceive(exchange,routingkey,proId);
        System.out.println("============== Response ==================");
        System.out.println(response);
        System.out.println("==========================================");
        return response;
    }
}
