package com.sde.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sde.models.Professor;
import com.sde.models.Ocorrencia;


@Repository
public interface ProfessorRepository extends CrudRepository<Professor, String>{
	Iterable<Professor> findByOcorrencia(Ocorrencia ocorrencia);
	Professor findByCod(String cod);
}
