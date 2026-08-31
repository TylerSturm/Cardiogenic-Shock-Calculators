package com.example.MedicalCalculator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FICKController
{
    @PostMapping("/calculateFICK")
    public String calculate(@RequestParam double Weight, 
                             @RequestParam double Height, 
                             @RequestParam double SaO2,
                             @RequestParam double SvO2,
                             @RequestParam double Hgb,
                             @RequestParam double HeartRate,
                             @RequestParam double Age,
                             Model model) {

        double BSA = Math.sqrt((Height * Weight) / 3600);

        double CaO2 = Hgb * 1.34 * SaO2;

        double CvO2 = Hgb * 1.34 * SvO2;

        double VO2;
        if(Age < 70)
        {
            VO2 = 125 * BSA;
        }
        else
        {
            VO2 = 110 * BSA;
        }
        double CO = 10 * (VO2 / (CaO2 - CvO2));
        double SV = 1000 * (CO / HeartRate);
        double CI = CO / BSA;
        model.addAttribute("result", CO);
        model.addAttribute("result2", CI);
        model.addAttribute("result3", SV);
        return "fragments/FICKResult :: resultFragment";
                             }
}