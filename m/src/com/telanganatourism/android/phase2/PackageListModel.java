package com.telanganatourism.android.phase2;

public class PackageListModel {
	
	private  String PackageName="";
    private  String PackageType="";
    private  String PackageAvailability="";

	public String getPackageName() {
		return PackageName;
	}

	public void setPackageName(String packageName) {
		PackageName = packageName;
	}

	public String getPackageType() {
		return PackageType;
	}

	public void setPackageType(String packageType) {
		PackageType = packageType;
	}

	public String getPackageAvailability() {
		return PackageAvailability;
	}

	public void setPackageAvailability(String packageAvailability) {
		PackageAvailability = packageAvailability;
	}
}
