package usts.pycro.pycslt.product.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import usts.pycro.pycslt.model.entity.product.Brand;
import usts.pycro.pycslt.product.mapper.BrandMapper;
import usts.pycro.pycslt.product.service.BrandService;

import java.util.List;

import static usts.pycro.pycslt.model.entity.product.table.BrandTableDef.BRAND;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-22 16:48
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {
    /**
     * 获取全部品牌
     *
     * @return
     */
    @Override
    public List<Brand> findAll() {
        return list(query()
                .orderBy(BRAND.ID.desc()));
    }
}
