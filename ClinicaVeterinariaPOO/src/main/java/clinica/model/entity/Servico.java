package clinica.model.entity;

import java.util.ArrayList;
import java.util.List;

public class Servico {
    private int id;
    private String nome;
    private String descricao;
    private float preco;
    private List<Consulta> consultas; // Relação N para N com Consulta

    public Servico() {
        this.consultas = new ArrayList<>(); // Inicializa a lista para evitar NullPointerException
    }

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

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }

    // Adicionar uma consulta à lista
    public void addConsulta(Consulta consulta) {
        if (consulta != null && !consultas.contains(consulta)) {
            this.consultas.add(consulta);
        }
    }
}
