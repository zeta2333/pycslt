package usts.pycro.pycslt.manager.product.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import usts.pycro.pycslt.manager.product.mapper.ProductSpecMapper;
import usts.pycro.pycslt.manager.product.service.ProductSpecService;
import usts.pycro.pycslt.model.entity.product.ProductSpec;

import java.util.List;

import static usts.pycro.pycslt.model.entity.product.table.ProductSpecTableDef.PRODUCT_SPEC;

/**
 * 商品规格 服务层实现。
 *
 * @author Pycro
 * @since 2023-10-30
 */
@Service
public class ProductSpecServiceImpl extends ServiceImpl<ProductSpecMapper, ProductSpec> implements ProductSpecService {
    /**
     * 查询所有
     *
     * @return
     */
    @Override
    public List<ProductSpec> findAll() {
        return list(QueryWrapper.create()
                .orderBy(PRODUCT_SPEC.ID, false));
    }

    /**
     * 分页查询
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Page<ProductSpec> pageQuery(Integer pageNum, Integer pageSize) {
        return page(new Page<>(pageNum, pageSize), QueryWrapper.create()
                .orderBy(PRODUCT_SPEC.ID, false));
    }
}
