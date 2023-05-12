package by.aurorasoft.chart.model;

import by.aurorasoft.chart.model.chart.format.ChartFormat;
import by.aurorasoft.chart.model.chart.format.formatter.ChartFormatter;
import org.icepear.echarts.Chart;
import org.icepear.echarts.render.Engine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
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

    private static final ChartFormat FORMAT_WITH_MOCKED_FORMATTER = HTML;

    @Mock
    private ChartFormatter mockedFormatter;

    @Captor
    private ArgumentCaptor<Chart<?, ?>> chartArgumentCaptor;

    @Before
    public void injectMockedFormatter()
            throws Exception {
        injectFormatter(FORMAT_WITH_MOCKED_FORMATTER, this.mockedFormatter);
    }

    @After
    public void injectGeneralFormatter()
            throws Exception {
        injectFormatter(FORMAT_WITH_MOCKED_FORMATTER, this.mockedFormatter);
    }

    @Test
    public void chartShouldBeFormattedOnlyOneTimeForSameFormat() {
        final Chart<?, ?> givenChart = mock(Chart.class);
        final PreparedChart givenPreparedChart = new PreparedChart(givenChart);

        final byte[] givenFormattedHtml = new byte[]{100, 101, 102};
        when(this.mockedFormatter.format(any(Chart.class), any(Engine.class)))
                .thenReturn(givenFormattedHtml);

        final byte[] actual = givenPreparedChart.format(FORMAT_WITH_MOCKED_FORMATTER);
        assertArrayEquals(givenFormattedHtml, actual);

        final byte[] secondActual = givenPreparedChart.format(FORMAT_WITH_MOCKED_FORMATTER);
        assertArrayEquals(givenFormattedHtml, secondActual);

        verify(this.mockedFormatter, times(1)).format(
                this.chartArgumentCaptor.capture(), any(Engine.class)
        );
        assertSame(givenChart, this.chartArgumentCaptor.getValue());
    }

    private static void injectFormatter(ChartFormat format, ChartFormatter formatter)
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
