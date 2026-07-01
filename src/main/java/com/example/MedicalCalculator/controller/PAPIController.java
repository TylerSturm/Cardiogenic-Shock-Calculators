package com.example.MedicalCalculator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class PAPIController
{
    @PostMapping("/calculatePAPI")
    public String calculate(@RequestParam double PAs, 
                             @RequestParam double PAd, 
                             @RequestParam double CUP,
                             Model model) {
        double result = (PAs + PAd) / CUP; 
        model.addAttribute("result", result);
        return "fragments/result :: resultFragment";
                             }
}