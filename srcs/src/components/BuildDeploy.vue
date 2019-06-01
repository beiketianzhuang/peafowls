<template>
    <div>
        <el-card class="box-card"  shadow="hover">
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
                        label="构建物版本"
                        width="140">
                </el-table-column>
                <el-table-column
                        label="构建状态">
                    <template slot-scope="scope">
                        <div v-if="scope.row.buildStatus == 'building'">
                            <span style="color:#409eff">
                                <a href="#">
                                    查看日志
                                </a>
                            </span>
                            <el-progress :percentage="scope.row.percentage"></el-progress>
                        </div>
                        <div v-else>
                            <el-badge is-dot class="item" :type="scope.row.badge">
                            </el-badge>
                            <span :style="{color:scope.row.color}">{{scope.row.statusCh}}</span>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="codeChange"
                        label="代码变更">
                </el-table-column>
                <el-table-column
                        prop="buildTime"
                        label="构建时间">
                </el-table-column>
                <el-table-column
                        fixed="right"
                        label="操作">
                    <template slot-scope="scope">
                        <div v-if="scope.row.buildStatus == 'building'">
                            <el-button type="text">
                                <span>取消构建</span>
                            </el-button>
                        </div>
                    </template>
                </el-table-column>
            </el-table>
        </el-card>
        <el-card class="box-card" style="margin-top: 20px">
            <div slot="header" class="clearfix">
                <span>部署</span>
                <el-button style="float: right; padding: 3px 0" type="text"  @click="selectDeployVersion" >立即部署</el-button>
                <el-button style="float: right; padding: 3px 10px" type="text"  @click="selectDeployVersion" >重启</el-button>
            </div>
            <el-card style="height: 300px" shadow="hover">
                <div slot="header" class="clearfix">
                    <span>运行状况</span>
                </div>
                <el-row :gutter="20">
                    <el-col :span="8">
                            <span>正在部署版本为<span style="color: green;font-size: 30px">#12</span>的包</span>
                    </el-col>
                    <el-col :span="8">
                        sdsf
                    </el-col>
                    <el-col :span="8">
                        <span>4版本部署日志</span>
                        <div style="background-color: black;color: white;">
                            <br>
                            部署开始<br>
                            停止服务成功<br>
                            删除旧包成功<br>
                            拷贝12版本的包成功<br>
                            启动服务成功<br>
                            <span style="color: red;">部署失败</span><br>
                            <br>
                        </div>
                    </el-col>
                </el-row>

            </el-card>
            <el-card style="margin-top: 20px" shadow="hover">
                <div slot="header" class="clearfix">
                    <span>部署历史</span>
                </div>
                <template>
                    <el-table
                            :data="tableData"
                            stripe
                            style="width: 100%">
                        <el-table-column
                                prop="version"
                                label="部署版本"
                                width="180">
                        </el-table-column>
                        <el-table-column
                                prop="version"
                                label="构建物版本">
                        </el-table-column>
                        <el-table-column
                                prop="name"
                                label="部署结果">
                        </el-table-column>
                        <el-table-column
                                prop="address"
                                label="部署时间">
                        </el-table-column>
                    </el-table>
                </template>
            </el-card>
        </el-card>
        <el-dialog style="height: 800px" title="选择部署版本" :visible.sync="dialogTableVisible">
            <div>
                <el-table
                        :data="deployData"
                        highlight-current-row
                        @current-change="handleCurrentChange"
                        style="width: 100%">
                    <el-table-column
                            prop="version"
                            label="版本"
                            width="150">
                    </el-table-column>
                    <el-table-column
                            prop="deployStatus"
                            label="状态"
                            min-width="150">
                        <template slot-scope="scope">
                            <div v-if="scope.row.deployStatus == 'deploying'">
                                <div style="float:left;" v-loading="loading">
                                    部署中...
                                </div>
                            </div>
                            <div v-else>
                                <el-button :type="scope.row.deployStatus" icon="el-icon-star-off" circle></el-button>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column
                            prop=""
                            label="选择"
                            max-width="20%">
                        <template slot-scope="scope">
                            <el-button :type="info" v-model="radixo" icon="el-icon-check" circle></el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="dialogVisible = false">立即部署</el-button>
              </span>
        </el-dialog>
    </div>

</template>

<script>
    export default {
        data() {
            return {
                websock:{},
                radio: '0',
                loading: true,
                dialogTableVisible:false,
                deployData:[{
                    version: '9',
                    deployStatus: 'success'
                }, {
                    version: '8',
                    deployStatus: 'success'
                }, {
                    version: '7',
                    deployStatus: 'danger'
                }, {
                    version: '6',
                    deployStatus: 'deploying'
                }],
                tableData: [{
                    buildStatus: 'building',
                    version: '4',
                    codeChange: '代码变更',
                    statusCh: '构建中',
                    badge: 'info',
                    color:'unknown',
                    percentage:80
                }, {
                    buildStatus: 'success',
                    version: '3',
                    codeChange: '代码变更',
                    statusCh: '构建成功',
                    badge: 'success',
                    color:'green',
                    percentage:100
                }, {
                    buildStatus: 'failed',
                    version: '2',
                    codeChange: '代码变更',
                    statusCh: '构建失败',
                    badge: 'danger',
                    color:'red',
                    percentage:100,
                    buildTime:10
                }, {
                    buildStatus: 'unstable',
                    version: '1',
                    codeChange: '代码变更',
                    statusCh: '构建不稳定',
                    badge: 'warning',
                    color:'#E6A23C',
                    percentage:100
                }]
            }
        },
        created() {
            this.initWebSocket()
        },
        methods: {
            selectDeployVersion() {

                this.dialogTableVisible = true
            },
            handleCurrentChange(val) {
                // this.currentRow = val;
                console.log(val);
            },
            initWebSocket() {
                // WebSocket与普通的请求所用协议有所不同，ws等同于http，wss等同于https
                this.websock = new WebSocket("ws://localhost:8082/myHandler");
                this.websock.onopen = this.websocketonopen;
                this.websock.onerror = this.websocketonerror;
                this.websock.onmessage = this.websocketonmessage;
                this.websock.onclose = this.websocketclose;
            },
            websocketonopen: function () {
                console.log("WebSocket连接成功");
                this.websock.send(JSON.stringify({"context":"demo"}));
            },
            websocketonerror: function (e) {
                console.log("WebSocket连接发生错误");
            },
            websocketonmessage: function (e) {
                var da = JSON.parse(e.data);
                console.log(da);
                this.message = da;
            },
            websocketclose: function (e) {
                console.log("connection closed (" + e.code + ")");
            }
        }
    }
</script>

<style scoped>
    body {
        margin: 0;
    }
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
        margin: auto;
    }

    .item {
        margin-top: 20px;
    }

    .el-row {
        margin-bottom: 20px;
    &:last-child {
         margin-bottom: 0;
     }
    }
    .el-col {
        border-radius: 4px;
    }
    .bg-purple-dark {
        background: #99a9bf;
    }
    .bg-purple {
        background: #d3dce6;
    }
    .bg-purple-light {
        background: #e5e9f2;
    }
    .grid-content {
        border-radius: 4px;
        min-height: 36px;
    }
    .row-bg {
        padding: 10px 0;
        background-color: #f9fafc;
    }
    /*.el-table td div{*/
        /*float: left;*/
    /*}*/
</style>
