package com.project.resources;

import com.project.RuleEngineConfiguration;
import com.project.obj.ApiResponse;
import com.project.service.AppView;
import com.project.service.RuleEngineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RuleEngineController {
    private static final Logger logger = LoggerFactory.getLogger(RuleEngineController.class);

    private final RuleEngineService ruleEngineService;
    private final RuleEngineConfiguration ruleEngineConfiguration;
    ApiResponse apiResponse = new ApiResponse();

    public RuleEngineController(RuleEngineConfiguration ruleEngineConfiguration) {
        //this.ruleEngineService = new RuleEngineService();
        this.ruleEngineService = new RuleEngineService(ruleEngineConfiguration);
        this.ruleEngineConfiguration = ruleEngineConfiguration;

    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/app-view")
    public Response appView(@Context HttpServletRequest request) {
        logger.info("Loading app view url");
        return Response.ok( new AppView("app_view.ftl", ruleEngineConfiguration)).build();
    }

    @GET
    @Path("/api/rule-engine/base-api")
    public Response getBaseApi() {
        String baseApi = ruleEngineService.getUiBaseApi();
//        String baseApi = "http://localhost:8080";
        return Response.ok(baseApi).build();
    }

    @POST
    @Path("/api/rule-engine/evaluate")
    public Response evaluateRule(HashMap<String, String> userData) {
        logger.info("Evaluate: {}", userData);

        if (userData != null) {
            HashMap<String, String> upperCaseUserData = new HashMap<>();
            for (Map.Entry<String, String> entry : userData.entrySet()) {
                upperCaseUserData.put(entry.getKey().toUpperCase(), entry.getValue().toUpperCase());
            }
            String ruleName = userData.get("rule_name");
            if (ruleName == null) {
                apiResponse.setStatus("FAILURE");
                apiResponse.setError("Rule name is null");
            } else {
                boolean result = ruleEngineService.evaluateRule(ruleName, upperCaseUserData);
                apiResponse.setStatus("SUCCESS");
                apiResponse.setData(Boolean.toString(result));
            }
        } else {
            apiResponse.setStatus("FAILURE");
            apiResponse.setError("User data is null");
        }
        logger.info("Return result: {}",apiResponse);
        return Response.ok(apiResponse.toJsonString()).build();
    }

    // Combine Rule
    @POST
    @Path("/api/rule-engine/combine")
    public Response combineRule(HashMap<String, String> userData) throws SQLException {
        logger.info("Combine: {}", userData);
        if (userData != null) {
                boolean result = ruleEngineService.combineRule(userData);
                apiResponse.setStatus("SUCCESS");
                apiResponse.setData(Boolean.toString(result));
        } else {
            apiResponse.setStatus("FAILURE");
            apiResponse.setError("User data is null");
        }
        return Response.ok(apiResponse.toJsonString()).build();
    }


    @POST
    @Path("/api/rule-engine/create-rule")
    public Response createRule(HashMap<String, String> userData) throws SQLException {
        logger.info("create Rule: {}", userData);
        if (userData != null) {
            boolean result = ruleEngineService.createRule(userData);
            apiResponse.setStatus("SUCCESS");
            apiResponse.setData(Boolean.toString(result));
        } else {
            apiResponse.setStatus("FAILURE");
            apiResponse.setError("User data is null");
        }
        return Response.ok(apiResponse.toJsonString()).build();
    }
    @GET
    @Path("/favicon.ico")
    @Produces("image/x-icon")
    public Response LoadFaviconIcon() throws URISyntaxException {
        return Response.seeOther(new URI("/assets/favicon.ico")).build();
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("{default: .*}")
    public Response defaultMethod(@Context HttpServletRequest request) {
        logger.info("Loading default url");
        return Response.ok( new AppView("file_not_found.ftl", ruleEngineConfiguration)).build();
    }
}
