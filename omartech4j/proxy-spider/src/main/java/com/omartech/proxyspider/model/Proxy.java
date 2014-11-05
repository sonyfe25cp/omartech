package com.omartech.proxyspider.model;

public class Proxy {

	public int id;
	
	public String host;
	
	public int port;
	
	public String proxyType;

    public int status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getProxyType() {
		return proxyType;
	}

	public void setProxyType(String proxyType) {
		this.proxyType = proxyType;
	}

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Proxy{" +
                "id=" + id +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", proxyType='" + proxyType + '\'' +
                ", status=" + status +
                '}';
    }
}
