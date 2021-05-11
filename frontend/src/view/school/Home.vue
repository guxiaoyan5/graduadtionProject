<template>
  <div class="" layout>
    <el-container class="container">
      <el-aside width="200px" class="aside">
        <div class="head">
          <div>
            <img src="../../assets/image/logo.png" alt="logo">
            <span>admin</span>
          </div>
        </div>
        <div class="line"/>
        <el-menu
          router
          default-active="3"
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
              <span>数据展示图</span>
            </template>
            <el-submenu index="1-1">
              <template slot="title">一日消费</template>
              <el-menu-item index="/oneDay">日(月)消费统计图</el-menu-item>
              <el-menu-item index="/oneDayTotal">消费总和统计图</el-menu-item>
              <!--              <el-menu-item index="/studentOneDay">学生数据统计图</el-menu-item>-->
            </el-submenu>
            <el-submenu index="1-2">
              <template slot="title">一日三餐</template>
              <el-menu-item index="/threeMeals">日(月)消费统计图</el-menu-item>
              <el-menu-item index="/threeMealsTotal">消费总和统计图</el-menu-item>
            </el-submenu>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>贫困生</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/analysis">贫困生信息</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-menu-item index="/collegeMonthCS">
            <i class="el-icon-setting"></i>
            <span slot="title">学院每月统计</span>
          </el-menu-item>
          <el-submenu index="12">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>设置</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/changePassword">修改密码</el-menu-item>
              <el-menu-item index="/changeName">修改昵称</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header>
          <Header/>
        </el-header>
        <el-main style="padding-top: 5px">
          <router-view/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import Header from '../../components/Header.vue'

export default {
  name: "SchoolHome",
  data() {
    return {
      uniqueOpened: true,
    }
  },
  components: {
    Header
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
.el-menu-vertical-demo {
  text-align: left;
}
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
