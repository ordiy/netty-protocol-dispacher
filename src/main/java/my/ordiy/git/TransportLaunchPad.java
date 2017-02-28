package my.ordiy.git;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import my.ordiy.git.proter.DefineChannelInitializer;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by pingxin on 2016/12/8.
 */
public class TransportLaunchPad implements  Runnable{
    private static final Logger log = LogManager.getLogger(TransportLaunchPad.class);
    private String host;//在双网卡的主机上 如果只在指定网卡上进行端口通信，则设置这个参数
    private int port ;

    public TransportLaunchPad(int port) {
        this.port = port;
    }

    public TransportLaunchPad(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() {
        log.info("will start launch pad ,the port:{},host:{}",port,host);
        NioEventLoopGroup boss= new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap server = new ServerBootstrap();
        server.group(boss,worker).channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO)).childHandler(new DefineChannelInitializer());

        configChannel(server);

            try {
                //only bind port ,or bind host and port
                ChannelFuture future = (StringUtils.isEmpty(host)) ? server.bind(port).sync(): server.bind(host,port);

                Channel channel = future.channel();
                log.info(" server  start success , host is {}" ,channel.localAddress().toString());
                channel.closeFuture().sync();
            } catch (InterruptedException e) {
                log.error("server bind port failed cause:{}",e.getCause());
                //a fatal error
                throw new RuntimeException(e);
            }

    }

    /**
     * 设置TCP/Socket通信的参数
     * @param server
     */
    private void configChannel(ServerBootstrap server) {
        server.option(ChannelOption.MAX_MESSAGES_PER_READ,12);
    }
}
