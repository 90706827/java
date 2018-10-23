package com.jangni.fastdfs.controller;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;

import java.io.IOException;

public class UploadFileUtil {

    public String upload(String local_filename) throws Exception {

        String propFilePath = "fastdfs-client.properties";
        try {
            ClientGlobal.initByProperties(propFilePath);
            System.out.println("ClientGlobal.configInfo() : " + ClientGlobal.configInfo());
        } catch (
                IOException e) {
            e.printStackTrace();
        } catch (
                MyException e) {
            e.printStackTrace();
        }
        System.out.println("java.version=" + System.getProperty("java.version"));

        String conf_filename = "fastdfs-client.properties";
        ClientGlobal.initByProperties(conf_filename);
        System.out.println("network_timeout=" + ClientGlobal.g_network_timeout + "ms");
        System.out.println("charset=" + ClientGlobal.g_charset);

        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        StorageServer storageServer = null;
        StorageClient1 client = new StorageClient1(trackerServer, storageServer);

        NameValuePair[] metaList = new NameValuePair[1];
        metaList[0] = new NameValuePair("fileName", local_filename);
        String fileId = client.upload_file1(local_filename, null, metaList);
        System.out.println("upload success. file id is: " + fileId);

        int i = 0;
        while (i++ < 10) {
            byte[] result = client.download_file1(fileId);
            System.out.println(i + ", download result is: " + result.length);
        }

        trackerServer.close();
        return fileId;
    }

}
