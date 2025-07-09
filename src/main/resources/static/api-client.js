document.addEventListener("DOMContentLoaded", () => {
  const listaTarefasContainer = document.getElementById("lista-tarefas");
  const addTarefaForm = document.getElementById("add-tarefa-form");
  const API_URL = "http://192.168.100.5:8080/api/tarefas";

  async function carregarTarefas() {
    try {
      const response = await fetch(API_URL);

      if (!response.ok) {
        throw new Error(`Erro na requisição: ${response.statusText}`);
      }

      const tarefas = await response.json();

      renderizarListaTarefas(tarefas);
    } catch (error) {
      console.error("Falha ao carregar tarefas", error);
      listaTarefasContainer.innerHTML =
        "<p class='text-danger text-center'> Não foi possivel carregar as tarefas. </p>";
    }
  }

  addTarefaForm.addEventListener("submit", async (event) => {
    event.preventDefault();

    const nomeInput = document.getElementById("nome-tarefa");
    const descricaoInput = document.getElementById("descricao-tarefa");

    const novaTarefa = {
      nome: nomeInput.value,
      descricao: descricaoInput.value,
      estado: true,
    };

    try {
      const response = await fetch(API_URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(novaTarefa),
      });

      if (!response.ok) {
        throw new Error("Falha ao adicionar tarefa.");
      }

      nomeInput.value = "";
      descricaoInput.value = "";

      carregarTarefas();
    } catch (error) {
      console.error("Erro ao adicionar a tarefa ", error);
      alert("Não foi possivel adicionar tarefa.");
    }
  });

  function renderizarListaTarefas(tarefas) {
    listaTarefasContainer.innerHTML = "";
    if (tarefas.length === 0) {
      listaTarefasContainer.innerHTML =
        '<p class= "text-muted"> Nenhuma tarefa na lista. Adicione uma nova!</p> ';
      return;
    }

    const ul = document.createElement("ul");
    ul.className = "list-group";

    tarefas.forEach((tarefa) => {
      const li = document.createElement("li");
      li.className =
        "list-group-item d-flex justify-content-between align-items-center";

      li.innerHTML = `
      <input class="form-check-input me-2 change-state-cb" type="checkbox" ${
        tarefa.estado ? "checked" : ""
      } data-id="${tarefa.id}">
      <span class="${
        tarefa.estado ? "text-decoration-line-through text-muted" : ""
      }">
      <strong> ${tarefa.nome}</strong> - ${tarefa.descricao}
      </span>
      <div>
        <button class="btn btn-danger btn-sm delete-btn" data-id="${
          tarefa.id
        }">Excluir</button>
      </div>
      `;
      ul.appendChild(li);
    });

    listaTarefasContainer.appendChild(ul);
  }

  listaTarefasContainer.addEventListener("click", async (event) => {
    if (event.target.classList.contains("delete-btn")) {
      const tarefaId = event.target.dataset.id;

      if (confirm("Tem certeza que deseja excluir essa tarefa? ")) {
        await excluirTarefa(tarefaId);
      }
    }

    if (event.target.classList.contains("change-state-cb")) {
      const tarefaId = event.target.dataset.id;
      const novoEstado = event.target.checked;
      await atulizarEstadoTarefa(tarefaId, novoEstado);
    }
  });

  async function excluirTarefa(id) {
    try {
      const response = await fetch(`${API_URL}/${id}`, {
        method: "DELETE",
      });

      if (!response.ok) {
        throw new Error("Falha ao excluir a tarefa.");
      }

      carregarTarefas();
    } catch (error) {
      console.error("Erro ao excluir a tarefa: ", error);
      alert("Não foi possível excluir a tarefa.");
    }
  }

  async function atulizarEstadoTarefa(id, novoEstado) {
    try {
      const responseGet = await fetch(`${API_URL}/${id}`);

      if (!responseGet.ok)
        throw new Error("Falha ao buscar tarefa para atualização");

      const tarefa = await responseGet.json();

      tarefa.estado = novoEstado;

      const responsePut = await fetch(
        `
      ${API_URL}/${id}`,
        {
          method: "PUT",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(tarefa),
        }
      );
      if (!responsePut.ok) {
        throw new Error("Falha ao atualizar tarefa. ");
      }

      carregarTarefas();
    } catch (error) {
      console.error("Erro ao atualizar estado: ", error);
      alert("Não foi possível atualizar o estado da tarefa. ");
    }
  }

  carregarTarefas();
});
