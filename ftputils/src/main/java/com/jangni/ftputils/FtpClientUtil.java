package com.jimmy.ftputils;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Paths;

/**
 * @ClassName FtpClientUtil
 * @Description
 * @Author Mr.jimmy
 * @Date 2019/3/9 18:44
 * @Version 1.0
 **/
public class FtpClientUtil {

    private final String host;
    private final String password;
    private final int port;
    private final String username;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private FTPClient ftpClient = null;

    FtpClientUtil(String host, int port, String username, String password) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.port = port;
    }

    public static void main(String[] args) {
        FtpClientUtil ftp = new FtpClientUtil("192.168.0.121",22,"root", "root");
        ftp.connect();
        ftp.close();
    }

    /**
     * Author Mr.jimmy
     * Description 连接ftp
     * Date 2019/3/9 18:53
     * Param []
     * Return void
     **/
    public void connect() {
        if (this.ftpClient == null || !this.ftpClient.isConnected()) {
            this.ftpClient = new FTPClient();
            //连接
            try {
                this.ftpClient.connect(this.host, this.port);
                this.ftpClient.login(this.username, this.password);
                this.ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                //检测连接是否成功
                int reply = this.ftpClient.getReplyCode();
                if (!FTPReply.isPositiveCompletion(reply)) {
                    logger.error("FTP连接[{}]:[{}]失败！", this.host, this.port);
                    this.close();
                } else {
                    logger.info("FTP连接[{}]:[{}]成功！", this.host, this.port);
                }
            } catch (IOException e) {
                logger.error("FTP连接[{}]:[{}]失败！", this.host, this.port, e);
                this.close();
            }
        }

    }

    /**
     * Author Mr.jimmy
     * Description 关闭ftp连接
     * Date 2019/3/9 18:58
     * Param []
     * Return void
     **/
    public void close() {

        if (this.ftpClient != null) {
            if (this.ftpClient.isConnected()) {
                try {
                    this.ftpClient.logout();
                    this.ftpClient.disconnect();
                    this.ftpClient = null;
                    logger.info("FTP关闭[{}]:[{}]完成！", this.host, this.port);
                } catch (IOException e) {
                    logger.error("FTP关闭[{}]:[{}]失败！", this.host, this.port, e);
                }
            }
        }
    }

    /**
     * Author Mr.jimmy
     * Description
     * Date 2019/3/9 18:53
     * Param [remotePathAndName-FTP上的路径及名字, localDirectory-本地路径, FileName-名字]
     * Return boolean
     **/
    public boolean download(String remotePathAndName, String localDirectory, String FileName) {
        boolean flag = false;
        if (this.ftpClient != null) {
            BufferedOutputStream buffOut = null;
            try {
                buffOut = new BufferedOutputStream(new FileOutputStream(Paths.get(localDirectory, FileName).toFile().getAbsolutePath()));
                flag = this.ftpClient.retrieveFile(remotePathAndName, buffOut);
            } catch (IOException e) {
                logger.error("FTP [{}]:[{}] 上下载异常！", this.host, this.port, e);
            } finally {
                try {
                    if (buffOut != null)
                        buffOut.close();
                } catch (IOException e) {
                    logger.error("FTP [{}]:[{}] 上下载异常后关闭异常！", this.host, this.port, e);
                }
            }
        }
        return flag;
    }

    /**
     * <p>删除ftp上的文件</p>
     *
     * @param remotePathAndName FTP上的路径及名字
     * @return true || false
     */
    public boolean removeFile(String remotePathAndName) {
        boolean flag = false;
        if (this.ftpClient != null) {
            try {
                flag = this.ftpClient.deleteFile(remotePathAndName);
            } catch (IOException e) {
                e.printStackTrace();
                this.close();
            }
        }
        return flag;
    }

    public boolean rename(String ftpPathAndName, String ftpPathAndNewName) {
        boolean flag = false;
        if (this.ftpClient != null) {
            try {
                flag = this.ftpClient.rename(ftpPathAndName, ftpPathAndNewName);
            } catch (IOException e) {
                e.printStackTrace();
                this.close();
            }
        }
        return flag;
    }

    /**
     * 上传文件
     *
     * @param localPathAndName 本地路径及名字
     * @param fptPathAndName   FTP上的路径及名字
     * @return
     */
    public boolean uploadFile(String localPathAndName, String fptPathAndName) {
        boolean flag = false;
        if (this.ftpClient != null) {
            BufferedInputStream buffIn = null;
            try {
                buffIn = new BufferedInputStream(new FileInputStream(localPathAndName));
                flag = this.ftpClient.storeFile(fptPathAndName, buffIn);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (buffIn != null)
                        buffIn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

}