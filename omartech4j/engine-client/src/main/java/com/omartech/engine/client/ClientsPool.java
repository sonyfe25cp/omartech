package com.omartech.engine.client;

import com.omartech.data.gen.DataService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by feng on 4/22/14.
 */
public class ClientsPool {
    private HashMap<String, LinkedList<ThriftClient>> clients = new HashMap<String, LinkedList<ThriftClient>>();
    private AtomicLong idx = new AtomicLong(0);
    private final String[] servers;

    public static final int MAX_PER_HOST = 5;

    public ClientsPool(String[] servers) {
        this.servers = servers;
    }


    public ThriftClient getClient(ThriftClient prev) throws TTransportException {
        int index;
        if (prev == null) {
            index = (int) (idx.incrementAndGet() % servers.length);
        } else {
            int i = 0;
            for (String s : servers) { // find the previous index and add one
                i += 1;
                if (prev.ip.equals(s)) {
                    break;
                }
            }
            index = i % servers.length;
        }


        synchronized (this) {
            int retry = 3;
            while (true) {
                // TODO temporary disable some server if exception is caught
                final String ip = servers[index];

                LinkedList<ThriftClient> clients = this.clients.get(ip);
                if (clients != null) {
                    Iterator<ThriftClient> iterator = clients.iterator();
                    if (iterator.hasNext()) {
                        ThriftClient next = iterator.next();
                        iterator.remove();
                        return next;
                    }
                }
                String[] parts = ip.split(":");
                TSocket trans = new TSocket(parts[0], Integer.parseInt(parts[1]), 2000);

                try {
                    trans.open();
                    TBinaryProtocol protocol = new TBinaryProtocol(new TFramedTransport(trans));
                    DataService.Client client = new DataService.Client(protocol);
                    return new ThriftClient(client, ip);

                } catch (TTransportException e) {
                    index = (index + 1) % servers.length;
                    if (retry-- <= 0) { // retry limited times
                        throw e;
                    }
                }
            }
        }
    }

    public void returnClient(ThriftClient client) {
        synchronized (this) {
            LinkedList<ThriftClient> list = this.clients.get(client.ip);
            if (list != null && list.size() >= MAX_PER_HOST) {
                client.client.getInputProtocol().getTransport().close();
            } else {
                if (list == null) {
                    list = new LinkedList<ThriftClient>();
                }
                list.add(client);
                this.clients.put(client.ip, list);
            }
        }
    }

    public void returnBrokenClient(ThriftClient client) {

        client.client.getInputProtocol().getTransport().close();

//        synchronized (this) {
//            // server maybe down, close all the connection, new request will reopen the connection
//            LinkedList<ThriftClient> list = this.clients.get(client.ip);
//            if (list != null) {
//                for (ThriftClient thriftClient : list) {
//                    thriftClient.client.getInputProtocol().getTransport().close();
//                }
//            }
//        }
    }

}
