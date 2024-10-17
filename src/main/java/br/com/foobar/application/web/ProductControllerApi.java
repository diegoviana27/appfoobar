package br.com.foobar.application.web;

import br.com.foobar.adapter.controller.ProductController;
import br.com.foobar.adapter.controller.dto.ProductDTO;
import br.com.foobar.application.service.QueueSender;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Set;

@RestController
@RequestMapping("/v1/products")
@Tag(name = "Products Resources API")
public class ProductControllerApi {

    public static final String TYPE = "text/csv";

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ProductControllerApi.class);

    private final ProductController productController;

    @Autowired
    QueueSender rabbitMQSender;

    public ProductControllerApi(
            final ProductController productController) {
        this.productController = productController;
    }

    @Operation(summary = "Send file to save products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "File accepted to process"),
            @ApiResponse(responseCode = "400", description = "Invalid file supplied")
    })
    @PostMapping(value = "/", consumes = "multipart/form-data")
    public ResponseEntity<String> saveProduct(final @RequestPart("file") MultipartFile file)
            throws IOException {

        if (!TYPE.equals(file.getContentType())) {
            LOGGER.error("Error type file: {}", file.getName());
            return new ResponseEntity<>(String.format("Error type file: %s ", file.getOriginalFilename()),
                    HttpStatus.BAD_REQUEST);
        }

        Long id = rabbitMQSender.send(file.getBytes());
        return new ResponseEntity<>("{\"tracking code\":\"" + id + "\"}", HttpStatus.ACCEPTED);

    }

    @Operation(summary = "Update product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated"),
            @ApiResponse(responseCode = "204", description = "Product not found")
    })
    @PutMapping
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody final ProductDTO product) {
        return new ResponseEntity<>(
                productController.update(ProductDTO.parseFromDto(product)), HttpStatus.OK);
    }

    @Operation(summary = "Delete product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deleted"),
            @ApiResponse(responseCode = "204", description = "Product not found")
    })
    @DeleteMapping("{lm}")
    @ResponseStatus(value = HttpStatus.OK)
    public void removeProduct(@PathVariable("lm") final Long lm) {
        this.productController.deleteProduct(lm);
    }

    @Operation(summary = "Find all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products found"),
            @ApiResponse(responseCode = "204", description = "Products is empty")
    })
    @GetMapping
    public ResponseEntity<Set<ProductDTO>> findAllProduct() {
        return new ResponseEntity<>(this.productController.findAllProduct(), HttpStatus.OK);
    }

    @Operation(summary = "Find product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "204", description = "Product not found")
    })
    @GetMapping("{lm}")
    public ResponseEntity<ProductDTO> findProduct(@PathVariable("lm") final Long lm) {
        return new ResponseEntity<>(this.productController.findProduct(lm), HttpStatus.OK);
    }

}
