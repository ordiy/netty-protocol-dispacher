package my.ordiy.git.proter;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import my.ordiy.git.protocol.http.HttpDecoder;
import my.ordiy.git.protocol.http.HttpEncoder;
import my.ordiy.git.protocol.http.NettyHTTPServerHandler;
import my.ordiy.git.protocol.tcp.iot.IotCarProtocolDecoder;
import my.ordiy.git.protocol.tcp.iot.IotCarProtocolEncoder;
import my.ordiy.git.protocol.tcp.iot.IotCarPtInboundDataHandler;
import my.ordiy.git.uitl.BytesUtil;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by pingxin on 2017/2/8.
 */
public class MsgInboundFilter extends ChannelInboundHandlerAdapter {
    private static final org.apache.logging.log4j.Logger LOG = org.apache.logging.log4j.LogManager.getLogger(MsgInboundFilter.class);
    private final ChannelPipeline pileline;
    private final byte[] POST_HEADER_BYTES = new byte[]{50, (byte) 4f, 53, 54};
    private final byte[] GET_HEADER_BYTES = new byte[]{0X47, 0X45, 0X54, 0X20};

    private AtomicInteger msgNumCalc;

    public MsgInboundFilter(ChannelPipeline pipeline) {
        this.pileline = pipeline;
        msgNumCalc = new AtomicInteger();
    }


    /**
     * 通过重写msg Reader判断报文特征实现
     * 只要收到数据就会调用该方法
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ByteBuf) {
            ByteBuf in = (ByteBuf) msg;
            byte[] src = new byte[in.readableBytes()];
            in.copy(0, in.readableBytes()).readBytes(src);
            msgNumCalc.incrementAndGet();

            switch (identifiedPro(src)) {
                case HTTT:
                    pileline.addLast(new HttpDecoder());
                    pileline.addLast(new HttpEncoder());
                    pileline.addLast(new NettyHTTPServerHandler());
                    break;
                case RTP_V1:
                    //流媒体RTP协议1
                    break;
                case Iot_Car:
                    //根据协议类型配置编解码器 , Iot car协议可参照文档 《车联网控制协议》
                    pileline.addLast(new IotCarProtocolDecoder());
                    pileline.addLast(new IotCarProtocolEncoder());
                    //分包器，防止数据黏包
//                    pileline.addLast(new DelimiterBasedFrameDecoder());
                    pileline.addLast(new IotCarPtInboundDataHandler());
                    break;
                default:
                    LOG.error("not identified this protocol");
                    break;
                //// TODO: 2017/2/13 关闭连接
            }
        } else {
           LOG.error("channel have error.....");
        }
        ctx.fireChannelActive();//转到下一个handle
    }


    private ProtocolEnum identifiedPro(byte[] src) {
        //50 4f 53 54 post
        ProtocolEnum protocolEnum = null;
        if (BytesUtil.startWithsArrays(src, GET_HEADER_BYTES) || BytesUtil.startWithsArrays(src, POST_HEADER_BYTES)) {
            protocolEnum = ProtocolEnum.HTTT;
        } else if (BytesUtil.startWithsArrays(src, ProtocolEnum.RTP_V1.getProPrefix())) {
            protocolEnum = ProtocolEnum.RTP_V1;
        } else if (BytesUtil.startWithsArrays(src, ProtocolEnum.Iot_Car.getProPrefix())) {
            protocolEnum = ProtocolEnum.Iot_Car;//物联网车辆通信协议
        }
        return protocolEnum;
    }

}
