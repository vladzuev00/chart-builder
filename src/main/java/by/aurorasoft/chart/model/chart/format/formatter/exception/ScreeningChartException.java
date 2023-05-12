package by.aurorasoft.chart.model.chart.format.formatter.exception;

public final class ScreeningChartException extends RuntimeException {
    public ScreeningChartException() {

    }

    public ScreeningChartException(String description) {
        super(description);
    }

    public ScreeningChartException(Exception cause) {
        super(cause);
    }

    public ScreeningChartException(String description, Exception cause) {
        super(description, cause);
    }
}
