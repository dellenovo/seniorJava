package com.lifei.lifeicat;

import com.lifei.servlet.LifeiRequest;
import com.lifei.servlet.LifeiResponse;
import com.lifei.servlet.LifeiServlet;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class DefaultLifeiServlet extends LifeiServlet {
    @Override
    public void doGet(LifeiRequest request, LifeiResponse response) throws Exception {
        String uri = request.getUri();

        URL resource = this.getClass().getResource(uri);
        if (resource == null) {
            response.write("404- no this servlet: " + (uri.contains("?") ? uri.substring(0, uri.lastIndexOf("?")) : uri));
        } else {
            File staticFile = new File(resource.getFile());
            if (staticFile.isDirectory()) {
                response.write("Directory query is not supported.");
            }

            FileInputStream fis = new FileInputStream(staticFile);
            FileChannel fc = fis.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate((int)staticFile.length());
            fc.read(buffer);

            if (response instanceof HttpLifeiResponse) {
                ((HttpLifeiResponse)response).setContentType(getContentTypeFromUri(uri));
            }

            response.write(new String(buffer.array()));
            fc.close();
        }

    }

    private String getContentTypeFromUri(String uri) {
        String contentType = "text/json";
        String suffix = uri.substring(uri.lastIndexOf('/') + 1);
        if (suffix.contains("#")) suffix = suffix.substring(0, suffix.indexOf('#'));
        suffix = suffix.substring(suffix.lastIndexOf(".") + 1);

        switch (suffix) {
            case "html":
                contentType = "text/html";
                break;
            case "js":
                contentType = "application/javascript";
                break;
            case "css":
                contentType = "text/css";
                break;
            case "png":
            case "jpg":
            case "jpeg":
                contentType = "image/" + suffix;
                break;
            default:break;
        }

        return contentType;
    }

    @Override
    public void doPost(LifeiRequest request, LifeiResponse response) throws Exception {
        doGet(request, response);
    }

    public static void main(String[] args) {
        String a = "html";
        System.out.println(a.substring(0, -1));
    }
}
