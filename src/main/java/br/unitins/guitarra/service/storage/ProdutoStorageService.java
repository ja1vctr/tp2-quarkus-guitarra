package br.unitins.guitarra.service.storage;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import br.unitins.guitarra.model.produto.Guitarra;
import br.unitins.guitarra.repository.produto.GuitarraRepository;
import br.unitins.guitarra.validation.ValidationException;

import java.io.IOException;
import java.io.InputStream;

@ApplicationScoped
public class ProdutoStorageService {

    @Inject
    GuitarraRepository guitarraRepository;   

    @Inject
    StorageService storageService;

    @ConfigProperty(name = "aws.s3.public-download-url")
    String publicDownloadUrlBase;   

    public String uploadImagem(Long produtoId, InputStream arquivo) throws IOException {
        validarGuitarraById(produtoId);

        String nomeArquivo = produtoId + "-imagem.png";
        return storageService.upload("produtos", nomeArquivo, arquivo);
    }

    
    public InputStream getPrivateImage(Long produtoId) {
        validarGuitarraById(produtoId);

        String key = "produtos/" + produtoId + "-imagem.png";
        InputStream img = storageService.downloadAsStream(key);
        return img;
    }

    // validadores

    public void validarGuitarraById(Long produtoId) {
        Guitarra guitarra = guitarraRepository.findById(produtoId); 
        if(guitarra == null)
            throw ValidationException.of("id","A guitarra com o id " + produtoId + " não foi encontrada.");
    }   
}
