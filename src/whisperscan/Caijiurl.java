package whisperscan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Caijiurl {
	private static ArrayList<String> GetUrl(String keyword,int pagenumber) {
	String result = "";
	BufferedReader in =null;
	String par = "<div class=\"f13\"><a target=\"_blank\" href=\"(.+?)\" class=\"c-showurl\" style=\"text-decoration:none;\">";
	ArrayList<String> stringList = new ArrayList<String>();
	try {
		String searchContent = URLEncoder.encode(keyword,"UTF-8");// 将url转码成UTF-8
		URL realUrl = new URL("http://www.baidu.com/s?ie=utf-8&mod=1&isbd=1&isid=69C31E2567F24841&ie=utf-8&f=8&rsv_bp=1&tn=baiduadv&wd="+searchContent+"&rn=50&pn=" + pagenumber);//拼接url
		URLConnection connection = realUrl.openConnection();//打开连接
		connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.155 Safari/537.36");//设置header头
		connection.connect();//连接
		
		in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
		
		String line;
		while((line = in.readLine()) != null){
			result += line; 
		}
	} catch (Exception e) {
		System.out.println("发送Get请求异常"+ e);
		e.printStackTrace();
	}finally{
		try {
			if(in != null)
				in.close();
		} catch (IOException e2) {
			System.out.println("in.close"+ e2);
			e2.printStackTrace();
		}
	}
	Pattern titlePattern = Pattern.compile(par);
	Matcher titleMatcher = titlePattern.matcher(result);
		while(titleMatcher.find()){
			stringList.add(titleMatcher.group(1));
		}
		return stringList;
	}

	private static String getRedirectUrl(String path) throws Exception {  
        HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();  
        conn.setInstanceFollowRedirects(false);  
        conn.setConnectTimeout(5000);  
        return conn.getHeaderField("Location");  
    }
	public static void main(String[] args)
	{
		String keyword = "inurl:php?id=";
		ArrayList<String> urls = GetUrl(keyword,1);
		try{
			for(String s : urls){
				System.out.println(getRedirectUrl(s));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
}
