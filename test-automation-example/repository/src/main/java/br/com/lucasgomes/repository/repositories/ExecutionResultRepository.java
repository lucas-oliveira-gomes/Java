package br.com.lucasgomes.repository.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import br.com.lucasgomes.testautomation.model.ExecutionResult;

public interface ExecutionResultRepository extends CrudRepository<ExecutionResult, UUID> {

}
