package edu.nju.service.repo;

import edu.nju.service.entity.ImageLatex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ImageRepository extends JpaRepository<ImageLatex, Integer>, JpaSpecificationExecutor<ImageLatex> {
}
