import request from '@/utils/request'
const base_api = '/admin/system/sysRole'


// 分页查询
export const PageQuery = (pageNum, pageSize, queryBo) => {
    return request({
        url: `${base_api}/page/${pageNum}/${pageSize}`,
        method: 'post',
        // 接口@RequestBody 前端 data: 名称  数据以json格式传输
        // 接口无注解 前端 params: 名称      数据以普通格式传输
        data: queryBo,
    })
}

// 添加
export const SaveSysRole = (sysRole) => {
    return request({
        url: `${base_api}/save`,
        method: 'post',
        data: sysRole,
    })
}

// 修改
export const UpdateSysRole = (sysRole) => {
    return request({
        url: `${base_api}/update`,
        method: 'put',
        data: sysRole,
    })
}

// 删除
export const DeleteSysRoleById = (roleId) => {
    return request({
        url: `${base_api}/deleteById/${roleId}`,
        method: 'delete',

    })
}