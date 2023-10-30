package usts.pycro.pycslt.manager.product.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import usts.pycro.pycslt.manager.product.mapper.ProductUnitMapper;
import usts.pycro.pycslt.manager.product.service.ProductUnitService;
import usts.pycro.pycslt.model.entity.product.ProductUnit;

import java.util.List;

import static usts.pycro.pycslt.model.entity.product.table.ProductUnitTableDef.PRODUCT_UNIT;

/**
 * 商品单位 服务层实现。
 *
 * @author Pycro
 * @since 2023-10-30
 */
@Service
public class ProductUnitServiceImpl extends ServiceImpl<ProductUnitMapper, ProductUnit> implements ProductUnitService {

    /**
     * 查询所有
     *
     * @return
     */
    @Override
    public List<ProductUnit> findAll() {
        return list(QueryWrapper.create()
                .orderBy(PRODUCT_UNIT.ID, false));
    }
}
