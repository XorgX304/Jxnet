package com.ardikars.jxnet.packet.tcp;

public class TCPFlags {

    private byte NS;
    private byte CWR;
    private byte ECE;
    private byte URG;
    private byte ACK;
    private byte PSH;
    private byte RST;
    private byte SYN;
    private byte FIN;

    private TCPFlags(byte NS, byte CWR, byte ECE, byte URG,
                    byte ACK, byte PSH, byte RST, byte SYN, byte FIN) {
        this.NS = (byte) (NS & 0x1);
        this.CWR = (byte) (CWR & 0x1);
        this.ECE = (byte) (ECE & 0x1);
        this.URG = (byte) (URG & 0x1);
        this.ACK = (byte) (ACK & 0x1);
        this.PSH = (byte) (PSH & 0x1);
        this.RST = (byte) (RST & 0x1);
        this.SYN = (byte) (SYN & 0x1);
        this.FIN = (byte) (FIN & 0x1);
    }

    private TCPFlags(short flags) {
        this.FIN = (byte) ((flags >> 0) & 0x1);
        this.SYN = (byte) ((flags >> 1) & 0x1);
        this.RST = (byte) ((flags >> 2) & 0x1);
        this.PSH = (byte) ((flags >> 3) & 0x1);
        this.ACK = (byte) ((flags >> 4) & 0x1);
        this.URG = (byte) ((flags >> 5) & 0x1);
        this.ECE = (byte) ((flags >> 6) & 0x1);
        this.CWR = (byte) ((flags >> 7) & 0x1);
        this.NS = (byte) ((flags >> 8) & 0x1);
    }

    public static TCPFlags newInstance(short flags) {
        return new TCPFlags(flags);
    }

    public static TCPFlags newInstance(byte NS, byte CWR, byte ECE, byte URG,
                                       byte ACK, byte PSH, byte RST, byte SYN, byte FIN) {
        return new TCPFlags(NS, CWR, ECE, URG, ACK, PSH, RST, SYN, FIN);
    }

    public byte getNS() {
        return (byte) (this.NS & 0x1);
    }

    public TCPFlags setNS(final byte NS) {
        this.NS = (byte) (NS & 0x1);
        return this;
    }

    public byte getCWR() {
        return (byte) (this.CWR & 0x1);
    }

    public TCPFlags setCWR(final byte CWR) {
        this.CWR = (byte) (CWR & 0x1);
        return this;
    }

    public byte getECE() {
        return (byte) (this.ECE & 0x1);
    }

    public TCPFlags setECE(final byte ECE) {
        this.ECE = (byte) (ECE & 0x1);
        return this;
    }

    public byte getURG() {
        return (byte) (this.URG & 0x1);
    }

    public TCPFlags setURG(final byte URG) {
        this.URG = (byte) (URG & 0x1);
        return this;
    }

    public byte getACK() {
        return (byte) (this.ACK & 0x1);
    }

    public TCPFlags setACK(final byte ACK) {
        this.ACK = (byte) (ACK & 0x1);
        return this;
    }

    public byte getPSH() {
        return (byte) (this.PSH & 0x1);
    }

    public TCPFlags setPSH(final byte PSH) {
        this.PSH = (byte) (PSH & 0x1);
        return this;
    }

    public byte getRST() {
        return (byte) (this.RST & 0x1);
    }

    public TCPFlags setRST(final byte RST) {
        this.RST = (byte) (RST & 0x1);
        return this;
    }

    public byte getSYN() {
        return (byte) (this.SYN & 0x1);
    }

    public TCPFlags setSYN(final byte SYN) {
        this.SYN = (byte) (SYN & 0x1);
        return this;
    }

    public byte getFIN() {
        return (byte) (this.FIN & 0x1);
    }

    public TCPFlags setFIN(final byte FIN) {
        this.FIN = (byte) (FIN & 0x1);
        return this;
    }

    public short toShort() {
        short flags = 0;
        flags += Math.pow(2, 1);
        flags += Math.pow(2, 1);
        flags += Math.pow(2, 1);
        flags += Math.pow(2, 1);
        flags += Math.pow(2, 1);
        flags += Math.pow(2, 1);
        flags += Math.pow(2, 1);
        flags += Math.pow(2, 1);
        return flags;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("NS: ").append(this.getNS())
                .append("CWR: ").append(this.getCWR())
                .append("ECE: ").append(this.getECE())
                .append("URG: ").append(this.getURG())
                .append("ACK: ").append(this.getACK())
                .append("PSH: ").append(this.getPSH())
                .append("RST: ").append(this.getRST())
                .append("SYN: ").append(this.getSYN())
                .append("FIN: ").append(this.getFIN())
                .toString();
    }

}