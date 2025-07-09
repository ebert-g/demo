package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Tarefa;
import com.example.demo.repository.TarefaRepository;

@Service
public class TarefaServiceImpl implements TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Override
    public List<Tarefa> listarTodas() {
        return tarefaRepository.findAll();
    }

    @Override
    public Tarefa salvar(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    @Override
    public Optional<Tarefa> buscarPorId(Long id) {
        return tarefaRepository.findById(id);
    }

    @Override
    public void alterarEstado(Long id) {
        tarefaRepository.findById(id).map(tarefa -> {
            tarefa.setEstado(!tarefa.isEstado());
            return tarefaRepository.save(tarefa);
        }).orElseThrow(() -> new IllegalArgumentException("Tarefa inválida com ID: " + id));
    }

    @Override
    public void excluir(Long id) {
        tarefaRepository.deleteById(id);
    }
    
}
