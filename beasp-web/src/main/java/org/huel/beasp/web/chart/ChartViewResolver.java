package org.huel.beasp.web.chart;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractCachingViewResolver;

public class ChartViewResolver extends AbstractCachingViewResolver {
	private String chartSuffix;// 后缀
	private View chartView;

	@Override
	protected View loadView(String viewName, Locale locale) throws Exception {
		View view = null;
		if (viewName.endsWith(this.getChartSuffix())) {
			view = this.getChartView();
		}
		return view;
	}

	public String getChartSuffix() {
		return chartSuffix;
	}

	public void setChartSuffix(String chartSuffix) {
		this.chartSuffix = chartSuffix;
	}

	public View getChartView() {
		return chartView;
	}

	public void setChartView(View chartView) {
		this.chartView = chartView;
	}
}