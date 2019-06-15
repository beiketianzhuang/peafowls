<template>
    <el-card class="box-card">
        <iframe frameborder="0" width="100%" height="800px" src="http://localhost:8082/spider"></iframe>
    </el-card>
</template>

<script>
    export default {
        name: "ContextMonitor"
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
        width: 100%;
        height: 800px;
        margin: auto;
    }
</style>