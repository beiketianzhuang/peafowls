<template>
    <div id="app">
        <!--<span>发布平台</span>-->
        <el-container style="height: 900px">
            <el-aside style="background-color:#545c64" width="200px">
                <el-col>
                    <el-menu
                            default-active="2"
                            class="el-menu-vertical-demo"
                            background-color="#545c64"
                            text-color="#fff"
                            active-text-color="#ffd04b">
                        <el-menu-item index="1">
                            <!--<i class="el-icon-menu"></i>-->
                            <span slot="title" style="font-size: 30px">walle</span>
                        </el-menu-item>
                        <el-menu-item index="2">
                            <i class="el-icon-menu"></i>
                            <router-link to="/" slot="title"><span style="color: azure">应用列表</span></router-link>
                        </el-menu-item>
                        <el-menu-item index="3">
                            <i class="el-icon-menu"></i>
                            <router-link to="/monitor" slot="title"><span style="color: azure">应用监控中心</span>
                            </router-link>
                        </el-menu-item>
                        <el-menu-item index="4">
                            <i class="el-icon-menu"></i>
                            <router-link to="/apiManager" slot="title"><span style="color: azure">API管理中心</span>
                            </router-link>
                        </el-menu-item>
                        <el-menu-item index="5">
                            <i class="el-icon-menu"></i>
                            <router-link to="/apiManager" slot="title"><span style="color: azure">告警中心</span>
                            </router-link>
                        </el-menu-item>
                        <el-menu-item index="6">
                            <i class="el-icon-menu"></i>
                            <router-link to="/apiManager" slot="title"><span style="color: azure">日志管理</span>
                            </router-link>
                        </el-menu-item>
                        <el-menu-item index="7">
                            <i class="el-icon-menu"></i>
                            <router-link to="/apiManager" slot="title"><span style="color: azure">配置中心</span>
                            </router-link>
                        </el-menu-item>
                        <el-menu-item index="8">
                            <i class="el-icon-menu"></i>
                            <router-link to="/apiManager" slot="title"><span style="color: azure">调度中心</span>
                            </router-link>
                        </el-menu-item>
                        <el-submenu index="9">
                            <template slot="title">
                                <i class="el-icon-menu"></i>
                                <span>工单平台</span>
                            </template>
                            <el-menu-item-group>
                                <el-menu-item index="9-1">
                                    <router-link to="/flowManager" slot="title"><span style="color: azure">流程管理</span>
                                    </router-link>
                                </el-menu-item>
                                <el-menu-item index="9-2">发起工单</el-menu-item>
                            </el-menu-item-group>
                        </el-submenu>
                    </el-menu>
                </el-col>
            </el-aside>
            <el-container>
                <el-header style="background-color: white">
                </el-header>
                <el-main>
                    <div style="height:100%">
                        <router-view></router-view>
                    </div>
                </el-main>
                <!--<el-footer>Footer</el-footer>-->
            </el-container>
        </el-container>

    </div>
</template>

<script>

    export default {
        data() {
            return {
                activeIndex: '1',
                activeIndex2: '1'
            };
        },
        methods: {
            handleSelect(key, keyPath) {
                console.log(key, keyPath);
            }
        }
    }
</script>

<style>

    .el-main {
        background-color: #E9EEF3;
        color: #333;
        /*text-align: center;*/
    }

    .router-link-active {
        text-decoration: none;
    }

    a {
        text-decoration: none;
    }
</style>
