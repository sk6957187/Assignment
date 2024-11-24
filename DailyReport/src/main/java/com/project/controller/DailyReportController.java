package com.project.controller;

import com.project.service.DailyReportService;
import com.project.view.TableData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DailyReportController {
    private static final Logger lOGGER = LoggerFactory.getLogger(TableData.class);
    DailyReportService dailyReportService = new DailyReportService();

    public DailyReportController() {}

    @GET
    @Path("/daily-report/view")
    public Response viewDailyReport() {
        ArrayList<ArrayList<String>> data = dailyReportService.getRecord();
        lOGGER.info("SQL data return : {}",data);
        return Response.ok(data).build();
    }
    @POST
    @Path("/daily-report/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateRecord(ArrayList<String> rowData) {
        lOGGER.info("Data fro update: {}", rowData);
        String status ="ok";
        //System.out.println(rowData.get("sno"));
        //status = dailyReportService.update(rowData);
        return Response.ok(status).build();
    }
    @GET
    @Path("{default: .*}")
    public Response getDailyReport() {
        return Response.ok("Hello from Daily Report Service").build();
    }
}
