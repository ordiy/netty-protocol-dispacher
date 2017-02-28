package my.ordiy.git.proter;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;

/**
 * Created by pingxin on 2017/2/8.
 */
public class DefineChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //设置读写空闲监听器，避免死链接
        pipeline.addLast(new ReadTimeoutHandler(180));
        pipeline.addLast(new WriteTimeoutHandler(180));
        new MsgInboundFilter(pipeline);
    }
}
