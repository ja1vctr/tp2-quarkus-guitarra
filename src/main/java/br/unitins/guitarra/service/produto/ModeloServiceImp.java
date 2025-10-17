package br.unitins.guitarra.service.produto;

import java.util.List;
import java.util.Set;

import br.unitins.guitarra.dto.produto.request.ModeloRequest;
import br.unitins.guitarra.dto.produto.response.ModeloResponse;
import br.unitins.guitarra.model.produto.Modelo;
import br.unitins.guitarra.repository.produto.GuitarraRepository;
import br.unitins.guitarra.repository.produto.ModeloRepository;
import br.unitins.guitarra.validation.ValidationException;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

@ApplicationScoped
public class ModeloServiceImp implements ModeloService {

  @Inject
  ModeloRepository repository;

  @Inject
  GuitarraRepository guitarraRepository;

  @Inject
  Validator validator;

  @Override
  @Transactional
  public ModeloResponse create(ModeloRequest request) {
      Set<ConstraintViolation<ModeloRequest>> violations = validator.validate(request);
      if (!violations.isEmpty()) {
          throw new ConstraintViolationException(violations);
      }
      Modelo newModelo = new Modelo();
      newModelo.setNome(request.nome());

      repository.persist(newModelo);

      return ModeloResponse.valueOf(newModelo);
  }

  @Override
  @Transactional
  public void update(Long id, ModeloRequest request) {
      Set<ConstraintViolation<ModeloRequest>> violations = validator.validate(request);
      if (!violations.isEmpty()) {
          throw new ConstraintViolationException(violations);
      }

      Modelo modelo = repository.findById(id);
      if (modelo == null) {
          throw new ValidationException("id", "O modelo com o id " + id + " não foi encontrada.");
      }

      modelo.setNome(request.nome());
  }

  @Override
  @Transactional
  public void delete(Long id) {
      Modelo modelo = repository.findById(id);
      if (modelo == null) {
          throw new ValidationException("id", "A modelo com o id " + id + " não foi encontrada.");
      }

      if (guitarraRepository.count("modelo", modelo) > 0) {
          throw new ValidationException("modelo", "Esta modelo está em uso e não pode ser excluída.");
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
      if (modelo == null) throw new ValidationException("id", "A modelo com o id " + id + " não foi encontrada.");
      return ModeloResponse.valueOf(modelo);
  }

  @Override
  public List<ModeloResponse> findByNome(String nome) {
      return repository.findByNome(nome).stream().map(ModeloResponse::valueOf).toList();
  }

  @Override
  public long count() {
      return repository.count();
  }

  @Override
  public long countByNome(String nome) {
      return repository.findByNome(nome).count();
  }
}