<template>
    <el-card class="box-card">
        <div slot="header" class="clearfix">
            <span style="float: left"><h4>应用列表</h4></span>
            <el-button style="float: right; padding: 3px 0" type="text" @click="addEvent">添加应用</el-button>
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
                            @click="handleEdit(scope.$index, scope.row)">编辑
                    </el-button>
                    <router-link :to="'/deploy?context='+scope.row.context">
                        <el-button size="mini">构建部署
                        </el-button>
                    </router-link>
                    <el-button
                            size="mini"
                            type="danger"
                            @click="handleDelete(scope.$index, scope.row)">删除
                    </el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog :title="title" :visible.sync="dialogFormVisible">
            <el-form :model="contextData" :rules="rules" ref="contextData" label-width="100px" class="demo-ruleForm">
                <el-form-item label="应用名称:" prop="context">
                    <el-input style="width: 50%" v-model="contextData.context"></el-input>
                </el-form-item>
                <el-form-item label="job名称:" prop="jobName">
                    <el-input style="width: 50%" v-model="contextData.jobName"></el-input>
                </el-form-item>
                <el-form-item label="应用类型:" prop="contextType">
                    <el-select v-model="contextData.contextType" placeholder="请选择应用类型">
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
                    <el-tabs>
                        <el-tab-pane label="密码登陆" name="first">
                            <el-form-item label="实例IP:">
                                <el-input style="width: 50%" v-model="contextData.passwordLogin.ip"></el-input>
                            </el-form-item>
                            <el-form-item label="端口:">
                                <el-input style="width: 50%" v-model="contextData.passwordLogin.port">22</el-input>
                            </el-form-item>
                            <el-form-item label="用户名:">
                                <el-input style="width: 50%" v-model="contextData.passwordLogin.username"></el-input>
                            </el-form-item>
                            <el-form-item label="密码:">
                                <el-input style="width: 50%" v-model="contextData.passwordLogin.password"
                                          type="password"></el-input>
                            </el-form-item>
                        </el-tab-pane>
                        <el-tab-pane label="密钥登陆" name="second"></el-tab-pane>
                    </el-tabs>
                </el-form-item>
                <el-form-item label="建物数量:" prop="buildCount">
                    <el-input style="width: 50%" v-model="contextData.buildCount"></el-input>
                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="addContext">确 定</el-button>
            </div>
        </el-dialog>
    </el-card>
</template>

<script>
    import axios from 'axios'

    export default {
        name: "ContextList",
        data() {
            return {
                labelPosition: 'right',
                contextData: {
                    context: '',
                    jobName: "",
                    contextType: '',
                    department: '',
                    buildCount: 5,
                    passwordLogin: {
                        ip: '',
                        port: 22,
                        username: '',
                        password: ''
                    },
                    sshLogin: {}
                },
                rules: {
                    context: [
                        {type: 'string', required: true, message: '请输入应用名称', trigger: 'blur'},
                        {min: 3, max: 100, message: '长度在 3 到 100 个字符', trigger: 'blur'}
                    ],
                    jobName: [
                        {type: 'string', required: true, message: '请输入job名称', trigger: 'blur'},
                        {min: 3, max: 100, message: '长度在 3 到 100 个字符', trigger: 'blur'}
                    ],
                    contextType: [
                        {type: 'string', required: true, message: '请选择应用类型', trigger: 'change'}
                    ],
                    department: [
                        {type: 'string', required: true, message: '请选择部门', trigger: 'change'}
                    ],
                    ip: [
                        {type: 'string', required: true, message: '请输入主机地址', trigger: 'change'}
                    ],
                    username: [
                        {type: 'string', required: true, message: '请输入用户名', trigger: 'change'}
                    ],
                    password: [
                        {type: 'string', required: true, message: '请输入密码', trigger: 'blur'}
                    ]
                },
                title: '',
                contexts: [{
                    context: 'demo',
                    contextType: 'spring boot应用',
                    name: '陈朗',
                    createdAt: '2019-06-04',
                    department: '质量体系部'
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
            buildDeploy(index, row) {

            },
            handleEdit(index, row) {
                console.log(index, row);
            },
            handleDelete(index, row) {
                console.log(index, row);
            },
            addEvent() {
                this.title = "新增";
                this.dialogFormVisible = true;
            },
            addContext() {
                axios.post('/contexts', this.contextData)
                    .then(function (response) {
                        this.dialogFormVisible = false
                    })
                    .catch(function (error) {
                        this.$message({
                            showClose: true,
                            message: '添加应用失败',
                            type: 'error'
                        });
                    });
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
        width: 100%;
        height: 800px;
        margin: auto;
    }
</style>