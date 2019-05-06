package com.zyd.petfamily.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
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

    private String pic;

    public PicUtil() {
    }

    @PostConstruct
    public void init() {
        picUtil = this;
        picUtil.environment = this.environment;
    }

    public PicUtil(String pic) {
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
    public String storePic(String kind, int userId, int id) throws IOException {
        log.info("存储图片到服务器中");

        //创建新目录
        String idPre = userId + "/" + kind + "/";
        File dir = new File(PIC_URL + idPre);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filePathSuffix = id + ".jpg";

        //创建服务器目录下的新文件
        String newFilePath = dir.getPath() + "/" + filePathSuffix;
        File picFile = new File(newFilePath);
        if (!picFile.exists()) {
            picFile.createNewFile();
        }

        try {
            // 对base64数据进行解码 生成 字节数组
            byte[] picImg = new BASE64Decoder().decodeBuffer(pic);
            for (int i = 0; i < picImg.length; ++i) {
                if (picImg[i] < 0) {
                    // 调整异常数据
                    picImg[i] += 256;
                }
            }
            //将图片数据写入文件
            FileOutputStream out = new FileOutputStream(picFile);
            out.write(picImg);
            out.flush();
            out.close();
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
