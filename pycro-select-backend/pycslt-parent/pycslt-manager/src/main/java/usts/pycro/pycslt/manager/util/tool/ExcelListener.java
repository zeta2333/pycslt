package usts.pycro.pycslt.manager.util.tool;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import org.springframework.beans.BeanUtils;
import usts.pycro.pycslt.manager.product.mapper.CategoryMapper;
import usts.pycro.pycslt.model.entity.product.Category;

import java.util.Date;
import java.util.List;

/**
 * @author Pycro
 * @version 1.0
 * 2023-10-29 20:55
 */
public class ExcelListener<T> implements ReadListener<T> {

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    /**
     * 缓存的数据
     */
    private List<T> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    // 构造传递mapper，操作数据库
    private CategoryMapper categoryMapper;

    public ExcelListener(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }


    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        // 每读取到一条数据，就加到缓存列表中
        cachedDataList.add(t);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    /**
     * 保存数据
     */
    private void saveData() {
        cachedDataList.forEach(t -> {
            Category category = new Category();
            category.setCreateTime(new Date());
            category.setUpdateTime(new Date());
            BeanUtils.copyProperties(t, category);
            categoryMapper.insertWithPk(category);
        });
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 保存数据
        saveData();
    }
}
