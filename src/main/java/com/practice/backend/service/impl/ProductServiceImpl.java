package com.practice.backend.service.impl;

import com.practice.backend.dao.*;
import com.practice.backend.dto.ProductDto;
import com.practice.backend.entity.*;
import com.practice.backend.service.ProductService;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductDao productDao, ChannelDao channelDao, ProductOfferingPriceDao productOfferingPriceDao, ProductOfferingRelationshipDao productOfferingRelationshipDao, PriceDao priceDao, DutyFreeAmountDao dutyFreeAmountDao, TaxIncludedAmountDao taxIncludedAmountDao, UnitOfMeasureDao unitOfMeasureDao, ValidForDao validForDao, ModelMapper modelMapper) {
        this.productDao = productDao;
        this.channelDao = channelDao;
        this.productOfferingPriceDao = productOfferingPriceDao;
        this.productOfferingRelationshipDao = productOfferingRelationshipDao;
        this.priceDao = priceDao;
        this.dutyFreeAmountDao = dutyFreeAmountDao;
        this.taxIncludedAmountDao = taxIncludedAmountDao;
        this.unitOfMeasureDao = unitOfMeasureDao;
        this.validForDao = validForDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductDto getProductById(String id) {
        log.info("Getting product by ID: {}", id);
        Product product = productDao.findById(id);

        if (product == null) {
            log.error("Product not found with id: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + id);
        }

        log.info("Fetched Product: {}", product);
        try {
            return modelMapper.map(product, ProductDto.class);
        } catch (Exception e) {
            log.error("Error mapping product to response dto: ", e);
            throw new RuntimeException("Error mapping product to response dto: ", e);
        }
    }

    @Override
    public List<ProductDto> getAllProducts() {
        log.info("Fetching all products");
        List<Product> products = productDao.findAll();

        if (products == null) {
            log.error("Something went wrong, the response is null");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong!");
        }

        log.info("Fetched products: {}", products);
        try {
            return products.stream()
                    .map(product -> modelMapper.map(product, ProductDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error mapping product to response dto: ", e);
            throw new RuntimeException("Error mapping product to response dto: ", e);
        }
    }

    @Override
    public ProductDto saveProduct(Product product) {
        Product savedProduct;

        validateProduct(product);

        log.info("Saving product: {}", product);

        if (productExists(product)) {
            savedProduct = productDao.update(product);
        } else {
            savedProduct = productDao.save(product);
        }

        if (product.getChannel() != null) {
            savedProduct.setChannel(channelDao.updateChannels(product.getId(), product.getChannel()));
        }

        if (product.getProductOfferingRelationship() != null) {
            savedProduct.setProductOfferingRelationship(productOfferingRelationshipDao.updateProductOfferingRelationships(product.getId(), product.getProductOfferingRelationship()));
        }

        savedProduct.setProductOfferingPrice(updateProductOfferingPrices(product.getId(), product.getProductOfferingPrice()));

        try {
            ProductDto productDto = modelMapper.map(savedProduct, ProductDto.class);
            log.info("mapped product: {}", productDto);
            log.info("Saved product: {}", savedProduct);
            return productDto;
        } catch (Exception e) {
            log.error("Error mapping product to response dto: ", e);
            throw new RuntimeException("Error mapping product to response dto: ", e);
        }
    }

    @Override
    public ProductDto updateProduct(Product product, String id) {
        Product updatedProduct;
        validateProductId(id);
        validateProduct(product);

        ProductDto existingProduct = getProductById(id);

        if (existingProduct == null) {
            log.error("Product not found with id: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + id);
        }

        product.setId(id);
        log.info("Updating product for ID: {}", id);
        updatedProduct = productDao.update(product);

        if (product.getChannel() != null) {
            updatedProduct.setChannel(channelDao.updateChannels(product.getId(), product.getChannel()));
        }

        if (product.getProductOfferingRelationship() != null) {
            updatedProduct.setProductOfferingRelationship(productOfferingRelationshipDao.updateProductOfferingRelationships(product.getId(), product.getProductOfferingRelationship()));
        }

        updatedProduct.setProductOfferingPrice(updateProductOfferingPrices(product.getId(), product.getProductOfferingPrice()));

        log.info("Updated product: {}", updatedProduct);

        try {
            return modelMapper.map(updatedProduct, ProductDto.class);
        } catch (Exception e) {
            log.error("Error mapping product to response dto: ", e);
            throw new RuntimeException("Error mapping product to response dto: ", e);
        }
    }

    @Override
    public void deleteProductById(String id) {
        if (id == null || id.isEmpty()) {
            log.error("Product ID cannot be null or empty");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product ID cannot be null or empty");
        }

        ProductDto existingProduct = getProductById(id);

        if (existingProduct != null) {
            log.info("Deleting product with id: {}", id);
            List<ProductOfferingPrice> productOfferingPrices = productOfferingPriceDao.getProductOfferingPricesByProductId(id);
            for (ProductOfferingPrice pop : productOfferingPrices) {
                Price price = pop.getPrice();
                if (price != null) {
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
        try {
            return productDao.findById(product.getId()) != null;
        } catch (EmptyResultDataAccessException e) {
            log.error("Error while checking product existence", e);
            return false;
        }
    }

    private List<ProductOfferingPrice> updateProductOfferingPrices(String productId, List<ProductOfferingPrice> productOfferingPrices) {
        log.info("Updating product offering prices for product id: " + productId);
        List<ProductOfferingPrice> productOfferingPriceList = new ArrayList<>();
        if (productOfferingPrices != null) {
            productOfferingPriceDao.updateProductOfferingPrices(productId, productOfferingPrices);

            for (ProductOfferingPrice pop : productOfferingPrices) {
                Price price = updatePrice(pop.getPrice(), pop.getId());
                UnitOfMeasure unitOfMeasure = updateUnitOfMeasure(pop.getUnitOfMeasure(), pop.getId());
                ValidFor validFor = updateValidFor(pop.getValidFor(), pop.getId());

                pop.setPrice(price);
                pop.setUnitOfMeasure(unitOfMeasure);
                pop.setValidFor(validFor);
                productOfferingPriceList.add(pop);
            }
        }
        log.info("Updated product offering prices: " + productOfferingPriceList);
        return productOfferingPriceList;
    }

    private Price updatePrice(Price price, String productOfferingPriceId) {
        log.info("Updating price for product offering price id: " + productOfferingPriceId);
        Price existingPrice = null;
        if (price != null) {
            try {
                existingPrice = priceDao.findByProductOfferingPriceId(productOfferingPriceId);
                price.setId(existingPrice.getId());
                log.info("Found existing price: {}", existingPrice);
            } catch (EmptyResultDataAccessException ignored) {
                log.warn("No existing price found for product offering price id: " + productOfferingPriceId);
                existingPrice = null;
            }
            if (existingPrice == null) {
                existingPrice = priceDao.savePrice(productOfferingPriceId, price);
                price.setId(existingPrice.getId());
            } else {
                existingPrice = priceDao.updatePrice(price);
            }

            log.info("Price: {}", price);
            DutyFreeAmount dutyFreeAmount = updateDutyFreeAmount(price.getDutyFreeAmount(), price.getId());
            TaxIncludedAmount taxIncludedAmount = updateTaxIncludedAmount(price.getTaxIncludedAmount(), price.getId());

            existingPrice.setDutyFreeAmount(dutyFreeAmount);
            existingPrice.setTaxIncludedAmount(taxIncludedAmount);
        }
        log.info("Updated price: " + existingPrice);
        return existingPrice;
    }

    private DutyFreeAmount updateDutyFreeAmount(DutyFreeAmount dutyFreeAmount, int priceId) {
        log.info("Updating DutyFreeAmount for price id: " + priceId);
        DutyFreeAmount existingDutyFreeAmount = null;
        if (dutyFreeAmount != null) {
            try {
                existingDutyFreeAmount = dutyFreeAmountDao.findByPriceId(priceId);
            } catch (EmptyResultDataAccessException ignored) {
                log.warn("No existing DutyFreeAmount found for price id: " + priceId);
                existingDutyFreeAmount = null;
            }
            if (existingDutyFreeAmount == null) {
                dutyFreeAmount.setPriceId(priceId);
                existingDutyFreeAmount = dutyFreeAmountDao.saveDutyFreeAmount(dutyFreeAmount);
            } else {
                existingDutyFreeAmount = dutyFreeAmountDao.updateDutyFreeAmount(dutyFreeAmount);
            }
        }
        log.info("Updated DutyFreeAmount: " + existingDutyFreeAmount);
        return existingDutyFreeAmount;
    }

    private TaxIncludedAmount updateTaxIncludedAmount(TaxIncludedAmount taxIncludedAmount, int priceId) {
        log.info("Updating TaxIncludedAmount for price id: " + priceId);
        TaxIncludedAmount existingTaxIncludedAmount = null;
        if (taxIncludedAmount != null) {
            try {
                existingTaxIncludedAmount = taxIncludedAmountDao.findByPriceId(priceId);
            } catch (EmptyResultDataAccessException ignored) {
                log.warn("No existing TaxIncludedAmount found for price id: " + priceId);
                existingTaxIncludedAmount = null;
            }
            if (existingTaxIncludedAmount == null) {
                taxIncludedAmount.setPriceId(priceId);
                existingTaxIncludedAmount = taxIncludedAmountDao.saveTaxIncludedAmount(taxIncludedAmount);
            } else {
                existingTaxIncludedAmount = taxIncludedAmountDao.updateTaxIncludedAmount(taxIncludedAmount);
            }
        }
        log.info("Updated TaxIncludedAmount: " + existingTaxIncludedAmount);
        return existingTaxIncludedAmount;
    }

    private UnitOfMeasure updateUnitOfMeasure(UnitOfMeasure unitOfMeasure, String productOfferingPriceId) {
        log.info("Updating UnitOfMeasure for product offering price id: " + productOfferingPriceId);
        UnitOfMeasure existingUnitOfMeasure = null;
        if (unitOfMeasure != null) {
            unitOfMeasure.setProductOfferingPriceId(productOfferingPriceId);
            try {
                existingUnitOfMeasure = unitOfMeasureDao.findByProductOfferingPriceId(productOfferingPriceId);
            } catch (EmptyResultDataAccessException ignored) {
                log.warn("No existing UnitOfMeasure found for product offering price id: " + productOfferingPriceId);
                existingUnitOfMeasure = null;
            }
            if (existingUnitOfMeasure == null) {
                existingUnitOfMeasure = unitOfMeasureDao.saveUnitOfMeasure(unitOfMeasure);
            } else {
                existingUnitOfMeasure = unitOfMeasureDao.updateUnitOfMeasure(unitOfMeasure);
            }
        }
        log.info("Updated UnitOfMeasure: " + existingUnitOfMeasure);
        return existingUnitOfMeasure;
    }

    private ValidFor updateValidFor(ValidFor validFor, String productOfferingPriceId) {
        log.info("Updating ValidFor for product offering price id: " + productOfferingPriceId);
        ValidFor existingValidFor = null;
        if (validFor != null) {
            validFor.setProductOfferingPriceId(productOfferingPriceId);
            try {
                existingValidFor = validForDao.findByProductOfferingPriceId(productOfferingPriceId);
            } catch (EmptyResultDataAccessException ignored) {
                log.warn("No existing ValidFor found for product offering price id: " + productOfferingPriceId);
                existingValidFor = null;
            }
            if (existingValidFor == null) {
                existingValidFor = validForDao.saveValidFor(validFor);
            } else {
                existingValidFor = validForDao.updateValidFor(validFor);
            }
        }
        log.info("Updated ValidFor: " + existingValidFor);
        return existingValidFor;
    }
}
