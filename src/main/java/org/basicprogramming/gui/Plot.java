package org.basicprogramming.gui;

import org.basicprogramming.db.DB;
import org.basicprogramming.db.models.VKUser;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.ui.UIUtils;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.Serial;
import java.util.stream.Collectors;

public class Plot extends ApplicationFrame
{
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String TITLE = "Нормальное распределение";

    private static final String PLOT_TITLE = "Рвспределение студентов по странам";

    private DefaultCategoryDataset getCountriesForStudents() {
        var dataset = new DefaultCategoryDataset();
        var students = DB.loadAllData(VKUser.class);

        var countries = students
                .stream()
                .filter(student -> student.getCountry() != null)
                .collect(Collectors.groupingBy(VKUser::getCountry));

        for(var item : countries.entrySet()){
            dataset.addValue(item.getValue().size(), PLOT_TITLE, item.getKey());
        }

        return dataset;
    }
    //----------------------------------------------------------------
    public Plot(final String title)
    {
        super(title);

        var dataset = getCountriesForStudents();
        JFreeChart barChart = ChartFactory.createBarChart(PLOT_TITLE, "",
                "Количество студентов из страны",dataset, PlotOrientation.HORIZONTAL, true, true, false);

        final ChartPanel chartPanel = new ChartPanel(barChart);
        final var plot = barChart.getPlot();

        chartPanel.setPreferredSize(new java.awt.Dimension(580, 480));
        setContentPane(chartPanel);
    }

    public static void main(final String[] args)
    {
        Plot demo = new Plot(TITLE);
        demo.pack();
        UIUtils.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }
}