package usts.pycro.pycslt.product.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import usts.pycro.pycslt.enums.RedisKeyEnum;
import usts.pycro.pycslt.model.entity.product.Category;
import usts.pycro.pycslt.product.mapper.CategoryMapper;
import usts.pycro.pycslt.product.service.CategoryService;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static usts.pycro.pycslt.model.entity.product.table.CategoryTableDef.CATEGORY;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-20 12:37
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 获取分类的树形数据
     *
     * @return
     */
    @Override
    @Cacheable(value = "category", key = "'all'")
    public List<Category> findCategoryTree() {
        return mapper.selectListWithRelationsByQuery(query()
                .where(CATEGORY.PARENT_ID.eq(0)));
    }

    /**
     * 返回一级分类
     *
     * @return
     */
    @Override
    public List<Category> selectPrimaryCategory() {
        // 1 查询redis
        String primaryCategoryKey = RedisKeyEnum.PRIMARY_CATEGORY.getValue();
        String primaryCategoryJson = redisTemplate.opsForValue().get(primaryCategoryKey);
        // 2 如果redis存在数据，则直接返回
        if (StrUtil.isNotEmpty(primaryCategoryJson)) {
            return JSON.parseArray(primaryCategoryJson, Category.class);
        }
        // 3 如果redis不存在数据，则查询数据库，将数据保存到redis，再返回
        List<Category> categories = list(QueryWrapper.create()
                .where(CATEGORY.PARENT_ID.eq(0))
                .orderBy(CATEGORY.ID.asc()));
        redisTemplate.opsForValue().set(primaryCategoryKey, JSON.toJSONString(categories), 7, TimeUnit.DAYS);
        return categories;
    }
}
