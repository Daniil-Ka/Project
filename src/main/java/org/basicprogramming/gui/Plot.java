package org.basicprogramming.gui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYPointerAnnotation;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.function.Function2D;
import org.jfree.data.function.NormalDistributionFunction2D;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.StatisticalCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.general.DatasetUtils;
import org.jfree.chart.ui.UIUtils;

import java.awt.*;

public class Plot extends ApplicationFrame
{
    private static final long   serialVersionUID = 1L;
    private static final String TITLE = "Нормальное распределение";
    //----------------------------------------------------------------
    public Plot(final String title)
    {
        super(title);

        String P1 = "Player 1";
        String P2 = "Player 2";
        String Attk = "Attack";
        String Def = "Defense";
        String Speed = "Speed";
        String Stealth = "Stealth";

        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );

        // Player 1
        dataset.addValue(10, P1, Attk);
        dataset.addValue(7, P1, Def);
        dataset.addValue(6, P1, Speed);
        dataset.addValue(6, P1, Stealth);

        // Player 2
        dataset.addValue(7, P2, Attk);
        dataset.addValue(9, P2, Def);
        dataset.addValue(8, P2, Speed);
        dataset.addValue(8, P2, Stealth);

        JFreeChart barChart = ChartFactory.createBarChart("JFreeChart BarChart", "Players",
                "Points",dataset, PlotOrientation.VERTICAL, true, true, false);

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