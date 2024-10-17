package br.com.foobar.application.service;

import br.com.foobar.adapter.controller.ProcessControlController;
import br.com.foobar.adapter.controller.dto.ProcessControlDTO;
import br.com.foobar.domain.ProcessControl;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;

@Service
public class QueueSender {

    private final ProcessControlController processControlController;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.routingkey}")
    private String routingkey;

    public QueueSender(final ProcessControlController processControlController) {
        this.processControlController = processControlController;
    }

    public Long send(final byte[] file) {

        LocalDateTime localDateTime = LocalDateTime.now();

        ProcessControl proccessControl = new ProcessControl(localDateTime, new HashSet<>());

        Long id = this.processControlController.create(proccessControl);

        ProcessControlDTO proccessControlDTO = new ProcessControlDTO(id, localDateTime, file);

        amqpTemplate.convertAndSend(exchange, routingkey, proccessControlDTO);

        return id;

    }

}