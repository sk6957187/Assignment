package com.project.resources;

import com.project.obj.ApiResponse;
import com.project.service.RuleEngineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;

@Path("/api/rule-engine")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RuleEngineController {
    private static final Logger logger = LoggerFactory.getLogger(RuleEngineController.class);

    private final RuleEngineService ruleEngineService;
    ApiResponse apiResponse = new ApiResponse();

    public RuleEngineController() {
        this.ruleEngineService = new RuleEngineService();
    }

    // Evaluate Rule
    @POST
    @Path("/evaluate")
    public Response evaluateRule(HashMap<String, String> userData) {
        logger.info("Evaluate: {}", userData);
        if (userData != null) {
            String ruleName = userData.get("rule_name");
            if (ruleName == null) {
                apiResponse.setStatus("FAILURE");
                apiResponse.setError("Rule name is null");
            } else {
                boolean result = ruleEngineService.evaluateRule(ruleName, userData);
                apiResponse.setStatus("SUCCESS");
                apiResponse.setData(Boolean.toString(result));
            }
        } else {
            apiResponse.setStatus("FAILURE");
            apiResponse.setError("User data is null");
        }
        return Response.ok(apiResponse.toJsonString()).build();
    }

    // Combine Rule
    @POST
    @Path("/combine")
    public Response combineRule(HashMap<String, String> userData) {
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
    @Path("/create-rule")
    public Response createRule(HashMap<String, String> userData){
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

}
