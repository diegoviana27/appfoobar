package br.com.foobar.application.service;

import br.com.foobar.adapter.controller.ProcessControlController;
import br.com.foobar.adapter.controller.ProductController;
import br.com.foobar.adapter.controller.dto.ProcessControlDTO;
import br.com.foobar.adapter.controller.dto.ProcessControlErrorDTO;
import br.com.foobar.adapter.controller.dto.ProductDTO;
import br.com.foobar.application.helper.HelperCSV;
import br.com.foobar.usecase.product.exception.ProductAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class QueueConsumer {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(QueueConsumer.class);

    private final ProductController productController;

    private final ProcessControlController processControlController;

    public QueueConsumer(final ProductController productController,
                         final ProcessControlController processControlController) {
        this.productController = productController;
        this.processControlController = processControlController;
    }

    @RabbitListener(queues = {"${spring.rabbitmq.queue}"}, errorHandler = "rabbitHandler")
    public void receive(final @Payload ProcessControlDTO proccessControlDTO) {

        Set<ProcessControlErrorDTO> erros = new HashSet<>();

        try {

            processFile(proccessControlDTO, erros);

        } catch (Exception e) {

            erros.add(new ProcessControlErrorDTO(
                    String.format("An error was encountered while processing the file: %s", e.getMessage())));
            LOGGER.error(
                    String.valueOf(String.format("An error was encountered while processing the file: %s",
                            e.getMessage())));

        } finally {

            proccessControlDTO.setTerminatedProcessDate(LocalDateTime.now());
            proccessControlDTO.setErrors(erros);
            proccessControlDTO.setProcessed(true);
            this.processControlController.update(
                    ProcessControlDTO.parseFromDto(proccessControlDTO));

        }
    }

    private void processFile(ProcessControlDTO proccessControlDTO,
                             Set<ProcessControlErrorDTO> erros)
            throws IOException {

        Set<ProductDTO> products = HelperCSV.parseProductsDTOList(proccessControlDTO.getFile());

        for (ProductDTO productDTO : products) {
            try {
                this.productController.create(ProductDTO.parseFromDto(productDTO));
            } catch (ProductAlreadyExistsException exp) {
                LOGGER.error(
                        String.valueOf(
                                String.format("Product already exists with the code %s", productDTO.getLm())));
                erros.add(new ProcessControlErrorDTO(
                        String.format("Product already exists with the code %s", productDTO.getLm())));
            }
        }

    }
}
