package com.pizza73.webapp.action.admin;

import java.text.ParseException;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.pizza73.model.DailySales;
import com.pizza73.model.Shop;
import com.pizza73.service.LookupManager;
import com.pizza73.webapp.action.admin.attribute.CommonAttributesInDailySalesReporting;
import com.pizza73.webapp.util.DateParser;

@Controller
@RequestMapping("/dailySalesExport.html")
public class ExportDailySalesController extends CommonAttributesInDailySalesReporting{
	@Autowired
	private LookupManager lookupManager;

	@RequestMapping(method=RequestMethod.GET)
	public String getPage(HttpServletRequest request,ModelMap model) throws ParseException{
	      HttpSession session = request.getSession();
	      Shop shop = (Shop) session.getAttribute("DAILYSALES_SHOP");
	      Calendar requestDate=DateParser.parseTrimedDate(request.getParameter("date"));
	      DailySales dailySales=this.lookupManager.dailySalesForShopAndDate(shop.getId(), requestDate);
	      model.addAttribute("dailySales", dailySales);
	      return "exportDailySalesView"; 
	}
}
