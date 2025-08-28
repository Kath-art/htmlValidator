package com.example.htmlvalidator.controllers;

import org.springframework.web.bind.annotation.RequestParam;

public interface ValidateHtmlController {
    void validateHtml(@RequestParam String htmlContent);
}

