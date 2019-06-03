<template>
    <div>
        <el-card class="box-card" shadow="hover">
            <div slot="header" class="clearfix">
                <span>构建列表</span>
                <el-button style="float: right; padding: 3px 0" type="text">
                    <span @click="startBuild">
                        立即构建
                    </span>
                </el-button>
            </div>
            <el-table
                    :data="jenkinsBuilds"
                    style="width: 100%">
                <el-table-column
                        prop="version"
                        label="构建物版本"
                        width="140">
                </el-table-column>
                <el-table-column
                        label="构建状态">
                    <template slot-scope="scope">
                        <div v-if="scope.row.buildStatus == 'BUILDING'">
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
                        label="构建耗时（秒）"
                        :formatter="formatTime">
                </el-table-column>
                <el-table-column
                        prop="createdAt"
                        label="构建时间">
                </el-table-column>
                <el-table-column
                        fixed="right"
                        label="操作">
                    <template slot-scope="scope">
                        <div v-if="scope.row.buildStatus == 'BUILDING'">
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
                <el-button style="float: right; padding: 3px 0" type="text" @click="selectDeployVersion">立即部署
                </el-button>
                <el-button style="float: right; padding: 3px 10px" type="text" @click="selectDeployVersion">重启
                </el-button>
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
                            :data="deploymentHistories"
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
        <el-dialog style="height: 800px"  title="选择部署版本" :visible.sync="dialogTableVisible">
            <div>
                <el-table
                        ref="singleTable"
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
                            <div v-if="scope.row.showSelect">
                                <i style="font-size: 30px;color: green" class="el-icon-check"></i>
                            </div>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <span slot="footer" class="dialog-footer">
                <div>
                     <div v-if="deployVersion != null" style="float: left">
                        部署版本<span style="color: green;font-size: 30px">#{{deployVersion}}</span>
                     </div>
                     <el-button type="primary" @click="dialogVisible = false">立即部署</el-button>
                </div>
              </span>
        </el-dialog>
    </div>

</template>

<script>
    import axios from 'axios'

    export default {
        data() {
            return {
                websock: {},
                radio: '0',
                loading: true,
                dialogTableVisible: false,
                deployData: [{
                    version: '9',
                    deployStatus: 'success',
                    showSelect:false
                }, {
                    version: '8',
                    deployStatus: 'success',
                    showSelect:true
                }, {
                    version: '7',
                    deployStatus: 'danger',
                    showSelect:false
                }, {
                    version: '6',
                    deployStatus: 'deploying',
                    showSelect:false
                }],
                deployVersion:null,
                jenkinsBuilds: [],
                deploymentHistories: []
            }
        },
        created() {
            this.initWebSocket()
        },
        methods: {
            deploy() {

            },
            startBuild() {
                axios.defaults.baseURL = 'http://localhost:8082'
                axios.post('/jenkins/contexts/build/demo')
                    .then(function (response) {
                        console.log(response);
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
            },
            formatTime(row, column) {
                return row.buildTime / 1000;
            },
            selectDeployVersion() {

                this.dialogTableVisible = true
            },
            handleCurrentChange(val) {
                this.deployVersion = val.version;
                this.deployData.forEach(function (value, index) {
                    value.showSelect = false;
                });
                val.showSelect = true;
                console.log(val.version);
            },
            initWebSocket() {
                // WebSocket与普通的请求所用协议有所不同，ws等同于http，wss等同于https
                this.websock = new WebSocket("ws://localhost:8082/myHandler");
                this.websock.onopen = this.websocketonopen;
                this.websock.onerror = this.websocketonerror;
                this.websock.onmessage = this.websocketonmessage;
                this.websock.onclose = this.websocketclose;
            },
            websocketonopen() {
                let data = {"context": "demo"};
                this.websock.send(JSON.stringify(data));
            },
            websocketonerror(e) {
            },
            websocketonmessage(e) {
                let resp = JSON.parse(e.data);
                console.log(resp.jenkinsBuilds[0]);
                this.jenkinsBuilds = resp.jenkinsBuilds;
            },
            websocketclose(e) {
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

    &
    :last-child {
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
