package com.code.cetboot.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

/**
 * @author : IMG
 * @create : 2025/4/28
 */
public class FileUtil {

    /**
     * MultipartFile 转 File
     * @param file 文件
     */
    public static File multipartFileToFile(MultipartFile file, String name) throws Exception {

        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(name);
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    /**
     * 获取流文件
     * @param ins 输入流
     * @param file 输出文件
     */
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = Files.newOutputStream(file.toPath());
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
