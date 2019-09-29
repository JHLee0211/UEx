package UEx.examinformation;

import java.io.IOException;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SearchNumberTest {
	public static void main(String[] args) {
		String url = "http://www.q-net.or.kr/crf005.do?id=crf00501&gSite=Q&gId=";
		Document doc = null;

		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Elements element = doc.select("li");
		
		Iterator<Element> ie1 = element.select("a").iterator();
		
		int count = 0;
		firstloop : while(ie1.hasNext()) {
			Element temp = ie1.next();
			if(temp.hasAttr("onclick")) {
				if(temp.attr("onclick").contains("getList")) {
					String temp2[] = temp.attr("onclick").split("'");
					System.out.println(temp2[1] + " " + temp2[5]);
					
					temp2[1] = temp2[1].equals("1") ? temp2[1] + "&obligFldCd" : temp2[1] + "&examInstiCd" ; 

					url = "http://q-net.or.kr/crf005.do?id=crf00501s01&Site=Q&div="+temp2[1]+"="+temp2[5];
					
					try {
						doc = Jsoup.connect(url).get();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					Elements tempelement = doc.select("ul");
					
					Iterator<Element> tempie1 = tempelement.select("a").iterator();
					while(tempie1.hasNext()) { 
						Element temptemp = tempie1.next();
						if(temptemp.hasAttr("href")) { 
							System.out.println(temptemp.attr("href").split("'")[1]);
							System.out.println(temptemp.text().split(" ")[1]);
						}
//						count++;
//						if(count == 100)
//							break firstloop;
					}
				}
			}
		}
	}
}
