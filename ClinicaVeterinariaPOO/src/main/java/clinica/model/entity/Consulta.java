package clinica.model.entity;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Classe de entidade Consulta.
 */
public class Consulta {
    private int id;
    private Date dataHora; 
    private Pet pet;
    private Veterinario veterinario;
    private String descricao;
    private List<Servico> servicos = new ArrayList<>();
    private String formaPagamento; // Novo campo
    private double valorPraticado; // Novo campo

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateTime() { 
        return dataHora;
    }

    public void setDateTime(Date dataHora) {
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

    public List<Servico> getServicos() {
        return servicos;
    }

    public void addServico(Servico servico) {
        this.servicos.add(servico);
    }

    public String getFormaPagamento() { // Getter do novo campo
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) { // Setter do novo campo
        this.formaPagamento = formaPagamento;
    }

    public double getValorPraticado() { // Getter do novo campo
        return valorPraticado;
    }

    public void setValorPraticado(double valorPraticado) { // Setter do novo campo
        this.valorPraticado = valorPraticado;
    }
    
    public void limparServicos() {
        if (servicos != null) {
            servicos.clear();  // Limpa todos os serviços da consulta
        }
    }
}
