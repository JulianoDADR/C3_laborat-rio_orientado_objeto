package br.com.nutricao.model;
import br.com.nutricao.enums.TipoUsuario;

public abstract class Usuario {
    protected Long id;
    protected String nome;
    protected String email;
    protected String senha;
    protected String telefone;
    protected TipoUsuario tipo;
    
    public Usuario() {}
    
    public Usuario(String nome, String email, String senha, TipoUsuario tipo) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
    }
    
    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public TipoUsuario getTipo() { return tipo; }
}