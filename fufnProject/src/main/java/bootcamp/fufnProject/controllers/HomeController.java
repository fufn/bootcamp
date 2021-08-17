package bootcamp.fufnProject.controllers;

import bootcamp.fufnProject.beans.DataBaseBean;
import bootcamp.fufnProject.entities.Brands;
import bootcamp.fufnProject.entities.Categories;
import bootcamp.fufnProject.entities.ShopItems;
import bootcamp.fufnProject.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    @Qualifier("db")
    private DataBaseBean dataBaseBean;

    @GetMapping(value = "/")
    public String index(Model model){
        model.addAttribute("CURRENT_USER", getUser());
        List<ShopItems> items = dataBaseBean.getAllShopItems();
        model.addAttribute("items", items);
        return ("index");

    }

    @GetMapping(value = "/about")
    public String about(Model model){
        return ("about");
    }

    @GetMapping(value = "/additem")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String addItem(Model model){
        model.addAttribute("CURRENT_USER", getUser());
        List<ShopItems> items = dataBaseBean.getAllShopItems();
        List<Brands> brands = dataBaseBean.getAllBrand();
        model.addAttribute("brands", brands);
        model.addAttribute("items", items);
        List<Categories> categories = dataBaseBean.getAllCategories();
        model.addAttribute("categories", categories);
        return ("addItem");
    }

    @PostMapping(value = "/additem")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String toaddItem(@RequestParam(name = "name", defaultValue = "No Name") String name,
                            @RequestParam(name = "description") String description,
                            @RequestParam(name = "price") double price,
                            @RequestParam(name = "amount") int amount,
                            @RequestParam(name = "url") String url,
                            @RequestParam(name = "brand_id") Long brand_id){

        Brands brand = dataBaseBean.getBrand(brand_id);

        if (brand != null){
            ShopItems item = new ShopItems();
            item.setAmount(amount);
            item.setPrice(price);
            item.setDescription(description);
            item.setName(name);
            item.setPicture_url(url);
            item.setBrand(brand);
            dataBaseBean.addShopItem(item);

            return "redirect:/additem?successadd";

        }

        return "redirect:/additem?fail";
    }

    @GetMapping(value = "/itemdetail/{id}-item.html")
    public String itemDetail(Model model,
                             @PathVariable (name = "id") Long id){
        model.addAttribute("CURRENT_USER", getUser());
        ShopItems item = dataBaseBean.getShopItem(id);
        model.addAttribute("item", item);
        List<Brands> brands = dataBaseBean.getAllBrand();
        model.addAttribute("brands", brands);
        List<Categories> categories = dataBaseBean.getAllCategories();
        categories.removeAll(item.getCategories());
        model.addAttribute("categories", categories);

        return ("itemDetail");
    }

    @PostMapping(value = "/saveitem")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String saveItem(@RequestParam(name = "name") String name,
                           @RequestParam(name = "description") String description,
                           @RequestParam(name = "price") double price,
                           @RequestParam(name = "amount") int amount,
                           @RequestParam(name = "id") Long id,
                           @RequestParam(name = "url") String url,
                           @RequestParam(name = "brand_id") Long brand_id){

        ShopItems item = dataBaseBean.getShopItem(id);
        if (item != null){

            Brands brand = dataBaseBean.getBrand(brand_id);

            if (brand != null){
                item.setAmount(amount);
                item.setPrice(price);
                item.setDescription(description);
                item.setName(name);
                item.setPicture_url(url);
                item.setBrand(brand);
                dataBaseBean.saveShopItem(item);

                return ("redirect:/itemdetail/" + item.getId() + "-item.html?success");

            }
        }

        return ("redirect:/itemdetail/" + item.getId() + "-item.html?fail");

    }

    @PostMapping(value = "/deleteitem")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String editItem(@RequestParam(name = "id") Long id){

        dataBaseBean.deleteShopItem(id);

        return "redirect:/additem?success";

    }

    @PostMapping(value = "/assigncategory")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String assignCategory(@RequestParam(name = "id") Long id,
                                 @RequestParam(name = "category_id") Long category_id){

        Categories category = dataBaseBean.getCategory(category_id);

        if (category != null) {

            ShopItems item = dataBaseBean.getShopItem(id);

            if (item != null){

                List<Categories> categories = item.getCategories();

                if (categories==null){
                    categories = new ArrayList<>();
                }

                if (!categories.contains(category)){
                    categories.add(category);
                }

                item.setCategories(categories);
                dataBaseBean.saveShopItem(item);

                return ("redirect:/itemdetail/" + item.getId() + "-item.html");
            }

        }

        return ("redirect:/itemdetail/" + id + "-item.html?fail");

    }

    @PostMapping(value = "/deletecategory")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String deleteCategory(@RequestParam(name = "id") Long id,
                                 @RequestParam(name = "category_id") Long category_id){

        Categories category = dataBaseBean.getCategory(category_id);

        if (category != null) {

            ShopItems item = dataBaseBean.getShopItem(id);

            if (item != null){

                List<Categories> categories = item.getCategories();

                if (categories==null){
                    categories = new ArrayList<>();
                }

                if (categories.contains(category)){
                    categories.remove(category);
                }

                item.setCategories(categories);
                dataBaseBean.saveShopItem(item);

                return ("redirect:/itemdetail/" + item.getId() + "-item.html");
            }

        }

        return ("redirect:/itemdetail/" + id + "-item.html?fail");

    }

    @GetMapping(value = "/loginpage")
    public String loginPage(Model model){
        return "login";
    }

    @GetMapping(value = "/profile")
    @PreAuthorize("isAuthenticated()")
    public String profilePage(Model model){
        model.addAttribute("CURRENT_USER", getUser());
        return "profile";
    }

    @GetMapping(value = "/accessdeniedpage")
    public String accessDeniedPage(Model model){
        return "403";
    }

    @PostMapping(value = "/addbucket")
    @PreAuthorize("isAuthenticated()")
    public String addBucket(@RequestParam(name = "id") Long id){

        Users user = getUser();
        System.out.println(user.getShopItems());
        ShopItems item = dataBaseBean.getShopItem(id);

        if (item != null) {

            List<ShopItems> shopItems = user.getShopItems();
            System.out.println(user.getShopItems());

            if (shopItems == null) {

                shopItems = new ArrayList<>();

            }

            shopItems.add(item);

            user.setShopItems(shopItems);


            return "redirect:/?success";

        }

        return "redirect:/?fail";

    }

    private Users getUser(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)){
            Users user = (Users)authentication.getPrincipal();
            return user;
        } else {
            return null;
        }

    }



}
