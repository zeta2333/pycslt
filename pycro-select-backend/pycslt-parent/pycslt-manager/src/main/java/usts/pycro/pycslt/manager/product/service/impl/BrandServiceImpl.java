package usts.pycro.pycslt.manager.product.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import usts.pycro.pycslt.manager.product.mapper.BrandMapper;
import usts.pycro.pycslt.manager.product.service.BrandService;
import usts.pycro.pycslt.model.entity.product.Brand;

import java.util.List;

import static usts.pycro.pycslt.model.entity.product.table.BrandTableDef.BRAND;

/**
 * 分类品牌 服务层实现。
 *
 * @author Pycro
 * @since 2023-10-30
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {

    /**
     * 分页查询
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Page<Brand> pageQuery(Integer pageNum, Integer pageSize) {
        return page(new Page<>(pageNum, pageSize), QueryWrapper.create()
                .orderBy(BRAND.ID, false));
    }

    /**
     * 查询所有
     *
     * @return
     */
    @Override
    public List<Brand> findAll() {
        return list(QueryWrapper.create()
                .orderBy(BRAND.ID, false));
    }
}
