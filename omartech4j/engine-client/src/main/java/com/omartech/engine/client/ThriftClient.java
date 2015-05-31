package com.omartech.engine.client;


import com.omartech.data.gen.DataService;

public class ThriftClient {
    public DataService.Client client;
    public String ip;

    public ThriftClient(DataService.Client client, String ip) {
        this.client = client;
        this.ip = ip;
    }
}
