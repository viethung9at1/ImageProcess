package com.example.imageprocess.imageprocess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@RestController
public class ImageProcess {
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> upload(@RequestParam("file") MultipartFile file) throws IOException {
        File convertFile = new File("/var/tmp/", file.getOriginalFilename());
        convertFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(convertFile);
        fout.write(file.getBytes());
        fout.close();
        ProcessImage(convertFile);
        return new ResponseEntity<>(convertFile.getAbsolutePath(), HttpStatus.ACCEPTED);
    }
    public void ProcessImage(File f) throws IOException{
        ProcessAlgo.Watermark(f);
    }
    @RequestMapping(value = "/")
    public ResponseEntity<Object> hello() {
        return new ResponseEntity<>("Hello", HttpStatus.ACCEPTED);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public ResponseEntity<Object> download(@RequestParam("filePath") String filePath) throws IOException {
        File file = new File(filePath);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers).contentLength(file.length())
                .contentType(
                        MediaType.parseMediaType("image/jpg"))
                .body(resource);
        file.delete();
        return responseEntity;
    }
}
