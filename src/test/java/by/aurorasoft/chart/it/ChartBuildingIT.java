package by.aurorasoft.chart.it;

import by.aurorasoft.chart.base.AbstractSpringBootTest;
import by.aurorasoft.chart.model.PreparedChart;
import by.aurorasoft.chart.model.chart.*;
import by.aurorasoft.chart.model.series.BarSeries;
import by.aurorasoft.chart.model.series.PieSeries;
import by.aurorasoft.chart.model.series.PieSeries.PieDataItem;
import by.aurorasoft.chart.service.chartbuilding.manager.ChartBuildingServiceManager;
import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.stream.Stream;

import static by.aurorasoft.chart.model.chart.format.ChartFormat.*;
import static com.github.romankh3.image.comparison.model.ImageComparisonState.MATCH;
import static java.io.File.separator;
import static java.nio.charset.StandardCharsets.UTF_8;
import static javax.imageio.ImageIO.read;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertSame;

public final class ChartBuildingIT extends AbstractSpringBootTest {
    private static final String PATH_NAME_CHART_DIRECTORY = "./src/test/resources/charts";

    private static final String FILE_NAME_IMAGE_BAR = "bar.png";
    private static final String PATH_NAME_IMAGE_BAR
            = PATH_NAME_CHART_DIRECTORY + separator + FILE_NAME_IMAGE_BAR;

    private static final String FILE_NAME_IMAGE_STACK_BAR = "stack-bar.png";
    private static final String PATH_NAME_IMAGE_STACK_BAR
            = PATH_NAME_CHART_DIRECTORY + separator + FILE_NAME_IMAGE_STACK_BAR;

    private static final String FILE_NAME_IMAGE_STACK_BAR_WITH_LINE = "stack-bar-with-line.png";
    private static final String PATH_NAME_IMAGE_STACK_BAR_WITH_LINE
            = PATH_NAME_CHART_DIRECTORY + separator + FILE_NAME_IMAGE_STACK_BAR_WITH_LINE;

    private static final String FILE_NAME_IMAGE_PIE = "pie.png";
    private static final String PATH_NAME_IMAGE_PIE
            = PATH_NAME_CHART_DIRECTORY + separator + FILE_NAME_IMAGE_PIE;

    @Autowired
    private ChartBuildingServiceManager buildingServiceManager;

    @ParameterizedTest
    @MethodSource("chartBuildingArgumentsProvider")
    public void chartShouldBeBuilt(Chart<?> givenChart,
                                   String expectedHtml,
                                   String expectedJson,
                                   String expectedImagePathName)
            throws Exception {
        final PreparedChart preparedChart = this.buildingServiceManager.build(givenChart);

        final byte[] actualHtmlBytes = preparedChart.format(HTML);
        final byte[] expectedHtmlBytes = transformToBytes(expectedHtml);
        assertArrayEquals(expectedHtmlBytes, actualHtmlBytes);

        final byte[] actualJsonBytes = preparedChart.format(JSON);
        final byte[] expectedJsonBytes = transformToBytes(expectedJson);
        assertArrayEquals(expectedJsonBytes, actualJsonBytes);

        final byte[] actualImageBytes = preparedChart.format(IMAGE);
        final BufferedImage actualImage = createImage(actualImageBytes);
        final BufferedImage expectedImage = readImage(expectedImagePathName);
        checkEquals(expectedImage, actualImage);
    }

    private static Stream<Arguments> chartBuildingArgumentsProvider() {
        return Stream.of(
                Arguments.of(
                        createBar(),
                        createBarHtml(),
                        createBarJson(),
                        PATH_NAME_IMAGE_BAR
                ),
                Arguments.of(
                        createStackBar(),
                        createStackBarHtml(),
                        createStackBarJson(),
                        PATH_NAME_IMAGE_STACK_BAR
                ),
                Arguments.of(
                        createStackBarWithLine(),
                        createStackBarWithLineHtml(),
                        createStackBarWithLineJson(),
                        PATH_NAME_IMAGE_STACK_BAR_WITH_LINE
                ),
                Arguments.of(
                        createPie(),
                        createPieHtml(),
                        createPieJson(),
                        PATH_NAME_IMAGE_PIE
                )
        );
    }

    private static Bar createBar() {
        return new Bar(
                "title",
                new BarSeries[]{
                        new BarSeries("2015", new Number[]{43.3, 83.1, 86.4, 72.4}),
                        new BarSeries("2016", new Number[]{44.3, 84.1, 87.4, 73.4}),
                        new BarSeries("2017", new Number[]{45.3, 85.1, 88.4, 74.4})
                },
                "axisXName",
                new String[]{"Matcha Latte", "Milk Tea", "Cheese Cocoa", "Walnut Brownie"},
                "axisYName"
        );
    }

    private static String createBarHtml() {
        return "<html>\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\n" +
                "    <title>ECharts Demo</title>\n" +
                "    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/echarts/5.2.2/echarts.min.js\"\n" +
                "        integrity=\"sha512-ivdGNkeO+FTZH5ZoVC4gS4ovGSiWc+6v60/hvHkccaMN2BXchfKdvEZtviy5L4xSpF8NPsfS0EVNSGf+EsUdxA==\"\n" +
                "        crossorigin=\"anonymous\" referrerpolicy=\"no-referrer\"></script>\n" +
                "    <style>\n" +
                "        #display-container {\n" +
                "            width: 100%;\n" +
                "            height: 100%;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "    <div id=\"display-container\">\n" +
                "    </div>\n" +
                "    <script type=\"text/javascript\">\n" +
                "        var chart = echarts.init(document.getElementById(\"display-container\")); \n" +
                "        var option = {\"title\":{\"text\":\"title\"},\"xAxis\":[{\"type\":\"category\",\"name\":\"axisXName\",\"data\":[\"Matcha Latte\",\"Milk Tea\",\"Cheese Cocoa\",\"Walnut Brownie\"]}],\"yAxis\":[{\"type\":\"value\",\"name\":\"axisYName\"}],\"series\":[{\"type\":\"bar\",\"name\":\"2015\",\"data\":[43.3,83.1,86.4,72.4]},{\"type\":\"bar\",\"name\":\"2016\",\"data\":[44.3,84.1,87.4,73.4]},{\"type\":\"bar\",\"name\":\"2017\",\"data\":[45.3,85.1,88.4,74.4]}]}\n" +
                "        chart.setOption(option);\n" +
                "    </script>\n" +
                "</body>\n" +
                "\n" +
                "</html>";
    }

    private static String createBarJson() {
        return "{\"title\":{\"text\":\"title\"},\"xAxis\":[{\"type\":\"category\",\"name\":\"axisXName\",\"data\":[\"Matcha Latte\",\"Milk Tea\",\"Cheese Cocoa\",\"Walnut Brownie\"]}],\"yAxis\":[{\"type\":\"value\",\"name\":\"axisYName\"}],\"series\":[{\"type\":\"bar\",\"name\":\"2015\",\"data\":[43.3,83.1,86.4,72.4]},{\"type\":\"bar\",\"name\":\"2016\",\"data\":[44.3,84.1,87.4,73.4]},{\"type\":\"bar\",\"name\":\"2017\",\"data\":[45.3,85.1,88.4,74.4]}]}";
    }

    private static StackBar createStackBar() {
        return new StackBar(
                "title",
                new BarSeries[]{
                        new BarSeries("Direct", new Number[]{320, 302, 301, 334, 390, 330, 320}),
                        new BarSeries("Mail Ad", new Number[]{120, 132, 101, 134, 90, 230, 210}),
                        new BarSeries("Affiliate Ad", new Number[]{220, 182, 191, 234, 290, 330, 310}),
                        new BarSeries("Video Ad", new Number[]{150, 212, 201, 154, 190, 330, 410}),
                        new BarSeries("Search Engine", new Number[]{820, 832, 901, 934, 1290, 1330, 1320})
                },
                "axisXName",
                new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"},
                "axisYName"
        );
    }

    private static String createStackBarHtml() {
        return "<html>\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\n" +
                "    <title>ECharts Demo</title>\n" +
                "    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/echarts/5.2.2/echarts.min.js\"\n" +
                "        integrity=\"sha512-ivdGNkeO+FTZH5ZoVC4gS4ovGSiWc+6v60/hvHkccaMN2BXchfKdvEZtviy5L4xSpF8NPsfS0EVNSGf+EsUdxA==\"\n" +
                "        crossorigin=\"anonymous\" referrerpolicy=\"no-referrer\"></script>\n" +
                "    <style>\n" +
                "        #display-container {\n" +
                "            width: 100%;\n" +
                "            height: 100%;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "    <div id=\"display-container\">\n" +
                "    </div>\n" +
                "    <script type=\"text/javascript\">\n" +
                "        var chart = echarts.init(document.getElementById(\"display-container\")); \n" +
                "        var option = {\"title\":{\"text\":\"title\"},\"xAxis\":[{\"type\":\"category\",\"name\":\"axisXName\",\"data\":[\"Mon\",\"Tue\",\"Wed\",\"Thu\",\"Fri\",\"Sat\",\"Sun\"]}],\"yAxis\":[{\"type\":\"value\",\"name\":\"axisYName\"}],\"series\":[{\"type\":\"bar\",\"name\":\"Direct\",\"emphasis\":{},\"data\":[320,302,301,334,390,330,320],\"stack\":\"total\"},{\"type\":\"bar\",\"name\":\"Mail Ad\",\"emphasis\":{},\"data\":[120,132,101,134,90,230,210],\"stack\":\"total\"},{\"type\":\"bar\",\"name\":\"Affiliate Ad\",\"emphasis\":{},\"data\":[220,182,191,234,290,330,310],\"stack\":\"total\"},{\"type\":\"bar\",\"name\":\"Video Ad\",\"emphasis\":{},\"data\":[150,212,201,154,190,330,410],\"stack\":\"total\"},{\"type\":\"bar\",\"name\":\"Search Engine\",\"emphasis\":{},\"data\":[820,832,901,934,1290,1330,1320],\"stack\":\"total\"}]}\n" +
                "        chart.setOption(option);\n" +
                "    </script>\n" +
                "</body>\n" +
                "\n" +
                "</html>";
    }

    private static String createStackBarJson() {
        return "{\"title\":{\"text\":\"title\"},\"xAxis\":[{\"type\":\"category\",\"name\":\"axisXName\",\"data\":[\"Mon\",\"Tue\",\"Wed\",\"Thu\",\"Fri\",\"Sat\",\"Sun\"]}],\"yAxis\":[{\"type\":\"value\",\"name\":\"axisYName\"}],\"series\":[{\"type\":\"bar\",\"name\":\"Direct\",\"emphasis\":{},\"data\":[320,302,301,334,390,330,320],\"stack\":\"total\"},{\"type\":\"bar\",\"name\":\"Mail Ad\",\"emphasis\":{},\"data\":[120,132,101,134,90,230,210],\"stack\":\"total\"},{\"type\":\"bar\",\"name\":\"Affiliate Ad\",\"emphasis\":{},\"data\":[220,182,191,234,290,330,310],\"stack\":\"total\"},{\"type\":\"bar\",\"name\":\"Video Ad\",\"emphasis\":{},\"data\":[150,212,201,154,190,330,410],\"stack\":\"total\"},{\"type\":\"bar\",\"name\":\"Search Engine\",\"emphasis\":{},\"data\":[820,832,901,934,1290,1330,1320],\"stack\":\"total\"}]}";
    }

    private static StackBarWithLine createStackBarWithLine() {
        return new StackBarWithLine(
                "title",
                new BarSeries[]{
                        new BarSeries("Direct", new Number[]{320, 302, 301, 334, 390, 330, 320}),
                        new BarSeries("Mail Ad", new Number[]{120, 132, 101, 134, 90, 230, 210}),
                        new BarSeries("Affiliate Ad", new Number[]{220, 182, 191, 234, 290, 330, 310}),
                        new BarSeries("Video Ad", new Number[]{150, 212, 201, 154, 190, 330, 410}),
                        new BarSeries("Search Engine", new Number[]{820, 832, 901, 934, 1290, 1330, 1320})
                },
                "axisXName",
                new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"},
                "axisYName",
                "line-value",
                new Number[]{150, 230, 224, 218, 135, 147, 260}
        );
    }

    private static String createStackBarWithLineHtml() {
        return "<html>\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\n" +
                "    <title>ECharts Demo</title>\n" +
                "    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/echarts/5.2.2/echarts.min.js\"\n" +
                "        integrity=\"sha512-ivdGNkeO+FTZH5ZoVC4gS4ovGSiWc+6v60/hvHkccaMN2BXchfKdvEZtviy5L4xSpF8NPsfS0EVNSGf+EsUdxA==\"\n" +
                "        crossorigin=\"anonymous\" referrerpolicy=\"no-referrer\"></script>\n" +
                "    <style>\n" +
                "        #display-container {\n" +
                "            width: 100%;\n" +
                "            height: 100%;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "    <div id=\"display-container\">\n" +
                "    </div>\n" +
                "    <script type=\"text/javascript\">\n" +
                "        var chart = echarts.init(document.getElementById(\"display-container\")); \n" +
                "        var option = {\"title\":{\"text\":\"title\"},\"xAxis\":[{\"type\":\"category\",\"name\":\"axisXName\",\"data\":[\"Mon\",\"Tue\",\"Wed\",\"Thu\",\"Fri\",\"Sat\",\"Sun\"]}],\"yAxis\":[{\"type\":\"value\",\"name\":\"axisYName\"},{\"position\":\"right\",\"type\":\"value\",\"name\":\"line-value\"}],\"series\":[{\"type\":\"bar\",\"name\":\"Direct\",\"emphasis\":{},\"data\":[320,302,301,334,390,330,320],\"stack\":\"total\"},{\"type\":\"bar\",\"name\":\"Mail Ad\",\"emphasis\":{},\"data\":[120,132,101,134,90,230,210],\"stack\":\"total\"},{\"type\":\"bar\",\"name\":\"Affiliate Ad\",\"emphasis\":{},\"data\":[220,182,191,234,290,330,310],\"stack\":\"total\"},{\"type\":\"bar\",\"name\":\"Video Ad\",\"emphasis\":{},\"data\":[150,212,201,154,190,330,410],\"stack\":\"total\"},{\"type\":\"bar\",\"name\":\"Search Engine\",\"emphasis\":{},\"data\":[820,832,901,934,1290,1330,1320],\"stack\":\"total\"},{\"type\":\"line\",\"data\":[150,230,224,218,135,147,260],\"yAxisIndex\":1}]}\n" +
                "        chart.setOption(option);\n" +
                "    </script>\n" +
                "</body>\n" +
                "\n" +
                "</html>";
    }

    private static String createStackBarWithLineJson() {
        return "{\"title\":{\"text\":\"title\"},\"xAxis\":[{\"type\":\"category\",\"name\":\"axisXName\",\"data\":[\"Mon\",\"Tue\",\"Wed\",\"Thu\",\"Fri\",\"Sat\",\"Sun\"]}],\"yAxis\":[{\"type\":\"value\",\"name\":\"axisYName\"},{\"position\":\"right\",\"type\":\"value\",\"name\":\"line-value\"}],\"series\":[{\"type\":\"bar\",\"name\":\"Direct\",\"emphasis\":{},\"data\":[320,302,301,334,390,330,320],\"stack\":\"total\"},{\"type\":\"bar\",\"name\":\"Mail Ad\",\"emphasis\":{},\"data\":[120,132,101,134,90,230,210],\"stack\":\"total\"},{\"type\":\"bar\",\"name\":\"Affiliate Ad\",\"emphasis\":{},\"data\":[220,182,191,234,290,330,310],\"stack\":\"total\"},{\"type\":\"bar\",\"name\":\"Video Ad\",\"emphasis\":{},\"data\":[150,212,201,154,190,330,410],\"stack\":\"total\"},{\"type\":\"bar\",\"name\":\"Search Engine\",\"emphasis\":{},\"data\":[820,832,901,934,1290,1330,1320],\"stack\":\"total\"},{\"type\":\"line\",\"data\":[150,230,224,218,135,147,260],\"yAxisIndex\":1}]}";
    }

    private static Pie createPie() {
        return new Pie(
                "title",
                new PieSeries(
                        new PieDataItem[]{
                                new PieDataItem("item1", 50),
                                new PieDataItem("item2", 75),
                                new PieDataItem("item3", 150)
                        }
                )
        );
    }

    private static String createPieHtml() {
        return "<html>\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\n" +
                "    <title>ECharts Demo</title>\n" +
                "    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/echarts/5.2.2/echarts.min.js\"\n" +
                "        integrity=\"sha512-ivdGNkeO+FTZH5ZoVC4gS4ovGSiWc+6v60/hvHkccaMN2BXchfKdvEZtviy5L4xSpF8NPsfS0EVNSGf+EsUdxA==\"\n" +
                "        crossorigin=\"anonymous\" referrerpolicy=\"no-referrer\"></script>\n" +
                "    <style>\n" +
                "        #display-container {\n" +
                "            width: 100%;\n" +
                "            height: 100%;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "    <div id=\"display-container\">\n" +
                "    </div>\n" +
                "    <script type=\"text/javascript\">\n" +
                "        var chart = echarts.init(document.getElementById(\"display-container\")); \n" +
                "        var option = {\"title\":{\"text\":\"title\"},\"series\":[{\"type\":\"pie\",\"data\":[{\"name\":\"item1\",\"value\":50.0},{\"name\":\"item2\",\"value\":75.0},{\"name\":\"item3\",\"value\":150.0}]}]}\n" +
                "        chart.setOption(option);\n" +
                "    </script>\n" +
                "</body>\n" +
                "\n" +
                "</html>";
    }

    private static String createPieJson() {
        return "{\"title\":{\"text\":\"title\"},\"series\":[{\"type\":\"pie\",\"data\":[{\"name\":\"item1\",\"value\":50.0},{\"name\":\"item2\",\"value\":75.0},{\"name\":\"item3\",\"value\":150.0}]}]}";
    }

    private static BufferedImage readImage(String filePathName)
            throws IOException {
        final File file = new File(filePathName);
        return read(file);
    }

    private static BufferedImage createImage(byte[] bytes)
            throws IOException {
        try (final ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes)) {
            return read(inputStream);
        }
    }

    private static byte[] transformToBytes(String source) {
        return source.getBytes(UTF_8);
    }

    private static void checkEquals(BufferedImage expected, BufferedImage actual) {
        final ImageComparisonResult imageComparisonResult = new ImageComparison(expected, actual)
                .compareImages();
        assertSame(MATCH, imageComparisonResult.getImageComparisonState());
    }
}
