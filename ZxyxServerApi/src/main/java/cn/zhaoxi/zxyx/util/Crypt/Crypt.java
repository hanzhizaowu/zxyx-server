package cn.zhaoxi.zxyx.util.Crypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.GeneralSecurityException;

public class Crypt {
    private static final String password = "hzpawd";
    private static Logger logger = LoggerFactory.getLogger(Crypt.class.getName());

    public static String Encrypt(String msg) {
        String encryptedMsg = null;
        try {
            encryptedMsg = AESCrypt.encrypt(password, msg);
        }catch (GeneralSecurityException e){
            e.printStackTrace();
        }

        return encryptedMsg;
    }

    public static String Decrypt(String msg) {
        String messageAfterDecrypt = null;
        try {
            logger.info("password is:" + password);
            messageAfterDecrypt = AESCrypt.decrypt(password, msg);
        }catch (GeneralSecurityException e){
            //handle error
            e.printStackTrace();
        }

        return messageAfterDecrypt;
    }
}
