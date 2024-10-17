package br.com.foobar.adapter.repository;

import br.com.foobar.adapter.repository.model.ProcessControlModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryProcessControl extends JpaRepository<ProcessControlModel, Long> {

}
