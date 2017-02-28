package my.ordiy.git.protocol.tcp.iot;

import java.util.Arrays;

/**
 * Created by ordiy on 2017/2/27.
 */
public class IotProtocolPoJo {
    //起始域
    private short startSuffix;
    //长度
    private short length ;
    //消息版本
    private byte version;
    //消息序号
    private byte msgSerialNum;
    //消息ID
    private short msgId;
    //是否通过校验
    private boolean isChecked;

    private byte[] msgContents ;

    public IotProtocolPoJo(short startSuffix, short length, byte version, byte msgSerialNum, short msgId, boolean isChecked, byte[] msgContents) {
        this.startSuffix = startSuffix;
        this.length = length;
        this.version = version;
        this.msgSerialNum = msgSerialNum;
        this.msgId = msgId;
        this.isChecked = isChecked;
        this.msgContents = msgContents;
    }

    public short getStartSuffix() {
        return startSuffix;
    }

    public void setStartSuffix(short startSuffix) {
        this.startSuffix = startSuffix;
    }

    public short getLength() {
        return length;
    }

    public void setLength(short length) {
        this.length = length;
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public byte getMsgSerialNum() {
        return msgSerialNum;
    }

    public void setMsgSerialNum(byte msgSerialNum) {
        this.msgSerialNum = msgSerialNum;
    }

    public short getMsgId() {
        return msgId;
    }

    public void setMsgId(short msgId) {
        this.msgId = msgId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public byte[] getMsgContents() {
        return msgContents;
    }

    public void setMsgContents(byte[] msgContents) {
        this.msgContents = msgContents;
    }

    @Override
    public String toString() {
        return "IotProtocolPoJo{" +
                "startSuffix=" + startSuffix +
                ", length=" + length +
                ", version=" + version +
                ", msgSerialNum=" + msgSerialNum +
                ", msgId=" + msgId +
                ", isChecked=" + isChecked +
                ", msgContents=" + Arrays.toString(msgContents) +
                '}';
    }
}
