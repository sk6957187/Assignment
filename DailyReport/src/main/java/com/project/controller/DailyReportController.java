package com.project.controller;

import com.project.DailyReportConfiguration;
import com.project.service.DailyReportService;
import com.project.view.AppView;
import com.project.view.TableDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DailyReportController {
    private static final Logger lOGGER = LoggerFactory.getLogger(TableDataRepository.class);
    private final DailyReportConfiguration configuration;
    private final DailyReportService dailyReportService;

    public DailyReportController(DailyReportConfiguration configuration) {
        this.configuration = configuration;
        this.dailyReportService = new DailyReportService(configuration);
    }
    @GET
    @Produces
    public Response appView1(@Context HttpServletRequest request) {
        lOGGER.info("Loading: /Home-page");
        return Response.ok( new AppView("app_view.ftl")).build();
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/view")
    public Response appView(@Context HttpServletRequest request) {
        lOGGER.info("Loading: /app-view");
        return Response.ok( new AppView("app_view.ftl")).build();
    }
    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/app-view/edit/sno/{sno}")
    public Response appViewEdit(@Context HttpServletRequest request,
                                @PathParam("sno") String serialNumber) {
        lOGGER.info("Loading: /app-view/edit/sno/{}", serialNumber);
        return Response.ok(new AppView("app_view.ftl")).build();
    }
    @GET
    @Path("/daily-report/view")
    public Response viewDailyReport(@Context HttpServletRequest request) {
        ArrayList<ArrayList<String>> data = dailyReportService.getRecord();
        lOGGER.info("SQL data return : {}",data);
        return Response.ok(data).build();
    }
    @POST
    @Path("/daily-report/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addRecord(ArrayList<String> addData){
        lOGGER.info("Data for add: {}",addData);
        String status = "Ok";
        status = dailyReportService.addRecord(addData);
        return Response.ok(status).build();
    }
    @POST
    @Path("/daily-report/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateRecord(ArrayList<String> rowData) {
        lOGGER.info("Data fro update: {}", rowData);
        String status ="ok";
        status = dailyReportService.update(rowData);
        return Response.ok(status).build();
    }
    @POST
    @Path("daily-report/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteRecord(int sno) {
        lOGGER.info("Request to delete row with SNO: {}", sno);
        String status = dailyReportService.deleteRec(sno);
        return Response.ok(status).build();
    }

    @GET
    @Path("/favicon.ico")
    @Produces("image/x-icon")
    public Response LoadFaviconIcon() throws URISyntaxException {
        return Response.seeOther(new URI("/assets/favicon1.ico")).build();
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("{default: .*}")
    public Response defaultMethod(@Context HttpServletRequest request) {
        return Response.ok( new AppView("file_not_found.ftl")).build();
    }

}
