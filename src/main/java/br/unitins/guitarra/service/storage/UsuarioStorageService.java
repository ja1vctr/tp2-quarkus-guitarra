package br.unitins.guitarra.service.storage;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.IOException;
import java.io.InputStream;

@ApplicationScoped
public class UsuarioStorageService {

    @Inject
    StorageService storageService;

    public String uploadFoto(Long usuarioId, InputStream arquivo) throws IOException {
        String nomeArquivo = usuarioId + "-foto.png";
        return storageService.upload("usuarios", nomeArquivo, arquivo);
    }
    
    // public String getPublicImageUrl(Long usuarioId) {
    //     String key = "usuarios/" + usuarioId + "-imagem.png";
    //     // Chama o método do StorageService e converte para String
    //     return storageService.generateFileUrl(key).toString();
    // }
}
