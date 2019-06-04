package com.leeframework.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件操作工具类 实现文件的创建、删除、复制、压缩、解压以及目录的创建、删除、复制、压缩解压等功能<br>
 * @author Lee[lee@leesoft.cn]
 * @datetime 2017年11月25日 下午9:44:33
 */
public class FileUtil {
    private static Logger log = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 复制整个目录的内容，如果目标目录存在，则不覆盖
     * @param srcDirName 源目录名
     * @param descDirName 目标目录名
     * @return 如果复制成功返回true，否则返回false
     */
    public static boolean copyDirectory(String srcDirName, String descDirName) {
        return copyDirectoryCover(srcDirName, descDirName, false);
    }

    /**
     * 复制整个目录的内容
     * @param srcDirName 源目录名
     * @param descDirName 目标目录名
     * @param coverlay 如果目标目录存在，是否覆盖
     * @return 如果复制成功返回true，否则返回false
     */
    public static boolean copyDirectoryCover(String srcDirName, String descDirName, boolean coverlay) {
        File srcDir = new File(srcDirName);
        if (!srcDir.exists()) {
            log.warn("Copy directory fails, the source directory [{}] does not exist", srcDirName);
            return false;
        } else if (!srcDir.isDirectory()) {
            log.warn("Copy directory fails, the source directory [{}] is not a directory", srcDirName);
            return false;
        }

        String descDirNames = descDirName;
        if (!descDirNames.endsWith(File.separator)) {
            descDirNames = descDirNames + File.separator;
        }
        File descDir = new File(descDirNames);

        if (descDir.exists()) {
            if (coverlay) {
                log.info("target direcotry is exist,ready to delete it");
                if (!delFile(descDirNames)) {
                    log.warn("Directory [{}] delete failed", descDirNames);
                    return false;
                }
            } else {
                log.warn("Failed to copy diretory! [{}] is exist", descDirNames);
                return false;
            }
        } else {
            if (!descDir.mkdirs()) {
                log.warn("Diretory create failed");
                return false;
            }

        }

        boolean flag = true;
        File[] files = srcDir.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                flag = FileUtil.copyFile(files[i].getAbsolutePath(), descDirName + files[i].getName());
                if (!flag) {
                    break;
                }
            }
            if (files[i].isDirectory()) {
                flag = FileUtil.copyDirectory(files[i].getAbsolutePath(), descDirName + files[i].getName());
                if (!flag) {
                    break;
                }
            }
        }
        if (!flag) {
            return false;
        }
        return true;

    }

    /**
     * 复制单个文件，如果目标文件存在，则不覆盖
     * @author lee
     * @date 2016年4月29日 下午1:42:51
     * @param srcFileName 待复制的文件名
     * @param descFileName 目标文件名
     * @return 成功返回true,失败返回false
     */
    public static boolean copyFile(String srcFileName, String descFileName) {
        return FileUtil.copyFileCover(srcFileName, descFileName, false);
    }

    /**
     * 复制单个文件
     * @param srcFileName 待复制的文件名
     * @param descFileName 目标文件名
     * @param coverlay 如果目标文件已存在，是否覆盖
     * @return 如果复制成功，则返回true，否则返回false
     */
    public static boolean copyFileCover(String srcFileName, String descFileName, boolean coverlay) {
        File srcFile = new File(srcFileName);
        if (!srcFile.exists()) {
            log.warn("Copy file failed,the source file [{}] not exist!", srcFileName);
            return false;
        } else if (!srcFile.isFile()) {
            log.warn("Copy file failed,the source file, [{}]  not a file!", srcFileName);
            return false;
        }
        File descFile = new File(descFileName);
        if (descFile.exists()) {
            if (coverlay) {
                log.info("The target file exist,ready to delete it");
                if (!delFile(descFileName)) {
                    log.warn("Delete the target file  [{}]  failed!", descFileName);
                    return false;
                }
            } else {
                log.warn("Copy file failed,the target file  [{}]  exist!", descFileName);
                return false;
            }
        } else {
            if (!descFile.getParentFile().exists()) {
                log.info("Create a directory");
                if (!descFile.getParentFile().mkdirs()) {
                    log.warn("Failed to create the target file's directory!");
                    return false;
                }
            }
        }
        return writeFileByStream(srcFile, descFile);
    }

    /**
     * 创建目录
     * @param descDirName 目录名,包含路径
     * @return 如果创建成功，则返回true，否则返回false
     */
    public static boolean createDirectory(String descDirName) {
        String descDirNames = descDirName;
        if (!descDirNames.endsWith(File.separator)) {
            descDirNames = descDirNames + File.separator;
        }
        File descDir = new File(descDirNames);
        if (descDir.exists()) {
            return false;
        }
        return descDir.mkdirs();
    }

    /**
     * 创建单个文件
     * @param descFileName 文件名，包含路径
     * @return 如果创建成功，则返回true，否则返回false
     */
    public static boolean createFile(String descFileName) {
        File file = new File(descFileName);
        if (file.exists()) {
            return false;
        }
        if (descFileName.endsWith(File.separator)) {
            return false;
        }
        if (!file.getParentFile().exists()) {
            if (!file.getParentFile().mkdirs()) {
                return false;
            }
        }

        try {
            return file.createNewFile();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }

    }

    /**
     * 删除目录及目录下的文件
     * @param dirName 被删除的目录所在的文件路径
     * @return 如果目录删除成功，则返回true，否则返回false
     */
    public static boolean delDirectory(String dirName) {
        String dirNames = dirName;
        if (!dirNames.endsWith(File.separator)) {
            dirNames = dirNames + File.separator;
        }
        File dirFile = new File(dirNames);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return true;
        }

        boolean flag = true;
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                flag = delSingleFile(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            } else if (files[i].isDirectory()) {
                flag = delDirectory(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }

        if (!flag) {
            return false;
        }
        return dirFile.delete();
    }

    /**
     * 删除文件，可以删除单个文件或文件夹
     * @param fileName 被删除的文件名
     * @return 如果删除成功，则返回true，否是返回false
     */
    public static boolean delFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            return true;
        } else {
            if (file.isFile()) {
                return delSingleFile(fileName);
            } else {
                return delDirectory(fileName);
            }
        }
    }

    /**
     * 删除单个文件
     * @param fileName 被删除的文件名
     * @return 如果删除成功，则返回true，否则返回false
     */
    public static boolean delSingleFile(String fileName) {
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {
            return file.delete();
        } else {
            return true;
        }
    }

    /**
     * 获取文件的大小,并全部格式化成KB的显示方式
     * @author lee
     * @date 2016年4月29日 下午2:43:36
     * @param file 文件
     * @return
     */
    public static String getFileSize2KB(File file) {
        if (file.isDirectory()) {
            long size = file.length();
            long kb = size / 1024;
            if (kb == 0) {
                return kb + "B";
            }
            return kb + "KB";
        }
        return null;
    }

    /**
     * I/O方式写文件
     * @author lee
     * @date 2016年4月29日 上午11:56:17
     * @param srcFile 源文件
     * @param descFile 目标文件
     * @return
     */
    public static boolean writeFileByStream(File srcFile, File descFile) {
        int readByte = 0;
        InputStream ins = null;
        OutputStream outs = null;
        try {
            ins = new FileInputStream(srcFile);
            outs = new FileOutputStream(descFile);
            byte[] buf = new byte[1024];
            while ((readByte = ins.read(buf)) != -1) {
                outs.write(buf, 0, readByte);
            }
            return true;
        } catch (Exception e) {
            log.error("Failed to write the file:" + e.getMessage(), e);
            return false;
        } finally {
            if (outs != null) {
                try {
                    outs.close();
                } catch (IOException oute) {
                    oute.printStackTrace();
                }
            }
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException ine) {
                    ine.printStackTrace();
                }
            }
        }
    }

}
