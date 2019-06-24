<template>
  <div id="app">
    <!--<span>发布平台</span>-->
    <el-container style="height: 900px">
      <el-aside style="background-color:#545c64"  width="200px">
        <el-col>
          <el-menu
                  default-active="2"
                  class="el-menu-vertical-demo"
                  background-color="#545c64"
                  text-color="#fff"
                  active-text-color="#ffd04b">
            <el-menu-item index="1">
              <!--<i class="el-icon-menu"></i>-->
              <span slot="title" style="font-size: 30px">发布平台</span>
            </el-menu-item>
            <el-menu-item index="2">
              <i class="el-icon-menu"></i>
              <router-link to="/" slot="title"><span >应用列表</span></router-link>
            </el-menu-item>
            <el-menu-item index="3">
              <i class="el-icon-menu"></i>
              <router-link to="/monitor" slot="title"><span >应用监控中心</span></router-link>
            </el-menu-item>
            <el-menu-item index="3">
              <i class="el-icon-menu"></i>
              <router-link to="/apiManager" slot="title"><span >API管理中心</span></router-link>
            </el-menu-item>
          </el-menu>
        </el-col>
      </el-aside>
      <el-container>
        <el-header style="background-color: white">
        </el-header>
        <el-main> <div style="height:100%">
          <router-view></router-view>
        </div></el-main>
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

</style>
