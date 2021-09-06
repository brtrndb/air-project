package tech.brtrndb.airproject.flightplan.service.mapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import tech.brtrndb.airproject.flightplan.domain.ProductQuantity;
import tech.brtrndb.airproject.flightplan.domain.Store;
import tech.brtrndb.airproject.flightplan.persistence.entity.StockEntity;
import tech.brtrndb.airproject.flightplan.persistence.entity.StoreEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StoreMapper {

    public static Store toModel(StoreEntity entity) {
        Map<String, Integer> rawProductQuantity = entity.getProductStocks()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getQuantity()));

        return new Store(
                entity.getId(),
                CreationInfoMapper.toModel(entity.getCreationInfo()),
                entity.getStoreId(),
                PositionMapper.toModel(entity.getPosition()),
                new ProductQuantity(rawProductQuantity)
        );
    }

    public static StoreEntity toEntity(Store model) {
        return toEntity(model, false);
    }

    public static StoreEntity toNewEntity(Store model) {
        return toEntity(model, true);
    }

    private static StoreEntity toEntity(Store model, boolean ignoreCreationFields) {
        StoreEntity entity = new StoreEntity();
        Map<String, StockEntity> stocks = model.getProductStocks()
                .getAllProductQuantity()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> new StockEntity(model.getStoreId(), e.getKey(), e.getValue())));

        if (!ignoreCreationFields) {
            entity.setId(model.getId());
            entity.setCreationInfo(CreationInfoMapper.toEntity(model.getCreationInfo()));
        }
        entity.setStoreId(model.getStoreId());
        entity.setPosition(PositionMapper.toEntity(model.getPosition()));
        entity.setProductStocks(stocks);

        return entity;
    }

    public static List<Store> toModels(List<StoreEntity> entities) {
        return entities.stream()
                .map(StoreMapper::toModel)
                .collect(Collectors.toList());
    }

}
