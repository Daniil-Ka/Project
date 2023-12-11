package org.basicprogramming.gui;

import org.basicprogramming.db.DB;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYPointerAnnotation;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.chart.ui.UIUtils;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;

// максимальные баллы по студентам
// количество
public class Chart extends ApplicationFrame {
    private static final String TITLE = "Нормальное распределение";
    //----------------------------------------------------------------
    public Chart(final String title)
    {
        super(title);

        XYSeriesCollection dataset = new XYSeriesCollection();

        var series = new XYSeries("2016");
        series.add(18, 567);
        series.add(20, 612);
        series.add(25, 800);
        series.add(30, 980);
        series.add(40, 1410);
        series.add(50, 2350);

        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(TITLE,
                null, "Y", dataset, PlotOrientation.VERTICAL,
                true, true, false);
        final ChartPanel chartPanel = new ChartPanel(chart);

        final XYPlot plot = chart.getXYPlot();
        plot.getRenderer().setSeriesPaint(2, new Color(255, 224, 64));

        XYPointerAnnotation pointer1;
        XYPointerAnnotation pointer2;
        XYTextAnnotation annotation;
        pointer1 = new XYPointerAnnotation(" Normal 1 ",
                -2.15, 0.65, Math.PI);
        pointer1.setBaseRadius(35.0);
        pointer1.setTipRadius(10.0);
        pointer1.setPaint          (Color.yellow);
        pointer1.setBackgroundPaint(Color.red   );
        pointer1.setFont(new Font("SansSerif", Font.PLAIN, 12));
        pointer1.setTextAnchor(TextAnchor.HALF_ASCENT_RIGHT);
        plot.addAnnotation(pointer1);

        pointer2 = new XYPointerAnnotation(" Normal 2 ",
                0.08, 0.95, 2.0 * Math.PI);
        pointer2.setBaseRadius(35.0);
        pointer2.setTipRadius(10.0);
        pointer2.setPaint          (Color.black);
        pointer2.setBackgroundPaint(Color.blue );
        pointer2.setFont(new Font("SansSerif", Font.PLAIN, 12));
        pointer2.setTextAnchor(TextAnchor.HALF_ASCENT_LEFT);
        plot.addAnnotation(pointer2);

        annotation = new XYTextAnnotation("  График  ", 2.5, 0.18);
        annotation.setBackgroundPaint(new Color (255, 224, 64));
        annotation.setFont(new Font("SansSerif", Font.PLAIN, 9));
        annotation.setRotationAngle(Math.PI / 4.0);
        plot.addAnnotation(annotation);

        chartPanel.setPreferredSize(new java.awt.Dimension(580, 480));
        setContentPane(chartPanel);
    }
    //----------------------------------------------------------------
    public static void main(final String[] args)
    {
        DB.init();

        Plot demo = new Plot(TITLE);
        demo.pack();
        UIUtils.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }
}
