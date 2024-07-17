package tn.arabsoft.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tn.arabsoft.auth.entity.Sanction;

public interface SanctionRepository extends JpaRepository<Sanction, Long> {

	@Query(value="select * from sanction where matriculep=:mat",nativeQuery=true)
	public List<Sanction> getSancByMat(@Param("mat") String mat);
}
