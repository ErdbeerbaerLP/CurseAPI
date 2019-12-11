package com.therandomlabs.curseapi.util;

import java.io.IOException;
import java.nio.file.Path;

import com.squareup.moshi.Moshi;
import com.therandomlabs.curseapi.CurseException;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/**
 * Contains utility methods for working with Moshi.
 */
public final class MoshiUtils {
	/**
	 * A {@link Moshi} instance with adapters for {@link org.jsoup.nodes.Element}s,
	 * {@link okhttp3.HttpUrl}s and {@link java.time.ZonedDateTime}s.
	 */
	public static final Moshi MOSHI = new Moshi.Builder().
			add(ElementAdapter.INSTANCE).
			add(HttpUrlAdapter.INSTANCE).
			add(ZonedDateTimeAdapter.INSTANCE).
			build();

	private MoshiUtils() {}

	/**
	 * Parses the specified JSON string.
	 *
	 * @param json a JSON string.
	 * @param type the {@link Class} of the type.
	 * @param <T> the type.
	 * @return an object with the specified type.
	 * @throws CurseException if an error occurs.
	 */
	public static <T> T fromJSON(String json, Class<T> type) throws CurseException {
		try {
			return MOSHI.adapter(type).fromJson(json);
		} catch (IOException ex) {
			throw new CurseException(ex);
		}
	}

	/**
	 * Parses the specified JSON file.
	 *
	 * @param json a {@link Path} to a JSON file.
	 * @param type the {@link Class} of the type.
	 * @param <T> the type.
	 * @return an object with the specified type.
	 * @throws CurseException if an error occurs.
	 */
	public static <T> T fromJSON(Path json, Class<T> type) throws CurseException {
		try (BufferedSource source = Okio.buffer(Okio.source(json))) {
			return fromJSON(source.readUtf8(), type);
		} catch (IOException ex) {
			throw new CurseException(ex);
		}
	}

	/**
	 * Converts the specified value to a JSON string.
	 *
	 * @param value a value.
	 * @param type the {@link Class} of the type.
	 * @param <T> the type.
	 * @return a JSON string.
	 */
	public static <T> String toJSON(T value, Class<T> type) {
		return MOSHI.adapter(type).toJson(value);
	}

	/**
	 * Converts the specified value to a JSON string and writes it to the specified {@link Path}.
	 *
	 * @param value a value.
	 * @param type the {@link Class} of the type.
	 * @param path a {@link Path}.
	 * @param <T> the type.
	 * @throws CurseException if an I/O error occurs.
	 */
	public static <T> void toJSON(T value, Class<T> type, Path path) throws CurseException {
		try (BufferedSink sink = Okio.buffer(Okio.sink(path))) {
			MOSHI.adapter(type).toJson(sink, value);
		} catch (IOException ex) {
			throw new CurseException(ex);
		}
	}
}
