package com.claz.repositories;

import com.claz.models.Product;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	@Query("SELECT p FROM Product p WHERE p.category.id=?1")
	List<Product> findAllByCategoryId(int Category_ID);

	@Query("SELECT p FROM Product p WHERE p.Name LIKE %:querySearch% OR p.category.name LIKE %:querySearch%")
	List<Product> findByContentContaining(@Param("querySearch") String querySearch);

	@Query("SELECT p FROM Product p WHERE p.Price BETWEEN ?1 AND ?2")
	List<Product> findByPrice(double minPrice, double maxPrice);

	@Query("SELECT o FROM Product o where o.category.id=?1")
	Page<Product> findbyDMandSort(int dm, Pageable pageable);

	@Query("SELECT p FROM Product p WHERE p.Price BETWEEN ?1 AND ?2")
	Page<Product> findByPricePage(double minPrice, double maxPrice, Pageable pageable);

	List<Product> findAll(Sort sort);

	@Query("SELECT p FROM Product p WHERE p.Hot=true")
	List<Product> findByHot();

	@Query("SELECT p FROM Product p WHERE p.Purchases >= ?1")
	List<Product> findByPurchasesGreaterThan(int purchases);

	@Query("SELECT p FROM Product p WHERE (:categoryId IS NULL OR p.category.id = :categoryId) "
			+ "AND (:genreId IS NULL OR EXISTS (SELECT 1 FROM GenreProduct gp WHERE gp.product.id = p.id AND gp.genre.id = :genreId)) "
			+ "AND (:minPrice IS NULL OR (p.Price * (1 - p.Discount / 100)) >= :minPrice) "
			+ "AND (:maxPrice IS NULL OR (p.Price * (1 - p.Discount / 100)) <= :maxPrice) "
			+ "ORDER BY CASE WHEN :sort = 'price_asc' THEN (p.Price * (1 - p.Discount / 100)) END ASC, "
			+ "CASE WHEN :sort = 'price_desc' THEN (p.Price * (1 - p.Discount / 100)) END DESC, "
			+ "CASE WHEN :sort = 'name_asc' THEN p.Name END ASC, "
			+ "CASE WHEN :sort = 'name_desc' THEN p.Name END DESC")
	List<Product> findByFilters(@Param("categoryId") Integer categoryId, @Param("genreId") Integer genreId,
			@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice, @Param("sort") String sort);

	@Query("SELECT p FROM Product p WHERE :genreId IS NULL OR EXISTS (SELECT 1 FROM GenreProduct gp WHERE gp.product.id = p.id AND gp.genre.id = :genreId)")
	List<Product> findAllByGenreId(@Param("genreId") int genreId);

	@Query("SELECT SUM(p.Quantity) FROM Product p")
	int getTotalProductQuantity();

	@Query("SELECT p FROM Product p WHERE p.Discount > 0")
	List<Product> findProductsWithDiscount();
}
