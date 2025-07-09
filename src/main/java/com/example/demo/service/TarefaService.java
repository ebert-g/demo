package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.Tarefa;

public interface  TarefaService {
    List<Tarefa> listarTodas();
    Tarefa salvar(Tarefa tarefa);
    Optional<Tarefa> buscarPorId(Long id);
    void alterarEstado(Long id);
    void excluir(Long id);
}
