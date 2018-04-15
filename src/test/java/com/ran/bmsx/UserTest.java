package com.ran.bmsx;

import com.wangfan.endecrypt.utils.EndecryptUtils;
import org.junit.Test;

public class UserTest {

    @Test
    public void genAdminPsw(){
        String userId = "Erk7B4aH";
        String psw = "123456";
        String decryptMd5 = EndecryptUtils.encrytMd5(psw, userId, 3);
        System.out.println(decryptMd5);
    }

}
