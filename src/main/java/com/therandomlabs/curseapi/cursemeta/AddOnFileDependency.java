package com.therandomlabs.curseapi.cursemeta;

import java.io.Serializable;
import com.therandomlabs.curseapi.project.RelationType;

public class AddOnFileDependency implements Cloneable, Serializable {
	private static final long serialVersionUID = 5846432969453933849L;

	public int AddOnId;
	public RelationType Type;

	@Override
	public AddOnFileDependency clone() {
		try {
			final AddOnFileDependency dependency = (AddOnFileDependency) super.clone();

			dependency.AddOnId = AddOnId;
			dependency.Type = Type;

			return dependency;
		} catch(CloneNotSupportedException ignored) {}

		return null;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[id=" + AddOnId + ",type=" + Type + "]";
	}
}