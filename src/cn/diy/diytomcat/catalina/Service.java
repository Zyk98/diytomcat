package cn.diy.diytomcat.catalina;

import cn.diy.diytomcat.util.ServerXMLUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.log.LogFactory;

import java.util.List;

public class Service {
    private String name;
    private cn.diy.diytomcat.catalina.Engine engine;
    private Server server;

    private List<Connector> connectors;

    public Service(Server server) {
        this.server = server;
        this.name = cn.diy.diytomcat.util.ServerXMLUtil.getServiceName();
        this.engine = new cn.diy.diytomcat.catalina.Engine(this);
        this.connectors = ServerXMLUtil.getConnectors(this);
    }

    public Engine getEngine() {
        return engine;
    }

    public Server getServer() {
        return server;
    }

    public void start() {
        init();
    }

    private void init() {
        TimeInterval timeInterval = DateUtil.timer();
        for (Connector c : connectors)
            c.init();
        LogFactory.get().info("Initialization processed in {} ms", timeInterval.intervalMs());
        for (Connector c : connectors)
            c.start();
    }
}