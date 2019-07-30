<template>
    <el-card class="box-card">
        <el-col :span="8">
            <el-input style="width: 60%" v-model="code" placeholder="请输入名称或者URL"></el-input>
        </el-col>
        <el-col :span="8">
            <el-autocomplete
                    class="inline-input"
                    v-model="group"
                    :fetch-suggestions="querySearch"
                    placeholder="请输入分组"
                    @select="handleSelect"
            ></el-autocomplete>
        </el-col>

        <el-button type="primary" icon="el-icon-search">搜索</el-button>
        <el-table
                :data="tableData"
                style="width: 100%">
            <el-table-column label="URL">
                <template slot-scope="scope">
                    <el-button type="success" disabled>{{scope.row.method}}</el-button>
                    <span style="margin-left: 4px">{{scope.row.url}}</span>
                </template>
            </el-table-column>
            <el-table-column
                    prop="apiName"
                    label="名称"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="group"
                    label="分组">
            </el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-link :underline="false">
                        <i class="el-icon-edit" style="font-size: 16px"
                           @click="handleEdit(scope.$index, scope.row)"></i>
                    </el-link>
                    <el-link :underline="false">
                        <router-link :to="'/apiDetails?id='+scope.row.id">
                            <el-tooltip class="item" effect="dark" content="测试" placement="right-end">
                                <i class="el-icon-tickets" style="margin-left: 6px;font-size: 16px"></i>
                            </el-tooltip>
                        </router-link>
                    </el-link>
                    <el-link :underline="false">
                        <i @click="handleDelete(scope.$index, scope.row)" style="margin-left: 6px;font-size: 16px"
                           class="el-icon-delete"></i>
                    </el-link>
                </template>
            </el-table-column>
        </el-table>
    </el-card>
</template>

<script>
    export default {
        name: "ApiManager",
        data() {
            return {
                tableData: [{
                    id: 1,
                    url: '/message/have',
                    apiName: '置为已读',
                    group: '消息通知模块',
                    method: "POST"
                }],
                groups: [],
                group: '',
                code: ''
            }
        },
        created() {
            this.groups = [{"value": "默认分组"}]
        },
        methods: {
            handleDelete(index, row) {
                console.log(index, row);
            },
            querySearch(queryString, cb) {
                let groups = this.groups;
                let results = queryString ? groups.filter(this.createFilter(queryString)) : groups;
                // 调用 callback 返回建议列表的数据
                cb(results);
            },
            createFilter(queryString) {
                return (restaurant) => {
                    return (restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
                };
            },
            handleSelect(item) {
                console.log(item);
            }

        }
    }
</script>

<style scoped>

</style>