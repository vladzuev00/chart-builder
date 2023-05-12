package by.aurorasoft.chart.util;

import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import lombok.experimental.UtilityClass;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import static com.github.romankh3.image.comparison.model.ImageComparisonState.MATCH;
import static java.io.File.separator;
import static java.nio.charset.StandardCharsets.UTF_8;
import static javax.imageio.ImageIO.read;
import static org.junit.Assert.assertSame;

@UtilityClass
public final class ChartImageUtil {

    private static final String PATH_NAME_CHART_DIRECTORY = "./src/test/resources/charts";

    private static final String FILE_NAME_IMAGE_BAR = "bar.png";
    public static final String PATH_NAME_IMAGE_BAR
            = PATH_NAME_CHART_DIRECTORY + separator + FILE_NAME_IMAGE_BAR;

    private static final String FILE_NAME_IMAGE_STACK_BAR = "stack-bar.png";
    public static final String PATH_NAME_IMAGE_STACK_BAR
            = PATH_NAME_CHART_DIRECTORY + separator + FILE_NAME_IMAGE_STACK_BAR;

    private static final String FILE_NAME_IMAGE_STACK_BAR_WITH_LINE = "stack-bar-with-line.png";
    public static final String PATH_NAME_IMAGE_STACK_BAR_WITH_LINE
            = PATH_NAME_CHART_DIRECTORY + separator + FILE_NAME_IMAGE_STACK_BAR_WITH_LINE;

    private static final String FILE_NAME_IMAGE_PIE = "pie.png";
    public static final String PATH_NAME_IMAGE_PIE
            = PATH_NAME_CHART_DIRECTORY + separator + FILE_NAME_IMAGE_PIE;

    public static BufferedImage readImage(String filePathName)
            throws IOException {
        final File file = new File(filePathName);
        return read(file);
    }

    public static BufferedImage createImage(byte[] bytes)
            throws IOException {
        try (final ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes)) {
            return read(inputStream);
        }
    }

    public static byte[] transformToBytes(String source) {
        return source.getBytes(UTF_8);
    }

    public static void checkEquals(BufferedImage expected, BufferedImage actual) {
        final ImageComparisonResult imageComparisonResult = new ImageComparison(expected, actual)
                .compareImages();
        assertSame(MATCH, imageComparisonResult.getImageComparisonState());
    }

}
