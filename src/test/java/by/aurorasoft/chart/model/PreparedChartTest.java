package by.aurorasoft.chart.model;

import by.aurorasoft.chart.model.chart.format.ChartFormat;
import by.aurorasoft.chart.model.chart.format.formatter.ChartFormatter;
import org.icepear.echarts.Chart;
import org.icepear.echarts.render.Engine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;

import static by.aurorasoft.chart.model.chart.format.ChartFormat.HTML;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public final class PreparedChartTest {
    private static final String FIELD_NAME_FORMATTER = "formatter";

    @Captor
    private ArgumentCaptor<Chart<?, ?>> chartArgumentCaptor;

    @Test
    public void chartShouldBeFormattedOnlyOneTimeForSameFormat()
            throws Exception {
        final ChartFormatter givenFormatter = mock(ChartFormatter.class);
        final ChartFormat givenFormat = HTML;
        injectFormatter(givenFormat, givenFormatter);

        final Chart<?, ?> givenChart = mock(Chart.class);
        final PreparedChart givenPreparedChart = new PreparedChart(givenChart);

        final byte[] givenFormattedHtml = new byte[]{100, 101, 102};
        when(givenFormatter.format(any(Chart.class), any(Engine.class)))
                .thenReturn(givenFormattedHtml);

        final byte[] actual = givenPreparedChart.format(givenFormat);
        assertArrayEquals(givenFormattedHtml, actual);

        final byte[] secondActual = givenPreparedChart.format(givenFormat);
        assertArrayEquals(givenFormattedHtml, secondActual);

        verify(givenFormatter, times(1)).format(
                this.chartArgumentCaptor.capture(), any(Engine.class)
        );
        assertSame(givenChart, this.chartArgumentCaptor.getValue());
    }

    private static void injectFormatter(final ChartFormat format, final ChartFormatter formatter)
            throws Exception {
        final Field formatterField = ChartFormat.class.getDeclaredField(FIELD_NAME_FORMATTER);
        formatterField.setAccessible(true);
        try {
            formatterField.set(format, formatter);
        } finally {
            formatterField.setAccessible(false);
        }
    }

}
