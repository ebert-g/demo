package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Tarefa;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa , Long>  {
    
}
