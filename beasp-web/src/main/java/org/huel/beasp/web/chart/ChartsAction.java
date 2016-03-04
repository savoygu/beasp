package org.huel.beasp.web.chart;

import java.util.List;

import org.huel.beasp.entity.book.Book;
import org.huel.beasp.service.book.BookService;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 报表控制层
 * @author 001
 *
 */
@Controller @RequestMapping(value="/admin/book")
public class ChartsAction {
	@Autowired private BookService bookService;
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value="/chart",method=RequestMethod.GET)
	public String inputBarChart() {
		return "admin/book/chart/chart";
	}
	
	/**
	 * 结果视图-柱状图
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/chart/bar",method=RequestMethod.GET)
	public String getBarChart(ModelMap modelMap) {
         //浏览量统计
		 DefaultCategoryDataset dataset = new DefaultCategoryDataset();  
         List<Book> books = bookService.getTop10ByOrderByPraiseDesc();
         for(Book book : books) {
       	   dataset.addValue(book.getBrowse(), book.getUser().getUserName(), book.getName());
         } 
         modelMap.addAttribute(IChart.CHARTTYPE,IChart.BAR);  
         modelMap.addAttribute(IChart.CHARDATASET,dataset);  
         return "webanalytics.chart";  
	}
	
	/**
	 * 结果视图-折线图
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/chart/line",method=RequestMethod.GET)
	public String getLineChart(ModelMap modelMap) {
		 //浏览量统计
		 DefaultCategoryDataset dataset = new DefaultCategoryDataset();  
         List<Book> books = bookService.getTop10ByOrderByBrowseDesc();
         for(Book book : books) {
       	   dataset.addValue(book.getBrowse(), book.getUser().getUserName(), book.getName());
         } 
         modelMap.addAttribute(IChart.CHARTTYPE,IChart.BAR);  
         modelMap.addAttribute(IChart.CHARDATASET,dataset);  
         return "webanalytics.chart";  
	}
	
	/**
	 * 结果视图-饼状图
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/chart/pie",method=RequestMethod.GET)
	public String getPieChart(ModelMap modelMap) {
		 DefaultPieDataset dataset = new DefaultPieDataset();
		 List<Book> books = bookService.getTop10ByOrderByCollectionDesc();
         for(Book book : books) {
        	 dataset.setValue(book.getName()+"-"+book.getUser().getNickName(), book.getCollection());
         }
         modelMap.addAttribute(IChart.CHARTTYPE,IChart.PIE);  
         modelMap.addAttribute(IChart.CHARDATASET,dataset);  
         return "webanalytics.chart";  
	}
	
}
