package usts.pycro.pycslt.manager.product.categoryBrand;

import com.mybatisflex.core.paginate.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import usts.pycro.pycslt.manager.product.service.CategoryBrandService;
import usts.pycro.pycslt.model.bo.product.CategoryBrandBo;
import usts.pycro.pycslt.model.entity.product.CategoryBrand;

/**
 * @author Pycro
 * @version 1.0
 * 2023-10-30 13:49
 */
@SpringBootTest
public class CategoryBrandTest {
    @Autowired
    private CategoryBrandService service;

    @Test
    public void testPageQuery() {
        CategoryBrandBo categoryBrandBo = new CategoryBrandBo();
        categoryBrandBo.setCategoryId(3L);
        categoryBrandBo.setBrandId(2L);
        Page<CategoryBrand> categoryBrandPage =
                service.pageQuery(1, 10, categoryBrandBo);
        System.out.println(categoryBrandPage);
    }

    @Test
    public void testFindBrandByCategoryId() {
    }
}
