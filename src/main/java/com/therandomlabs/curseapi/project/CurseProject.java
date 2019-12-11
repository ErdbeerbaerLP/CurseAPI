package com.therandomlabs.curseapi.project;

import java.awt.image.BufferedImage;
import java.time.ZonedDateTime;
import java.util.Set;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.therandomlabs.curseapi.CurseException;
import com.therandomlabs.curseapi.file.CurseFile;
import com.therandomlabs.curseapi.file.CurseFiles;
import com.therandomlabs.curseapi.game.CurseCategory;
import com.therandomlabs.curseapi.game.CurseCategorySection;
import com.therandomlabs.curseapi.util.JsoupUtils;
import com.therandomlabs.curseapi.util.OkHttpUtils;
import okhttp3.HttpUrl;
import org.jsoup.nodes.Element;

/**
 * Represents a CurseForge project.
 * <p>
 * Implementations of this class should be effectively immutable.
 */
public abstract class CurseProject implements Comparable<CurseProject> {
	/**
	 * {@inheritDoc}
	 * <p>
	 * Calling this method is equivalent to calling {@link #id()}.
	 */
	@Override
	public final int hashCode() {
		return id();
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This method returns {@code true} if and only if the other object is also a
	 * {@link CurseProject} and the value returned by {@link #id()} is the same for both
	 * {@link CurseProject}s.
	 */
	@Override
	public final boolean equals(Object object) {
		return this == object ||
				(object instanceof CurseProject && id() == ((CurseProject) object).id());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).
				add("id", id()).
				add("name", name()).
				add("url", url()).
				toString();
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * {@link String#compareTo(String)} is used on the values returned by
	 * {@link #name()} to determine the value that this method returns.
	 */
	@Override
	public final int compareTo(CurseProject project) {
		return name().compareTo(project.name());
	}

	/**
	 * Returns this project's ID.
	 *
	 * @return this project's ID.
	 */
	public abstract int id();

	/**
	 * Returns this project's name.
	 *
	 * @return this project's name.
	 */
	public abstract String name();

	/**
	 * Returns this project's main author.
	 *
	 * @return this project's main author.
	 */
	public abstract CurseMember author();

	/**
	 * Returns this project's authors.
	 *
	 * @return a mutable {@link Set} that contains this project's authors.
	 */
	public abstract Set<CurseMember> authors();

	/**
	 * Returns the URL to this project's avatar.
	 *
	 * @return the URL to this project's avatar.
	 */
	public abstract HttpUrl avatarURL();

	/**
	 * Returns the URL to this project's avatar thumbnail.
	 *
	 * @return the URL to this project's avatar thumbnail.
	 */
	public abstract HttpUrl avatarThumbnailURL();

	/**
	 * Reads a {@link BufferedImage} from the URL returned by {@link #avatarURL()}.
	 *
	 * @return this project's avatar as a {@link BufferedImage}.
	 * @throws CurseException if an error occurs.
	 */
	public BufferedImage avatar() throws CurseException {
		return OkHttpUtils.readImage(avatarURL());
	}

	/**
	 * Reads a {@link BufferedImage} from the URL returned by {@link #avatarThumbnailURL()}.
	 *
	 * @return this project's avatar thumbnail as a {@link BufferedImage}.
	 * @throws CurseException if an error occurs.
	 */
	public BufferedImage avatarThumbnail() throws CurseException {
		return OkHttpUtils.readImage(avatarThumbnailURL());
	}

	/**
	 * Returns this project's URL.
	 *
	 * @return this project's URL.
	 */
	public abstract HttpUrl url();

	/**
	 * Returns the ID of the game that this project belongs in.
	 *
	 * @return the ID of the game that this project belongs in.
	 */
	public abstract int gameID();

	/**
	 * Returns this project's summary.
	 *
	 * @return this project's summary.
	 */
	public abstract String summary();

	/**
	 * Returns this project's description as an {@link Element}.
	 *
	 * @return this project's description as an {@link Element}.
	 * @throws CurseException if an error occurs.
	 */
	public abstract Element description() throws CurseException;

	/**
	 * Returns this project's description as plain text.
	 *
	 * @return this project's description as plain text as returned by
	 * {@link JsoupUtils#getPlainText(Element, int)}.
	 * @throws CurseException if an error occurs.
	 */
	public String descriptionPlainText() throws CurseException {
		return descriptionPlainText(Integer.MAX_VALUE);
	}

	/**
	 * Returns this project's description as plain text.
	 *
	 * @param maxLineLength the maximum length of a line. This value is used for word wrapping.
	 * @return this project's description as plain text as returned by
	 * {@link JsoupUtils#getPlainText(Element, int)}.
	 * @throws CurseException if an error occurs.
	 */
	public String descriptionPlainText(int maxLineLength) throws CurseException {
		Preconditions.checkArgument(maxLineLength > 0, "maxLineLength should be greater than 0");
		return JsoupUtils.getPlainText(description(), maxLineLength);
	}

	/**
	 * Returns this project's download count.
	 *
	 * @return this project's download count.
	 */
	public abstract int downloadCount();

	/**
	 * Returns a {@link CurseFiles} instance for this project.
	 *
	 * @return a {@link CurseFiles} instance for this project.
	 * @throws CurseException if an error occurs.
	 */
	public abstract CurseFiles<CurseFile> files() throws CurseException;

	/**
	 * Returns this project's primary category.
	 *
	 * @return this project's primary category.
	 */
	public abstract CurseCategory primaryCategory();

	/**
	 * Returns this project's categories.
	 *
	 * @return a mutable {@link Set} that contains this project's categories.
	 */
	public abstract Set<CurseCategory> categories();

	/**
	 * Returns this project's category section.
	 *
	 * @return this project's category section.
	 */
	public abstract CurseCategorySection categorySection();

	/**
	 * Returns this project's slug.
	 *
	 * @return this project's slug.
	 */
	public abstract String slug();

	/**
	 * Returns this project's creation time.
	 *
	 * @return a {@link ZonedDateTime} instance that represents this project's creation time.
	 */
	public abstract ZonedDateTime creationTime();

	/**
	 * Returns this project's last update time.
	 *
	 * @return a {@link ZonedDateTime} instance that represents this project's last update time.
	 */
	public abstract ZonedDateTime lastUpdateTime();

	/**
	 * Returns this project's last modification time.
	 *
	 * @return a {@link ZonedDateTime} instance that represents this project's last modification
	 * time.
	 */
	public abstract ZonedDateTime lastModificationTime();

	/**
	 * Returns whether this project is experimental.
	 *
	 * @return {@code true} if this project is experimental, or otherwise {@code false}.
	 */
	public abstract boolean experimental();
}
