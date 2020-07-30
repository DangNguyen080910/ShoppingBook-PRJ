package com.shoppingbook.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.shoppingbook.entity.BillingAddress;
import com.shoppingbook.entity.Book;
import com.shoppingbook.entity.CartItem;
import com.shoppingbook.entity.Order;
import com.shoppingbook.entity.Payment;
import com.shoppingbook.entity.ShippingAddress;
import com.shoppingbook.entity.ShoppingCart;
import com.shoppingbook.entity.User;
import com.shoppingbook.repository.OrderRepository;
import com.shoppingbook.service.CartItemService;
import com.shoppingbook.service.OrderService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private CartItemService cartItemService;
	
	@Override
	public synchronized Order createOrder(ShoppingCart shoppingCart, ShippingAddress shippingAddress, BillingAddress billingAddress,
			Payment payment, String shippingMethod, User user) {
		Order order = new Order();
		order.setBillingAddress(billingAddress);
		order.setOrderStatus("created");
		order.setPayment(payment);
		order.setShippingAddress(shippingAddress);
		order.setShippingMethod(shippingMethod);
		
		List<CartItem> cartItems = cartItemService.findByShoppingCart(shoppingCart);
		for (CartItem cartItem : cartItems) {
			Book book = cartItem.getBook();
			cartItem.setOrder(order);
			book.setInStockNumber(book.getInStockNumber() - cartItem.getQty());
		}
		order.setCarttItems(cartItems);
		order.setOrderDate(Calendar.getInstance().getTime());
		order.setOrderTotal(shoppingCart.getGrandTotal());
		shippingAddress.setOrder(order);
		billingAddress.setOrder(order);
		payment.setOrder(order);
		order.setUser(user);
		order = orderRepository.save(order);
		
		return order;
	}

	@Override
	public Order findById(Long id) {
		return orderRepository.findById(id).get();
	}
	
	@Override
	public Long countOrder(boolean active) {
		return orderRepository.count(active);
	}
	
	@Override
	public List<Order> findAllOrder() {
		List<Order> orders = orderRepository.findAll();
		return orders.stream().sorted(Comparator.comparing(Order::getId).reversed()).collect(Collectors.toList());
	}
	
	@Override
	public Optional<Order> findOne(Long id) {
		return orderRepository.findById(id);
	}

	@Override
	public Order findOrderById(Long id) {
		return orderRepository.findOrderById(id);
	}
	
	@Override
	public void removeOne(long parseLong) {
		orderRepository.deleteById(parseLong);
		
	}
	

	
	
	
	private void save(Order order) {
		orderRepository.save(order);
	}
	
	@Override
	public void dlvOneOrder(Long id) {
		Order order = orderRepository.getOne(id);
		order.setActive(true);
		save(order);
	}

	

	@Override
	public void ndlvOneOrder(Long id) {
		Order order = orderRepository.getOne(id);
		order.setActive(false);
		save(order);
	}
	
	@Override
	public boolean createPdf(List<Order> orderList, ServletContext context, HttpServletRequest request,
			HttpServletResponse response) {
		Document document = new Document(PageSize.A4, 15, 15, 45, 30);
		var datePattern = new java.text.SimpleDateFormat("yyyy-MM-dd");
		try {
			String filePath = context.getRealPath("/resources/reports");
			File file = new File(filePath);
			boolean exists = new File(filePath).exists();
			if(!exists) {
				new File(filePath).mkdirs();
			}
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file+"/"+"orderList"+".pdf"));
			document.open();
			
			Font mainFont = FontFactory.getFont("Arial", 10, BaseColor.BLACK);
			
			Image image1 = Image.getInstance("src/main/resources/static/images/logo.png");
		    image1.scaleAbsolute(100, 80);
		    image1.setAlignment(Element.ALIGN_LEFT);
		    document.add(image1);
			
			Paragraph paragraph = new Paragraph("All Orders", mainFont);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph.setIndentationLeft(50);
			paragraph.setIndentationRight(50);
			paragraph.setSpacingAfter(10);
			document.add(paragraph);
			
			PdfPTable table = new PdfPTable(5);
			table.setWidthPercentage(100);
			table.setSpacingBefore(10f);
			table.setSpacingAfter(10f);
			
			Font tableHeader = FontFactory.getFont("Arial", 10, BaseColor.BLACK);
			Font tableBody = FontFactory.getFont("Arial", 9, BaseColor.BLACK);
			
			float[] columnWidths = {2f, 2f, 2f, 2f, 2f};
			table.setWidths(columnWidths);
			
			PdfPCell id = new PdfPCell(new Paragraph("ID", tableHeader));
			id.setBorderColor(BaseColor.BLACK);
			id.setPaddingLeft(10);
			id.setHorizontalAlignment(Element.ALIGN_CENTER);
			id.setVerticalAlignment(Element.ALIGN_CENTER);
			id.setBackgroundColor(BaseColor.GRAY);
			id.setExtraParagraphSpace(5f);
			table.addCell(id);
			
			PdfPCell date = new PdfPCell(new Paragraph("ORDER DATE", tableHeader));
			date.setBorderColor(BaseColor.BLACK);
			date.setPaddingLeft(10);
			date.setHorizontalAlignment(Element.ALIGN_CENTER);
			date.setVerticalAlignment(Element.ALIGN_CENTER);
			date.setBackgroundColor(BaseColor.GRAY);
			date.setExtraParagraphSpace(5f);
			table.addCell(date);
			
			PdfPCell status = new PdfPCell(new Paragraph("ORDER STATUS", tableHeader));
			status.setBorderColor(BaseColor.BLACK);
			status.setPaddingLeft(10);
			status.setHorizontalAlignment(Element.ALIGN_CENTER);
			status.setVerticalAlignment(Element.ALIGN_CENTER);
			status.setBackgroundColor(BaseColor.GRAY);
			status.setExtraParagraphSpace(5f);
			table.addCell(status);
			
			PdfPCell total = new PdfPCell(new Paragraph("TOTAL PRICE", tableHeader));
			total.setBorderColor(BaseColor.BLACK);
			total.setPaddingLeft(10);
			total.setHorizontalAlignment(Element.ALIGN_CENTER);
			total.setVerticalAlignment(Element.ALIGN_CENTER);
			total.setBackgroundColor(BaseColor.GRAY);
			total.setExtraParagraphSpace(5f);
			table.addCell(total);
			
			PdfPCell sMethod = new PdfPCell(new Paragraph("SHIPPING METHOD", tableHeader));
			sMethod.setBorderColor(BaseColor.BLACK);
			sMethod.setPaddingLeft(10);
			sMethod.setHorizontalAlignment(Element.ALIGN_CENTER);
			sMethod.setVerticalAlignment(Element.ALIGN_CENTER);
			sMethod.setBackgroundColor(BaseColor.GRAY);
			sMethod.setExtraParagraphSpace(5f);
			table.addCell(sMethod);
			
			for(Order order : orderList) {
				PdfPCell idValue = new PdfPCell(new Paragraph(String.valueOf(order.getId()), tableBody));
				idValue.setBorderColor(BaseColor.BLACK);
				idValue.setPaddingLeft(10);
				idValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				idValue.setVerticalAlignment(Element.ALIGN_CENTER);
				idValue.setBackgroundColor(BaseColor.WHITE);
				idValue.setExtraParagraphSpace(5f);
				table.addCell(idValue);
				
				PdfPCell dateValue = new PdfPCell(new Paragraph(datePattern.format(order.getOrderDate()), tableBody));
				dateValue.setBorderColor(BaseColor.BLACK);
				dateValue.setPaddingLeft(10);
				dateValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				dateValue.setVerticalAlignment(Element.ALIGN_CENTER);
				dateValue.setBackgroundColor(BaseColor.WHITE);
				dateValue.setExtraParagraphSpace(5f);
				table.addCell(dateValue);
				
				PdfPCell statusValue = new PdfPCell(new Paragraph(order.getOrderStatus(), tableBody));
				statusValue.setBorderColor(BaseColor.BLACK);
				statusValue.setPaddingLeft(10);
				statusValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				statusValue.setVerticalAlignment(Element.ALIGN_CENTER);
				statusValue.setBackgroundColor(BaseColor.WHITE);
				statusValue.setExtraParagraphSpace(5f);
				table.addCell(statusValue);
				
				PdfPCell totalValue = new PdfPCell(new Paragraph(String.valueOf(order.getOrderTotal() +"$"), tableBody));
				totalValue.setBorderColor(BaseColor.BLACK);
				totalValue.setPaddingLeft(10);
				totalValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				totalValue.setVerticalAlignment(Element.ALIGN_CENTER);
				totalValue.setBackgroundColor(BaseColor.WHITE);
				totalValue.setExtraParagraphSpace(5f);
				table.addCell(totalValue);
				
				PdfPCell sMethodValue = new PdfPCell(new Paragraph(order.getShippingMethod(), tableBody));
				sMethodValue.setBorderColor(BaseColor.BLACK);
				sMethodValue.setPaddingLeft(10);
				sMethodValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				sMethodValue.setVerticalAlignment(Element.ALIGN_CENTER);
				sMethodValue.setBackgroundColor(BaseColor.WHITE);
				sMethodValue.setExtraParagraphSpace(5f);
				table.addCell(sMethodValue);
			}
			
			
			
			document.add(table);
			document.close();
			writer.close();
			return true;
			
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean createExcel(List<Order> orderList, ServletContext context, HttpServletRequest request,
			HttpServletResponse response) {
		
		String filePath = context.getRealPath("/resources/reports"); 
		File file  = new File(filePath);
		boolean exists = new  File(filePath).exists();
		if(!exists) {
			new File(filePath).mkdirs();
		}
		try {
			FileOutputStream outputStream = new FileOutputStream(file+"/"+"orderList"+".xls");
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet workSheet = workbook.createSheet("orderList");
			workSheet.setDefaultColumnWidth(30);
			
			HSSFCellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFillForegroundColor(HSSFColor.BLUE.index);
			headerCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			
			HSSFRow headerRow = workSheet.createRow(0);
			
			HSSFCell id = headerRow.createCell(0);
			id.setCellValue("ID");
			id.setCellStyle(headerCellStyle);

			HSSFCell date = headerRow.createCell(1);
			date.setCellValue("Order Date");
			date.setCellStyle(headerCellStyle);
			
			HSSFCell status = headerRow.createCell(2);
			status.setCellValue("Order Status");
			status.setCellStyle(headerCellStyle);
			
			HSSFCell total = headerRow.createCell(3);
			total.setCellValue("Total");
			total.setCellStyle(headerCellStyle);
			
			HSSFCell sDate = headerRow.createCell(4);
			sDate.setCellValue("Shipping Method");
			sDate.setCellStyle(headerCellStyle);
			
			int i = 1;
			for(Order order : orderList) {
				HSSFRow bodyRow = workSheet.createRow(i);
				
				HSSFCellStyle bodyCellStyle = workbook.createCellStyle();
				bodyCellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
				
				HSSFCell idValue = bodyRow.createCell(0);
				idValue.setCellValue(order.getId());
				idValue.setCellStyle(bodyCellStyle);

				HSSFCell dateValue = bodyRow.createCell(1);
				dateValue.setCellValue(order.getOrderDate());
				dateValue.setCellStyle(bodyCellStyle);
				
				HSSFCell statusValue = bodyRow.createCell(2);
				statusValue.setCellValue(order.getOrderStatus());
				statusValue.setCellStyle(bodyCellStyle);
				
				HSSFCell totalValue = bodyRow.createCell(3);
				totalValue.setCellValue(String.valueOf(order.getOrderTotal() + "$"));
				totalValue.setCellStyle(bodyCellStyle);
				
				HSSFCell sDateValue = bodyRow.createCell(4);
				sDateValue.setCellValue(order.getShippingMethod());
				sDateValue.setCellStyle(bodyCellStyle);
				
				i++;
				
			}
			workbook.write(outputStream);
			outputStream.flush();
			outputStream.close();
			return 	true;
			
			
		} catch (Exception e) {			
			return false;
		}

	}
	

	@Override
	public boolean printBill(Order order, ServletContext context, HttpServletRequest request,
			HttpServletResponse response) {
		Document document = new Document(PageSize.A4, 15, 15, 45, 30);
		
		try {
			String filePath = context.getRealPath("/resources/reports");
			File file = new File(filePath);
			boolean exists = new File(filePath).exists();
			if(!exists) {
				new File(filePath).mkdirs();
			}
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file+"/"+"printbill"+".pdf"));
			document.open();
			
			Font mainFont = FontFactory.getFont("Arial", 20, BaseColor.BLACK);
			Font tableBody = FontFactory.getFont("Arial", 9, BaseColor.BLACK);
			
			Image image1 = Image.getInstance("src/main/resources/static/images/logo.png");
		    image1.scaleAbsolute(100, 80);
		    image1.setAlignment(Element.ALIGN_LEFT);
		    document.add(image1);
			
			Paragraph paragraph2 = new Paragraph("INVOICE", mainFont);
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setIndentationLeft(50);
			paragraph2.setIndentationRight(50);
			paragraph2.setSpacingAfter(10);
			document.add(paragraph2);
			
			PdfPTable table1 = new PdfPTable(2);	
			
			PdfPCell from = new PdfPCell(new Paragraph(" ", tableBody));
			from.setBorderColor(BaseColor.BLACK);
			from.setPadding(10);
			from.setHorizontalAlignment(Element.ALIGN_LEFT);
			from.setVerticalAlignment(Element.ALIGN_CENTER);
			from.setBackgroundColor(BaseColor.WHITE);
			from.setExtraParagraphSpace(5f);
			Phrase phraseFrom = new Phrase("FROM: \n G3's BOOKSTORE \n "
					+ "Serving our customers with the best. \n"
					+ "Address: 123 Cach Mang Thang 8 Q.10, TP HCM \n"
					+ "Phone : 0909 999 999 \n"
					+ "E-mail : group3t11805e0@gmail.com");
			from.addElement(phraseFrom);
			table1.addCell(from);	
			
			PdfPCell to = new PdfPCell(new Paragraph(" ", tableBody));
			to.setBorderColor(BaseColor.BLACK);
			to.setPadding(10);
			to.setHorizontalAlignment(Element.ALIGN_LEFT);
			to.setVerticalAlignment(Element.ALIGN_CENTER);
			to.setBackgroundColor(BaseColor.WHITE);
			to.setExtraParagraphSpace(5f);
			Phrase phraseTo = new Phrase("TO: \n Name: " + order.getUser().getUsername() +
					"\n Address: " + order.getShippingAddress().getShippingAddressStreet1() +", "+ 
					order.getShippingAddress().getShippingAddressCity() + ", "+ 
					order.getShippingAddress().getShippingAddressState() + ", "+ 
					order.getShippingAddress().getShippingAddressCountry() + ", "+ 
					order.getShippingAddress().getShippingAddressZipcode() + ", "+
					order.getUser().getPhone() + ". ") ;
			to.addElement(phraseTo);
			table1.addCell(to);
			
			document.add(table1);
			
			PdfPTable table2 = new PdfPTable(1);	
			
			PdfPCell content = new PdfPCell(new Paragraph(" ", tableBody));
			content.setBorderColor(BaseColor.BLACK);
			content.setPadding(10);
			content.setHorizontalAlignment(Element.ALIGN_LEFT);
			content.setVerticalAlignment(Element.ALIGN_CENTER);
			content.setBackgroundColor(BaseColor.WHITE);
			content.setExtraParagraphSpace(5f);
			Phrase phraseContent = new Phrase("Total: " + order.getOrderTotal() + "$");
			content.addElement(phraseContent);
			table2.addCell(content);	
			
			document.add(table2);
			
			document.close();
			writer.close();
			return true;
			
		} catch (Exception e) {
			return false;
		}
	}

	
//	@Override
//	public Long countOrder(boolean active) {
//		return orderRepository.count(active);
//	}

}















