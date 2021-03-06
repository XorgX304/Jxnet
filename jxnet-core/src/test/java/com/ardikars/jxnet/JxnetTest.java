/**
 * Copyright (C) 2015-2018 Jxnet
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ardikars.jxnet;

import static com.ardikars.jxnet.Jxnet.OK;
import static com.ardikars.jxnet.Jxnet.PcapActivate;
import static com.ardikars.jxnet.Jxnet.PcapBreakLoop;
import static com.ardikars.jxnet.Jxnet.PcapCanSetRfMon;
import static com.ardikars.jxnet.Jxnet.PcapCheckActivated;
import static com.ardikars.jxnet.Jxnet.PcapClose;
import static com.ardikars.jxnet.Jxnet.PcapCompile;
import static com.ardikars.jxnet.Jxnet.PcapCompileNoPcap;
import static com.ardikars.jxnet.Jxnet.PcapCreate;
import static com.ardikars.jxnet.Jxnet.PcapDataLink;
import static com.ardikars.jxnet.Jxnet.PcapDataLinkNameToVal;
import static com.ardikars.jxnet.Jxnet.PcapDataLinkValToDescription;
import static com.ardikars.jxnet.Jxnet.PcapDataLinkValToName;
import static com.ardikars.jxnet.Jxnet.PcapDispatch;
import static com.ardikars.jxnet.Jxnet.PcapDispatch0;
import static com.ardikars.jxnet.Jxnet.PcapDump;
import static com.ardikars.jxnet.Jxnet.PcapDumpClose;
import static com.ardikars.jxnet.Jxnet.PcapDumpFTell;
import static com.ardikars.jxnet.Jxnet.PcapDumpFlush;
import static com.ardikars.jxnet.Jxnet.PcapDumpOpen;
import static com.ardikars.jxnet.Jxnet.PcapFindAllDevs;
import static com.ardikars.jxnet.Jxnet.PcapFreeCode;
import static com.ardikars.jxnet.Jxnet.PcapGetErr;
import static com.ardikars.jxnet.Jxnet.PcapGetNonBlock;
import static com.ardikars.jxnet.Jxnet.PcapGetTStampPrecision;
import static com.ardikars.jxnet.Jxnet.PcapIsSwapped;
import static com.ardikars.jxnet.Jxnet.PcapLibVersion;
import static com.ardikars.jxnet.Jxnet.PcapListDataLinks;
import static com.ardikars.jxnet.Jxnet.PcapListTStampTypes;
import static com.ardikars.jxnet.Jxnet.PcapLoop;
import static com.ardikars.jxnet.Jxnet.PcapLoop0;
import static com.ardikars.jxnet.Jxnet.PcapMajorVersion;
import static com.ardikars.jxnet.Jxnet.PcapMinorVersion;
import static com.ardikars.jxnet.Jxnet.PcapNext;
import static com.ardikars.jxnet.Jxnet.PcapNextEx;
import static com.ardikars.jxnet.Jxnet.PcapOfflineFilter;
import static com.ardikars.jxnet.Jxnet.PcapOpenDead;
import static com.ardikars.jxnet.Jxnet.PcapOpenDeadWithTStampPrecision;
import static com.ardikars.jxnet.Jxnet.PcapOpenLive;
import static com.ardikars.jxnet.Jxnet.PcapOpenOffline;
import static com.ardikars.jxnet.Jxnet.PcapOpenOfflineWithTStampPrecision;
import static com.ardikars.jxnet.Jxnet.PcapPError;
import static com.ardikars.jxnet.Jxnet.PcapSetDataLink;
import static com.ardikars.jxnet.Jxnet.PcapSetDirection;
import static com.ardikars.jxnet.Jxnet.PcapSetFilter;
import static com.ardikars.jxnet.Jxnet.PcapSetImmediateMode;
import static com.ardikars.jxnet.Jxnet.PcapSetNonBlock;
import static com.ardikars.jxnet.Jxnet.PcapSetPromisc;
import static com.ardikars.jxnet.Jxnet.PcapSetRfMon;
import static com.ardikars.jxnet.Jxnet.PcapSetSnaplen;
import static com.ardikars.jxnet.Jxnet.PcapSetTStampPrecision;
import static com.ardikars.jxnet.Jxnet.PcapSetTStampType;
import static com.ardikars.jxnet.Jxnet.PcapSetTimeout;
import static com.ardikars.jxnet.Jxnet.PcapSnapshot;
import static com.ardikars.jxnet.Jxnet.PcapStats;
import static com.ardikars.jxnet.Jxnet.PcapStatusToStr;
import static com.ardikars.jxnet.Jxnet.PcapStrError;
import static com.ardikars.jxnet.Jxnet.PcapTStampTypeNameToVal;
import static com.ardikars.jxnet.Jxnet.PcapTStampTypeValToDescription;
import static com.ardikars.jxnet.Jxnet.PcapTStampTypeValToName;

import com.ardikars.common.net.Inet4Address;
import com.ardikars.common.net.MacAddress;
import com.ardikars.common.util.Platforms;
import com.ardikars.jxnet.exception.DeviceNotFoundException;
import com.ardikars.jxnet.exception.NativeException;
import com.ardikars.jxnet.exception.PlatformNotSupportedException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JxnetTest {

    private final Logger logger = Logger.getLogger(JxnetTest.class.getName());

    private int resultCode;

    private final String resourceDumpFile = "../gradle/resources/pcap/icmp.pcap";

    private StringBuilder errbuf = new StringBuilder();
    private Pcap pcap;
    private PcapDumper dumper;
    private BpfProgram bpfProgram;
    private ByteBuffer pkt;
    private PcapPktHdr pktHdr = new PcapPktHdr();

    private String source;
    private final int snaplen = 65335;
    private final int promisc = 1;
    private final int timeout = 2000;
    private final int immediate = 1;
    private final int optimize = 1;
    private final int bufferSize = 1500;
    private final String filter = "";
    private final int precision = 0;

    private final int maxPkt = 5;
    private static int cntPkt = 0;

    private List<PcapIf> alldevsp = new ArrayList<PcapIf>();

    private Inet4Address netp = Inet4Address.valueOf(0);
    private Inet4Address maskp = Inet4Address.valueOf("255.255.255.0");

    private final PcapHandler<String> callback = new PcapHandler<String>() {
        @Override
        public void nextPacket(String user, PcapPktHdr h, ByteBuffer bytes) {
            System.out.println("Argument    : " + user);
            System.out.println("PacketHeader: " + h);
            System.out.println("PacketBuffer: " + bytes);
        }
    };

    private final RawPcapHandler<String> callback0 = new RawPcapHandler<String>() {
        @Override
        public void nextPacket(String user, int capLen, int len, int tvSec, long tvUsec, long memoryAddress) {
            System.out.println("Argument       : " + user);
            System.out.println("Calen          : " + capLen);
            System.out.println("Len            : " + len);
            System.out.println("TvSec          : " + tvSec);
            System.out.println("TvUSec         : " + tvUsec);
            System.out.println("Memory Address : " + memoryAddress);
        }
    };

    /**
     * Initialize
     * @throws Exception Exception.
     */
    @Before
    public void create() throws Exception {
        if ((resultCode = PcapFindAllDevs(alldevsp, errbuf)) != OK) {
            logger.warning("create:PcapFindAllDevs(): " + errbuf.toString());
        }
        for (PcapIf dev : alldevsp) {
            for (PcapAddr addr : dev.getAddresses()) {
                if (addr.getAddr().getSaFamily() == SockAddr.Family.AF_INET) {
                    if (addr.getAddr().getData() != null) {
                        Inet4Address d = Inet4Address.valueOf(addr.getAddr().getData());
                        if (!d.equals(Inet4Address.LOCALHOST) && !d.equals(Inet4Address.ZERO)) {
                            source = dev.getName();
                        }
                    }
                }
            }
        }
        if (source == null) {
            throw new Exception("Failed to lookup device");
        } else {
            System.out.println("Source: " + source);
        }
        pcap = PcapCreate(source, errbuf);
        if (pcap == null) {
            logger.warning("create:PcapCreate(): " + errbuf.toString());
            return;
        }
        if ((resultCode = PcapSetSnaplen(pcap, snaplen)) != OK) {
            logger.warning("create:PcapSetSnaplen(): " + PcapStrError(resultCode));
            PcapClose(pcap);
            return;
        }
        if ((resultCode = PcapSetPromisc(pcap, promisc)) != OK) {
            logger.warning("create:PcapSetPromisc(): " + PcapStrError(resultCode));
            PcapClose(pcap);
            return;
        }
        if ((resultCode = PcapSetTimeout(pcap, timeout)) != OK) {
            logger.warning("create:PcapSetTimeout(): " + PcapStrError(resultCode));
            PcapClose(pcap);
            return;
        }
        if (!Platforms.isWindows()) {
            if ((resultCode = PcapSetImmediateMode(pcap, immediate)) != OK) {
                logger.warning("create:PcapSetImmediateMode(): " + PcapStrError(resultCode));
                PcapClose(pcap);
                return;
            }
        }
        if ((resultCode = PcapCheckActivated(pcap)) == 0) {
            if ((resultCode = PcapActivate(pcap)) != OK) {
                logger.warning("create:PcapActivate(): " + PcapStrError(resultCode));
                PcapClose(pcap);
                return;
            }
        }

        bpfProgram = new BpfProgram();

        File file = File.createTempFile("dump", ".pcap");
        if (!file.delete()) {
            logger.warning("create:File.delete()");
            return;
        }
        dumper = PcapDumpOpen(pcap, file.getAbsolutePath());
        if (dumper == null) {
            logger.warning("create:PcapDumpOpen() ");
            return;
        }
    }

    @Test
    public void Test01_PcapFindAllDevs() {
        if ((resultCode = PcapFindAllDevs(alldevsp, errbuf)) != OK) {
            logger.warning("PcapFindAllDevs:PcapFindAllDevs(): " + errbuf.toString());
        }
        for (PcapIf dev : alldevsp) {
            System.out.println("================================================\n\n");
            System.out.println("Name                  = " + dev.getName());
            System.out.println("Description           = " + dev.getDescription());
            System.out.println("Flags                 = " + dev.getFlags());
            System.out.println("Is loopback           = " + dev.isLoopback());
            System.out.println("Is Up                 = " + dev.isUp());
            System.out.println("Is Running            = " + dev.isRunning());
            for (PcapAddr addr : dev.getAddresses()) {
                if (addr.getAddr().getSaFamily() == SockAddr.Family.AF_INET) {
                    System.out.println("------------------------------------------------");
                    System.out.println("IPv4");
                    System.out.println("Addr                   = " + addr.getAddr().toString());
                    System.out.println("Netmask                = " + addr.getNetmask().toString());
                    System.out.println("BroadAddr              = " + addr.getBroadAddr().toString());
                    System.out.println("DstAddr                = " + addr.getDstAddr().toString());
                    System.out.println("------------------------------------------------");
                } else if (addr.getAddr().getSaFamily() == SockAddr.Family.AF_INET6) {
                    System.out.println("------------------------------------------------");
                    System.out.println("IPv6");
                    System.out.println("Addr                   = " + addr.getAddr().toString());
                    System.out.println("Netmask                = " + addr.getNetmask().toString());
                    System.out.println("BroadAddr              = " + addr.getBroadAddr().toString());
                    System.out.println("DstAddr                = " + addr.getDstAddr().toString());
                    System.out.println("------------------------------------------------");
                } else {
                    logger.info(addr.toString());
                }
                System.out.println("Family                 = " + addr.getAddr().getSaFamily().toString());
            }
            System.out.println("================================================\n\n");
        }
    }

    @Test
    public void Test02_PcapOpenLiveAndPcapClose() {
        Pcap pcap = PcapOpenLive(source, snaplen, promisc, timeout, errbuf);
        if (pcap == null) {
            logger.warning("PcapOpenLiveAndPcapClose:PcapOpenLive(): " + errbuf.toString());
        } else {
            PcapClose(pcap);
        }
    }

    @Test
    public void Test03_PcapLoopAndPcapStats() {
        if ((resultCode = PcapLoop(pcap, maxPkt, callback, "This Is User Argument")) != OK) {
            logger.warning("PcapLoop:PcapLoop(): " + PcapStrError(resultCode));
            return;
        }
        PcapStat stat = new PcapStat();
        System.out.println("Droped by kernel    : " + stat.getPsDrop());
        System.out.println("Droped by interface : " + stat.getPsIfdrop());
        System.out.println("Recieved            : " + stat.getPsRecv());
        if ((resultCode = PcapStats(pcap, stat)) != OK) {
            logger.warning("PcapLoopAndPcapStats:PcapStats(): " + PcapStrError(resultCode));
            return;
        }
        System.out.println(stat);
    }

    @Test
    public void Test04_PcapDispatchAndPcapSetNonBlock() {
        if ((resultCode = PcapSetNonBlock(pcap, 1, errbuf)) != OK) {
            logger.warning("PcapDispatch:PcapSetNonBlock(): " + PcapStrError(resultCode));
        }
        if ((resultCode = PcapDispatch(pcap, maxPkt, callback, "This Is User Argument")) != OK) {
            logger.warning("PcapDispatch:PcapDispatch(): " + PcapStrError(resultCode));
            return;
        }
        if ((resultCode = PcapDispatch0(pcap, maxPkt, callback0, "This Is User Argument")) != OK) {
            logger.warning("PcapDispatch:PcapDispatch0(): " + PcapStrError(resultCode));
            return;
        }
    }

    @Test
    public void Test05_PcapDumpOpenAndPcapDumpClose() throws IOException {
        File file = File.createTempFile("dump", ".pcap");
        if (!file.delete()) {
            logger.warning("PcapDumpOpenAndClose:File.delete()");
            return;
        }
        PcapDumper dumper = PcapDumpOpen(pcap, file.getAbsolutePath());
        if (dumper == null) {
            logger.warning("PcapDumpOpenAndClose:PcapDumpOpen() ");
            return;
        }
        PcapDumpClose(dumper);
    }

    @Test
    public void Test06_PcapDumpPcapDumpFTellAndPcapDumpFlush() {
        if ((resultCode = PcapLoop(pcap, maxPkt, new PcapHandler<String>() {
            @Override
            public void nextPacket(String user, PcapPktHdr h, ByteBuffer bytes) {
                if (h != null && bytes != null) {
                    System.out.println("Dumping packet into a file.");
                    System.out.println("File position: " + PcapDumpFTell(dumper));
                    PcapDump(dumper, h, bytes);
                    PcapDumpFlush(dumper);
                }
            }
        }, "This Is Dumper Argument")) != OK) {
            logger.warning("PcapDumpPcapDumpFTellAndPcapDumpFlush:PcapLoop(): " + PcapStrError(resultCode));
            return;
        }
    }

    @Test
    public void Test07_PcapOpenOfflinePcapLoopAndPcapClose() {
        Pcap pcap = PcapOpenOffline(resourceDumpFile, errbuf);
        if (pcap == null) {
            logger.warning("PcapOpenOfflinePcapLoopAndPcapClose:PcapOpenOffline(): " + errbuf.toString());
            return;
        }
        PcapLoop(pcap, maxPkt, callback, "");
        PcapLoop0(pcap,maxPkt, callback0, "");
        PcapClose(pcap);
    }

    @Test
    public void Test08_PcapCompilePcapSetFilterAndPcapLoop() {
        if ((resultCode = PcapCompile(pcap, bpfProgram, filter, optimize, maskp.toInt())) != OK) {
            logger.warning("PcapCompilePcapSetFilterAndPcapLoop:PcapCompile(): " + PcapStrError(resultCode));
            return;
        }
        if ((resultCode = PcapSetFilter(pcap, bpfProgram)) != OK) {
            logger.warning("PcapCompilePcapSetFilterAndPcapLoop:PcapSetFilter(): " + PcapStrError(resultCode));
            return;
        }
        if ((resultCode = PcapLoop(pcap, maxPkt, callback, "This Is User Argument")) != OK) {
            logger.warning("PcapCompilePcapSetFilterAndPcapLoop:PcapLoop(): " + PcapStrError(resultCode));
            return;
        }
    }

    @Test
    public void Test09_PcapSendPacket() throws Exception {
        // throw new Exception("Not implemented yet");
    }

    @Test
    public void Test10_PcapNext() {
        for (int i = 0; i < maxPkt; i++) {
            pkt = PcapNext(pcap, pktHdr);
            if (pktHdr != null && pkt != null) {
                System.out.println("PacketHeader: " + pktHdr);
                System.out.println("PacketBuffer: " + pkt);
            }
        }
    }

    //@Test
    public void Test11_PcapNextEx() {
        pkt = ByteBuffer.allocateDirect(bufferSize);
        for (int i = 0; i < maxPkt; i++) {
            resultCode = PcapNextEx(pcap, pktHdr, pkt);
            logger.info("Result: " + resultCode);
            if (pktHdr != null && pkt != null) {
                System.out.println("PacketHeader: " + pktHdr);
                System.out.println("PacketBuffer: " + pkt);
                pkt.clear();
            }

        }
    }

    @Test
    public void Test12_PcapDataLink() {
        System.out.println("Data Link Type: " + PcapDataLink(pcap));
    }

    @Test
    public void Test13_PcapSetDataLinkPcapDataLinkPcapOpenDeadAndPcapClose() {
        Pcap pcap = PcapOpenDead(DataLinkType.EN10MB.getValue(), snaplen);
        if (pcap == null) {
            logger.warning("PcapSetDataLinkPcapDataLinkPcapOpenDeadAndPcapClose:PcapOpenDead()");
            return;
        }
        System.out.println("Data Link Type (Before): " + PcapDataLink(pcap));
        try {
            if ((resultCode = PcapSetDataLink(pcap, DataLinkType.LINUX_SLL.getValue())) != OK) {
                logger.warning("PcapSetDataLinkPcapDataLinkPcapOpenDeadAndPcapClose:PcapSetDataLink(): " + PcapStrError(resultCode));
                PcapClose(pcap);
                return;
            }
        } catch (NativeException e) {
            logger.warning(e.getMessage());
        }
        System.out.println("Data Link Type (After): " + PcapDataLink(pcap));
        PcapClose(pcap);
    }

    @Test
    public void Test14_PcapBreakLoopAndPcapLoop() {
        cntPkt = 0;
        if ((resultCode = PcapLoop(pcap, maxPkt, new PcapHandler<String>() {
            @Override
            public void nextPacket(String user, PcapPktHdr h, ByteBuffer bytes) {
            if (cntPkt == (maxPkt / 2)) {
                System.out.println("Break loop.");
                PcapBreakLoop(pcap);
            }
            System.out.println("Argument    : " + user);
            System.out.println("PacketHeader: " + h);
            System.out.println("PacketBuffer: " + bytes);
            cntPkt++;
            }
        }, "This Is User Argument")) != OK) {
            logger.warning("PcapBreakLoopAndPcapLoop:PcapLoop(): " + PcapStrError(resultCode));
            return;
        }
    }

    @Test
    public void Test15_PcapLookupDev() {
        /*String device = PcapLookupDev(errbuf);
        if (device == null) {
            logger.warning("PcapLookupDev:PcapLookupDev(): " + errbuf.toString());
            return;
        }
        System.out.println("Lookup device: " + device);*/
    }

    @Test
    public void Test16_PcapGetErr() {
        System.out.println("Pcap Error: " + PcapGetErr(pcap));
    }

    @Test
    public void Test17_PcapLibVersionPcapMajorVersionAndPcapMinorVersion() {
        System.out.println("Pcap Library Version: " + PcapLibVersion());
        System.out.println("Pcap Major Version: " + PcapMajorVersion(pcap));
        System.out.println("Pcap Minor Version: " + PcapMinorVersion(pcap));
    }

    @Test
    public void Test18_PcapIsSwapped() {
        System.out.println("Pcap is swapped: " + PcapIsSwapped(pcap));
    }

    @Test
    public void Test19_PcapSnapshot() {
        System.out.println("Snapshot: " + PcapSnapshot(pcap));
    }

    @Test
    public void Test20_PcapDataLinkValToNamePcapDataLinkValToDescriptionAndPcapDataLinkNameToVal() {
        String dataLinkName = PcapDataLinkValToName(DataLinkType.EN10MB.getValue());
        System.out.println("Data Link Name: " + dataLinkName);
        System.out.println("Data Link Description: " + PcapDataLinkValToDescription(DataLinkType.EN10MB.getValue()));
        System.out.println("Data Link Value: " + PcapDataLinkNameToVal(dataLinkName));
    }

    @Test
    public void Test21_PcapSetNonBlockPcapGetNonBlock() {
        int blocking = PcapGetNonBlock(pcap, errbuf);
        System.out.println("Blocking (Before) : " + blocking);
        System.out.println("Error Buffer      : " + errbuf.toString());
        if ((resultCode = PcapSetNonBlock(pcap, (blocking == 0) ? 1 : 0, errbuf)) != OK) {
            logger.warning("PcapSetNonBlockPcapGetNonBlock:PcapSetNonBlock(): " + errbuf.toString());
            return;
        }
        System.out.println("Blocking (After)  : " + PcapGetNonBlock(pcap, errbuf));
        System.out.println("Error Buffer      : " + errbuf.toString());
        if ((resultCode = PcapSetNonBlock(pcap, (blocking == 0) ? 0 : 1, errbuf)) != OK) {
            logger.warning("PcapSetNonBlockPcapGetNonBlock:PcapSetNonBlock(): " + errbuf.toString());
            return;
        }
        System.out.println("Blocking          : " + PcapGetNonBlock(pcap, errbuf));
        System.out.println("Error Buffer      : " + errbuf.toString());
    }

    @Test
    public void Test22_PcapCompileNoPcapSetFilterAndPcapLoop() {
        if ((resultCode = PcapCompileNoPcap(snaplen, DataLinkType.EN10MB.getValue(),
                bpfProgram, filter, optimize, maskp.toInt())) != OK) {
            logger.warning("PcapCompileNoPcapSetFilterAndPcapLoop:PcapCompileNoPcap(): " + errbuf.toString());
            return;
        }
        if ((resultCode = PcapSetFilter(pcap, bpfProgram)) != OK) {
            logger.warning("PcapCompileNoPcapSetFilterAndPcapLoop:PcapSetFilter(): " + PcapStrError(resultCode));
            return;
        }
        if ((resultCode = PcapLoop(pcap, maxPkt, callback, "This Is User Argument")) != OK) {
            logger.warning("PcapCompileNoPcapSetFilterAndPcapLoop:PcapLoop(): " + PcapStrError(resultCode));
            return;
        }
    }

    @Test
    public void Test23_PcapPError() {
        PcapPError(pcap, "Pcap Error");
    }

    @Test
    public void Test24_PcapSetBufferSize() throws Exception {
        // throw new Exception("Not implemented yet.");
    }

    @Test
    public void Test25_PcapCanSetRfMonAndPcapSetRfMon() {
        if (PcapCanSetRfMon(pcap) == 1) {
            if ((resultCode = PcapSetRfMon(pcap, 1)) != OK) {
                logger.warning("PcapCanSetRfMonAndPcapSetRfMon:PcapCanSetRfMon(): " + PcapStrError(resultCode));
                return;
            }
        }
    }

    @Test
    public void Test26_PcapSetDirection() {
        try {
            if ((resultCode = PcapSetDirection(pcap, PcapDirection.PCAP_D_IN)) != OK) {
                logger.warning("PcapSetDirection:PcapSetDirection() " + PcapStrError(resultCode));
                return;
            }
        } catch (PlatformNotSupportedException e) {
            logger.warning(e.getMessage());
        }
    }

    @Test
    public void Test27_PcapGetTStampPrecisionAndPcapSetTStampPrecision() {
        int timestamp = 0;
        try {
            timestamp = PcapGetTStampPrecision(pcap);
        } catch (PlatformNotSupportedException e) {
            logger.warning(e.getMessage());
        }
        System.out.println("Time stamp precision (before): " + timestamp);
        try {
            if ((resultCode = PcapSetTStampPrecision(pcap, (timestamp == 0 ? 1 : 0))) != OK) {
                logger.warning("Timestamp precision not supported by operation system.");
            }
        } catch (PlatformNotSupportedException e) {
            logger.warning(e.getMessage());
        }
        try {
            timestamp = PcapGetTStampPrecision(pcap);
        } catch (PlatformNotSupportedException e) {
            logger.warning(e.getMessage());
        }
        System.out.println("Time stamp precision (after) : " + timestamp);
    }

    @Test
    public void Test28_PcapSetTStampType() {
        try {
            if ((resultCode = PcapSetTStampType(pcap, 1)) != OK) {
                logger.warning("Timestamp type not supported by operation system.");
            }
        } catch (PlatformNotSupportedException e) {
            logger.warning(e.getMessage());
        }
    }

    @Test
    public void Test29_PcapListDatalinks() {
        List<Integer> datalinks = new ArrayList<Integer>();
        try {
            if ((resultCode = PcapListDataLinks(pcap, datalinks)) < 0) {
                logger.warning("PcapListDataLinks:PcapListDataLinks(): " + PcapStrError(resultCode));
                return;
            }
        } catch (PlatformNotSupportedException e) {
            logger.warning(e.getMessage());
        }
        System.out.print("DataLinks:");
        for (Integer datalink : datalinks) {
            System.out.print(" " + datalink);
        }
    }

    @Test
    public void Test30_PcapListTStampTypes() {
        List<Integer> tsTypes = new ArrayList<Integer>();
        try {
            if ((resultCode = PcapListTStampTypes(pcap, tsTypes)) < 0) {
                logger.warning("PcapListTStampTypes:PcapListTStampTypes(): " + PcapStrError(resultCode));
                return;
            }
        } catch (PlatformNotSupportedException e) {
            logger.warning(e.getMessage());
        }
        System.out.print("Time Stamp Types:");
        for (Integer tsType : tsTypes) {
            System.out.print(" " + tsType);
        }
    }

    @Test
    public void Test31_PcapTStampTypeNameToValPcapTStampTypeValToNameAndPcapTStampTypeValToDescription() {
        int tsVal = PcapTimestampType.HOST.getValue();
        String tsName = null;
        try {
            tsName = PcapTStampTypeValToName(tsVal);
        } catch (PlatformNotSupportedException e) {
            logger.warning(e.getMessage());
        }
        System.out.println("Time stamp name       : " + tsName);
        try {
            if (tsName != null) {
                System.out.println("Time stamp value      : " + PcapTStampTypeNameToVal(tsName));
            }
        } catch (PlatformNotSupportedException e) {
            logger.warning(e.getMessage());
        }
        try {
            System.out.println("Time stamp description: " + PcapTStampTypeValToDescription(tsVal));
        } catch (PlatformNotSupportedException e) {
            logger.warning(e.getMessage());
        }
    }

    @Test
    public void Test32_PcapStatusToStr() {
        int errNum = 2;
        try {
            System.out.println("Pcap Error (" + errNum + "): " + PcapStatusToStr(errNum));
        } catch (PlatformNotSupportedException e) {
            logger.warning(e.getMessage());
        }
    }

    @Test
    public void Test33_PcapOpenDeadWithTStampPrecisionAndPcapClose() {
        DataLinkType linkType = DataLinkType.LINUX_SLL;
        Pcap pcap = null;
        try {
            pcap = PcapOpenDeadWithTStampPrecision(linkType.getValue(), snaplen, precision);
        } catch (PlatformNotSupportedException e) {
            logger.warning(e.getMessage());
        }
        if (pcap == null) {
            logger.warning("PcapOpenDeadWithTStampPrecisionAndPcapClose:"
                    + "PcapOpenDeadWithTStampPrecision(): ");
            return;
        }
        PcapClose(pcap);
    }

    @Test
    public void Test34_PcapOfflineFilterPcapOpenOfflineWithTStampPrecisionAndPcapClose() {
        Pcap pcap = null;
        try {
            pcap = PcapOpenOfflineWithTStampPrecision(resourceDumpFile, precision, errbuf);
        } catch (PlatformNotSupportedException e) {
            logger.warning(e.getMessage());
        }
        if (pcap == null) {
            logger.warning("PcapOfflineFilterPcapOpenOfflineWithTStampPrecisionAndPcapClose:"
                    + "PcapOpenOfflineWithTStampPrecision(): " + errbuf.toString());
            return;
        }
        if ((resultCode = PcapCompile(pcap, bpfProgram, filter, optimize, maskp.toInt())) != OK) {
            logger.warning("PcapCompilePcapSetFilterAndPcapLoop:PcapCompile(): " + PcapStrError(resultCode));
            return;
        }
        pkt = ByteBuffer.allocateDirect(snaplen);
        if ((resultCode = PcapOfflineFilter(bpfProgram, pktHdr, pkt)) != OK) {
            logger.warning("PcapOfflineFilterPcapOpenOfflineWithTStampPrecisionAndPcapClose:"
                    + "PcapOfflineFilter");
        }
        if ((resultCode = PcapLoop(pcap, maxPkt, callback, "This Is User Argument")) < 0) {
            logger.warning("PcapOfflineFilterPcapOpenOfflineWithTStampPrecisionAndPcapClose:"
                    + "PcapLoop(): ");
        }
        PcapClose(pcap);
    }

    @Test
    public void Test35_PcapInject() {
        // Do nothing
    }

    @Test
    public void Test36_FindHardwareAddress() {
        try {
            byte[] address = Jxnet.FindHardwareAddress(source);
            if (address.length == 6) {
                System.out.println(MacAddress.valueOf(address));
            }
        } catch (DeviceNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (PlatformNotSupportedException e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * Destroy.
     */
    @After
    public void destroy() {
        if (pcap != null && !pcap.isClosed()) {
            PcapClose(pcap);
        }
        if (bpfProgram != null && !bpfProgram.isClosed()) {
            PcapFreeCode(bpfProgram);
        }
        if (dumper != null && !dumper.isClosed()) {
            PcapDumpClose(dumper);
        }
    }

    public static void destroyBuffer(Buffer buffer) {
        if(buffer.isDirect()) {
            try {
                if(!buffer.getClass().getName().equals("java.nio.DirectByteBuffer")) {
                    Field attField = buffer.getClass().getDeclaredField("att");
                    attField.setAccessible(true);
                    buffer = (Buffer) attField.get(buffer);
                }

                Method cleanerMethod = buffer.getClass().getMethod("cleaner");
                cleanerMethod.setAccessible(true);
                Object cleaner = cleanerMethod.invoke(buffer);
                Method cleanMethod = cleaner.getClass().getMethod("clean");
                cleanMethod.setAccessible(true);
                cleanMethod.invoke(cleaner);
            } catch(Exception e) {
                throw new IllegalStateException("Could not destroy direct buffer " + buffer, e);
            }
        }
    }

}
