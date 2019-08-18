package com.therandomlabs.curseapi;

import com.google.gson.annotations.SerializedName;

public enum RelationType {
	@SerializedName("All Types")
	ALL_TYPES("All Types"),
	@SerializedName("Embedded")
	EMBEDDED_LIBRARY("Embedded Library"),
	@SerializedName("Optional")
	OPTIONAL_DEPENDENCY("Optional Dependency"),
	@SerializedName("Required")
	REQUIRED_LIBRARY("Required Dependency"),
	@SerializedName("Tool")
	TOOL("Tool"),
	@SerializedName("Incompatible")
	INCOMPATIBLE("Incompatible"),
	@SerializedName("Include")
	INCLUDE("Include"),
	@SerializedName("Unknown")
	UNKNOWN("Unknown");

	private final String name;

	RelationType(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	public static RelationType fromName(String name) {
		for(RelationType type : values()) {
			if(type.name.equalsIgnoreCase(name)) {
				return type;
			}
		}

		return UNKNOWN;
	}
}
