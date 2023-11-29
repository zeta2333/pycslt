package usts.pycro.pycslt.manager.product.service;

import com.mybatisflex.core.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import usts.pycro.pycslt.model.entity.product.Category;

import java.util.List;

/**
 * 商品分类 服务层。
 *
 * @author Pycro
 * @since 2023-10-29
 */
public interface CategoryService extends IService<Category> {

    /**
     * 根据parentId查询列表
     *
     * @param parentId
     * @return
     */
    List<Category> findList(Long parentId);

    /**
     * 导出
     *
     * @param response
     */
    void exportData(HttpServletResponse response);

    /**
     * 导入
     *
     * @param file
     */
    void importData(MultipartFile file);
}
