package by.aurorasoft.chart.model.series;

import lombok.Value;

public final class PieSeries extends Series<PieSeries.PieDataItem[]> {

    public PieSeries(PieDataItem[] value) {
        super(value);
    }

    @Value
    public static class PieDataItem {
        String name;
        double value;

        public PieDataItem(String name, double value) {
            this.name = name;
            this.value = value;
        }
    }
}
