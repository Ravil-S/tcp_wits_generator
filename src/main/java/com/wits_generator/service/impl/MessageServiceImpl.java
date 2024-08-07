package com.wits_generator.service.impl;

import com.wits_generator.gateway.TcpClientGateway;
import com.wits_generator.service.InclinometryDataGenerator;
import com.wits_generator.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);

    private TcpClientGateway tcpClientGateway;

    @Autowired
    public MessageServiceImpl(TcpClientGateway tcpClientGateway) {
        this.tcpClientGateway = tcpClientGateway;
    }

    @Override
    public void sendMessage(InclinometryDataGenerator inclinometryDataGenerator) {
      /*  String message = LocalDateTime.now().toString();
        LOGGER.info("Send message: {}", message);
        byte[] responseBytes = tcpClientGateway.send(message.getBytes());
        String response = new String(responseBytes);
        LOGGER.info("Receive response: {}", response);*/
        String message = inclinometryDataGenerator.getNewInclinometryData();

       // System.out.println(message);
        byte[] responseBytes = tcpClientGateway.send(message.getBytes());
        String response = new String(responseBytes);
        if (response.contains("stop")) {inclinometryDataGenerator.stop();}
        if (response.contains("start")) {inclinometryDataGenerator.start();}
        System.out.println(response);
    }

}
