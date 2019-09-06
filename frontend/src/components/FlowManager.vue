<template>
    <div>
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <span>流程管理</span>
                <el-button style="float: right; padding: 3px 0" type="text" @click="dialogProcessorVisible = true">上传</el-button>
            </div>
            <el-table
                    :data="flowData"
                    style="width: 100%">
                <el-table-column
                        prop="date"
                        label="日期"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="name"
                        label="姓名"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="address"
                        label="详情">
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <div style="padding: 8px">
                            <i class="el-icon-edit"></i>
                            <i class="el-icon-delete"></i>
                        </div>

                    </template>
                </el-table-column>
            </el-table>
        </el-card>

        <el-dialog
                title="上传流程图"
                :visible.sync="dialogProcessorVisible"
                width="30%">
            <el-upload
                    class="upload-demo"
                    ref="uploadProcessFile"
                    :action="uploadProcessUrl"
                    :on-preview="handleFilePreview"
                    :on-remove="handleFileRemove"
                    :before-remove="beforeFileRemove"
                    multiple="true"
                    name="file"
                    :limit="1"
                    :on-exceed="handleFileExceed"
                    :file-list="fileList"
                    :auto-upload="false">
                <el-button size="small" type="primary">选择文件</el-button>
                <div slot="tip" class="el-upload__tip">只能上传xml/文件，且不超过500kb</div>
            </el-upload>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogProcessorVisible = false">取 消</el-button>
                <el-button type="primary" @click="deployProcess">部 署</el-button>
            </span>
        </el-dialog>

    </div>
</template>

<script>
    import axios from 'axios'

    export default {
        name: "FlowManager",
        data() {
            return {
                fileList: [],
                dialogProcessorVisible:false,
                baseUrl:axios.defaults.baseURL,
                flowData: [{
                    date: '2016-05-02',
                    name: '王小虎',
                    address: '上海市普陀区金沙江路 1518 弄'
                }, {
                    date: '2016-05-04',
                    name: '王小虎',
                    address: '上海市普陀区金沙江路 1517 弄'
                }, {
                    date: '2016-05-01',
                    name: '王小虎',
                    address: '上海市普陀区金沙江路 1519 弄'
                }, {
                    date: '2016-05-03',
                    name: '王小虎',
                    address: '上海市普陀区金沙江路 1516 弄'
                }]
            }
        },
        methods: {
            deployProcess() {
                this.$refs.uploadProcessFile.submit();
            },
            handleFileRemove(file, fileList) {
                console.log(file, fileList);
            },
            handleFilePreview(file) {
                console.log(file);
            },
            handleFileExceed(files, fileList) {
                this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
            },
            beforeFileRemove(file, fileList) {
                return this.$confirm(`确定移除 ${ file.name }？`);
            }
        },
        computed: {
            uploadProcessUrl() {
                return this.baseUrl+"/flowplus/process/deployment";
            }
        }
    }
</script>

<style scoped>

</style>