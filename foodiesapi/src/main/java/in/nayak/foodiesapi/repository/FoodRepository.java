package in.nayak.foodiesapi.repository;

import in.nayak.foodiesapi.entity.FoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<FoodEntity, String> {
}
