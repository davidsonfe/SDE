package com.sde.repository;

import org.springframework.data.repository.CrudRepository;

import com.sde.models.Estudante;
import com.sde.models.Ocorrencia;

public interface EstudanteRepository extends CrudRepository<Estudante, String> {
	
	Iterable<Estudante> findByOcorrencia(Ocorrencia ocorrencia);
	Estudante findByMat(String mat);

}
