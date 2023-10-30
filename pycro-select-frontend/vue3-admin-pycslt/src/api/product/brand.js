import request from '@/utils/request'

const base_api = '/admin/product/brand'

// 分页列表
export const GetBrandPageList = (pageNum, pageSize) => {
    return request({
        url: `${base_api}/page/${pageNum}/${pageSize}`,
        method: 'get'
    })
}

// 保存品牌
export const SaveBrand = brand => {
    return request({
        url: `${base_api}/save`,
        method: 'post',
        data: brand,
    })
}

// 修改品牌
export const UpdateBrand = brand => {
    return request({
        url: `${base_api}/update`,
        method: 'put',
        data: brand,
    })
}

// 删除品牌
export const DeleteBrandById = id => {
    return request({
        url: `${base_api}/remove/${id}`,
        method: 'delete',
    })
}

// 查询所有的品牌数据
export const FindAllBrand = () => {
    return request({
      url: `${base_api}/findAll`,
      method: 'get',
    })
}