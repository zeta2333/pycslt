package usts.pycro.pycslt.manager.product.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import usts.pycro.pycslt.model.dto.product.ProductBo;
import usts.pycro.pycslt.model.entity.product.Product;

import java.io.Serializable;

/**
 * 商品 服务层。
 *
 * @author Pycro
 * @since 2023-10-30
 */
public interface ProductService extends IService<Product> {

    /**
     * 条件分页查询
     * @param pageNum
     * @param pageSize
     * @param productBo
     * @return
     */
    Page<Product> pageQuery(Integer pageNum, Integer pageSize, ProductBo productBo);

    /**
     * 添加
     * @param product
     */
    void add(Product product);

    /**
     * 根据id获取
     * @param id
     * @return
     */
    Product findById(Serializable id);

    /**
     * 修改
     * @param product
     */
    void updateProductById(Product product);

    /**
     * 根据id删除
     * @param productId
     */
    void deleteById(Serializable productId);

    /**
     * 商品审核
     * @param id
     * @param auditStatus
     */
    void updateAuditStatus(Long id, Integer auditStatus);
}
