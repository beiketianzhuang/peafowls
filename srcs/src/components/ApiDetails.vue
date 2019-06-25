<template>
    <div>
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
                    <i class="el-icon-circle-plus-outline"></i>
                </el-link>
            </div>
        </el-card>
        <el-card class="box-card" style="margin-top: 15px">
            <div slot="header" class="clearfix">
                <span>请求参数</span>
                <el-link :underline="false" style="float: right;font-size: 25px">
                    <i class="el-icon-circle-plus-outline"></i>
                </el-link>
            </div>
        </el-card>
    </div>

</template>

<script>
    export default {
        name: "ApiDetails"
    }
</script>

<style scoped>

</style>