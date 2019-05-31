<template>
    <div>
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <span>构建列表</span>
                <el-button style="float: right; padding: 3px 0" type="text">
                    立即构建
                </el-button>
            </div>
            <el-table
                    :data="tableData"
                    style="width: 100%">
                <el-table-column
                        prop="version"
                        label="版本"
                        width="180">
                </el-table-column>
                <el-table-column
                        label="构建状态"
                        width="240">
                    <template slot-scope="scope">
                        <div v-if="scope.row.buildStatus == 'building'">
                            <span @click="dialogTableVisible = true" style="color:#409eff">
                                <a href="#">
                                    查看日志
                                </a>
                            </span>
                            <el-progress :percentage="scope.row.percentage" status=""></el-progress>
                        </div>
                        <div v-else>
                            <el-badge is-dot class="item" :type="scope.row.badge">
                            </el-badge>
                            <span :style="{color:scope.row.color}">{{scope.row.status}}</span>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="codeChange"
                        label="代码变更">
                </el-table-column>
                <el-table-column
                        fixed="right"
                        label="操作"
                        min-width="25%">
                    <template slot-scope="scope">
                        <el-button type="text">
                            <span>取消构建</span>
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-card>
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <span>部署</span>
                <el-button style="float: right; padding: 3px 0" type="text">立即部署</el-button>
            </div>

        </el-card>
        <el-dialog title="收货地址" :visible.sync="dialogTableVisible">
            <iframe  src="http://10.10.31.25:8080/job/sqcsbackend_prod/4/console">

            </iframe>
        </el-dialog>
    </div>

</template>

<script>
    export default {
        data() {
            return {
                dialogTableVisible:false,
                tableData: [{
                    buildStatus: 'building',
                    version: '4',
                    codeChange: '代码变更',
                    status: '构建中',
                    badge: 'info',
                    color:'unknown',
                    percentage:80
                }, {
                    buildStatus: 'success',
                    version: '3',
                    codeChange: '代码变更',
                    status: '构建成功',
                    badge: 'success',
                    color:'green',
                    percentage:100
                }, {
                    buildStatus: 'failed',
                    version: '2',
                    codeChange: '代码变更',
                    status: '构建失败',
                    badge: 'danger',
                    color:'red',
                    percentage:100
                }, {
                    buildStatus: 'unstable',
                    version: '1',
                    codeChange: '代码变更',
                    status: '构建不稳定',
                    badge: 'warning',
                    color:'#E6A23C',
                    percentage:100
                }]
            }
        },
        methods: {
            buildStatusFormat() {
                return "失败";
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
        width: 900px;
        margin: auto;
    }

    .item {
        margin-top: 20px;
    }
</style>
