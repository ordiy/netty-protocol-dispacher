package my.ordiy.git.protocol.tcp.iot;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * desc:自定义Iot车辆通信协议编码器
 */
public class IotCarProtocolDecoder extends MessageToMessageDecoder<ByteBuf> {


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        //iot cat协议的包不能小于8
        if (msg.readableBytes()<9){
            return;
        }
        short startSuffix = msg.getShort(0);
        short msgLength = msg.getShort(2);
        byte version = msg.getByte(4);
        byte msgSN= msg.getByte(5);
        short msgID = msg.getShort(6);

        byte[] msgContents = new byte[msg.readableBytes()-7];
        msg.readBytes(msgContents,7,msgContents.length);

        byte checkArea = msg.getByte(7+msgContents.length);
        boolean b = checkMsg(checkArea, msg);
        IotProtocolPoJo iotProtocolPoJo = new IotProtocolPoJo(startSuffix, msgLength, version, msgSN, msgID
               ,b , msgContents);
        out.add(iotProtocolPoJo);
    }

    private boolean checkMsg(byte checkValue ,ByteBuf byteBuf){
        boolean is = true;
        //TODO 实现和校验算法
        return is;
    }
}
