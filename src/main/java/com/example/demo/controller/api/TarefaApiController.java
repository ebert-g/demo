package com.example.demo.controller.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Tarefa;
import com.example.demo.service.TarefaService;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaApiController {
    @Autowired
    private TarefaService tarefaService;

    @GetMapping
    public List<Tarefa> listarTodas() {
        return tarefaService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarPorId(@PathVariable Long id) {
        Optional<Tarefa> tarefaOptional = tarefaService.buscarPorId(id);

        if (tarefaOptional.isPresent()) {
            return ResponseEntity.ok(tarefaOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping
    public ResponseEntity<Tarefa> criarTarefa(@RequestBody Tarefa novaTarefa) {
        novaTarefa.setEstado(false);
        Tarefa tarefaSalva = tarefaService.salvar(novaTarefa);

        return ResponseEntity.status(HttpStatus.CREATED).body(tarefaSalva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizarTarefa(
            @PathVariable Long id, @RequestBody Tarefa tarefaAtualizada) {

        Optional<Tarefa> tarefaExistenteOptional = tarefaService.buscarPorId(id);

        if (tarefaExistenteOptional.isPresent()) {
            Tarefa tarefaExistente = tarefaExistenteOptional.get();
            tarefaExistente.setNome(tarefaAtualizada.getNome());
            tarefaExistente.setDescricao(tarefaAtualizada.getDescricao());
            tarefaExistente.setEstado(tarefaAtualizada.isEstado());
            Tarefa tarefaSalva = tarefaService.salvar(tarefaExistente);

            return ResponseEntity.ok(tarefaSalva);

        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirTarefa(@PathVariable Long id) {
        Optional<Tarefa> optionalTarefa = tarefaService.buscarPorId(id);

        if (optionalTarefa.isPresent()) {
            tarefaService.excluir(id);
            return ResponseEntity.noContent().build();

        } else {
            return ResponseEntity.notFound().build();
        }

    }

}
