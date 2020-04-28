package com.supercalc.app.pageControllers;

import com.supercalc.app.CategoryData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping(value={"/index", "/"})
    public String page(Model model) {
        model.addAttribute("Categories", CategoryData.getCategories());
        return "index";
    }
}
