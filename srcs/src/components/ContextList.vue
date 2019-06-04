<template>
    <el-card class="box-card">
        <div slot="header" class="clearfix">
            <span>应用列表</span>
            <el-button style="float: right; padding: 3px 0" type="text" @click="addContext">添加应用</el-button>
        </div>
        <el-table
                :data="contexts"
                style="width: 100%">
            <el-table-column
                    prop="context"
                    label="应用名"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="contextType"
                    label="应用类型"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="name"
                    label="创建人">
            </el-table-column>
            <el-table-column
                    prop="department"
                    label="所属部门">
            </el-table-column>
            <el-table-column
                    prop="createdAt"
                    label="创建时间">
            </el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button
                            size="mini"
                            @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                    <el-button
                            size="mini"
                            type="danger"
                            @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog :title="title" :visible.sync="dialogFormVisible">
            <el-form :model="contextData" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                <el-form-item label="应用名称:" prop="context">
                    <el-input style="width: 50%" v-model="contextData.context"></el-input>
                </el-form-item>
                <el-form-item label="job名称:" prop="context">
                    <el-input style="width: 50%" v-model="contextData.jobName"></el-input>
                </el-form-item>
                <el-form-item label="应用类型:" prop="region">
                    <el-select v-model="contextData.region" placeholder="请选择应用类型">
                        <el-option label="springboot应用" value="SPRING_BOOT"></el-option>
                        <el-option label="tomcat应用" value="TOMCAT"></el-option>
                        <el-option label="前端应用" value="WEB"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="所属部门" prop="department">
                    <el-select v-model="contextData.department" placeholder="请选择部门">
                        <el-option label="质量体系部" value="zhiliang"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="登录方式" prop="loginType">
                    <el-radio-group v-model="radio1">
                        <el-radio-button label="密码登录"></el-radio-button>
                        <el-radio-button label="密钥登录"></el-radio-button>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="实例IP:">
                    <el-input style="width: 50%" v-model="contextData.loginData.ip"></el-input>
                </el-form-item>
                <el-form-item label="端口:">
                    <el-input style="width: 50%" v-model="contextData.loginData.port">22</el-input>
                </el-form-item>
                <el-form-item label="用户名:">
                    <el-input style="width: 50%" v-model="contextData.loginData.username"></el-input>
                </el-form-item>
                <el-form-item label="密码:">
                    <el-input style="width: 50%" v-model="contextData.loginData.password" type="password"></el-input>
                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
            </div>
        </el-dialog>
    </el-card>
</template>

<script>
    export default {
        name: "ContextList",
        data() {
            return {
                labelPosition: 'right',
                radio1:"密码登录",
                contextData: {
                    context: '',
                    jobName:"",
                    contextType: '',
                    department: '',
                    date2: '',
                    delivery: false,
                    type: [],
                    resource: '',
                    desc: '',
                    loginData: {
                        name: '',
                        region: '',
                        type: ''
                    },
                },
                rules: {
                    context: [
                        { required: true, message: '请输入活动名称', trigger: 'blur' },
                        { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
                    ],
                    region: [
                        { required: true, message: '请选择活动区域', trigger: 'change' }
                    ],
                    date1: [
                        { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
                    ],
                    date2: [
                        { type: 'date', required: true, message: '请选择时间', trigger: 'change' }
                    ],
                    type: [
                        { type: 'array', required: true, message: '请至少选择一个活动性质', trigger: 'change' }
                    ],
                    resource: [
                        { required: true, message: '请选择活动资源', trigger: 'change' }
                    ],
                    desc: [
                        { required: true, message: '请填写活动形式', trigger: 'blur' }
                    ]
                },
                title:'',
                contexts: [{
                    context: 'demo',
                    contextType: 'spring boot应用',
                    name: '陈朗',
                    createdAt:'2019-06-04',
                    department:'质量体系部'
                }],
                gridData: [{
                    date: '2016-05-02',
                    name: '王小虎',
                    address: '上海市普陀区金沙江路 1518 弄'
                }, {
                    date: '2016-05-04',
                    name: '王小虎',
                    address: '上海市普陀区金沙江路 1518 弄'
                }, {
                    date: '2016-05-01',
                    name: '王小虎',
                    address: '上海市普陀区金沙江路 1518 弄'
                }, {
                    date: '2016-05-03',
                    name: '王小虎',
                    address: '上海市普陀区金沙江路 1518 弄'
                }],
                dialogTableVisible: false,
                dialogFormVisible: false,
                form: {
                    name: '',
                    region: '',
                    date1: '',
                    date2: '',
                    delivery: false,
                    type: [],
                    resource: '',
                    desc: ''
                },
                formLabelWidth: '120px'
            }
        },
        methods: {
            handleEdit(index, row) {
                console.log(index, row);
            },
            handleDelete(index, row) {
                console.log(index, row);
            },
            addContext() {
                this.title = "新增";
                this.dialogFormVisible = true;
            }
        }
    }
</script>

<style scoped>
    .text {
        font-size: 14px;
    }

    .item {
        margin-bottom: 18px;
    }

    .clearfix:before,
    .clearfix:after {
        display: table;
        content: "";
    }
    .clearfix:after {
        clear: both
    }

    .box-card {
        width: 90%;
    }
</style>