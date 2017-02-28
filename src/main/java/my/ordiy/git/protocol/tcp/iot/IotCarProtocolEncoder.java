package my.ordiy.git.protocol.tcp.iot;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * desc: 自定义Iot车辆通信协议编码器
 */
public class IotCarProtocolEncoder extends MessageToMessageEncoder<IotProtocolPoJo> {

    @Override
    protected void encode(ChannelHandlerContext ctx, IotProtocolPoJo msg, List<Object> out) throws Exception {

    }
}
