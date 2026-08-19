package com.example.MedicalCalculator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SCAIController
{
    @PostMapping("/calculateSCAI")
    public String calculate(@RequestParam double SBP, 
                             @RequestParam double MAP, 
                             @RequestParam double SL,
                             @RequestParam double SAA,
                             @RequestParam double PH,
                             @RequestParam double vaso,
                             @RequestParam double MCD,
                             @RequestParam Boolean Hypo,
                             Model model) {
        String result;

        if(SBP > 90 && MAP > 65 && SL < 2 && SAA < 200 && PH > 7.2 && vaso == 0 && MCD == 0 && Hypo == false)
        {
            result = "SCAI Stage A";
        }
        else if(((SBP > 60 && SBP < 90) || (MAP > 50 && MAP < 65)) ||
                ((SL >= 2 && SL < 5) || (SAA > 200 && SAA < 500)) && 
                PH > 7.2 && vaso == 0 && MCD == 0 && Hypo == false)
        {
            result = "SCAI Stage B";
        }
        else if(((SBP > 60 && SBP < 90) || (MAP > 50 && MAP < 65)) &&
                ((SL >= 2 && SL < 5) || (SAA > 200 && SAA < 500)) && 
                PH > 7.2 && ((vaso == 0 && MCD == 0 && Hypo == true) || (vaso + MCD == 1 && Hypo == false)))
        {
            result = "SCAI Stage C";
        }
        else if((((SBP > 60 && SBP < 90) || (MAP > 50 && MAP < 65)) &&
                ((SL >= 5 && SL < 10) || (SAA > 500)) && 
                PH > 7.2) || 
                ((((vaso + MCD) >= 2 && (vaso + MCD) <= 5) && Hypo == false) || (vaso + MCD == 1 && Hypo == true)))
        {
            result = "SCAI Stage D";
        }
        else
        {
            result = "SCAI Stage E";
        }

        model.addAttribute("result", result);
        return "fragments/SCAIResult :: resultFragment";
                             }
}