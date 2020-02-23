package com.toulan.controller;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author LOL_toulan
 * @Time 2020/2/23 10:38
 * @Message
 */
@Controller
@RequestMapping("/fileupload")
public class FileUploadController {

    @RequestMapping("/upload")
    public String fileupload(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //先创建文件夹，
        String realPath = request.getSession().getServletContext().getRealPath("/uploads");
        System.out.println("realPath" + realPath);

        File file = new File(realPath);

        if (!file.exists()) {
            file.mkdirs();
        }

        //解析request
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> fileItems = upload.parseRequest(request);

        for (FileItem fileItem : fileItems) {
            if (!fileItem.isFormField()) {
                String fileName = fileItem.getName();
                System.out.println("fileName:" + fileName);
                //防止重名，导致文件被覆盖
                String uuid = UUID.randomUUID().toString();
                fileName = uuid + "_" + fileName;
                fileItem.write(new File(realPath, fileName));
            }
        }


        return "success";

        /*//因为我们是post请求，所以我们上传的文件肯定在request中，所以我们要获取请求中的文件

        //将上传的文件传到
        String realPath = request.getSession().getServletContext().getRealPath("/uploads");
        File file = new File(realPath);
        //如果文件不存在，那么创建对应的文件
        if (!file.exists()) {
            file.mkdirs();
        }

        //解析request，获取上传文件项
        DiskFileItemFactory factory = new DiskFileItemFactory();

        ServletFileUpload upload = new ServletFileUpload(factory);

        //解析request
        List<FileItem> fileItems = upload.parseRequest(request);

        for (FileItem item : fileItems) {
            if (!item.isFormField()) {
                //获取文件名称
                String filename = item.getName();
                //给文件名生成一个唯一的UUID，防止文件名相同被覆盖
                String replace = UUID.randomUUID().toString().replace("-", "");
                //拼接文件名
                filename = replace + "_" + filename;
                //上传文件
                item.write(new File(realPath, filename));
                //删除临时文件
                item.delete();
            }
        }

        System.out.println("文件上传成功... ");
        return "success";*/
    }


    @RequestMapping("/mvcupload")
    public String fileupload2(HttpServletRequest request, HttpServletResponse response, MultipartFile upload) throws Exception {
        //将上传的文件传到
        String realPath = request.getSession().getServletContext().getRealPath("/uploads");
        File file = new File(realPath);
        //如果文件不存在，那么创建对应的文件
        if (!file.exists()) {
            file.mkdirs();
        }
        String filename = upload.getOriginalFilename();
        //给文件名生成一个唯一的UUID，防止文件名相同被覆盖
        String replace = UUID.randomUUID().toString().replace("-", "");
        //拼接文件名
        filename = replace + "_" + filename;

        upload.transferTo(new File(realPath, filename));

        return "success";
    }

    @RequestMapping("/springmvcupload")
    public String fileupload3( MultipartFile upload) throws Exception {
        System.out.println("跨服务器文件上传。。。");
        //将上传的文件传到
        String path = "http://localhost:8090/fileuploadserver_war/uploads/";

        //获取文件名
        String fileName = upload.getOriginalFilename();
        //给文件名生成一个唯一的UUID，防止文件名相同被覆盖
        String uuid = UUID.randomUUID().toString().replace("-", "");
        //拼接文件名
        int suffixIndex = fileName.lastIndexOf(".");
        // 如果有合法的后缀名，获取到后缀名
        if(suffixIndex > 0){
            fileName = fileName.substring(suffixIndex);
        }else{
            fileName = "";
        }
        fileName = uuid  + fileName;

        //完成问价跨服务器上传
        Client client = Client.create();
        //将路径传给系统
        WebResource webResource = client.resource(path + fileName);
        webResource.put(String.class,upload.getBytes());

        return "success";
    }
}
//http://localhost:8090/fileuploadserver_war/uploads/ae6022de70ad45c2896f4a1d6fe58cdc_美女 - 14.jpg