package clinica.model.entity;

import java.util.Date;

public class Venda {
    private int id;
    private Date data;
    private Produto produto;
    private Cliente cliente; 
    private int quantidade;
    private String formaPagamento;
    private double valorPraticado;  

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public double getValorPraticado() {
        return valorPraticado;
    }

    public void setValorPraticado(double valorPraticado) {
        this.valorPraticado = valorPraticado;
    }
}