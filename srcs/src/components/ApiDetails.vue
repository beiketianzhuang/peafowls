<template>
    <div>
        <el-form :model="dynamicValidateForm" ref="dynamicValidateForm" label-width="100px" class="demo-dynamic">
            <el-card class="box-card">
                <div slot="header" class="clearfix">
                    <span>接口信息</span>
                    <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>
                </div>
            </el-card>
            <el-card class="box-card" style="margin-top: 15px">
                <div slot="header" class="clearfix">
                    <span>请求头</span>
                    <el-link :underline="false" style="float: right;font-size: 25px">
                        <i  @click="addHead" class="el-icon-circle-plus-outline"></i>
                    </el-link>
                </div>
                <el-form-item
                        v-for="(domain, index) in dynamicValidateForm.domains"
                        :key="domain.key"
                        :rules="{
      required: true, message: '域名不能为空', trigger: 'blur'
    }"
                >
                    <el-input v-model="domain.value"></el-input>
                    <el-button @click.prevent="removeDomain(domain)">删除</el-button>
                </el-form-item>
            </el-card>
            <el-card class="box-card" style="margin-top: 15px">
                <div slot="header" class="clearfix">
                    <span>请求参数</span>
                    <el-link :underline="false" style="float: right;font-size: 25px">
                        <i class="el-icon-circle-plus-outline"></i>
                    </el-link>
                </div>
            </el-card>
        </el-form>
    </div>

</template>

<script>
    export default {
        name: "ApiDetails",
        data() {
            return {
                dynamicValidateForm: {
                    domains: [{
                        value: ''
                    }],
                    email: ''
                }
            };
        },
        methods:{
            addHead() {
                this.dynamicValidateForm.domains.push({
                    value: '',
                    key: Date.now()
                });
            }
        }
    }
</script>

<style scoped>

</style>