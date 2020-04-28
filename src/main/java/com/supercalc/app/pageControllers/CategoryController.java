package com.supercalc.app.pageControllers;

import com.supercalc.app.CategoryData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CategoryController {
    @GetMapping("category/{category}")
    public String page(
            @PathVariable String category,
            Model model) {
        model.addAttribute("Category", CategoryData.getCategory(category));
        return "category";
    }
}
