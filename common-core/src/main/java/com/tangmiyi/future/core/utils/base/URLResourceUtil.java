package com.tangmiyi.future.core.utils.base;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class URLResourceUtil {

    public static void download(HttpServletResponse response, File file)
            throws IOException{
        response.reset();
        response.setContentType("application/x-download");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String(file.getName().getBytes(), "UTF-8"));
        response.addHeader("Content-length", String.valueOf(file.length()));
        response.setContentType("application/octet-stream");
        try (InputStream fis = new BufferedInputStream(new FileInputStream(file))) {
            byte[] buffer = new byte[1024 * 1024 * 10];
            int i = -1;
            try ( OutputStream out = new BufferedOutputStream(response.getOutputStream())) {
                while ((i = fis.read(buffer)) != -1) {
                    out.write(buffer, 0, i);
                }
                out.flush();
            }
        }
    }
}
