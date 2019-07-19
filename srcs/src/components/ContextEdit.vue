<template>
    <el-card class="box-card">
        <div slot="header" class="clearfix">
            <span>应用更新</span>
        </div>
        <el-collapse v-model="activeNames" @change="handleChange">
            <el-collapse-item title="基本信息" name="1">
                <el-form :model="contextData" :rules="rules" ref="contextData" label-width="100px" class="demo-ruleForm">
                    <el-form-item label="应用名称:" prop="context">
                        <el-input style="width: 50%" v-model="contextData.context"></el-input>
                    </el-form-item>
                    <el-form-item label="job名称:" prop="jobName">
                        <el-input style="width: 50%" v-model="contextData.jobName"></el-input>
                    </el-form-item>
                    <el-form-item label="应用类型:" prop="contextType">
                        <el-select v-model="contextData.contextType" placeholder="请选择应用类型">
                            <el-option label="springboot应用" value="SPRINGBOOT"></el-option>
                            <el-option label="tomcat应用" value="TOMCAT"></el-option>
                            <el-option label="前端应用" value="WEB"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="所属部门" prop="department">
                        <el-select v-model="contextData.department" placeholder="请选择部门">
                            <el-option label="质量体系部" value="zhiliang"></el-option>
                        </el-select>
                    </el-form-item>
                </el-form>
            </el-collapse-item>
            <el-collapse-item title="部署策略" name="2">
               <el-form :model="contextData">
                   <el-form-item label="部署方式">
                       <el-radio-group v-model="contextData.deploymentStrategy">
                           <el-radio :label="0">滚动部署</el-radio>
                           <el-radio :label="1">删除重建</el-radio>
                       </el-radio-group>
                   </el-form-item>
               </el-form>
            </el-collapse-item>
            <el-collapse-item title="容器配置" name="3">
                <div>kubernetes模板配置</div>
                <editor v-model="contextData.kubernetesConfig" @init="editorInit" lang="yaml" theme="chrome" width="500" height="400"></editor>
            </el-collapse-item>
        </el-collapse>

        <div style="margin-top: 20px">
            <el-button @click="cancelContextEdit">取 消</el-button>
            <el-button type="primary" @click="saveConfiguration">保存</el-button>
        </div>
    </el-card>

</template>

<script>
    import axios from 'axios'

    export default {
        data() {
            return {
                activeNames: ['1'],
                contextData: {
                    deployType:"KUBERNETES",
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
                    sshLogin: {},
                    deploymentStrategy:0,
                    kubernetesConfig:""


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
            };
        },
        methods: {
            cancelContextEdit() {
                this.$router.push("/")
            },
            editorInit() {
                require('brace/mode/yaml');
                require('brace/theme/chrome');
            },
            handleChange(val) {
                console.log(val);
            },
            saveConfiguration() {
                console.log(this.contextData)
                axios.post('/contexts', this.contextData)
                    .then(resp=>{
                        // this.dialogFormVisible = false
                        console.log(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
                        this.$router.push("/")
                    })
                    .catch(function (error) {
                        this.$message({
                            showClose: true,
                            message: '添加应用失败',
                            type: 'error'
                        });
                    });
            }
        },
        components: {
            editor:require('vue2-ace-editor'),
        },
    }
</script>

<style scoped>
    .text {
        font-size: 14px;
    }

    .item {
        padding: 18px 0;
    }

    .box-card {
        width: 100%;
    }
</style>