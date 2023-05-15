package by.aurorasoft.chart.model.chart.format.formatter;

import by.aurorasoft.chart.base.AbstractSpringBootTest;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.icepear.echarts.Bar;
import org.icepear.echarts.Chart;
import org.icepear.echarts.charts.bar.BarSeries;
import org.icepear.echarts.render.Engine;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;

import static by.aurorasoft.chart.util.ChartImageUtil.*;
import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertTrue;

public final class ChartToImageFormatterTest extends AbstractSpringBootTest {
    private static final String WILD_CARD_TEMP_FILE = "temp-*.html";

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

        assertTrue(isTempFileNotExist());
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

    private static boolean isTempFileNotExist() {
        final File root = new File(".");
        final FileFilter fileFilter = new WildcardFileFilter(WILD_CARD_TEMP_FILE);
        final File[] matchingFiles = root.listFiles(fileFilter);
        requireNonNull(matchingFiles);
        return matchingFiles.length == 0;
    }
}
