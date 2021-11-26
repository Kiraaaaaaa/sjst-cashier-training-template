package com.meituan.catering.management.common.remote;

import com.facebook.nifty.client.FramedClientConnector;
import com.facebook.swift.service.ThriftClientManager;
import com.meituan.catering.management.common.api.thrift.service.ConsoleThriftService;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

@Slf4j
public abstract class BaseThriftRemoteService {

    private static final ThriftClientManager CLIENT_MANAGER = new ThriftClientManager();

    private static final ConcurrentHashMap<Class<? extends ConsoleThriftService>, ConsoleThriftService> SERVICE_CACHE = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public <T extends ConsoleThriftService> T buildConsoleThriftService(Class<T> consoleThriftServiceClazz, int port) {
        if (SERVICE_CACHE.containsKey(consoleThriftServiceClazz)) {
            return (T) SERVICE_CACHE.get(consoleThriftServiceClazz);
        }
        return (T) SERVICE_CACHE.computeIfAbsent(consoleThriftServiceClazz, consoleThriftServiceClazz0 -> {
            try {
                FramedClientConnector connector = new FramedClientConnector(new InetSocketAddress("localhost", port));
                return CLIENT_MANAGER.createClient(connector, consoleThriftServiceClazz).get();
            } catch (InterruptedException | ExecutionException e) {
                log.error("创建Thrift服务失败", e);
                throw new IllegalStateException("创建Thrift服务失败", e);
            }
        });
    }
}
