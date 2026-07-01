package com.example.MedicalCalculator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class CPOController
{
    @PostMapping("/calculateCPO")
    public String calculate(@RequestParam double CO, 
                             @RequestParam double MAP, 
                             Model model) {
        double result = (MAP * CO) / 451; 
        model.addAttribute("result", result);
        return "fragments/result :: resultFragment";
                             }
}