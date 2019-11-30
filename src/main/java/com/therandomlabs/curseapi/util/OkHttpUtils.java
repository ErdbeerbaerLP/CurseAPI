package com.therandomlabs.curseapi.util;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.therandomlabs.curseapi.CurseException;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public final class OkHttpUtils {
	private static final OkHttpClient client = new OkHttpClient();

	private OkHttpUtils() {}

	public static BufferedImage readImage(HttpUrl url) throws CurseException {
		try {
			final Request request = new Request.Builder().url(url).build();
			return ImageIO.read(client.newCall(request).execute().body().byteStream());
		} catch (IOException ex) {
			throw new CurseException(ex);
		}
	}
}
