package bootcamp.fufnProject.beans;

import bootcamp.fufnProject.entities.Brands;
import bootcamp.fufnProject.entities.Categories;
import bootcamp.fufnProject.entities.ShopItems;
import bootcamp.fufnProject.entities.Users;
import bootcamp.fufnProject.repositories.BrandRepository;
import bootcamp.fufnProject.repositories.CategoryRepository;
import bootcamp.fufnProject.repositories.ShopItemsRepository;
import bootcamp.fufnProject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class DataBaseBean{

    @Autowired
    private ShopItemsRepository shopItemsRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    public List<ShopItems> getAllShopItems(){
        return shopItemsRepository.findAllByAmountGreaterThanEqualAndPriceGreaterThanEqual(0, 0);
    }

    public ShopItems getShopItem(Long id){
        return shopItemsRepository.findByAmountGreaterThanEqualAndPriceGreaterThanEqualAndIdEquals(0, 0, id);
    }

    public void addShopItem(ShopItems item){
        shopItemsRepository.save(item);
    }

    public void deleteShopItem(Long id){
        shopItemsRepository.deleteById(id);
    }

    public void saveShopItem(ShopItems item){
        shopItemsRepository.save(item);
    }

    public List<Brands> getAllBrand(){
        return brandRepository.findAll();
    }

    public Brands getBrand(Long id){
        return brandRepository.findById(id).orElse(null);
    }

    public List<Categories> getAllCategories(){
        return categoryRepository.findAll();
    }

    public Categories getCategory(Long id){
        return categoryRepository.findById(id).orElse(null);
    }

}

