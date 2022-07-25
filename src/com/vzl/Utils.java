package com.vzl;

public class Utils {
	public static String intToHexString(int a) {
		return String.format("%02X", a);
	}
	
	public static int to16bit (int hi, int lo) {
		return ((hi << 8) + lo);
	}
}
