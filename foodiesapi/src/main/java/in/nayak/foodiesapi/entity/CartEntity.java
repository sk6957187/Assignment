package in.nayak.foodiesapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "carts")
public class CartEntity {

    @Id
    @Column(name = "id", length = 36, updatable = false, nullable = false)
    private String id;    // Primary key as String

    private String userId;

    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }

    // Store the map in a separate table: cart_items
    @ElementCollection
    @CollectionTable(
            name = "cart_items",
            joinColumns = @JoinColumn(name = "cart_id")   // FK to carts.id
    )
    @MapKeyColumn(name = "item_key")    // column for the map key
    @Column(name = "item_quantity")     // column for the map value
    private Map<String, Integer> items = new HashMap<>();

    public CartEntity(String userId, HashMap<String, Integer> items) {
        this.userId = userId;
        this.items = items;
    }
}
