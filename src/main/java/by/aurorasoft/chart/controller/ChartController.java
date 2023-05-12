package by.aurorasoft.chart.controller;

import by.aurorasoft.chart.model.PreparedChart;
import by.aurorasoft.chart.model.chart.*;
import by.aurorasoft.chart.model.chart.format.ChartFormat;
import by.aurorasoft.chart.service.building.manager.ChartBuildingServiceManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileOutputStream;
import java.io.OutputStream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/chart")
@RequiredArgsConstructor
public final class ChartController {
    private final ChartBuildingServiceManager buildingServiceManager;

    @PostMapping("/bar/{format}")
    public ResponseEntity<byte[]> buildBar(@PathVariable ChartFormat format,
                                           @RequestBody Bar bar) throws Exception {
        final PreparedChart preparedChart = this.buildingServiceManager.build(bar);
        final byte[] bytes = preparedChart.format(format);

        //for pdf
        OutputStream out = new FileOutputStream("out.png");
        out.write(bytes);
        out.close();

        System.out.println(new String(bytes, UTF_8));
        return ok(bytes);
    }

    @PostMapping("/stackBar/{format}")
    public ResponseEntity<byte[]> buildStackBar(@PathVariable ChartFormat format,
                                                @RequestBody StackBar bar) throws Exception {
        final PreparedChart preparedChart = this.buildingServiceManager.build(bar);
        final byte[] bytes = preparedChart.format(format);

        //for pdf
        OutputStream out = new FileOutputStream("out.png");
        out.write(bytes);
        out.close();

        System.out.println(new String(bytes, UTF_8));
        return ok(bytes);
    }

    @PostMapping("/stackBarWithLine/{format}")
    public ResponseEntity<byte[]> buildStackBarWithLine(@PathVariable ChartFormat format,
                                                        @RequestBody StackBarWithLine bar) throws Exception {
        final PreparedChart preparedChart = this.buildingServiceManager.build(bar);
        final byte[] bytes = preparedChart.format(format);

        //for pdf
        OutputStream out = new FileOutputStream("out.png");
        out.write(bytes);
        out.close();

        System.out.println(new String(bytes, UTF_8));
        return ok(bytes);
    }

    @PostMapping("/pie/{format}")
    public ResponseEntity<byte[]> buildPie(@PathVariable ChartFormat format,
                                           @RequestBody Pie bar) throws Exception {
        final PreparedChart preparedChart = this.buildingServiceManager.build(bar);
        final byte[] bytes = preparedChart.format(format);

        //for pdf
        OutputStream out = new FileOutputStream("out.png");
        out.write(bytes);
        out.close();

        System.out.println(new String(bytes, UTF_8));
        return ok(bytes);
    }

//    @PostMapping("/pie/{format}")
//    public ResponseEntity<String> buildPie(@PathVariable ChartFormat format,
//                                           @RequestBody Pie pie) {
//        return this.buildAndWrapToResponseEntity(pie, format);
//    }
//
//    @PostMapping("/stackBar/{format}")
//    public ResponseEntity<String> buildStackBar(@PathVariable ChartFormat format,
//                                                @RequestBody StackBar stackBar) {
//        return this.buildAndWrapToResponseEntity(stackBar, format);
//    }
//
//    @PostMapping("/stackBarWithLine/{format}")
//    public ResponseEntity<String> buildStackBarWithLine(@PathVariable ChartFormat format,
//                                                        @RequestBody StackBarWithLine stackBarWithLine) {
//        return this.buildAndWrapToResponseEntity(stackBarWithLine, format);
//    }
//
//    private ResponseEntity<String> buildAndWrapToResponseEntity(Chart<?> chart, ChartFormat format) {
//        final PreparedChart builtBar = this.buildingServiceManager.build(chart);
//        return ok(builtBar);
//    }

}
