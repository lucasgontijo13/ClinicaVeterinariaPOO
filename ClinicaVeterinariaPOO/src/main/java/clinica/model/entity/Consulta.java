package clinica.model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de entidade Consulta.
 */
public class Consulta {
    private int id;
    private LocalDateTime dataHora; // Alterado para LocalDateTime
    private Pet pet;
    private Veterinario veterinario;
    private String descricao;
    private Pagamento pagamento;
    private List<Servico> servicos = new ArrayList<>();

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() { // Alterado para LocalDateTime
        return dataHora;
    }

    public void setDateTime(LocalDateTime dataHora) { // Alterado para LocalDateTime
        this.dataHora = dataHora;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void addServico(Servico servico) {
        this.servicos.add(servico);
    }


}
