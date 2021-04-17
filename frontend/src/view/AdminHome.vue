<template>
  <div class="layout">
    <el-container class="container">
      <el-aside width="200px" class="aside">
        <div class="head">
          <div>
            <img src="../assets/image/logo.png" alt="logo">
            <span>admin</span>
          </div>
        </div>
        <div class="line"/>
        <el-menu
          router
          default-active="1"
          collapse-transition="true"
          :unique-opened="true"
          class="el-menu-vertical-demo"
          @open="handleOpen"
          @close="handleClose"
          background-color="#222832"
          text-color="#fff"
          active-text-color="#ffd04b">

          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>基本信息管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/student">学生</el-menu-item>
              <el-menu-item index="/store">商户</el-menu-item>
              <el-menu-item index="/consume">消费数据</el-menu-item>
            </el-menu-item-group>
          </el-submenu>

          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>学校管理员</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/school">信息管理</el-menu-item>
            </el-menu-item-group>
          </el-submenu>

          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>设置</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/adminChangePassword">修改密码</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header>
          <AdminHeader/>
        </el-header>
        <el-main>
          <router-view/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import AdminHeader from "../components/AdminHeader";

export default {
  name: "AdminHome",
  data() {
    return {
      uniqueOpened: true,
    }
  },
  components: {
    AdminHeader
  },
  methods: {
    handleOpen(key, keyPath) {
      // console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      // console.log(key, keyPath);
    }
  }
}
</script>

<style scoped>
.el-menu-vertical-demo{
  text-align: left;
}
/*.el-header, .el-footer {*/
/*  background-color: #B3C0D1;*/
/*  color: #333;*/
/*  text-align: center;*/
/*  line-height: 60px;*/
/*}*/

/*.el-aside {*/
/*  background-color: #D3DCE6;*/
/*  color: #333;*/
/*  text-align: center;*/
/*  line-height: 200px;*/
/*}*/

/*.el-main {*/
/*  background-color: #E9EEF3;*/
/*  color: #333;*/
/*  text-align: center;*/
/*  line-height: 160px;*/
/*}*/

/*body > .el-container {*/
/*  margin-bottom: 40px;*/
/*}*/

/*.el-container:nth-child(5) .el-aside,*/
/*.el-container:nth-child(6) .el-aside {*/
/*  line-height: 260px;*/
/*}*/

/*.el-container:nth-child(7) .el-aside {*/
/*  line-height: 320px;*/
/*}*/
</style>

<style>
body {
  padding: 0;
  margin: 0;
  box-sizing: border-box;
}

.layout {
  min-height: 100vh;
  background-color: #ffffff;
}

.container {
  height: 100vh;
}

.aside {
  width: 200px;
  background-color: #222832;
  overflow: hidden;
  overflow-y: auto;
  -ms-overflow-style: none;
  overflow: -moz-scrollbars-none;
}

.aside::-webkit-scrollbar {
  display: none;
}

.head {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 50px;
}

.head > div {
  display: flex;
  align-items: center;
}

.head img {
  width: 40px;
  height: 40px;
  margin-right: 10px;
}

.head span {
  font-size: 20px;
  color: #ffffff;
}

.line {
  border-top: 1px solid hsla(0, 0%, 100%, .05);
  border-bottom: 1px solid rgba(0, 0, 0, .2);
}
</style>
