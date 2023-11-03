package usts.pycro.pycslt.manager.product.product;

import com.alibaba.fastjson2.JSON;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.util.UpdateEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import usts.pycro.pycslt.manager.product.mapper.ProductMapper;
import usts.pycro.pycslt.manager.product.service.ProductService;
import usts.pycro.pycslt.model.dto.product.ProductBo;
import usts.pycro.pycslt.model.entity.product.Product;

import java.util.ArrayList;
import java.util.List;

import static usts.pycro.pycslt.model.entity.product.table.ProductTableDef.PRODUCT;

/**
 * @author Pycro
 * @version 1.0
 * 2023-10-30 16:22
 */
@SpringBootTest
public class ProductTest {
    @Autowired
    private ProductService service;

    @Autowired
    private ProductMapper mapper;

    @Test
    public void testPageQuery() {
        ProductBo productBo = new ProductBo();
        // productBo.setBrandId(1L);
        // productBo.setCategory1Id(1L);
        // productBo.setCategory2Id(2L);
        // productBo.setCategory3Id(3L);
        Page<Product> productPage = service.pageQuery(1, 10, productBo);
        productPage.getRecords().forEach(System.out::println);
        System.out.println(JSON.toJSONString(productPage.getRecords()));
    }

    @Test
    public void testSql() {
        String msg = null;
        List<Product> list = service.list(QueryWrapper.create()
                .where(PRODUCT.BRAND_ID.isNull(true)));
        System.out.println(list);
    }

    @Test
    public void testInsert() {
        Product product = new Product();
        product.setName("pname");
        product.setProductSkuList(new ArrayList<>());
        product.setDetailsImageUrls("123");
        service.save(product);
    }

    @Test
    public void testUpdatePartField() {
        Product product = UpdateEntity.of(Product.class, 233);
        product.setAuditStatus(null);
        product.setName(null);
        mapper.update(product);
    }
}
