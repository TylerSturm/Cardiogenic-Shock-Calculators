package com.example.MedicalCalculator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class SVRController
{
    @PostMapping("/calculateSVR")
    public String calculate(@RequestParam double CO, 
                             @RequestParam double MAP,
                             @RequestParam double CVP, 
                             Model model) {
        double result = (80 * (MAP - CVP)) / CO; 
        model.addAttribute("result", result);
        return "fragments/result :: resultFragment";
                             }
}