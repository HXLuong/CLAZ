package com.claz.serviceImpls;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.claz.models.Comment;
import com.claz.models.Galary;
import com.claz.models.GenreProduct;
import com.claz.models.OrderDetail;
import com.claz.models.Product;
import com.claz.models.Rating;
import com.claz.models.Reply;
import com.claz.repositories.CommentRepository;
import com.claz.repositories.GalaryRepository;
import com.claz.repositories.GenreProductRepository;
import com.claz.repositories.OrderDetailRepository;
import com.claz.repositories.ProductRepository;
import com.claz.repositories.RatingRepository;
import com.claz.repositories.ReplyRepository;
import com.claz.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private GenreProductRepository genreProductRepository;

	@Autowired
	private GalaryRepository galaryRepository;

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private ReplyRepository replyRepository;

	@Autowired
	private RatingRepository ratingRepository;

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public Product finById(int id) {
		Optional<Product> product = productRepository.findById(id);
		return product.orElse(null);
	}

	@Override
	public List<Product> findAllByCategoryId(int Category_ID) {
		return productRepository.findAllByCategoryId(Category_ID);
	}

	@Override
	public List<Product> findBySearch(String search) {
		return productRepository.findByContentContaining(search);
	}

	@Override
	public Product create(Product product) {
		return productRepository.save(product);
	}

	@Override
	public Product update(Product product) {
		return productRepository.save(product);
	}

	@Override
	public void delete(int id) {
		List<OrderDetail> orderDetails = orderDetailRepository.findByProductId(id);
		for (OrderDetail orderDetail : orderDetails) {
			orderDetailRepository.delete(orderDetail);
		}

		List<Galary> galaries = galaryRepository.findAllByProductId(id);
		for (Galary galary : galaries) {
			galaryRepository.delete(galary);
		}

		List<GenreProduct> genreProducts = genreProductRepository.findAllByProductId(id);
		for (GenreProduct genreProduct : genreProducts) {
			genreProductRepository.delete(genreProduct);
		}

		List<Comment> comments = commentRepository.findByProductId(id);
		for (Comment comment : comments) {
			List<Reply> replies = replyRepository.findByCommentId(comment.getId());
			for (Reply reply : replies) {
				replyRepository.delete(reply);
			}
			commentRepository.delete(comment);
		}

		List<Rating> ratings = ratingRepository.findByProductId(id);
		for (Rating rating : ratings) {
			ratingRepository.delete(rating);
		}

		productRepository.deleteById(id);
	}

	@Override
	public List<Product> findByPrice(double minPrice, double maxPrice) {
		return productRepository.findByPrice(minPrice, maxPrice);
	}

	@Override
	public Page<Product> findbyDMandSort(int dm, Pageable pageable) {
		return productRepository.findbyDMandSort(dm, pageable);
	}

	@Override
	public Page<Product> findByPricePage(double minPrice, double maxPrice, Pageable pageable) {
		return productRepository.findByPricePage(minPrice, maxPrice, pageable);
	}

	@Override
	public List<Product> fillbyprice(Sort sort) {
		return productRepository.findAll(sort);
	}

	@Override
	public Page<Product> findAll(Pageable pageable) {
		return productRepository.findAll(pageable);
	}

	@Override
	public Optional<Product> findById(int id) {
		Optional<Product> product = productRepository.findById(id);
		return product;
	}

	@Override
	public Map<String, Double> getRevenuePerMonth() {
		List<Product> products = productRepository.findAll();

		Map<String, Double> revenueMap = products.stream().collect(Collectors.groupingBy(product -> {
			if (product.getCreated_at() != null) {
				LocalDate date = product.getCreated_at().toLocalDate();
				return date.getMonth().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("vi"));
			}
			return null;
		}, Collectors.summingDouble(product -> product.getTotal_Pay() != null ? product.getTotal_Pay() : 0.0)));
		return revenueMap;
	}

	@Override
	public long countTotalProduct() {
		return productRepository.count();
	}

	@Override
	public Map<String, Double> getRevenueByDateRange(LocalDate startDate, LocalDate endDate) {
		List<Product> products = productRepository.findAll();
		return products.stream()
				.filter(p -> p.getCreated_at() != null && (p.getCreated_at().toLocalDate().isEqual(startDate)
						|| p.getCreated_at().toLocalDate().isEqual(endDate)
						|| (p.getCreated_at().toLocalDate().isAfter(startDate)
								&& p.getCreated_at().toLocalDate().isBefore(endDate))))
				.collect(Collectors.groupingBy(
						product -> product.getCreated_at().toLocalDate().getMonth().getDisplayName(TextStyle.FULL,
								Locale.forLanguageTag("vi")),
						Collectors.summingDouble(
								product -> product.getTotal_Pay() != null ? product.getTotal_Pay() : 0.0)));
	}

	@Override
	public List<Product> findByHot() {
		return productRepository.findByHot();
	}

	@Override
	public List<Product> findByBestSeller(int purchases) {
		return productRepository.findByPurchasesGreaterThan(purchases);
	}

	@Override
	public List<Product> findProducts(Integer categoryId, Integer genreId, Double minPrice, Double maxPrice,
			String sort) {
		return productRepository.findByFilters(categoryId, genreId, minPrice, maxPrice, sort);
	}

	@Override
	public List<Product> findAllByGenreId(int Genre_ID) {
		return productRepository.findAllByGenreId(Genre_ID);
	}

}
