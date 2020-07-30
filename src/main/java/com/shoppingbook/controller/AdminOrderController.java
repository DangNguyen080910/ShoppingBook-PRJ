package com.shoppingbook.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shoppingbook.entity.Order;
import com.shoppingbook.service.OrderService;

@Controller
public class AdminOrderController {
	
	@Autowired
	private OrderService orderService;
	@Autowired private ServletContext context;
	
	@GetMapping("/admin/orderList")
	public String orderList(Model model) {
		List<Order> orderList = orderService.findAllOrder();
		model.addAttribute("orderList", orderList);
		return "admin/orderList";
	}

	@GetMapping("admin/order/info")
	public String orderInfo(@RequestParam("id") Long id, Model model) {
		orderService.findOne(id).ifPresent(order -> model.addAttribute("order", order));
		return "admin/orderinfo";
	}
	@PostMapping("/admin/order/remove")
	@ResponseBody
	public String removeOrder(@RequestParam("idorder") int id, Model model) {
		orderService.removeOne(id);
		return "ok";
	}
	
	@PostMapping("/admin/order/delivered")
	@ResponseBody
	public String dlvOrder(@RequestParam("idorder") Long id, Model model) {
		orderService.dlvOneOrder(id);
		return "ok";
	}
	
	@PostMapping("/admin/order/notyet")
	@ResponseBody
	public String ndlvOrder(@RequestParam("idorder") Long id, Model model) {
		orderService.ndlvOneOrder(id);
		return "ok";
	}
	
	@GetMapping("/createPdf")
	public void createPdf(HttpServletRequest request, HttpServletResponse response) {
		List<Order> orderList = orderService.findAllOrder();
		boolean isFlag = orderService.createPdf(orderList, context, request, response);
		if(isFlag) {
			String fullPath = request.getServletContext().getRealPath("/resources/reports/"+"orderList"+".pdf");
			fileDownload(fullPath, response, "orderList.pdf");
		}		
	}
	
	@GetMapping("/createExcel")
	public void createExcel(HttpServletRequest request, HttpServletResponse response) {
		List<Order> orderList = orderService.findAllOrder();
		boolean isFlag = orderService.createExcel(orderList, context, request, response);
		if(isFlag) {
			String fullPath = request.getServletContext().getRealPath("/resources/reports/"+"orderList"+".xls");
			fileDownload(fullPath, response, "orderList.xls");
		}	
	}
	
	@GetMapping("/admin/order/printbill")
	public void printBill(@RequestParam("id") Long id, HttpServletRequest request, HttpServletResponse response,
			Order order){
		order = orderService.findById(id);
		boolean isFlag = orderService.printBill(order, context, request, response);
		if(isFlag) {
			String fullPath = request.getServletContext().getRealPath("/resources/reports/"+"printbill"+".pdf");
			fileDownload(fullPath, response, "printbill.pdf");
		}else {
			System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAa");
		}		
	}
	

	private void fileDownload(String fullPath, HttpServletResponse response, String fileName) {
		File file = new File(fullPath);
		final int BUFFER_SIZE = 4096;
		if(file.exists()) {
			try {
				FileInputStream inputStream =  new FileInputStream(file);
				String mimeType = context.getMimeType(fullPath);
				response.setContentType(mimeType);
				response.setHeader("content-disposition", "attachment; filename="+ fileName);
				OutputStream outputStream = response.getOutputStream();
				byte[] buffer = new byte[BUFFER_SIZE];
				int bytesRead = -1;
				while((bytesRead = inputStream.read(buffer))!= 	-1) {
					outputStream.write(buffer, 0, bytesRead);
				}
				inputStream.close();
				outputStream.close();
				file.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}




















