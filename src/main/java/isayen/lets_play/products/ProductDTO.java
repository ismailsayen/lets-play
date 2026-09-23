package isayen.lets_play.products;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ProductDTO(

    @NotBlank(message = "Product name  is required.")
    @Size(min = 3, max = 100, message = "Product name must be between 3 and 100 characters.")   
    String name,

    String description,


    @NotNull(message = "Product price is required.")
    @Pattern (regexp = "^\\d+(\\.\\d+)?$", message = "Price must be a valid positive number.")
    String price
) {
}