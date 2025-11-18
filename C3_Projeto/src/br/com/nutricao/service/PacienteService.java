package br.com.nutricao.service;
import br.com.nutricao.model.Paciente;
import br.com.nutricao.exception.*;
import java.util.*;

public class PacienteService {
    private List<Paciente> pacientes = new ArrayList<>();
    private Long proximoId = 1L;
    
    public Paciente cadastrar(Paciente p) throws DadosInvalidosException {
        if (p.getNome() == null || p.getNome().isEmpty()) {
            throw new DadosInvalidosException("Nome obrigatório");
        }
        p.setId(proximoId++);
        pacientes.add(p);
        return p;
    }
    
    public Paciente buscarPorId(Long id) throws PacienteNaoEncontradoException {
        return pacientes.stream()
            .filter(p -> p.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente não encontrado"));
    }
    
    public List<Paciente> listarAtivos() {
        return pacientes.stream().filter(Paciente::isAtivo).toList();
    }
    
    public void remover(Long id) throws PacienteNaoEncontradoException {
        Paciente p = buscarPorId(id);
        p.setAtivo(false);
    }
}
