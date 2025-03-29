package everyway.everyway.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// TODO : THE UPLOAD AND DOWNLOAD METHODS ARE NOT SECURE, ADD SECURITY CHECKS, VALIDATION, EXCEPTION HANDLING AND DISTINCTIONS FOR DIFFERENT TYPES OF IMAGES UPLOADS AND DOWNLAODS (profile pictures, itinerary pictures, etc.)

@Controller
public class ImageController {

    @Value("${file.upload.path}") 
    private String uploadPath;

    @PostMapping("/upload")
    public ResponseEntity<String> handle_fileUpload(@RequestParam("file") MultipartFile file) {
        
        if (file.isEmpty()) {
            return new ResponseEntity<>("Missing File.", HttpStatus.BAD_REQUEST);
        }

        try {
        
            save_file(file);
            // TODO : generate image description

            return new ResponseEntity<>("Success.", HttpStatus.OK);
        
        } 
        
        catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/uploadMultiple")
    public ResponseEntity<String> handle_multipleFileUpload ( @RequestParam("files") MultipartFile[] files ) {
        
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                return new ResponseEntity<>("Missing files.", HttpStatus.BAD_REQUEST);
            }
        }

        try {
    
            for (MultipartFile file : files) {
    
                save_file(file);
                // TODO : generate images' description
    
            }
    
            return new ResponseEntity<>("Success.", HttpStatus.OK);
    
        } 
        
        catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> download_file(@RequestParam("filename") String filename) {
    
        try {
    
            byte[] file = read_file(filename);
    
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + filename)
                    .body(file);
    
        } 
        
        catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    
    }

    @GetMapping("/downloadMultiple")
    public ResponseEntity<byte[]> download_multipleFiles ( @RequestParam("filenames") String[] filenames ) {
        
        try {
        
            byte[] combinedFiles = combine_files(filenames);
        
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=files.zip")
                    .body(combinedFiles);
        
        } 
        
        catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    
    }


    
    private void save_file ( MultipartFile file ) throws IOException {
        
        Path path = get_filePath(file.getOriginalFilename());
        
        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());
    
    }

    private byte[] read_file ( String filename ) throws IOException {
        
        Path path = get_filePath(filename);

        return Files.readAllBytes(path);
    
    }

    private byte[] combine_files ( String[] filenames ) throws IOException {

        byte[] combined = read_file(filenames[0]);

        for (int i = 1; i < filenames.length; i++) {

            byte[] temp = read_file(filenames[i]);
            byte[] result = new byte[combined.length + temp.length];
            System.arraycopy(combined, 0, result, 0, combined.length);
            System.arraycopy(temp, 0, result, combined.length, temp.length);
            combined = result;

        }

        return combined;

    }

    private Path get_filePath ( String filename ) {
        return Paths.get(uploadPath, filename);
    }

}