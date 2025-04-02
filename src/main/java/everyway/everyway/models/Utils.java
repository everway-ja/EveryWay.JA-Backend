package everyway.everyway.models;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Component

public class Utils {
    
    @Value("${file.upload.path}")  private String uploadPath;
    @Value("${file.personal.email}")  private String personalEmail;

    @Autowired
    private JavaMailSender javaMailSender;

    public String encryptString ( String password ) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error encrypting password", e);
        }
    }

    public void save_file ( MultipartFile file ) throws IOException {

        byte[] fileBytes = file.getBytes();
        String fileHash = generate_fileHash(fileBytes);
    
        Path path = get_filePath(fileHash);
        create_directoriesIfNotExist(path);
    
        if (!Files.exists(path)) {
            Files.write(path, fileBytes);
        }

    }

    public void create_directoriesIfNotExist ( Path path ) throws IOException {
        Path directory = path.getParent();
        if (directory != null && !Files.exists(directory)) {
            Files.createDirectories(directory);
        }
    }

    public byte[] read_file ( String filename ) throws IOException {
        
        Path path = get_filePath(filename);

        return Files.readAllBytes(path);
    
    }

    public byte[] combine_files ( String[] filenames ) throws IOException {

        int totalLength = 0;
        byte[][] fileContents = new byte[filenames.length][];

        for (int i = 0; i < filenames.length; i++) {
            fileContents[i] = read_file(filenames[i]);
            totalLength += fileContents[i].length;
        }

        byte[] combined = new byte[totalLength];
        int currentPosition = 0;

        for (byte[] fileContent : fileContents) {
            System.arraycopy(fileContent, 0, combined, currentPosition, fileContent.length);
            currentPosition += fileContent.length;
        }

        return combined;

    }

    public Path get_filePath ( String filename ) {
        String sanitizedFilename = FilenameUtils.getName(filename);
        return Paths.get(uploadPath, sanitizedFilename);
    }

    public String generate_fileHash ( byte[] fileBytes ) {
    
        try {

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(fileBytes);
            return new BigInteger(1, hashBytes).toString(16); // Convert to hex
    
        } 
        
        catch ( Exception e ) {
            throw new RuntimeException();
        }
    
    }

    public void sendEmail(String nome, String email, String messaggio) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(personalEmail);
        mailMessage.setSubject("Nuovo contatto da "+nome);
        mailMessage.setText("Nome: " + nome + "\nEmail: " + email + "\nMessaggio: " + messaggio);

        javaMailSender.send(mailMessage);
    }
}
