package br.unitins.guitarra.service.storage;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import br.unitins.guitarra.model.perfil.Usuario;
import br.unitins.guitarra.repository.perfil.UsuarioRepository;
import br.unitins.guitarra.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UsuarioStorageService {
    
    @Inject
    UsuarioRepository usuarioRepository;   

    @Inject
    StorageService storageService;

    @ConfigProperty(name = "aws.s3.public-download-url")
    String publicDownloadUrlBase;   

    public String uploadImagem(Long usuarioId, InputStream arquivo) throws IOException {
        validarUsuarioById(usuarioId);

        String nomeArquivo = usuarioId + "-imagem.png";
        return storageService.upload("usuarios", nomeArquivo, arquivo);
    }

    
    public InputStream getPrivateImage(Long usuarioId) {
        validarUsuarioById(usuarioId);

        String key = "usuarios/" + usuarioId + "-imagem.png";
        InputStream img = storageService.downloadAsStream(key);
        return img;
    }

    //-------------------------------------------validadores

    public void validarUsuarioById(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId); 
        if(usuario == null)
            throw ValidationException.of("id","O usuario com o id " + usuarioId + " não foi encontradoo.");
    }  
}
