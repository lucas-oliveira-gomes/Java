package br.com.lucasgomes.repository.repositories;

import org.springframework.data.repository.CrudRepository;

import br.com.lucasgomes.testautomation.model.TestClass;

public interface TestClassRepository extends CrudRepository<TestClass, String> {

}
