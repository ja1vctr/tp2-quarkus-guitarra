package br.unitins.guitarra.service.storage;

import java.io.ByteArrayInputStream;
// import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.apache.tika.Tika;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@ApplicationScoped
public class StorageService {

    private final S3Client s3Client;
    private final String bucketName;

    @Inject
    public StorageService(
            @ConfigProperty(name = "aws.s3.bucket") String bucketName,
            @ConfigProperty(name = "B2_ACCESS_KEY") String accessKey,
            @ConfigProperty(name = "B2_SECRET_KEY") String secretKey
    ) {
        this.bucketName = bucketName;

        // ⚙️ Configuração correta para o endpoint S3 do Backblaze
        this.s3Client = S3Client.builder()
                .endpointOverride(URI.create("https://s3.us-east-005.backblazeb2.com"))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)))
                .region(Region.US_EAST_2)
                .forcePathStyle(true)
                .build();
    }

 
    public String upload(String folder, String fileName, InputStream file) throws IOException {
        String key = folder + "/" + fileName;

        // 1) Ler bytes uma única vez
        byte[] bytes = file.readAllBytes();
        System.out.println("DEBUG: bytes lidos = " + bytes.length);

        // 2) Detectar MIME com Tika (com ByteArrayInputStream e hint do nome)
        String detectedContentType = null;

        try {
                Tika tika = new Tika();
                detectedContentType = tika.detect(new ByteArrayInputStream(bytes), fileName);
                System.out.println("DEBUG: Tika detectou = " + detectedContentType);
        } catch (Exception e) {
                // captura problemas como "No Archiver found for the stream signature"
                System.err.println("WARN: Tika falhou na detecção: " + e.getMessage());
        }

        // 3) Fallbacks caso Tika não retorne resultado válido
        if (detectedContentType == null || detectedContentType.isBlank()) {
                try {
                // tenta detectar a partir do stream (menos preciso)
                detectedContentType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(bytes));
                System.out.println("DEBUG: guessContentTypeFromStream = " + detectedContentType);
                } catch (Exception e) {
                System.err.println("WARN: guessContentTypeFromStream falhou: " + e.getMessage());
                }
        }

        if (detectedContentType == null || detectedContentType.isBlank()) {
                // tenta pela extensão do nome do arquivo
                detectedContentType = URLConnection.guessContentTypeFromName(fileName);
                System.out.println("DEBUG: guessContentTypeFromName = " + detectedContentType);
        }

        if (detectedContentType == null || detectedContentType.isBlank()) {
                // fallback final
                detectedContentType = "application/octet-stream";
                System.out.println("DEBUG: fallback content-type = " + detectedContentType);
        }

        // 4) PutObject com contentType determinado
        s3Client.putObject(
                PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(detectedContentType)
                .build(),
                RequestBody.fromBytes(bytes)
        );

        return key;
    }

    /**
     * Faz o download de um arquivo privado usando o SDK da AWS (via S3Client).
     * O arquivo é retornado como InputStream.
     */
    public InputStream downloadAsStream(String key) {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        ResponseInputStream<GetObjectResponse> response = s3Client.getObject(request);
        return response; // pode ser retornado diretamente (stream)
    }

    /**
     * Faz o download e salva o arquivo localmente (exemplo de uso).
     */
    public void downloadToFile(String key, Path outputPath) throws IOException {
        try (ResponseInputStream<GetObjectResponse> response = s3Client.getObject(
                GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build())) {

            Files.copy(response, outputPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    /**
     * Deleta um arquivo do bucket.
     */
    public void delete(String key) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        s3Client.deleteObject(deleteObjectRequest);
    }

    /**
     * Teste local
     */
    public static void main(String[] args) throws IOException {
        StorageService s = new StorageService(
                "tpii-quarkus-bucket",
                "00571793480a81d0000000001",
                "K005AQhT39bAtazSYyY725S9/loyQoo"
        );

        String key = "produtos/3-imagem.png";
        Path destino = Path.of("imagem-baixada.png");

        s.downloadToFile(key, destino);
        System.out.println("✅ Arquivo baixado em: " + destino.toAbsolutePath());
    }
}
