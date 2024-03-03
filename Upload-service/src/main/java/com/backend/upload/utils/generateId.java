package com.backend.upload.utils;

import java.util.Random;

public class generateId {

	final static String rawString = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	final static int length = 5;

	public static String generateRandomId() {

		Random random = new Random();
		StringBuilder sb = new StringBuilder(length);

		for (int i = 1; i <= 6; i++) {
			int randomIndex = random.nextInt(rawString.length());
			sb.append(rawString.charAt(randomIndex));

		}

		return sb.toString();
	}

}
