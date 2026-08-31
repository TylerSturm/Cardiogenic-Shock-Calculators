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
        else if((((SBP >= 60) || (MAP >= 50)) ||
                ((SL <= 5) || (SAA <= 500))) &&
                PH > 7.2 && vaso == 0 && MCD == 0 && Hypo == false)
        {
            result = "SCAI Stage B";
        }
        else if(((SBP >= 60) || (MAP >= 50)) &&
                ((SL <= 5) || (SAA <= 500)) &&
                PH > 7.2 && ((vaso == 0 && MCD == 0 && Hypo == true) || (vaso + MCD == 1 && Hypo == false)))
        {
            result = "SCAI Stage C";
        }
        // Stage E is checked BEFORE Stage D here so that supportCount >= 3 always
        // resolves to E, regardless of the Hypo flag. In the original ordering,
        // (vaso+MCD >= 2 && vaso+MCD <= 5 && Hypo == false) could catch a
        // supportCount of 3, 4, or 5 before the E check ever ran. This is an
        // assumption on my part (see caveat below) -- confirm against your
        // source material before relying on it.
        else if(SL > 10 || PH <= 7.2 || (vaso + MCD) >= 3)
        {
            result = "SCAI Stage E";
        }
        else if((((SBP >= 60) || (MAP >= 50)) &&
                ((SL >= 5 && SL <= 10) || (SAA > 500)) &&
                PH > 7.2) ||
                (((vaso + MCD) >= 2 && (vaso + MCD) <= 5 && Hypo == false) || (vaso + MCD == 1 && Hypo == true)))
        {
            result = "SCAI Stage D";
        }
        else
        {
            // Fallback: shouldn't normally be reached with clinically plausible
            // inputs, but if nothing above matched, defaulting to the most
            // severe stage is the safer failure mode for shock staging.
            result = "SCAI Stage E";
        }

        model.addAttribute("result", result);
        return "fragments/SCAIResult :: resultFragment";
    }
}