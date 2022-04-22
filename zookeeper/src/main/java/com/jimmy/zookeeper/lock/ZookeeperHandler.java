package com.jimmy.zookeeper.lock;

/**
 * @author jimmy
 */
public interface ZookeeperHandler {

    /**
     * 临时
     *
     * @param path
     */
    boolean createEphemeral(String path);

    /**
     * 临时有序有值
     *
     * @param path
     * @param init
     */
    boolean createEphemeralSequential(String path, byte init);

    /**
     * 持久
     *
     * @param path
     */
    boolean createPersistent(String path);

    /**
     * 持久 有值
     *
     * @param path
     * @param init
     */
    boolean createPersistentSequential(String path, byte init);

    boolean delete(String path);

    boolean delete(String path, boolean children);

    boolean delete(String path, int version);

    boolean delete(String path, boolean children, int version);

    boolean delete(String path, boolean children, int version, boolean guaranteed);



}
