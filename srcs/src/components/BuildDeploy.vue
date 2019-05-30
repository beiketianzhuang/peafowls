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
                        prop="context"
                        label="应用"
                        width="180">
                </el-table-column>
                <el-table-column
                        label="构建状态"
                        width="240">
                    <template slot-scope="scope">
                        <el-progress :percentage="90" status="success"></el-progress>
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

    </div>

</template>

<script>
    export default {
        data() {
            return {
                tableData: [{
                    buildStatus: '2016-05-02',
                    context: 'devops',
                    codeChange: '代码变更'
                }, {
                    buildStatus: '2016-05-04',
                    context: 'devops',
                    codeChange: '代码变更'
                }, {
                    buildStatus: '2016-05-01',
                    context: 'devops',
                    codeChange: '代码变更'
                }, {
                    buildStatus: '2016-05-03',
                    context: 'devops',
                    codeChange: '代码变更'
                }]
            }
        },
        methods:{
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
</style>
