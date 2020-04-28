package com.supercalc.app;

import lombok.Getter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Getter
public class CategoryData {
    private String name;
    private String description;
    private String link;
    private Map<String, String> methods;

    public CategoryData(String name, String description) {
        this.name = name.substring(0, 1).toUpperCase() + name.substring(1);
        this.description = description;
        this.link = "category/" + name;

        methods = new TreeMap<>();
    }

    public void addMethods(String[] methods) {
        for (String method : methods) {
            String methodFormatted = method.substring(0, method.length() - 5);
            this.methods.put(methodFormatted, "method/" + methodFormatted);
        }
    }

    /**
     * Loads the categories from the categoryData file, gathers all methods present in the corresponding fragment directories.
     * Used in the index page to display available methods (/index or /)
     *
     * @return A list of FunctionCategories with all data
     */
    public static List<CategoryData> getCategories() {
        List<CategoryData> categories = new ArrayList<>();

        try {
            Object object = new JSONParser().parse(new FileReader("src\\main\\resources\\categoryData.json"));

            JSONObject categoryData = (JSONObject) object;

            for (Object category : categoryData.keySet()) {
                String categoryName = (String) category;
                String description = (String) categoryData.get("description");

                CategoryData functionCategory = new CategoryData(categoryName, description);

                String[] methods = new File("src\\main\\resources\\templates\\categories\\" + categoryName).list();

                functionCategory.addMethods(Objects.requireNonNullElseGet(methods, () -> new String[]{"No methods found"}));

                categories.add(functionCategory);
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        return categories;
    }

    /**
     * Gets the data for a category
     * Used on the category-specific landing pages (/category)
     *
     * @param category a specific category to load data for
     * @return a FunctionCategory object for the specified category
     */
    public static CategoryData getCategory(String category) {
        try {
            Object object = new JSONParser().parse(new FileReader("src\\main\\resources\\categoryData.json"));

            JSONObject categoryData = (JSONObject) object;

            String description = (String) ((JSONObject) categoryData.get(category)).get("description");

            CategoryData functionCategory = new CategoryData(category, description);

            String[] methods = new File("src\\main\\resources\\templates\\categories\\" + category).list();

            if (methods != null) {
                functionCategory.addMethods(methods);
            }

            return functionCategory;

        } catch (ParseException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns the file path for a specific method
     *
     * @param method
     * @return
     */
    public static String getMethod(String method) {
        File folder = new File("src\\main\\resources\\templates\\categories");

        File[] files = folder.listFiles();

        if (files == null) {
            System.out.println("no files found");
            return null;
        }

        for (File file : files) {
            File[] methods = file.listFiles();

            if (methods != null) {
                for (File m : methods) {
                    if (m.getName().equals(method + ".html")) {
                        return file.getName() + "/" + method;
                    }
                }
            }
        }

        return null;
    }
}
