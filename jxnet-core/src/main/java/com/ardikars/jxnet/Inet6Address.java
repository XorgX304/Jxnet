/**
 * Copyright (C) 2017-2018  Ardika Rommy Sanjaya <contact@ardikars.com>
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

import com.ardikars.common.util.Validate;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents an Internet Protocol version 6 (IPv6) address.
 * @author Ardika Rommy Sanjaya
 * @since 1.0.0
 */
public final class Inet6Address extends InetAddress {

	/**
	 * Zero IPv6 Address.
	 */
	public static final Inet6Address ZERO = valueOf(new byte[] {
			(byte) 0x0, (byte) 0x0, (byte) 0x0, (byte) 0x0,
			(byte) 0x0, (byte) 0x0, (byte) 0x0, (byte) 0x0,
			(byte) 0x0, (byte) 0x0, (byte) 0x0, (byte) 0x0,
			(byte) 0x0, (byte) 0x0, (byte) 0x0, (byte) 0x0,
	});

	/**
	 * IPv6 Loopback Address (::1).
	 */
	@SuppressWarnings("PMD.AvoidUsingHardCodedIP")
	public static final Inet6Address LOCALHOST = valueOf("::1");

	/**
	 * IPv6 Address Length.
	 */
	public static final int IPV6_ADDRESS_LENGTH = 16;
	
	{
		address = new byte[Inet6Address.IPV6_ADDRESS_LENGTH];
	}

	private Inet6Address() {
		super();
	}

	private Inet6Address(final byte[] address) {
		super();
		Validate.nullPointer(address);
		Validate.notIllegalArgument(address.length == IPV6_ADDRESS_LENGTH);
		System.arraycopy(address, 0, this.address, 0, address.length);
	}

	/**
	 * Create Inet6Address instance.
	 * @param address ipv6 bytes address.
	 * @return returns Inet6Address instance.
	 */
	public static Inet6Address valueOf(final byte[] address) {
		return new Inet6Address(address);
	}

	/**
	 * Create Inet6Address instance.
	 * @param inet6Address ipv6 string address.
	 * @return returns Inet6Address instance.
	 */
	public static Inet6Address valueOf(String inet6Address) {

		inet6Address = Validate.nullPointer(inet6Address, "::");

		final int ipv6MaxHexGroups = 8;
		final int ipv6MaxHexDigitsPerGroup = 4;

		final boolean containsCompressedZeroes = inet6Address.contains("::");
		Validate.notIllegalArgument(!(containsCompressedZeroes && (inet6Address.indexOf("::") != inet6Address.lastIndexOf("::"))));
		Validate.notIllegalArgument(!(inet6Address.charAt(0) == ':' && !inet6Address.startsWith("::")
				|| (inet6Address.endsWith(":") && !inet6Address.endsWith("::"))));
		String[] parts = inet6Address.split(":");
		if (containsCompressedZeroes) {
			final List<String> partsAsList = new ArrayList<String>(Arrays.asList(parts));
			if (inet6Address.endsWith("::")) {
				partsAsList.add("");
			} else if (inet6Address.startsWith("::") && !partsAsList.isEmpty()) {
				partsAsList.remove(0);
			}
			parts = partsAsList.toArray(new String[partsAsList.size()]);
		}
		Validate.notIllegalArgument(!(parts.length > ipv6MaxHexGroups/*&& parts.length < 3*/));
		int validOctets = 0;
		int emptyOctets = 0;
		for (int index = 0; index < parts.length; index++) {
			final String octet = parts[index];
			if (octet.length() == 0) {
				emptyOctets++;
				Validate.notIllegalArgument(!(emptyOctets > 1));
			} else {
				emptyOctets = 0;
				if (index == parts.length - 1 && octet.contains(".")) {
					byte[] quad;
					quad = Inet4Address.valueOf(octet).toBytes();
					final String initialPart = inet6Address.substring(0, inet6Address.lastIndexOf(':') + 1);
					final String penultimate = Integer.toHexString(((quad[0] & 0xff) << 8) | (quad[1] & 0xff));
					final String ultimate = Integer.toHexString(((quad[2] & 0xff) << 8) | (quad[3] & 0xff));
					inet6Address = initialPart + penultimate + ultimate;
					validOctets += 2;
					continue;
				}
				Validate.notIllegalArgument(!(octet.length() > ipv6MaxHexDigitsPerGroup));
			}
			validOctets++;
		}
		Validate.notIllegalArgument(!(validOctets > ipv6MaxHexGroups || (validOctets < ipv6MaxHexGroups && !containsCompressedZeroes)));
		parts = inet6Address.split(":", 8 + 2);
		Validate.notIllegalArgument(!(parts.length < 3 || parts.length > 8 + 1));
		int skipIndex = -1;
		for (int i = 1; i < parts.length - 1; i++) {
			if (parts[i].length() == 0) {
				Validate.notIllegalArgument(!(skipIndex >= 0));
				skipIndex = i;
			}
		}
		int partsHi;
		int partsLo;
		if (skipIndex >= 0) {
			partsHi = skipIndex;
			partsLo = parts.length - skipIndex - 1;
			Validate.notIllegalArgument(!(parts[0].length() == 0 && --partsHi != 0));
			Validate.notIllegalArgument(!(parts[parts.length - 1].length() == 0 && --partsLo != 0));
		} else {
			partsHi = parts.length;
			partsLo = 0;
		}
		final int partsSkipped = 8 - (partsHi + partsLo);
		Validate.notIllegalArgument((skipIndex >= 0 ? partsSkipped >= 1 : partsSkipped == 0));
		final ByteBuffer rawBytes = ByteBuffer.allocate(2 * 8);
		try {
			for (int i = 0; i < partsHi; i++) {
				rawBytes.putShort(parseHextet(parts[i]));
			}
			for (int i = 0; i < partsSkipped; i++) {
				rawBytes.putShort((short) 0);
			}
			for (int i = partsLo; i > 0; i--) {
				rawBytes.putShort(parseHextet(parts[parts.length - i]));
			}
		} catch (NumberFormatException ex) {
			return null;
		}
		return new Inet6Address(rawBytes.array());
	}

	/**
	 * Returns bytes address of Inet6Address.
	 * @return returns ipv6 bytes address.
	 */
	public byte[] toBytes() {
		return Arrays.copyOf(this.address, this.address.length);
	}

	private long toLong() {
		final ByteBuffer bb = ByteBuffer.allocate(this.address.length);
		bb.put(this.address);
		return bb.getLong();
	}

	private static short parseHextet(final String ipPart) {
		final int hextet = Integer.parseInt(ipPart, 16);
		if (hextet > 0xffff) {
			throw new NumberFormatException();
		}
		return (short) hextet;
	}

	/**
	 * Change value of Inet6Address.
	 * @param inet6address Inet6Address.
	 */
	public void update(final Inet6Address inet6address) {
		Validate.nullPointer(inet6address);
		this.address = inet6address.toBytes();
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final Inet6Address that = (Inet6Address) o;

		return Arrays.equals(this.address, that.address);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(this.address);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		Inet6Address inet6Address = (Inet6Address) super.clone();
		return inet6Address;
	}

	/**
	 * Returns ipv6 string of Inet6Address.
	 * @return returns ipv6 string address.
	 */
	public String toString() {
		int cmprHextet = -1;
		int cmprSize = 0;
		for (int hextet = 0; hextet < 7;) {
			int curByte = hextet * 2;
			int size = 0;
			while (curByte < this.address.length && this.address[curByte] == 0
					&& this.address[curByte + 1] == 0) {
				curByte += 2;
				size++;
			}
			if (size > cmprSize) {
				cmprHextet = hextet;
				cmprSize = size;
			}
			hextet = (curByte / 2) + 1;
		}
		final StringBuilder sb = new StringBuilder(39);
		if (cmprHextet == -1 || cmprSize < 2) {
			ipv6toStr(sb, this.address, 0, 8);
			return sb.toString();
		}
		ipv6toStr(sb, this.address, 0, cmprHextet);
		sb.append(new char[]{':', ':'});
		ipv6toStr(sb, this.address, cmprHextet + cmprSize, 8);
		return sb.toString();
	}
	
	private static void ipv6toStr(final StringBuilder sb, final byte[] src,
	                                    final int fromHextet, final int toHextet) {
		for (int i = fromHextet; i < toHextet; i++) {
			sb.append(Integer.toHexString(((src[i << 1] << 8) & 0xff00)
					| (src[(i << 1) + 1] & 0xff)));
			if (i < toHextet - 1) {
				sb.append(':');
			}
		}
	}

}
