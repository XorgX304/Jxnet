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

package com.ardikars.jxnet.spring.boot.autoconfigure.jxpacket;

import static com.ardikars.jxnet.spring.boot.autoconfigure.constant.JxnetObjectName.DATALINK_TYPE_BEAN_NAME;
import static com.ardikars.jxnet.spring.boot.autoconfigure.constant.JxnetObjectName.EXECUTOR_SERVICE_BEAN_NAME;
import static com.ardikars.jxnet.spring.boot.autoconfigure.constant.JxnetObjectName.JXPACKET_HANDLER_CONFIGURATION_BEAN_NAME;

import com.ardikars.common.logging.Logger;
import com.ardikars.common.logging.LoggerFactory;
import com.ardikars.common.tuple.Pair;
import com.ardikars.common.tuple.Tuple;
import com.ardikars.jxnet.DataLinkType;
import com.ardikars.jxnet.PcapHandler;
import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxnet.spring.boot.autoconfigure.JxpacketHandler;
import com.ardikars.jxpacket.common.Packet;
import com.ardikars.jxpacket.common.UnknownPacket;
import com.ardikars.jxpacket.core.ethernet.Ethernet;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.nio.ByteBuffer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

/**
 * Jxpacket handler.
 *
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.4.9
 */
@ConditionalOnClass({Packet.class, ByteBuf.class})
@Configuration(JXPACKET_HANDLER_CONFIGURATION_BEAN_NAME)
public class JxpacketHandlerConfiguration<T> implements PcapHandler<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JxpacketHandlerConfiguration.class);

    private final int rawDataLinkType;
    private final JxpacketHandler<T> packetHandler;
    private final ExecutorService executorService;

    /**
     *
     * @param executorService thread pool.
     * @param dataLinkType datalink type.
     * @param packetHandler callback function.
     */
    public JxpacketHandlerConfiguration(@Qualifier(EXECUTOR_SERVICE_BEAN_NAME) ExecutorService executorService,
                                        @Qualifier(DATALINK_TYPE_BEAN_NAME) DataLinkType dataLinkType,
                                        JxpacketHandler<T> packetHandler) {
        this.rawDataLinkType = dataLinkType != null ? dataLinkType.getValue() : 1;
        this.packetHandler = packetHandler;
        this.executorService = executorService;
    }

    @Override
    public void nextPacket(final T user, final PcapPktHdr h, final ByteBuffer bytes) {
        Future<Pair<PcapPktHdr, Packet>> packet = executorService.submit(new Callable<Pair<PcapPktHdr, Packet>>() {
            @Override
            public Pair<PcapPktHdr, Packet> call() throws Exception {
                ByteBuf buffer = Unpooled.wrappedBuffer(bytes);
                Packet packet;
                if (rawDataLinkType == 1) {
                    packet = Ethernet.newPacket(buffer);
                } else {
                    packet = UnknownPacket.newPacket(buffer);
                }
                return Tuple.of(h, packet);
            }
        });
        try {
            packetHandler.next(user, packet);
        } catch (ExecutionException | InterruptedException e) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(e.getMessage());
            }
        }
    }

}
