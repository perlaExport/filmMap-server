package pl.perlaexport.filmmap.category.repository;

import org.springframework.data.repository.CrudRepository;
import pl.perlaexport.filmmap.category.model.CategoryEntity;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<CategoryEntity,Long> {
    Optional<CategoryEntity> findByCategoryName(String name);
}
