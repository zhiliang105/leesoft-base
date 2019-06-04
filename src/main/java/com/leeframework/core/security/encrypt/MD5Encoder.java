package com.leeframework.core.security.encrypt;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leeframework.common.utils.properties.SysConfigProperty;

/**
 * MD5密码加密工具类<br>
 * 依赖shiro中的md5加密相关类
 * @author 李志亮 (Lee) <279683131(@qq.com),zhiliang.li@wirelessandon.cn>
 * @date Date:2016年4月25日 Time: 下午1:45:58
 * @version 1.0
 * @since version 1.0
 * @see org.apache.shiro.crypto.hash.Md5Hash
 * @update 2016-05-25 by lee 采用散列算法
 */
public abstract class MD5Encoder {
    private static Logger log = LoggerFactory.getLogger(MD5Encoder.class);

    /**
     * 默认的散列次数
     */
    public final static int DEFAULT_HASHITERATIONS = SysConfigProperty.getMD5HashIterations();

    /**
     * 默认的盐
     */
    public final static String DEFAULT_SALT = "";

    private MD5Encoder() {

    }

    /**
     * 比较原文与密文是否匹配
     * @author lee
     * @date 2016年5月25日 下午3:22:24
     * @param rawText 原文
     * @param encText 密文
     * @return
     * @see #encode(String)
     */
    public static boolean compare(String rawText, String encText) {
        return encode(rawText).equals(encText);
    }

    /**
     * 比较原文与密文是否匹配
     * @author lee
     * @date 2016年5月25日 下午3:22:24
     * @param rawText 原文
     * @param encText 密文
     * @param salt 盐
     * @return
     * @see #encode(String,String)
     */
    public static boolean compare(String rawText, String encText, String salt) {
        return encode(rawText, salt).equals(encText);
    }

    /**
     * 比较原文与密文是否匹配
     * @author lee
     * @date 2016年5月25日 下午3:22:24
     * @param rawText 原文
     * @param encText 密文
     * @param salt 盐
     * @param hashIterations 散列值
     * @return
     * @see #encode(String,String,int)
     */
    public static boolean compare(String rawText, String encText, String salt, int hashIterations) {
        return encode(rawText, salt, hashIterations).equals(encText);
    }

    /**
     * MD5加密,直接加密
     * @author lee
     * @date 2016年5月25日 下午3:02:46
     * @param rawText 明文
     * @return 返回密文
     */
    public static String encode(String rawText) {
        return encode(rawText, DEFAULT_SALT);
    }

    /**
     * MD5加密,用盐加密
     * @author lee
     * @date 2016年5月25日 下午3:14:59
     * @param rawText 明文
     * @param salt 盐
     * @return 返回密文
     */
    public static String encode(String rawText, String salt) {
        return encode(rawText, salt, DEFAULT_HASHITERATIONS);
    }

    /**
     * MD5加密,用盐和散列值加密
     * @author lee
     * @date 2016年5月25日 下午3:16:37
     * @param rawText 明文
     * @param salt 盐
     * @param hashIterations 散列值
     * @return 返回密文
     */
    public static String encode(String rawText, String salt, int hashIterations) {
        return createMd5Hash(rawText, salt, hashIterations).toString();
    }

    /**
     * 创建一个MD5加密工具类
     * @author lee
     * @date 2016年5月25日 下午3:05:37
     * @param rawText 明文
     * @param salt 盐 如果为空,将不用盐加密
     * @param hashIterations 散列次数 如果为空,将不用散列值加密
     * @return
     */
    public static Md5Hash createMd5Hash(String rawText, String salt, Integer hashIterations) {
        if (rawText == null) {
            String msg = "The system does not support null encryption";
            log.warn(msg);
            throw new RuntimeException(msg);
        }
        if (salt == null) {
            return new Md5Hash(rawText);
        }

        if (hashIterations == null) {
            return new Md5Hash(rawText, salt);
        }

        return new Md5Hash(rawText, salt, hashIterations);
    }
}
