import request from '@/utils/request'

const base_api = '/admin/system/sysMenu'
// 分页列表
export const FindNodes = () => {
    return request({
        url: `${base_api}/findNodes`,
        method: 'get',
    })
}

// 保存信息
export const SaveMenu = sysMenu => {
    return request({
        url: `${base_api}/save`,
        method: 'post',
        data: sysMenu,
    })
}

// 修改信息
export const UpdateSysMenuById = sysMenu => {
    return request({
        url: `${base_api}/update`,
        method: 'put',
        data: sysMenu,
    })
}

// 根据id删除数据
export const RemoveSysMenuById = id => {
    return request({
        url: `${base_api}/removeById/${id}`,
        method: 'delete',
    })
}

// 查询角色分配的菜单id
export const GetSysRoleMenuIds = (roleId) => {
    return request({
        url: `${base_api}/findMenuIdsByRoleId/${roleId}`,
        method: 'get'
    })
}