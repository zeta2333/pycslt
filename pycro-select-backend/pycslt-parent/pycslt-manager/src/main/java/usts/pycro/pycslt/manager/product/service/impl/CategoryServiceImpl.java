package usts.pycro.pycslt.manager.product.service.impl;

import com.alibaba.excel.EasyExcel;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import usts.pycro.pycslt.common.exception.ServiceException;
import usts.pycro.pycslt.manager.product.mapper.CategoryMapper;
import usts.pycro.pycslt.manager.product.service.CategoryService;
import usts.pycro.pycslt.manager.util.tool.ExcelListener;
import usts.pycro.pycslt.model.entity.product.Category;
import usts.pycro.pycslt.model.vo.common.ResultCodeEnum;
import usts.pycro.pycslt.model.vo.product.CategoryExcelVo;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static usts.pycro.pycslt.model.entity.product.table.CategoryTableDef.CATEGORY;

/**
 * 商品分类 服务层实现。
 *
 * @author Pycro
 * @since 2023-10-29
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    /**
     * 根据parentId查询列表
     *
     * @param parentId
     * @return
     */
    @Override
    public List<Category> findList(Long parentId) {
        // 根据parentId查询第一层分类，返回list集合
        List<Category> categories = list(QueryWrapper.create()
                .where(CATEGORY.PARENT_ID.eq(parentId))
                .orderBy(CATEGORY.ID, false));

        // 遍历list，判断是否存在下一层
        categories.forEach(category -> {
            long subCategoryCnt = count(QueryWrapper.create()
                    .where(CATEGORY.PARENT_ID.eq(category.getId())));
            // 设置hasChildren属性
            category.setHasChildren(subCategoryCnt > 0);
        });

        return categories;
    }

    /**
     * 导入
     *
     * @param file
     */
    @Override
    public void importData(MultipartFile file) {
        // 监听器
        ExcelListener<CategoryExcelVo> excelListener = new ExcelListener<>(mapper);
        try {
            EasyExcel.read(file.getInputStream(), CategoryExcelVo.class, excelListener)
                    .sheet().doRead();
        } catch (IOException e) {
            throw new ServiceException(ResultCodeEnum.DATA_ERROR);
        }
    }

    /**
     * 导出
     *
     * @param response
     */
    @Override
    public void exportData(HttpServletResponse response) {
        try {
            // 1 设置响应头和其他信息
            // 设置响应结果类型
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("分类数据", StandardCharsets.UTF_8);
            // 设置响应头信息
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            // 2 查询所有记录，封装为list集合
            List<Category> categories = list(QueryWrapper.create()
                    .orderBy(CATEGORY.ID, true));
            // List<Category> --> List<CategoryExcelVo>
            List<CategoryExcelVo> categoryExcelVos = categories.stream()
                    .map(category -> {
                        CategoryExcelVo categoryExcelVo = new CategoryExcelVo();
                        BeanUtils.copyProperties(category, categoryExcelVo);
                        return categoryExcelVo;
                    })
                    .toList();

            // 3 调用EasyExcel完成写操作
            EasyExcel.write(response.getOutputStream(), CategoryExcelVo.class)
                    .sheet("分类数据").doWrite(categoryExcelVos);
        } catch (Exception e) {
            throw new ServiceException(ResultCodeEnum.DATA_ERROR);
        }
    }
}
