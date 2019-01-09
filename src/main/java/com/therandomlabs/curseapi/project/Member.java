package com.therandomlabs.curseapi.project;

import java.net.MalformedURLException;
import java.net.URL;
import com.therandomlabs.curseapi.CurseForge;
import com.therandomlabs.curseapi.CurseForgeSite;
import com.therandomlabs.curseapi.cursemeta.CMAuthor;

//TODO avatar and projects
public final class Member {
	public static final Member UNKNOWN = new Member(MemberType.OWNER, "Unknown");

	private final MemberType type;
	private final String username;
	private final String urlString;
	private final URL url;

	Member(MemberType type, String username) {
		this.type = type == null ? MemberType.UNKNOWN : type;
		this.username = username;
		urlString = getURLString(username);

		URL url = null;

		try {
			url = new URL(urlString);
		} catch(MalformedURLException ignored) {}

		this.url = url;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[type=\"" + type + "\",username=\"" + username + "\"]";
	}

	public MemberType type() {
		return type;
	}

	public String username() {
		return username;
	}

	public URL url() {
		return url;
	}

	public String urlString() {
		return urlString;
	}

	public static String getURLString(String username) {
		return CurseForge.URL + "members/" + username;
	}

	public static String getURLString(CurseForgeSite site, String username) {
		return site.urlString() + "members/" + username;
	}

	static Member[] fromAuthors(CMAuthor[] authors) {
		final Member[] members = new Member[authors.length];

		for(int i = 0; i < authors.length; i++) {
			members[i] = new Member(null, authors[i].name);
		}

		return members;
	}
}
