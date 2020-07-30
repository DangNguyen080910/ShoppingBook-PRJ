package com.shoppingbook.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VNConstants {
	public final static String VN = "VN";

	private final static Map<String, String> mapOfVNProvince = new HashMap<String, String>(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("AG", "An Giang");
			put("BRVT", "Bà Rịa - Vũng Tàu");
			put("BG", "Bắc Giang");
			put("BK", "Bắc Kạn");
			put("BL", "Bạc Liêu");
			put("BN", "Bắc Ninh");
			put("BT", "Bến Tre");
			put("BĐ", "Bình Định");
			put("BD", "Bình Dương");
			put("BP", "Bình Phước");
			put("BT", "Bình Thuận");
			put("CM", "Cà Mau");
			put("CB", "Cao Bằng");
			put("ĐL", "Đắk Lắk");
			put("ĐN", "Đắk Nông");
			put("ĐB", "Điện Biên");
			put("ĐN", "Đồng Nai");
			put("ĐT", "Đồng Tháp");
			put("GL", "Gia Lai");
			put("HG", "Hà Giang");
			put("HN", "Hà Nam");
			put("HT", "Hà Tĩnh");
			put("HD", "Hải Dương");
			put("HG", "Hậu Giang");
			put("HB", "Hòa Bình");
			put("HY", "Hưng Yên");
			put("KH", "Khánh Hòa");
			put("KG", "Kiên Giang");
			put("KT", "Kon Tum");
			put("LC", "Lai Châu");
			put("LĐ", "Lâm Đồng");
			put("LS", "Lạng Sơn");
			put("LC", "Lào Cai");
			put("LA", "Long An");
			put("NĐ", "Nam Định");
			put("NA", "Nghệ An");
			put("NB", "Ninh Bình");
			put("NT", "Ninh Thuận");
			put("PT", "Phú Thọ");
			put("QB", "Quảng Bình");
			put("ST", "Sóc Trăng");
			put("TTH", "Thừa Thiên Huế");
			put("TG", "Tiền Giang");
			put("TV", "Trà Vinh");
			put("VL", "Vĩnh Long");
			put("CT", "Cần Thơ");
			put("ĐN", "Đà Nẵng");
			put("HP", "Hải Phòng");
			put("HN", "Hà Nội");
			put("TP HCM", "TP Hồ Chí Minh");
		}
	};
	public final static List<String> listOfVNStatesCode = new ArrayList<>(mapOfVNProvince.keySet());
	public final static List<String> listOfVNStatesName = new ArrayList<>(mapOfVNProvince.values());
}









































