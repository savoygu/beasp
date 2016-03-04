package org.huel.beasp.web.chart;

import java.awt.Color;
import java.awt.Font;
import java.text.NumberFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.TextAnchor;
import org.jfree.util.Rotation;
import org.springframework.web.servlet.view.AbstractView;

public class ChartView extends AbstractView{

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JFreeChart chart = null;
		//IChart为自定义的接口规范。根据输出类型的不同调用相应的方法
		switch (((Integer) model.get(IChart.CHARTTYPE)).intValue()) {
		case IChart.BAR:
				chart =getChartBar(model);
			break;
		case IChart.PIE:
				chart =getChartPie(model);
			break;
		case IChart.LINE:
				chart =getChartLine(model);
			break;
		default:
			break;
		}
		//图片的输出 
		ChartUtilities.writeChartAsPNG(response.getOutputStream(), chart, 700, 500);
	}

	private JFreeChart getChartLine(Map<String, Object> model) {
		// TODO Auto-generated method stub
		return null;
	}

	private JFreeChart getChartPie(Map<String, Object> model) {
		JFreeChart chart = ChartFactory.createPieChart3D("书籍点赞前10名柱状图", (PieDataset)model.get(IChart.CHARDATASET), true, true, true);
		//副标题
		chart.addSubtitle(new TextTitle("书籍交换与分享平台2016"));
		
		//字体
		PiePlot3D piePlot3D = (PiePlot3D) chart.getPlot();
		piePlot3D.setLabelFont(new Font("宋体", 0, 11));
		
		//设置饼图是圆的（true）,还是椭圆的（false）；默认为true 
		piePlot3D.setCircular(true);
		
		//没有数据的时候显示的内容
		piePlot3D.setNoDataMessage("无数据显示");
		StandardPieSectionLabelGenerator standardPieSectionLabelGenerator = 
				new StandardPieSectionLabelGenerator("{0}:({1}.{2})", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance());
		
		piePlot3D.setLabelGenerator(standardPieSectionLabelGenerator);
		
		//突出显示
		//piePlot3D.setExplodePercent("", 0.23);
		
		//设置开始角度  
		piePlot3D.setStartAngle(120D);
		
		//设置方向为”顺时针方向“  
		
		piePlot3D.setDirection(Rotation.CLOCKWISE);  
		
		//设置透明度，0.5F为半透明，1为不透明，0为全透明  
		piePlot3D.setForegroundAlpha(0.7F);
		
		return chart;
	}

	private JFreeChart getChartBar(Map<String, Object> model) {
		JFreeChart chart = ChartFactory.createBarChart3D("书籍点赞前10名柱状图", "用户名", "数量",  
                (CategoryDataset)model.get(IChart.CHARDATASET), PlotOrientation.VERTICAL, true, true, true);  
        chart.getTitle().setFont(new Font("宋体", Font.BOLD, 20));  
        chart.getCategoryPlot().getDomainAxis().setTickLabelFont(new Font("宋体", Font.BOLD, 11));  
        chart.getCategoryPlot().getDomainAxis().setLabelFont(new Font("黑体", Font.BOLD, 12));  
        chart.getCategoryPlot().getRangeAxis().setTickLabelFont(new Font("宋体", Font.BOLD, 11));  
        chart.getCategoryPlot().getRangeAxis().setLabelFont(new Font("黑体", Font.BOLD, 12));  
        chart.getLegend().setItemFont(new Font("宋体", Font.BOLD, 11)); 
        
        CategoryPlot plot = chart.getCategoryPlot();
		//设置网格背景颜色
		plot.setBackgroundPaint(Color.white);
		//设置网格竖线颜色
		plot.setDomainGridlinePaint(Color.blue);
		//设置网格横线颜色
		plot.setRangeGridlinePaint(Color.red);
		
		//显示每个柱的数字，并修改该数值的字体属性
		BarRenderer3D renderer3d = new BarRenderer3D();
		renderer3d.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer3d.setBaseItemLabelsVisible(true);
		
		renderer3d.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
		renderer3d.setItemLabelAnchorOffset(10D);
		
		//设置平行柱的之间距离
		renderer3d.setItemMargin(0.4);
		
		plot.setRenderer(renderer3d);
		
		return chart;
	}

}
