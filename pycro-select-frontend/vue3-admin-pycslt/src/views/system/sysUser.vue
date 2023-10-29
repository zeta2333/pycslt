<template>
    <!---搜索表单-->
    <div class="search-div">
        <el-form label-width="70px" size="small">
            <el-row>
                <el-col :span="12">
                    <el-form-item label="关键字">
                        <el-input v-model="queryBo.keyword" style="width: 100%" placeholder="用户名"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="创建时间">
                        <el-date-picker v-model="createTimes" type="daterange" range-separator="To" start-placeholder="开始时间"
                            end-placeholder="结束时间" format="YYYY-MM-DD" value-format="YYYY-MM-DD" />
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row style="display:flex">
                <el-button type="primary" size="small" @click="searchSysUser">
                    搜索
                </el-button>
                <el-button size="small" @click="resetData">重置</el-button>
            </el-row>
        </el-form>


        <!--添加按钮-->
        <div class="tools-div">
            <el-button type="success" size="small" @click="addShow">添 加</el-button>
        </div>
        <!-- 弹框 -->
        <el-dialog v-model="dialogVisible" title="添加或修改" width="40%">
            <el-form label-width="120px">
                <el-form-item label="用户名">
                    <el-input v-model="sysUser.userName" />
                </el-form-item>
                <el-form-item v-if="sysUser.id == null" label="密码">
                    <el-input type="password" show-password v-model="sysUser.password" />
                </el-form-item>
                <el-form-item label="姓名">
                    <el-input v-model="sysUser.name" />
                </el-form-item>
                <el-form-item label="手机">
                    <el-input v-model="sysUser.phone" />
                </el-form-item>
                <el-form-item label="头像">
                    <el-upload class="avatar-uploader" action="http://localhost:6815/admin/system/fileUpload"
                        :show-file-list="false" :on-success="handleAvatarSuccess" :headers="headers">
                        <img v-if="sysUser.avatar" :src="sysUser.avatar" class="avatar" />
                        <el-icon v-else class="avatar-uploader-icon">
                            <Plus />
                        </el-icon>
                    </el-upload>
                </el-form-item>
                <el-form-item label="描述">
                    <el-input v-model="sysUser.description" />
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submit">提交</el-button>
                    <el-button @click="dialogVisible = false">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <!---数据表格-->
        <el-table v-loading="listLoading" :data="list" style="width: 100%">
            <el-table-column prop="userName" label="用户名" />
            <el-table-column prop="name" label="姓名" />
            <el-table-column prop="phone" label="手机" />
            <el-table-column prop="avatar" label="头像" #default="scope">
                <img :src="scope.row.avatar" width="50" />
            </el-table-column>
            <el-table-column prop="description" label="描述" />
            <el-table-column prop="status" label="状态" #default="scope">
                {{ scope.row.status == 1 ? '正常' : '停用' }}
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间" />
            <el-table-column label="操作" align="center" width="280" #default="scope">
                <el-button type="primary" size="small" @click="editShow(scope.row)">
                    修改
                </el-button>
                <el-button type="danger" size="small" @click="deleteById(scope.row)">
                    删除
                </el-button>
                <el-button type="warning" size="small" @click="showAssignRole(scope.row)">
                    分配角色
                </el-button>
            </el-table-column>
        </el-table>
        <el-dialog v-model="dialogRoleVisible" title="分配角色" width="40%">
            <el-form label-width="80px">
                <el-form-item label="用户名">
                    <el-input disabled :value="sysUser.userName"></el-input>
                </el-form-item>

                <el-form-item label="角色列表">
                    <el-checkbox-group v-model="userRoleIds">
                        <el-checkbox v-for="role in allRoles" :key="role.id" :label="role.id">
                            {{ role.roleName }}
                        </el-checkbox>
                    </el-checkbox-group>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="doAssign">提交</el-button>
                    <el-button @click="dialogRoleVisible = false">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
        <el-pagination v-model:current-page="pageParams.page" v-model:page-size="pageParams.limit"
            :page-sizes="[5, 10, 20, 50, 100]" @size-change="fetchData" @current-change="fetchData"
            layout="total, sizes, prev, pager, next" :total="total" />
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { DoAssignRoleToUser, PageQuery, saveSysUser, UpdateSysUser, DeleteSysUserById } from '@/api/system/sysUser';
import { ElMessage, ElMessageBox } from 'element-plus';
import { useApp } from '@/pinia/modules/app'
import { GetAllRoleList, GetAssignedRoleList } from '@/api/system/sysRole'
/////////////////用户分配角色
// 角色列表
const userRoleIds = ref([])
const allRoles = ref([])
const dialogRoleVisible = ref(false)
const showAssignRole = async (row) => {
    sysUser.value = { ...row }
    dialogRoleVisible.value = true
    // 获取所有角色
    const allRoleList = await GetAllRoleList()
    allRoles.value = allRoleList.data
    // 获取当前用户已分配的角色
    const assignedRoleList = await GetAssignedRoleList(row.id)
    userRoleIds.value = assignedRoleList.data
}

// 分配角色
const doAssign = async () => {
    let assignRoleBo = {
        userId: sysUser.value.id,
        roleIdList: userRoleIds.value
    }
    const { code, message } = await DoAssignRoleToUser(assignRoleBo)
    if (code === 200) {
        ElMessage.success(message)
        dialogRoleVisible.value = false
        fetchData()
    }
}
////////////////上传
const headers = {
    token: useApp().authorization.token     // 从pinia中获取token，在进行文件上传的时候将token设置到请求头中
}

// 图像上传成功以后的事件处理函数
const handleAvatarSuccess = (response, uploadFile) => {
    sysUser.value.avatar = response.data
}
////////////////用户删除
const deleteById = (row) => {
    ElMessageBox.confirm('此操作将永久删除该记录, 是否继续?', 'Warning', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
    }).then(async () => {
        const { code } = await DeleteSysUserById(row.id)
        if (code === 200) {
            ElMessage.success('删除成功')
            fetchData()
        }
    })
}
///////////////用户添加和修改
const dialogVisible = ref(false)

const sysUser = ref({
    id: "",
    userName: "",
    name: "",
    phone: "",
    password: "",
    description: "",
    avatar: ""
})

// 点击修改弹出框
const editShow = (row) => {
    sysUser.value = { ...row }
    dialogVisible.value = true
}
// 点击添加弹出框
const addShow = () => {
    sysUser.value = {}
    dialogVisible.value = true
}

// 提交方法
const submit = async () => {
    if (sysUser.value.id) { // 有id --> 修改
        const { code, message } = await UpdateSysUser(sysUser.value)
        if (code === 200) {
            // 关闭弹框
            dialogVisible.value = false
            // 提示成功
            ElMessage.success(message)
            // 刷新页面
            fetchData()
        } else {
            ElMessage.error(message)
        }
    } else { // 无id --> 添加
        const { code, message } = await saveSysUser(sysUser.value)
        if (code === 200) {
            // 关闭弹框
            dialogVisible.value = false
            // 提示成功
            ElMessage.success(message)
            // 刷新页面
            fetchData()
        } else {
            ElMessage.error(message)
        }
    }
}

////////////////用户列表
// 加载动画
const listLoading = ref(true)
// 表格数据模型
const list = ref([]);

// 分页条数据模型
const total = ref(0)

// 分页
const pageParams = ref({
    page: 1, // 当前页
    limit: 5 // 每页记录数
})

// 封装条件数据模型
const queryBo = ref({
    keyword: "", // 关键词
    createTimeBegin: "", // 开始时间
    createTimeEnd: "" // 结束时间
})
// 时间范围
const createTimes = ref([])
// 钩子函数
onMounted(() => {
    fetchData();
})
// 条件查询
const fetchData = async () => {
    // 获取开始时间和结束时间
    if (createTimes.value.length == 2) {
        console.log("时间赋值")
        queryBo.value.createTimeBegin = createTimes.value[0]
        queryBo.value.createTimeEnd = createTimes.value[1]
    }
    console.log(queryBo.value)
    const { data } = await PageQuery(
        pageParams.value.page,
        pageParams.value.limit,
        queryBo.value
    );
    listLoading.value = false
    list.value = data.records
    total.value = data.totalRow
}

// 搜索方法
const searchSysUser = () => {
    listLoading.value = true
    fetchData();
}
// 重置方法
const resetData = () => {
    listLoading.value = true
    queryBo.value.keyword = ""
    createTimes.value = ['', '']
    fetchData()
}

</script>

<style scoped>
.search-div {
    margin-bottom: 10px;
    padding: 10px;
    border: 1px solid #ebeef5;
    border-radius: 3px;
    background-color: #fff;
}

.tools-div {
    margin: 10px 0;
    padding: 10px;
    border: 1px solid #ebeef5;
    border-radius: 3px;
    background-color: #fff;
}

.avatar-uploader .avatar {
    width: 178px;
    height: 178px;
    display: block;
}

.avatar-uploader .el-upload {
    border: 1px dashed var(--el-border-color);
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
    border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    text-align: center;
}
</style>