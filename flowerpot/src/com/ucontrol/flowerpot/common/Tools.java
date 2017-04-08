package com.ucontrol.flowerpot.common;

import java.util.List;

public class Tools {

	public static String listToString(List<String> list) {

		if (list.size()!=0) {
			StringBuffer sb=new StringBuffer();
			int i;
			for (i = 0; i < list.size()-1; i++) {
				sb.append(""+list.get(i)+",");
				
			}
			sb.append(""+list.get(i)+"");
			return sb.toString();
		}else {
			return "";
		}

		
	}
}
