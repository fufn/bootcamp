package bootcamp.fufnProject.repositories;

import bootcamp.fufnProject.entities.Brands;
import bootcamp.fufnProject.entities.Categories;
import bootcamp.fufnProject.entities.ShopItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ShopItemsRepository extends JpaRepository<ShopItems, Long> {

    List<ShopItems> findAllByAmountGreaterThanEqualAndPriceGreaterThanEqual(int amount, double price);
    ShopItems findByAmountGreaterThanEqualAndPriceGreaterThanEqualAndIdEquals(int amount, double price, Long id);
    List<ShopItems> findAllByBrandAndCategories(Brands brand, Categories categories);
}
