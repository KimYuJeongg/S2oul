package com.example.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.TheaterModel;

@RestController
public class TheaterController {

	@GetMapping("/theaterList")
	public List<TheaterModel> theaterList() {
		List<TheaterModel> theaterList = collectTheaterList();
		return theaterList;
	}

	public List<TheaterModel> collectTheaterList() {
		List<TheaterModel> theaterList = new ArrayList<TheaterModel>();
		try {
			int cnt = 0;
			String str = "서울";
			String utf8 = URLEncoder.encode(str, "UTF-8");
			for (int i = 0; i < 5; i++) {
				Document doc = Jsoup.connect(
						"http://www.xn--vk1br5hppx9qddtd.com/ticket.do?command=hall_board_list&pageset=10&p_retrieve_type=3&p_retrieve_key="
								+ utf8 + "&currentPage=" + (i + 1))
						.get();
				Elements tags = doc.select(".hall-name a");
				TheaterModel obj = new TheaterModel();
				for (int j = 0; j < tags.size(); j++) {
					Element e = tags.get(j);
					obj.setTheaterName(e.text());
					String href = e.attr("href");
					Document doc2 = Jsoup.connect("http://www.xn--vk1br5hppx9qddtd.com" + href).get();
					Elements tags2 = doc2.select("span.info-data");
					Elements imgs = doc2.select(".view-hallimg img");
					Element im = imgs.get(0);
					obj.setTheaterImage(im.attr("src"));
					for (Element r : tags2) {
						cnt += 1;
						switch (cnt) {
						case 3:
							obj.setLocation(r.text());
							break;
						case 4:
							obj.setPhoneNumber(r.text());
							break;
						case 6:
							String s = r.text();
							String[] array = s.split("/");
							array = array[0].split(":");
							obj.setSeatNumber(Integer.parseInt(array[1].trim()));
							break;
						}
					}
					cnt = 0;
				}
				theaterList.add(obj);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return theaterList();
	}
}
