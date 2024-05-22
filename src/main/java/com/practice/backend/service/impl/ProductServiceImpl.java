package com.practice.backend.service.impl;

import com.practice.backend.dao.*;
import com.practice.backend.entity.*;
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
        validateProduct(product);

        log.info("Saving product: {}", product);

        Product existingProduct = productDao.findById(product.getId());
        String productId = product.getId();

        if (productExists(product)) {
            productDao.update(product);
        } else {
            productDao.save(product);
        }

        updateRelatedEntities(product);

        log.info("Saved product: {}", product);
    }

    @Override
    public void updateProduct(Product product, String id) {
        validateProductId(id);
        validateProduct(product);

        Product existingProduct = getProductById(id);

        if(existingProduct == null){
            log.error("Product not found with id: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + id);
        }

        product.setId(id);
        log.info("Updating product for ID: {}", id);
        productDao.update(product);

        updateRelatedEntities(product);

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
            List<ProductOfferingPrice> productOfferingPrices = productOfferingPriceDao.getProductOfferingPricesByProductId(id);
            for (ProductOfferingPrice pop : productOfferingPrices){
                Price price = pop.getPrice();
                if (price != null){
                    dutyFreeAmountDao.deleteByPriceId(price.getId());
                    taxIncludedAmountDao.deleteByPriceId(price.getId());
                    priceDao.deleteById(price.getId());
                }
                unitOfMeasureDao.deleteByProductOfferingPriceId(pop.getId());
                validForDao.deleteByProductOfferingPriceId(pop.getId());
            }

            productOfferingPriceDao.deleteByProductId(id);
            productOfferingRelationshipDao.deleteByProductId(id);
            channelDao.deleteByProductId(id);

            productDao.deleteById(id);
            log.info("Deleted product with id: {}", id);
        }
    }

    private void validateProduct(Product product) {
        if (product == null || product.getName() == null || product.getName().isEmpty()) {
            String errorMessage = (product == null) ? "Product cannot be null" : "Product name cannot be null or empty";
            log.error(errorMessage);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
        }
    }

    private void validateProductId(String id) {
        if (id == null || id.isEmpty()) {
            log.error("Product ID cannot be null or empty");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product ID cannot be null or empty");
        }
    }

    private boolean productExists(Product product) {
        return productDao.findById(product.getId()) != null;
    }

    private void updateRelatedEntities(Product product) {
        String productId = product.getId();

        if (product.getChannel() != null) {
            channelDao.updateChannels(productId, product.getChannel());
        }

        if (product.getProductOfferingRelationship() != null) {
            productOfferingRelationshipDao.updateProductOfferingRelationships(productId, product.getProductOfferingRelationship());
        }

        updateProductOfferingPrices(product.getProductOfferingPrice());
    }

    private void updateProductOfferingPrices(List<ProductOfferingPrice> productOfferingPrices) {
        if (productOfferingPrices != null) {
            for (ProductOfferingPrice pop : productOfferingPrices) {
                updatePrice(pop.getPrice(), pop.getId());
                updateUnitOfMeasure(pop.getUnitOfMeasure(), pop.getId());
                updateValidFor(pop.getValidFor(), pop.getId());
            }
        }
    }

    private void updatePrice(Price price, String productOfferingPriceId) {
        if (price != null) {
            Price existingPrice = priceDao.findByProductOfferingPriceId(productOfferingPriceId);
            if (existingPrice == null) {
                int priceId = priceDao.savePrice(price.getProductOfferingPriceId(), price);
                price.setId(priceId);
            } else {
                priceDao.updatePrice(price);
            }

            updateDutyFreeAmount(price.getDutyFreeAmount(), price.getId());
            updateTaxIncludedAmount(price.getTaxIncludedAmount(), price.getId());
        }
    }

    private void updateDutyFreeAmount(DutyFreeAmount dutyFreeAmount, int priceId) {
        if (dutyFreeAmount != null) {
            DutyFreeAmount existingDutyFreeAmount = dutyFreeAmountDao.findByPriceId(priceId);
            if (existingDutyFreeAmount == null) {
                dutyFreeAmount.setPriceId(priceId);
                dutyFreeAmountDao.saveDutyFreeAmount(dutyFreeAmount);
            } else {
                dutyFreeAmountDao.updateDutyFreeAmount(dutyFreeAmount);
            }
        }
    }

    private void updateTaxIncludedAmount(TaxIncludedAmount taxIncludedAmount, int priceId) {
        if (taxIncludedAmount != null) {
            TaxIncludedAmount existingTaxIncludedAmount = taxIncludedAmountDao.findByPriceId(priceId);
            if (existingTaxIncludedAmount == null) {
                taxIncludedAmount.setPriceId(priceId);
                taxIncludedAmountDao.saveTaxIncludedAmount(taxIncludedAmount);
            } else {
                taxIncludedAmountDao.updateTaxIncludedAmount(taxIncludedAmount);
            }
        }
    }

    private void updateUnitOfMeasure(UnitOfMeasure unitOfMeasure, String productOfferingPriceId) {
        if (unitOfMeasure != null) {
            UnitOfMeasure existingUnitOfMeasure = unitOfMeasureDao.findByProductOfferingPriceId(productOfferingPriceId);
            if (existingUnitOfMeasure == null) {
                unitOfMeasureDao.saveUnitOfMeasure(unitOfMeasure);
            } else {
                unitOfMeasureDao.updateUnitOfMeasure(unitOfMeasure);
            }
        }
    }

    private void updateValidFor(ValidFor validFor, String productOfferingPriceId) {
        if (validFor != null) {
            ValidFor existingValidFor = validForDao.findByProductOfferingPriceId(productOfferingPriceId);
            if (existingValidFor == null) {
                validForDao.saveValidFor(validFor);
            } else {
                validForDao.updateValidFor(validFor);
            }
        }
    }
}
