import request from '@/utils/request'
const base_api = '/admin/system/sysUser'


// 分页查询
export const PageQuery = (pageNum, pageSize, queryBo) => {
    return request({
        url: `${base_api}/page/${pageNum}/${pageSize}`,
        method: 'post',
        data: queryBo,
    })
}

// 添加
export const saveSysUser = (sysUser) => {
    return request({
        url: `${base_api}/save`,
        method: 'post',
        data: sysUser
    })
}

// 修改
export const UpdateSysUser = (sysUser) => {
    return request({
        url: `${base_api}/update`,
        method: 'put',
        data: sysUser,
    })
}

// 删除
export const DeleteSysUserById = (userId) => {
    return request({
        url: `${base_api}/deleteById/${userId}`,
        method: 'delete',
    })
}

// 给用户分配角色请求
export const DoAssignRoleToUser = (assignRoleBo) => {
    return request({
        url: `${base_api}/doAssign`,
        method: 'post',
        data: assignRoleBo
    })
}