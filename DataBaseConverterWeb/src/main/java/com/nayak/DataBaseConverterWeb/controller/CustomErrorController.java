package com.nayak.DataBaseConverterWeb.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Object exception = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        int statusCode = 500;
        String statusText = "Internal Server Error";
        String errorMessage = "An unexpected error occurred";

        if (status != null) {
            statusCode = Integer.parseInt(status.toString());
            statusText = HttpStatus.valueOf(statusCode).getReasonPhrase();
        }

        if (message != null) {
            errorMessage = message.toString();
        }

        String errorCode = "ERR_HTTP_" + statusCode;

        model.addAttribute("statusCode", statusCode);
        model.addAttribute("statusText", statusText);
        model.addAttribute("errorCode", errorCode);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("timestamp", System.currentTimeMillis());

        return "error";
    }
}
