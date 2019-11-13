package com.example.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.ShowModel;

@RestController
public class ShowController {
//	@GetMapping("/")
//	public String GetTest() {
//		// List<Show> showList = collectShowList();
//		return "Hello";
//	}

	@GetMapping("/showList")
	public List<ShowModel> showList() {
		List<ShowModel> showList = collectShowList();
		return showList;
	}
	
	@Async
	public List<ShowModel> collectShowList() {
		List<ShowModel> theaterList = new ArrayList<ShowModel>();
		// List<Show> showList = new ArrayList<Show>(); // CJYCJYCJYCJY 20191105
		try {
			int cnt = 0;
			// CJYCJYCJYCJY 20191105 --->
			Document doc = null;
			try {
				doc = Jsoup.connect("http://www.xn--vk1br5hppx9qddtd.com/ticket.do?command=performList").get();
			} catch (IOException e) {
				e.printStackTrace();
				return theaterList;
			}
			// <<--- // CJYCJYCJYCJY 20191105
			Elements tags = doc.select(".colist-img a");
			ShowModel obj = new ShowModel();
			for (int i = 0; i < tags.size(); i++) {
				Element e = tags.get(i);
				String href = e.attr("href");
				Document doc2 = Jsoup.connect("http://www.xn--vk1br5hppx9qddtd.com" + href).get();
				Elements tags3 = doc2.select(".view-title span");
				Elements tags4 = doc2.select("span.info-data");
				Elements posterimgs = doc2.select(".viewbigimg img");
				Elements storyimgs = doc2.select(".info-area ul li img");
				Element bige = posterimgs.get(0);
				Element imgt = storyimgs.get(0);
				for (int k = 0; k < tags3.size(); k++) {
					Element t = tags3.get(k);
					obj.setShowName(t.text());
					obj.setPosterImage(bige.attr("src"));
					obj.setStoryImage(imgt.attr("src"));
					for (Element r : tags4) {
						cnt += 1;
						switch (cnt) {
						case 6:
							obj.setLocation(r.text());
							break;
						case 7:
							obj.setPeriod(r.text());
							break;
						case 12:
							obj.setRunningTime(r.text());
							break;
						case 13:
							obj.setAge(r.text());
							break;
						case 14:
							obj.setCost(r.text());
							break;
						case 10:
							String genreCompare = r.text();
							if (genreCompare.indexOf("아동") != -1)
								obj.setGenre("아동/가족");
							else if (genreCompare.indexOf("뮤지컬") != -1 || genreCompare.indexOf("전시") != -1)
								obj.setGenre("콘서트/전시");
							else {
								String[] array = genreCompare.split("\\(");
								obj.setGenre(array[0]);
							}
						}
					}
					cnt = 0;
				}
				// showList.add(obj); // CJYCJYCJYCJY 20191105
				theaterList.add(obj); // CJYCJYCJYCJY 20191105
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// return showList(); // CJYCJYCJYCJY 20191105
		return theaterList; // CJYCJYCJYCJY 20191105
	}
}