<template>
    <el-card class="box-card">
        <div class="block">
            <el-timeline>
                <el-timeline-item
                        v-for="(activity, index) in activities"
                        :key="index"
                        :icon="activity.icon"
                        :type="activity.type"
                        :color="activity.color"
                        :size="activity.size"
                        :timestamp="activity.timestamp">
                    {{activity.content}}
                </el-timeline-item>
            </el-timeline>
        </div>

    </el-card>

</template>

<script>
    export default {
        name: "ContextDeployment",
        data() {
            return {
                activities: [{
                    content: '部署开始',
                    timestamp: '2018-04-12 20:46',
                    color: '#0bbd87'
                }, {
                    content: 'Deployment开始',
                    timestamp: '2018-04-03 20:46',
                    color: '#0bbd87'
                }, {
                    content: '下载8版本的镜像开始',
                    timestamp: '2018-04-03 20:46',
                    color: '#0bbd87'
                }, {
                    content: '下载镜像完成',
                    timestamp: '2018-04-03 20:46',
                    color: '#0bbd87'
                }, {
                    content: '部署pod开始',
                    timestamp: '2018-04-03 20:46',
                    color: '#0bbd87'
                }, {
                    content: '部署pod成功',
                    timestamp: '2018-04-03 20:46',
                    color: '#0bbd87'
                }, {
                    content: '部署验证开始',
                    timestamp: '2018-04-03 20:46',
                    color: '#0bbd87'
                }, {
                    content: '部署验证成功',
                    timestamp: '2018-04-03 20:46',
                    color: '#0bbd87'
                }, {
                    content: '部署成功',
                    timestamp: '2018-04-03 20:46',
                    color: '#0bbd87'
                }]
            };
        }
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
        width: 480px;
        height: 300px;
    }
</style>