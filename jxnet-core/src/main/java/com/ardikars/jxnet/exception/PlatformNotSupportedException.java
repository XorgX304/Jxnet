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

package com.ardikars.jxnet.exception;

/**
 * Platform Not Supported Exception
 *
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.1.0
 */
public class PlatformNotSupportedException extends RuntimeException {

    private static final long serialVersionUID = -5714154798477963289L;

    public PlatformNotSupportedException() {
        this("");
    }

    public PlatformNotSupportedException(final String message) {
        this(message, new RuntimeException(message));
    }

    public PlatformNotSupportedException(final Throwable cause) {
        this(cause.getMessage(), cause);
    }

    public PlatformNotSupportedException(final String message, final Throwable cause) {
        super(message, cause);
    }

}

