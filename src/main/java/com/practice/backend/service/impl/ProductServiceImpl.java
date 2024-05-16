package com.practice.backend.service.impl;

import com.practice.backend.dao.*;
import com.practice.backend.entity.Product;
import com.practice.backend.entity.ProductOfferingPrice;
import com.practice.backend.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LogManager.getLogger(ProductServiceImpl.class);
    private final ProductDao productDao;
    private final ChannelDao channelDao;
    private final ProductOfferingPriceDao productOfferingPriceDao;
    private final ProductOfferingRelationshipDao productOfferingRelationshipDao;
    private final PriceDao priceDao;
    private final DutyFreeAmountDao dutyFreeAmountDao;
    private final TaxIncludedAmountDao taxIncludedAmountDao;
    private final UnitOfMeasureDao unitOfMeasureDao;
    private final ValidForDao validForDao;

    @Autowired
    public ProductServiceImpl(ProductDao productDao, ChannelDao channelDao, ProductOfferingPriceDao productOfferingPriceDao, ProductOfferingRelationshipDao productOfferingRelationshipDao, PriceDao priceDao, DutyFreeAmountDao dutyFreeAmountDao, TaxIncludedAmountDao taxIncludedAmountDao, UnitOfMeasureDao unitOfMeasureDao, ValidForDao validForDao) {
        this.productDao = productDao;
        this.channelDao = channelDao;
        this.productOfferingPriceDao = productOfferingPriceDao;
        this.productOfferingRelationshipDao = productOfferingRelationshipDao;
        this.priceDao = priceDao;
        this.dutyFreeAmountDao = dutyFreeAmountDao;
        this.taxIncludedAmountDao = taxIncludedAmountDao;
        this.unitOfMeasureDao = unitOfMeasureDao;
        this.validForDao = validForDao;
    }

    @Override
    public Product getProductById(String id) {
        log.info("Getting product by ID: {}", id);
        Product product = productDao.findById(id);

        if(product == null){
            log.error("Product not found with id: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + id);
        }

        log.info("Fetched Product: {}", product);
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        log.info("Fetching all products");
        List<Product> products = productDao.findAll();

        if(products == null){
            log.error("Something went wrong, the response is null");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong!");
        }

        log.info("Fetched products: {}", products);
        return products;
    }

    @Override
    public void saveProduct(Product product) {
        if (product == null){
            log.error("Product cannot be null");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product cannot be null");
        }

        if(product.getName() == null || product.getName().isEmpty()){
            log.error("Product name cannot be null or empty");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product name cannot be null or empty");
        }

        log.info("Saving product: {}", product);

        String productId = product.getId();

        productDao.save(product);
        if (product.getChannel() != null) channelDao.saveChannels(productId, product.getChannel());
        if (product.getProductOfferingRelationship() != null) productOfferingRelationshipDao.saveProductOfferingRelationships(productId, product.getProductOfferingRelationship());

        if (product.getProductOfferingPrice() != null){
            productOfferingPriceDao.saveProductOfferingPrices(productId, product.getProductOfferingPrice());

            for (ProductOfferingPrice offeringPrice : product.getProductOfferingPrice()){
                int priceId = priceDao.savePrice(offeringPrice.getId(), offeringPrice.getPrice());
                //TODO complete the save method...
            }
        }

        log.info("Saved product: {}", product);
    }

    @Override
    public void updateProduct(Product product, String id) {
        if (id == null || id.isEmpty()) {
            log.error("Product ID cannot be null or empty");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product ID cannot be null or empty");
        }

        if(product == null){
            log.error("Product cannot be null");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product cannot be null");
        }

        if(product.getName() == null || product.getName().isEmpty()){
            log.error("Product name cannot be null or empty");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product name cannot be null or empty");
        }

        Product existingProduct = getProductById(id);

        if(existingProduct == null){
            log.error("Product not found with id: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + id);
        }

        product.setId(id);
        log.info("Updating product for ID: {}", id);
        productDao.update(product);
        log.info("Updated product: {}", product);
    }

    @Override
    public void deleteProductById(String id) {
        if (id == null || id.isEmpty()) {
            log.error("Product ID cannot be null or empty");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product ID cannot be null or empty");
        }

        Product existingProduct = getProductById(id);

        if(existingProduct != null){
            log.info("Deleting product with id: {}", id);
            productDao.deleteById(id);
            log.info("Deleted product with id: {}", id);
        }
    }
}
