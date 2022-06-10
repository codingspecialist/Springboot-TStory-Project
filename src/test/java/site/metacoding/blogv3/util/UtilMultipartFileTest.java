package site.metacoding.blogv3.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UtilMultipartFileTest {

    @Test
    public void mockMutltipartfile_테스트() throws IOException {
        File file = new File(
                "C:\\green_workspace\\tstory_lab\\Springboot-Blog-V3\\src\\main\\resources\\static\\images\\dog.jpg");

        MockMultipartFile image = new MockMultipartFile("profileImgFile", "dog.jpg", "image/jpeg",
                Files.readAllBytes(file.toPath()));

        System.out.println("=======================");
        System.out.println(image.getOriginalFilename());
        System.out.println("=======================");
    }

    @Test
    public void multipartfile_테스트() {
        try {
            File file = new File(
                    "C:\\green_workspace\\tstory_lab\\Springboot-Blog-V3\\src\\main\\resources\\static\\images\\dog.jpg");
            FileItem fileItem = new DiskFileItem("mainFile", Files.probeContentType(file.toPath()), false,
                    file.getName(),
                    (int) file.length(), file.getParentFile());
            InputStream input = new FileInputStream(file);
            OutputStream os = fileItem.getOutputStream();
            IOUtils.copy(input, os);
            MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
            System.out.println("========================");
            System.out.println(multipartFile.getOriginalFilename());
            System.out.println("========================");

            assertEquals("dog.jpg", multipartFile.getOriginalFilename());
        } catch (Exception e) {
            System.out.println("========================");
            System.out.println(e.getMessage());
            System.out.println("========================");
        }

    }
}
