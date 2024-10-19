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

    public RuleEngineController() {
        this.ruleEngineService = new RuleEngineService();
    }

    @POST
    @Path("/evaluate")
    public Response evaluateRule(HashMap<String, String> userData) {
        logger.info("Evaluate: {}", userData);
        ApiResponse apiResponse = new ApiResponse();
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

}
