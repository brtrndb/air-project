package tech.brtrndb.airproject.flightplan.service.mapper;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import tech.brtrndb.airproject.flightplan.domain.Product;
import tech.brtrndb.airproject.flightplan.persistence.entity.ProductEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProductMapper {

    public static Product toModel(ProductEntity entity) {
        return new Product(
                entity.getId(),
                CreationInfoMapper.toModel(entity.getCreationInfo()),
                entity.getProductId(),
                entity.getName()
        );
    }

    public static ProductEntity toEntity(Product model) {
        return toEntity(model, false);
    }

    public static ProductEntity toNewEntity(Product model) {
        return toEntity(model, true);
    }

    private static ProductEntity toEntity(Product model, boolean ignoreCreationFields) {
        ProductEntity entity = new ProductEntity();

        if (!ignoreCreationFields) {
            entity.setId(model.getId());
            entity.setCreationInfo(CreationInfoMapper.toEntity(model.getCreationInfo()));
        }
        entity.setProductId(model.getProductId());
        entity.setName(model.getName());

        return entity;
    }

    public static List<Product> toModels(List<ProductEntity> entities) {
        return entities.stream()
                .map(ProductMapper::toModel)
                .collect(Collectors.toList());
    }

}
