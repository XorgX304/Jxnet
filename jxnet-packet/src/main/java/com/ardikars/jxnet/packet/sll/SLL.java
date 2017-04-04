package com.ardikars.jxnet.packet.sll;

import com.ardikars.jxnet.packet.Packet;
import com.ardikars.jxnet.packet.ethernet.EthernetType;
import com.ardikars.jxnet.util.Builder;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class SLL extends Packet implements Builder<Packet> {

    public static final int SLL_HEADER_LENGTH = 16;

    private short packetType;
    private short hardwareAddressType;
    private short hardwareAddressLength;
    private byte[] address;
    private EthernetType protocol;

    /**
     * SLL payload
     */
    private byte[] payload;

    public SLL() {
        this.address = new byte[8];
    }

    public short getPacketType() {
        return (short) (this.packetType & 0xffff);
    }

    public SLL setPacketType(final short packetType) {
        this.packetType = (short) (packetType & 0xffff);
        return this;
    }

    public short getHardwareAddressType() {
        return (short) (this.hardwareAddressType & 0xffff);
    }

    public SLL setHardwareAddressType(final short hardwareAddressType) {
        this.hardwareAddressType = (short) (hardwareAddressType & 0xffff);
        return this;
    }

    public short getHardwareAddressLength() {
        return (short) (this.hardwareAddressLength & 0xffff);
    }

    public SLL setHardwareAddressLength(final short hardwareAddressLength) {
        this.hardwareAddressLength = (short) (hardwareAddressLength & 0xffff);
        return this;
    }

    public byte[] getAddress() {
        return this.address;
    }

    public SLL setAddress(final byte[] address) {
        this.address = address;
        return this;
    }

    public EthernetType getProtocol() {
        return this.protocol;
    }

    public SLL setProtocol(final EthernetType protocol) {
        this.protocol = protocol;
        return this;
    }

    public byte[] getPayload() {
        return this.payload;
    }

    public SLL setPayload(final byte[] payload) {
        this.payload = payload;
        return this;
    }

    @Override
    public Packet setPacket(Packet packet) {
        return this.setPayload(packet.toBytes());
    }

    @Override
    public Packet getPacket() {
        return null;
    }

    @Override
    public byte[] toBytes() {
        byte[] data = new byte[SLL_HEADER_LENGTH + ((this.getPayload() != null) ? 0 : this.getPayload().length)];
        ByteBuffer buffer = ByteBuffer.wrap(data);
        buffer.putShort(this.getPacketType());
        buffer.putShort(this.getHardwareAddressType());
        buffer.putShort(this.getHardwareAddressLength());
        buffer.put(this.getAddress());
        buffer.putShort((short) (this.getProtocol().getValue() & 0xffff));
        if (this.getPayload() != null) {
            buffer.put(this.getPayload());
        }
        return data;
    }

    @Override
    public Packet build() {
        return this;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("[Packet Type: ").append(this.getPacketType())
                .append(", Hardware Address Type: " + this.getHardwareAddressType())
                .append(", Hardware Address Length: " + this.getHardwareAddressLength())
                .append(", Address: " + Arrays.toString(this.getAddress()))
                .append(", Protocol: " + this.getProtocol().toString())
                .append("]").toString();
    }

}
