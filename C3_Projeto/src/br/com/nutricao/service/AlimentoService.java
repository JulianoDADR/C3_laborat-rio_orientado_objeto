package br.com.nutricao.service;
import br.com.nutricao.model.Alimento;
import java.util.*;

public class AlimentoService {
    private List<Alimento> alimentos = new ArrayList<>();
    private Long proximoId = 1L;
    
    public AlimentoService() {
        carregarAlimentosBase();
    }
    
    private void carregarAlimentosBase() {
        cadastrar(new Alimento("Arroz branco", 130, "1 colher de servir"));
        cadastrar(new Alimento("Feijão preto", 77, "1 concha"));
        cadastrar(new Alimento("Frango grelhado", 165, "100g"));
        cadastrar(new Alimento("Ovo cozido", 155, "1 unidade"));
    }
    
    public Alimento cadastrar(Alimento alimento) {
        alimento.setId(proximoId++);
        alimentos.add(alimento);
        return alimento;
    }
    
    public List<Alimento> listar() {
        return new ArrayList<>(alimentos);
    }
    
    public List<Alimento> buscarPorNome(String nome) {
        return alimentos.stream()
            .filter(a -> a.getNome().toLowerCase().contains(nome.toLowerCase()))
            .toList();
    }
}