package util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.R.array;
import android.R.integer;
import android.util.Log;

public class StringUtil {   
    public static String getStringNoBlank(String str) {   
        if(str!=null && !"".equals(str)) {   
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");   
            Matcher m = p.matcher(str);   
            String strNoBlank = m.replaceAll("");   
            return strNoBlank;   
        }else {   
            return str;   
        }        
    } 
    
	/**
	 * �жϸ����ַ����Ƿ�հ״���
	 * �հ״���ָ�ɿո��Ʊ�����س��������з���ɵ��ַ���
	 * �������ַ���Ϊnull����ַ���������true
	 * @param input
	 * @return boolean
	 */
	public static boolean isEmpty( String input ) 
	{
		if ( input == null || "".equals( input ) )
			return true;
		
		for ( int i = 0; i < input.length(); i++ ) 
		{
			char c = input.charAt( i );
			if ( c != ' ' && c != '\t' && c != '\r' && c != '\n' )
			{
				return false;
			}
		}
		return true;
	}
	
	/**
	   * �ָ��ַ���
	   * @param str String ԭʼ�ַ���
	   * @param splitsign String �ָ���
	   * @return String[] �ָ����ַ�������
	   */
	@SuppressWarnings("unchecked")
	public static String[] split(String str, String splitsign) {
	    int index;
	    if (str == null || splitsign == null)
	      return null;
	    ArrayList al = new ArrayList();
	    while ((index = str.indexOf(splitsign)) != -1) {
	      al.add(str.substring(0, index));
	      str = str.substring(index + splitsign.length());
	    }
	    al.add(str);
	    return (String[]) al.toArray(new String[0]);
	}
	
	
	/**
	 *��Wordpress <img src="xxx"/>�л�ȡͼƬ·��
	 *@param url
	 */
	public static List<String> paserPicturePath(String url){
		List<String> result = new ArrayList<String>();
		if(url!=null&&(!"".equals(url))){
			int index=0;
			while((index=url.indexOf("src=\"")) != -1){
				String str = null;
				str = url.substring(index + "src=\"".length());
				Log.d("str",str);
				int index2 = str.indexOf("\"");
				String str2 = str.substring(0,index2);
				str2=str2.replace("localhost", "192.168.1.100");
				Log.d("str2",str2);
				
				result.add(str2);
				url=str.substring(index2+"\"".length());
				Log.d("url",url);
			}
		}
		return result;
	}
} 