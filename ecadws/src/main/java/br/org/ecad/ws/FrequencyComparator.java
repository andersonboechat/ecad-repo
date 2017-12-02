package br.org.ecad.ws;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FrequencyComparator implements Comparator<String> {

	private List<String> strings;
	
	public FrequencyComparator(List<String> strings) {
		this.strings = strings;
	}
	
	@Override
	public int compare(String str1, String str2) {
		return Collections.frequency(strings, str1) > Collections.frequency(strings, str2) ? 1 
					: Collections.frequency(strings, str1) < Collections.frequency(strings, str2) ? -1 : 0;
	}

}
