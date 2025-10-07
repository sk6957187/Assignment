package in.nayak.foodiesapi.repository;

import in.nayak.foodiesapi.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRespository extends JpaRepository<CartEntity, String> {
    Optional<CartEntity> findByUserId(String userId);
    void deleteByUserId(String userId);
}
