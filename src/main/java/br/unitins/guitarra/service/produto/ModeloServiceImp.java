package br.unitins.guitarra.service.produto;

import java.util.List;

import br.unitins.guitarra.dto.produto.request.ModeloRequest;
import br.unitins.guitarra.dto.produto.response.MarcaResponse;
import br.unitins.guitarra.dto.produto.response.ModeloResponse;
import br.unitins.guitarra.model.produto.Marca;
import br.unitins.guitarra.model.produto.Modelo;
import br.unitins.guitarra.repository.produto.GuitarraRepository;
import br.unitins.guitarra.repository.produto.MarcaRepository;
import br.unitins.guitarra.repository.produto.ModeloRepository;
import br.unitins.guitarra.validation.ValidationException;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;

@ApplicationScoped
public class ModeloServiceImp implements ModeloService {

  @Inject
  ModeloRepository repository;

  @Inject
  GuitarraRepository guitarraRepository;

  @Inject
  MarcaRepository marcaRepository;

  @Inject
  Validator validator;

  @Override
  @Transactional
  public ModeloResponse create(ModeloRequest request) {
    Modelo newModelo = new Modelo();
    newModelo.setNome(request.nome());

    List<Marca> marcas = loadMarcas(request.idMarcas());
    newModelo.setListaMarcas(marcas);
                   
    repository.persist(newModelo);

    return ModeloResponse.valueOf(newModelo);
  }

  @Override
  @Transactional
  public void update(Long id, ModeloRequest request) {
      Modelo modelo = repository.findById(id);
      if (modelo == null) {
          throw ValidationException.of("id", "O modelo com o id " + id + " não foi encontrada.");
      }

    List<Marca> marcas = loadMarcas(request.idMarcas());
    modelo.setListaMarcas(marcas);                                 
  }

  @Override
  @Transactional
  public void delete(Long id) {
      Modelo modelo = repository.findById(id);
      if (modelo == null) {
          throw ValidationException.of("id", "A modelo com o id " + id + " não foi encontrada.");
      }

      if (guitarraRepository.count("modelo", modelo) > 0) {
          throw ValidationException.of("modelo", "Esta modelo está em uso e não pode ser excluída.");
      }

      repository.delete(modelo);
  }

  @Override
  public List<ModeloResponse> findAll(Integer page, Integer pageSize) {
      PanacheQuery<Modelo> query = null;
      if (page == null || pageSize == null)
          query = repository.findAll();
      else
          query = repository.findAll().page(page, pageSize);

      return query.list().stream().
                    map(ModeloResponse::valueOf).
                    toList();
  }

  @Override
  public ModeloResponse findById(Long id) {
      Modelo modelo = repository.findById(id);
      if (modelo == null) throw ValidationException.of("id", "A modelo com o id " + id + " não foi encontrada.");
      return ModeloResponse.valueOf(modelo);
  }

  @Override
  public List<ModeloResponse> findByNome(String nome) {
      return repository.findByNome(nome).stream().map(ModeloResponse::valueOf).toList();
  }

  @Override
  public List<MarcaResponse> findMarcasByModelo(Long idModelo) {
    Modelo modelo = repository.findById(idModelo);

    if(modelo == null) {
        throw ValidationException.of("idModelo", "O modelo com o id " + idModelo + " não foi encontrado.");
    }
    return modelo.getListaMarcas()
            .stream()
            .map(MarcaResponse::valueOf)
            .toList();
  }

  @Override
  public long count() {
      return repository.count();
  }

  @Override
  public long countByNome(String nome) {
      return repository.findByNome(nome).count();
  }

  private List<Marca> loadMarcas(List<Long> ids) {
    if (ids == null || ids.isEmpty()) {
        return List.of();
    }

    return ids.stream()
        .map(id -> {
            Marca marca = marcaRepository.findById(id);
            if (marca == null) {
                throw ValidationException.of(
                    "idMarcas",
                    "A marca com id " + id + " não existe."
                );
            }
            return marca;
        })
        .toList();
}

}