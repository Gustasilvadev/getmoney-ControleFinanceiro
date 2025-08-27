package com.getmoney.repository;

import com.getmoney.entity.Categoria;
import com.getmoney.enums.CategoriaTipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    List<Categoria> findByTipo(CategoriaTipo tipo);
}
