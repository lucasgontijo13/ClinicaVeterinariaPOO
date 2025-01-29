package clinica.model.entity;

import java.util.ArrayList;
import java.util.List;

public class Servico {
    private int id;
    private String nome;
    private String descricao;
    private float preco;
    private List<Consulta> consultas = new ArrayList<>(); // Lista de consultas associadas ao serviço

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void addConsulta(Consulta consulta) {
        this.consultas.add(consulta);
    }
}
