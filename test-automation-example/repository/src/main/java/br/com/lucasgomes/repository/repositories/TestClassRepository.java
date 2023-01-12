package br.com.lucasgomes.repository.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.lucasgomes.testautomation.model.TestClass;

@Repository
public interface TestClassRepository extends CrudRepository<TestClass, String> {

}
