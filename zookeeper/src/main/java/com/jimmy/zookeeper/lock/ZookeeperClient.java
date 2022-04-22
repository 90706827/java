package com.jimmy.zookeeper.lock;

public class ZookeeperClient implements ZookeeperHandler {

    @Override
    public boolean createEphemeral(String path) {
        return false;
    }

    @Override
    public boolean createEphemeralSequential(String path, byte init) {
        return false;
    }

    @Override
    public boolean createPersistent(String path) {
        return false;
    }

    @Override
    public boolean createPersistentSequential(String path, byte init) {
        return false;
    }

    @Override
    public boolean delete(String path) {
        return false;
    }

    @Override
    public boolean delete(String path, boolean children) {
        return false;
    }

    @Override
    public boolean delete(String path, int version) {
        return false;
    }

    @Override
    public boolean delete(String path, boolean children, int version) {
        return false;
    }

    @Override
    public boolean delete(String path, boolean children, int version, boolean guaranteed) {
        return false;
    }
}
