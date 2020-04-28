package com.supercalc.app.pageControllers;

import com.supercalc.app.CategoryData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MethodController {
    @GetMapping("/method/{method}")
    public String page(
            @PathVariable String method,
            Model model
    ) {
        System.out.println("Resolving " + method);
        model.addAttribute("method", CategoryData.getMethod(method));
        return "method";
    }
}
