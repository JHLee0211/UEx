package UEx.examinformation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class opicDataSearch {
	public static void main(String[] args) {
		String url = "http://www.opic.or.kr/opics/servlet/controller.opic.site.receipt.ExamReceiptServlet?p_process=select-list&p_nav=1_1";
		Document doc = null;

		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Elements element = doc.select(".pExam > table > tbody");
		
		System.out.println("======================================");
		System.out.println("======================================");
		System.out.println("======================================");
		System.out.println("======================================");
		
		Iterator<Element> ie1 = element.select("td").iterator();
		
		int idx = 0;
		while(ie1.hasNext()) {
			if(idx == 1 || idx == 3 || idx == 5) {
				ie1.next().text();
			} else {
				System.out.println(ie1.next().text());
			}
			idx++;
			idx = idx % 6;
		}
	}
}