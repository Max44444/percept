package bsa.java.concurrency.fs;

import lombok.SneakyThrows;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/files")
public class FSController {

    @Autowired
    FileSystemService systemService;

    @SneakyThrows
    @GetMapping("/{path}")
    public void getImage(HttpServletResponse response, @PathVariable String path) {
        var in = systemService.getImageByPathAsInputStream(path);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(in, response.getOutputStream());
    }

}
