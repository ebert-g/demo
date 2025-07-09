package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 a 100 carcteres.")
    private String nome;

    @NotBlank
    @Size(min = 5, max = 255, message = "A descrição deve ter entre 5 a 255 carecteres")
    private String descricao;

    private boolean estado;

    public Tarefa(String nome, String descricao, boolean estado) {
        this.nome = nome;
        this.descricao = descricao;
        this.estado = estado;
    }

}
