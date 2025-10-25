package br.unitins.guitarra.service.produto;

import java.util.List;
import java.util.Set;

import br.unitins.guitarra.dto.produto.request.GuitarraRequest;
import br.unitins.guitarra.dto.produto.response.GuitarraResponse;
import br.unitins.guitarra.model.produto.Braco;
import br.unitins.guitarra.model.produto.Captador;
import br.unitins.guitarra.model.produto.Cor;
import br.unitins.guitarra.model.produto.Guitarra;
import br.unitins.guitarra.model.produto.Marca;
import br.unitins.guitarra.model.produto.Modelo;
import br.unitins.guitarra.model.produto.Ponte;
import br.unitins.guitarra.model.produto.Tarracha;
import br.unitins.guitarra.repository.produto.BracoRepository;
import br.unitins.guitarra.repository.produto.CaptadorRepository;
import br.unitins.guitarra.repository.produto.CorRepository;
import br.unitins.guitarra.repository.produto.GuitarraRepository;
import br.unitins.guitarra.repository.produto.MarcaRepository;
import br.unitins.guitarra.repository.produto.ModeloRepository;
import br.unitins.guitarra.repository.produto.PonteRepository;
import br.unitins.guitarra.repository.produto.TarrachaRepository;
import br.unitins.guitarra.validation.ValidationException;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

@ApplicationScoped
public class GuitarraServiceImp implements GuitarraService {

  @Inject
  GuitarraRepository repository;

  @Inject
  BracoRepository bracoRepository;
  
  @Inject
  CaptadorRepository captadorRepository;
  
  @Inject
  CorRepository corRepository;
  
  @Inject
  MarcaRepository marcaRepository;
  
  @Inject
  ModeloRepository modeloRepository;

  @Inject
  PonteRepository ponteRepository;

  @Inject
  TarrachaRepository tarrachaRepository;

  @Inject
  Validator validator;

  @Override
  @Transactional
  public GuitarraResponse create(GuitarraRequest request) {
    Set<ConstraintViolation<GuitarraRequest>> violations = validator.validate(request);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }

    // Busca as entidades relacionadas
    Braco braco = findBracoById(request.idBraco());
    Captador captadorBraco = findCaptadorById(request.idCaptadorBraco());
    Captador captadorMeio = findCaptadorById(request.idCaptadorMeio());
    Captador captadorPonte = findCaptadorById(request.idCaptadorPonte());
    Cor cor = findCorById(request.idCor());
    Marca marca = findMarcaById(request.idMarca());
    Modelo modelo = findModeloById(request.idModelo());
    Ponte ponte = findPonteById(request.idPonte());
    Tarracha tarracha = findTarrachaById(request.idTarracha());

    Guitarra newGuitarra = new Guitarra();
    // Atributos de Produto
    newGuitarra.setDescricao(request.descricao());
    newGuitarra.setNome(request.nome());
    newGuitarra.setPreco(request.preco());
    newGuitarra.setQuantidade(request.quantidade());
    newGuitarra.setStatus(request.status());
    // Atributos de Guitarra
    newGuitarra.setAnoFabricacao(request.anoFabricacao());
    newGuitarra.setAssinatura(request.assinatura());
    newGuitarra.setBlindagemEletronica(request.blindagemEletronica());
    newGuitarra.setMadeira(request.madeira());
    newGuitarra.setNumeroDeCordas(request.numeroDeCordas());
    newGuitarra.setPeso(request.peso());
    // Relacionamentos
    newGuitarra.setBraco(braco);
    newGuitarra.setCaptadorBraco(captadorBraco);
    newGuitarra.setCaptadorMeio(captadorMeio);
    newGuitarra.setCaptadorPonte(captadorPonte);
    newGuitarra.setCor(cor);
    newGuitarra.setMarca(marca);
    newGuitarra.setModelo(modelo);
    newGuitarra.setPonte(ponte);
    newGuitarra.setTarracha(tarracha);

    repository.persist(newGuitarra);

    return GuitarraResponse.valueOf(newGuitarra);
  }

  @Override
  @Transactional
  public void update(GuitarraRequest request, Long id) {
    Set<ConstraintViolation<GuitarraRequest>> violations = validator.validate(request);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }

    Guitarra guitarra = repository.findById(id);
    if (guitarra == null) {
      throw ValidationException.of("id", "A guitarra com o id " + id + " não foi encontrada.");
    }

    // Busca as entidades relacionadas
    Braco braco = findBracoById(request.idBraco());
    Captador captadorBraco = findCaptadorById(request.idCaptadorBraco());
    Captador captadorMeio = findCaptadorById(request.idCaptadorMeio());
    Captador captadorPonte = findCaptadorById(request.idCaptadorPonte());
    Cor cor = findCorById(request.idCor());
    Marca marca = findMarcaById(request.idMarca());
    Modelo modelo = findModeloById(request.idModelo());
    Ponte ponte = findPonteById(request.idPonte());
    Tarracha tarracha = findTarrachaById(request.idTarracha());

    // Atributos de Produto
    guitarra.setDescricao(request.descricao());
    guitarra.setNome(request.nome());
    guitarra.setPreco(request.preco());
    guitarra.setQuantidade(request.quantidade());
    guitarra.setStatus(request.status());
    // Atributos de Guitarra
    guitarra.setAnoFabricacao(request.anoFabricacao());
    guitarra.setAssinatura(request.assinatura());
    guitarra.setBlindagemEletronica(request.blindagemEletronica());
    guitarra.setMadeira(request.madeira());
    guitarra.setNumeroDeCordas(request.numeroDeCordas());
    guitarra.setPeso(request.peso());
    // Relacionamentos
    guitarra.setBraco(braco);
    guitarra.setCaptadorBraco(captadorBraco);
    guitarra.setCaptadorMeio(captadorMeio);
    guitarra.setCaptadorPonte(captadorPonte);
    guitarra.setCor(cor);
    guitarra.setMarca(marca);
    guitarra.setModelo(modelo);
    guitarra.setPonte(ponte);
    guitarra.setTarracha(tarracha);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    if(repository.findById(id) == null)
      throw ValidationException.of("id", "A guitarra com o id " + id + " não foi encontrada.");
    repository.deleteById(id);
  }
  
  @Override
  public List<GuitarraResponse> findAll( Integer page, Integer pageSize) {
    PanacheQuery<Guitarra> query = null;
        if (page == null || pageSize == null)
            query = repository.findAll();
        else
            query = repository.findAll().page(page, pageSize);

        return query.list().stream().
                      map(GuitarraResponse::valueOf).
                      toList();
    }

  @Override
  public GuitarraResponse findById(Long id) {
    if(id == null) {
      throw ValidationException.of("id", "O id para busca não pode ser nulo.");
    }
    Guitarra guitarra = repository.findById(id);
    if (guitarra == null) {
      throw ValidationException.of("id", "A guitarra com o id " + id + " não foi encontrada.");
    }
    return GuitarraResponse.valueOf(guitarra);
  }

  @Override
  public List<GuitarraResponse> findByNome(String nome) {
    if(nome == null || nome.isEmpty()) {
      throw ValidationException.of("nome", "O nome para busca não pode ser nulo ou vazio.");
    }
    return repository.findByNome(nome).
                      stream().
                      map(GuitarraResponse::valueOf).
                      toList();
  }

  @Override
  public List<GuitarraResponse> findByModelo(String modelo) {
    return repository.findByModelo(modelo).
                      stream().
                      map(GuitarraResponse::valueOf).
                      toList();
  }

  @Override
  public Long count() {
    return repository.findAll().count();
  }

  @Override
  public Long count(String nome) {
    return repository.count("nome", nome);
  }

  // Métodos auxiliares para buscar entidades relacionadas

  private Marca findMarcaById(Long id) {
    Marca marca = marcaRepository.findById(id);
    if (marca == null) {
      throw ValidationException.of("idMarca", "A marca com o id " + id + " não foi encontrada.");
    }
    return marca;
  }

  private Modelo findModeloById(Long id) {
    Modelo modelo = modeloRepository.findById(id);
    if (modelo == null) {
      throw ValidationException.of("idMarca", "A modelo com o id " + id + " não foi encontrada.");
    }
    return modelo;
  }

  private Braco findBracoById(Long id) {
    if (id == null) return null;
    Braco braco = bracoRepository.findById(id);
    if (braco == null) {
      throw ValidationException.of("idBraco", "O braço com o id " + id + " não foi encontrado.");
    }
    return braco;
  }

  private Captador findCaptadorById(Long id) {
    if (id == null) return null;
    Captador captador = captadorRepository.findById(id);
    if (captador == null) {
      throw ValidationException.of("idCaptador", "O captador com o id " + id + " não foi encontrado.");
    }
    return captador;
  }

  private Cor findCorById(Long id) {
    if (id == null) return null;
    Cor cor = corRepository.findById(id);
    if (cor == null) {
      throw ValidationException.of("idCor", "A cor com o id " + id + " não foi encontrada.");
    }
    return cor;
  }

  private Ponte findPonteById(Long id) {
    if (id == null) return null;
    Ponte ponte = ponteRepository.findById(id);
    if (ponte == null) {
      throw ValidationException.of("idPonte", "A ponte com o id " + id + " não foi encontrada.");
    }
    return ponte;
  }

  private Tarracha findTarrachaById(Long id) {
    if (id == null) return null;
    Tarracha tarracha = tarrachaRepository.findById(id);
    if (tarracha == null) {
      throw ValidationException.of("idTarracha", "A tarracha com o id " + id + " não foi encontrada.");
    }
    return tarracha;
  }

}
