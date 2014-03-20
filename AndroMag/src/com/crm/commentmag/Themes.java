package com.crm.commentmag;


import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;





public class Themes {
	
	public enum Theme {Jardin,Musique,Maison,TV}

	
	public static void writeToParcel(Parcel dest, List<Theme> flags) {
		List<String> themesStrings = new ArrayList<String>();
		for (Theme theme : flags) {
			themesStrings.add(theme.name());
		}
		dest.writeList(themesStrings);
	}

	public static List<Theme> readFromParcel(Parcel in) {
		List<Theme> themes = new ArrayList<Theme>();
		List<String> themeStrings = new ArrayList<String>();
		in.readList(themeStrings, null);
		for (String themeS : themeStrings) {
			themes.add(Theme.valueOf(themeS));
		}
		return themes;
		
	}
}


