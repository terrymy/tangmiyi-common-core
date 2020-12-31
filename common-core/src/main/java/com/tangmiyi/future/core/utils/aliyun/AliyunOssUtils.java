package com.tangmiyi.future.core.utils.aliyun;

import brave.Tracer;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.tangmiyi.future.core.properties.AliyunOssProperties;
import com.tangmiyi.future.core.thread.AliyunOssUploadThread;
import com.tangmiyi.future.core.utils.base.DateUtils;
import com.tangmiyi.future.core.utils.base.ThreadPoolUtils;
import com.tangmiyi.future.core.utils.base.URLResourceUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;

@Component
@Slf4j
public class AliyunOssUtils {

    @Autowired
    private AliyunOssProperties aliyunOssProperties;

    @Autowired
    private Tracer tracer;

    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * 普通文件上传文件
     */
    public String upload(MultipartFile file) {
        try {
            if(file == null){
                return null;
            }
            String fileName[] = file.getOriginalFilename().split("\\.");
            String suffix = fileName.length > 1 ? fileName[fileName.length - 1] : "";
            String key = applicationName + "/" + DateUtils.formatDate(new Date(), DateUtils.FORMAT_YYYYMMDD) + "/"
                    + UUID.randomUUID().toString().replaceAll("-", "") + "." + suffix;
            return upload(file.getInputStream(),key);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * App安装包上传
     * @param file
     * @param appType 1苹果，0安卓
     * @return
     */
    public String uploadApp(MultipartFile file,Integer appType) {
        try {
            if(file == null){
                return null;
            }
            String fileName = file.getOriginalFilename();
            String key = applicationName + "/" + (appType == 1 ? "apple" : "android") + "/"
                    + fileName;
            return upload(file.getInputStream(),key);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 上传文件
     * @param inputStream   文件流
     * @param key   bucket文件的唯一key标识
     * @return 访问路径 ，结果为null时说明上传失败
     */
    public String upload(InputStream inputStream,String key) {
        if (inputStream == null || StringUtils.isBlank(key)) {
            return null;
        }
        OSSClient ossClient = initOssClient();
        ThreadPoolExecutor threadPoolExecutor = ThreadPoolUtils.getInstance().getThreadPoolExecutor();
        // 异步执行上传
        threadPoolExecutor.submit(new AliyunOssUploadThread(ossClient,inputStream,key,aliyunOssProperties,tracer));
        // 拼接文件访问路径
        StringBuffer sb = new StringBuffer();
        sb.append("http://").append(aliyunOssProperties.getOssCdn()).append("/").append(key);
        return sb.toString();
    }

    /**
     * 删除单个文件
     *
     * @param key 文件名
     */
    public Boolean delete(String key) {
        OSSClient ossClient = initOssClient();
        try{
            ossClient.deleteObject(aliyunOssProperties.getBucketName(), key);
        }catch (Exception e){
            log.error("Aliyun.oss.delete.error:",e);
            tracer.currentSpan().tag("Aliyun.oss.delete.error", e.getMessage());
            return false;
        }finally {
            ossClient.shutdown();
        }
        return true;
    }

    /**
     * 下载附件
     * @param response
     * @param key
     */
    public void downLoadFile(HttpServletResponse response, String key){
        OSSClient ossClient = initOssClient();
        try{
            //获取fileid对应的阿里云上的文件对象
            OSSObject ossObject = ossClient.getObject(aliyunOssProperties.getBucketName(), key);
            String fileName = key;
            if(key.contains("/")){
                String[] fileNameArr = key.split("/");
                fileName = fileNameArr[fileNameArr.length - 1];
            }
            File targetFile = new File(fileName);
            FileUtils.copyInputStreamToFile(ossObject.getObjectContent(), targetFile);
            URLResourceUtil.download(response,targetFile);
        } catch (Exception e) {
            log.error("Aliyun.oss.download.error:",e);
            tracer.currentSpan().tag("Aliyun.oss.download.error", e.getMessage());
        } finally {
            ossClient.shutdown();
        }
    }

    private OSSClient initOssClient(){
        OSSClient ossClient = new OSSClient(aliyunOssProperties.getEndpoint(),
                aliyunOssProperties.getKeyId(), aliyunOssProperties.getKeySecret());
        return ossClient;
    }

    @Bean
    public AliyunOssProperties aliyunOssProperties() {
        return new AliyunOssProperties();
    }
}
