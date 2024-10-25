package xyz.fanpool.chat_service.common.util;

import java.net.Inet4Address;
import java.net.UnknownHostException;

public class NetworkUtil {

	private final static String SERVER_IP_ADDRESS;

	static {
		try {
			SERVER_IP_ADDRESS = Inet4Address.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		}
	}

	public static String getIpAddress() {
		return SERVER_IP_ADDRESS;
	}
}
