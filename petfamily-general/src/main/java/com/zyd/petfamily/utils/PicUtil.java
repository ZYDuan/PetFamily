package com.zyd.petfamily.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

import static com.zyd.petfamily.utils.CodeUtil.PIC_URL;
import static com.zyd.petfamily.utils.CodeUtil.getLocalUrl;

/**
 * @program: petfamily
 * @author: zyd
 * @description:
 * @create: 2019-04-28 11:18
 */
@Component
public class PicUtil {
    @Autowired
    Environment environment;

    private static PicUtil picUtil;

    private MultipartFile pic;

    public PicUtil() {
    }

    @PostConstruct
    public void init() {
        picUtil = this;
        picUtil.environment = this.environment;
    }

    public PicUtil(MultipartFile pic) {
        this.pic = pic;
    }

    public String getPort() {
        return picUtil.environment.getProperty("local.server.port");
    }

    private static Logger log = LoggerFactory.getLogger(PicUtil.class);

    /**
     * 存储照片到本地服务器
     *
     * @param kind
     * @param userId
     * @param id
     * @return 图片url
     */
    public String storePic(String kind, int userId, int id) {
        log.info("存储图片到服务器中");

        //过滤合法的图片类型
        String fileName = pic.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String allowSuffixs = "gif,jpg,jpeg,bmp,png,ico";
        if (allowSuffixs.indexOf(suffix) == -1) {
            return null;
        }
        //创建新目录
        String idPre = userId + "/" + kind + "/";
        File dir = new File(PIC_URL + idPre);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String filePathSuffix = id + "." + suffix;
        //创建服务器目录下的新文件
        String newFilePath = dir.getPath() + "/" + filePathSuffix;
        File f = new File(newFilePath);

        // 转存文件
        try {
            pic.transferTo(f);
        } catch (IOException e) {
            log.error("存储图片出现错误");
            e.printStackTrace();
        }

        //可以访问的图片url
        return getLocalUrl() + ":" + getPort() + "/images/" + idPre + filePathSuffix;
    }


    /**
     * 删除图片文件
     *
     * @param url
     * @return 是否删除成功
     */
    public boolean deletePic(String url) {
        File file = new File(url);
        file.delete();
        return true;
    }
}
