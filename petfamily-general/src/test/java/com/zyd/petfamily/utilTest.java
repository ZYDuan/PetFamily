package com.zyd.petfamily;

import com.zyd.petfamily.utils.MailUtil;
import org.junit.Test;

/**
 * @program: petfamily
 * @author: zyd
 * @description:
 * @create: 2019-04-26 16:47
 */

public class utilTest {

    @Test
    public void mailTest(){
        try {
            MailUtil.sendActiveMail("852887995@qq.com", "zyd", 1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
