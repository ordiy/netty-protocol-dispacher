package my.ordiy.git.protocol.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.HttpMessage;

import java.util.List;

/**
 * Created by pingxin on 2017/2/13.
 */
public class HttpDecoder  extends MessageToMessageDecoder<HttpMessage> {
    @Override
    protected void decode(ChannelHandlerContext ctx, HttpMessage msg, List<Object> out) throws Exception {

    }
}
