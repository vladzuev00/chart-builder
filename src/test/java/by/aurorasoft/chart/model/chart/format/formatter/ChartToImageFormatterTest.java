package by.aurorasoft.chart.model.chart.format.formatter;

import by.aurorasoft.chart.base.AbstractSpringBootTest;
import org.icepear.echarts.Bar;
import org.icepear.echarts.Chart;
import org.icepear.echarts.charts.bar.BarSeries;
import org.icepear.echarts.render.Engine;
import org.junit.Test;

import java.awt.image.BufferedImage;

import static by.aurorasoft.chart.util.ChartImageUtil.*;

public final class ChartToImageFormatterTest extends AbstractSpringBootTest {

    private final ChartToImageFormatter formatter = new ChartToImageFormatter();

    @Test
    public void chartShouldBeFormatted()
            throws Exception {
        final Chart<?, ?> givenChart = createBar();
        final Engine givenEngine = new Engine();

        final byte[] actualImageBytes = this.formatter.format(givenChart, givenEngine);

        final BufferedImage actualImage = createImage(actualImageBytes);
        final BufferedImage expectedImage = readImage(PATH_NAME_IMAGE_BAR);
        checkEquals(expectedImage, actualImage);
    }

    private static Bar createBar() {
        return new Bar()
                .setTitle("title")
                .addXAxis("axisXName", new String[]{"Matcha Latte", "Milk Tea", "Cheese Cocoa", "Walnut Brownie"})
                .addYAxis("axisYName")
                .addSeries(createSeries("2015", new Number[]{43.3, 83.1, 86.4, 72.4}))
                .addSeries(createSeries("2016", new Number[]{44.3, 84.1, 87.4, 73.4}))
                .addSeries(createSeries("2017", new Number[]{45.3, 85.1, 88.4, 74.4}));
    }

    private static BarSeries createSeries(String name, Number[] data) {
        return new BarSeries()
                .setName(name)
                .setAnimation(false)
                .setData(data);
    }
}
