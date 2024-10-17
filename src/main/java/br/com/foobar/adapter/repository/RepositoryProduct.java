package br.com.foobar.adapter.repository;

import br.com.foobar.adapter.repository.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryProduct extends JpaRepository<ProductModel, Long> {

}
