package br.com.lucasgomes.repository.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.lucasgomes.testautomation.model.Test;

@Repository
public interface TestRepository extends CrudRepository<Test, UUID> {

}
