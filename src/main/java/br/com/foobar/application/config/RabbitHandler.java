package br.com.foobar.application.config;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.stereotype.Component;

@Component
class RabbitHandler implements RabbitListenerErrorHandler {

    @Override
    public Object handleError(Message amqpMessage, org.springframework.messaging.Message<?> message,
                              ListenerExecutionFailedException exception) {

        if (Boolean.TRUE.equals(amqpMessage.getMessageProperties().isRedelivered())) {
            throw new AmqpRejectAndDontRequeueException(exception);
        } else {
            throw exception;
        }
    }

}
