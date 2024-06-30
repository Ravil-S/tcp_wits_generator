package com.wits_generator.scheduler;

import com.wits_generator.service.InclinometryDataGenerator;
import com.wits_generator.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MessageJobScheduler {

    private MessageService messageService;
    @Autowired
    InclinometryDataGenerator inclinometryDataGenerator;

  //  private InclinometryDataGenerator inclinometryDataGenerator=new InclinometryDataGenerator();

    @Autowired
    public MessageJobScheduler(MessageService messageService) {
        this.messageService = messageService;
       // textSplitter(WITS_TEXT, this.pairWitsData);
    }

    @Scheduled(fixedDelay = 500L)
    public void sendMessageJob() {
       // messageService.sendMessage(pairWitsData.get(count));
        messageService.sendMessage(inclinometryDataGenerator);
    }
}
