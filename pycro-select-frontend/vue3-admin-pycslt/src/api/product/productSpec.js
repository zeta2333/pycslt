import request from '@/utils/request'

const base_api = '/admin/product/productSpec'

// 分页列表
export const GetProductSpecPageList = (page, limit) => {
    return request({
      url: `${base_api}/page/${page}/${limit}`,
      method: 'get'
    })
}

// 保存信息
export const SaveProductSpec = productSpec => {
    return request({
      url: `${base_api}/save`,
      method: 'post',
      data: productSpec,
    })
}

// 修改信息
export const UpdateProductSpecById = productSpec => {
    return request({
        url: `${base_api}/update`,
        method: 'put',
        data: productSpec,
    })
}

// 根据id删除数据
export const DeleteProductSpecById = id => {
    return request({
      url: `${base_api}/remove/${id}`,
      method: 'delete',
    })
}