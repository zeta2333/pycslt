import request from '@/utils/request'

const base_api = '/admin/product/categoryBrand'

// 分页列表
export const GetCategoryBrandPageList = (page, limit, searchObj) => {
    return request({
        url: `${base_api}/page/${page}/${limit}`,
        method: 'post',
        data: searchObj,
    })
}

// 保存信息
export const SaveCategoryBrand = categoryBrand => {
    return request({
        url: `${base_api}/save`,
        method: 'post',
        data: categoryBrand,
    })
}

//修改
export const UpdateCategoryBrand = categoryBrand => {
    return request({
        url: `${base_api}/update`,
        method: 'put',
        data: categoryBrand,
    })
}

//删除
export const DeleteCategoryBrandById = id => {
    return request({
        url: `${base_api}/remove/${id}`,
        method: 'delete',
    })
}