package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Tarefa;
import com.example.demo.service.TarefaService;

import jakarta.validation.Valid;

@Controller
public class TarefaController {
    @Autowired
    private TarefaService tarefaService;

    @GetMapping("/")
    public String listarTarefas(Model model) {
        model.addAttribute("tarefas", tarefaService.listarTodas());
        return "index";
    }

    @PostMapping("/add")
    public String addTarefa(@Valid Tarefa tarefa, BindingResult result, RedirectAttributes redirectAttributes,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("tarefas", tarefaService.listarTodas());
            model.addAttribute("tarefa", tarefa);
            model.addAttribute("errors", result.getAllErrors());
            return "index";
        }
        tarefa.setEstado(false);
        tarefaService.salvar(tarefa);

        redirectAttributes.addFlashAttribute("mensagemSucesso", "Tarefa adicionada com sucesso!");

        return "redirect:/";
    }

    @PostMapping("/alterarEstado")
    public String alterarEstado(@RequestParam Long id) {
        tarefaService.alterarEstado(id);

        return "redirect:/";

    }

    @PostMapping("/excluir")
    public String excluirTarefa(@RequestParam Long id) {
        tarefaService.excluir(id);
        return "redirect:/";
    }

}
